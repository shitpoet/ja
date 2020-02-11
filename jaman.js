const port = 5404
const maxCodeLen = 32768
const poolSize = 10
//const poolSize = 3
//const idleWarmUpInterval = 15*60*1000 // every 15 mins
//const idleWarmUpInterval = 10*1000

const http = require('http')
const fs = require('fs')
const { spawn } = require('child_process')
const { performance } = require('perf_hooks')

let maxPoolSize = 0
let lastRequestTime = performance.now()
//let timer = null
//let timerInterval = idleWarmUpInterval / 4
let debug = false

let pool = {
}

let nextChildId = 0

let now = performance.now

function log(...args) {
  if (debug) console.log(...args)
}

function createChild(id, warmup) {
  //log(`creating child process ${id}`)
  const javaOpts = '-Xmx256m'
  //const cmd = `nice -n 10 java ${javaOpts} ja.java ${warmup ? ' warmup' : ''}`
  const cmd = `java ${javaOpts} ja.java ${warmup ? ' warmup' : ''}`
  let child = {
    state: 'starting', // starting error ready running finished
    output: '',
    lastWarmUp: now()
  }
  let p = spawn(cmd, {
    shell: true
  })
  child.process = p
  p.on('error', (err) => {
    console.error('failed to start subprocess');
    child.output += err;
    child.state = 'error'
  });
  let stdoutCharsRead = 0
  p.stdout.on('data', (data) => {
    if (debug) process.stdout.write(`stdout: ${data}`);
    let s = '' + data

    if (child.state != 'ready') {
      //log(`child ${id} ready`)
      //log(stdoutCharsRead)
      let n = s.length
      if (stdoutCharsRead + n >= 'READY'.length - 1) {
        //spawn('renice -n -0 -p ' + p.pid)
        child.state = 'ready'
        //log(`child ${id} ready`)
      }
      if (stdoutCharsRead >= 'READY'.length) {
        child.output += s
      } else {
        child.output += s.slice('READY'.length - stdoutCharsRead)
      }
      stdoutCharsRead += s.length
    } else {
      child.output += data
    }
  });
  p.stderr.on('data', (data) => {
    if (debug) process.stdout.write(`stderr: ${data}`);
    //if (child.state == 'ready') {
    child.output += data
    //}
  });
  /*process.on('close', (code) => {
    log(`child process closed with code ${code}`);
    child.state = 'finished'
    if child.doneCallback
      doneCallback(codde)
  });*/
  p.on('exit', (code) => {
    //log(`child process exited with code ${code}`);
    child.state = 'finished'
    if (child.doneCallback) {
      child.doneCallback(child.output)
    } else {
      log('no done callback register for child!')
    }
  });
  return child
}

function getPoolSize() {
  return Object.keys(pool).length
}

function haveStartingChild() {
  let n = getPoolSize()
  for (let i = n; i < n; i++) {
    let s = pool[i].state
    if (s == 'starting') {
      return true;
    }
  }
  return false;
}

/*function warmUpChild(child) {
  if (child.state == 'ready') {
    log('additional warmup for child ' + child.pid)
    child.warmup = true
    child.process.stdin.write('WARMUP')
    child.process.stdin.write(warmUpCode)
    child.process.stdin.flush()
  }
}*/

/*function idleWarmUp() {
  let t = now()
  for (var child of pool) {
    if (child.state == 'ready' && t - child.lastWarmUp >= idleWarmUpInterval) {
      child.lastWarmUp = t
      warmUpChild(child)
    }
    break
  }
}*/

/*function startTimer() {
  log('start timer')
  timer = setInterval(function() {
    var t = performance.now()
    if (t - lastRequestTime > idleWarmUpInterval) {
      idleWarmUp()
    }
  }, timerInterval)
}*/

function populatePool(warmup, cb) {
  //log('populate runners pool')
  let n = getPoolSize()
  if (haveStartingChild()) {
    // do not start multiple childs at once
    setTimeout(function() {
      //log('waiting for running child to finish (pool size '+n+')')
      populatePool(true, cb)
    }, 100)
  } else {
    for (let i = n; i < poolSize; i++) {
      pool[nextChildId] = createChild(nextChildId, warmup);
      nextChildId++;
      n++;
      if (n > maxPoolSize) maxPoolSize = n
      break;
    }
    if (n < poolSize) {
      if (maxPoolSize < poolSize) { // still loading
        setTimeout(function() {
          populatePool(true, cb)
        }, debug ? 100 : 5000)
      } else { // already working for long time
        populatePool(true)
      }
    } else {
      //if (!timer) startTimer()
      if (cb) cb()
    }
  }
}

function findReadyChild(cb) {
  let n = getPoolSize()
  for (let id in pool)
    if (pool[id].state == 'ready') {
      //log('found ready child')
      cb(id)
      return;
    }
  //log('jaman: no ready childs yet, retry...')
  setTimeout(function() {
    findReadyChild(cb)
  }, 50)
}

function maintancePool() {
  let n = getPoolSize()
  let delList = []
  for (let id in pool) {
    let state = pool[id].state
    if (state == 'finished' || state == 'error') {
      delList.push(id)
    }
  }
  for (let id of delList) {
    delete pool[id]
  }
  populatePool(true)
}

function runCode(code, cb) {
  //log('run code')
  findReadyChild(function(id) {
    let child = pool[id]
    delete pool[id]
    child.state = 'running'
    child.doneCallback = cb
    child.process.stdin.end(code)
    maintancePool()
  })
}

const requestHandler = (request, response) => {
  //console.log(request.method, request.url)
  lastRequestTime = performance.now()
  if (request.method == 'GET') {
    response.end(poolSize == maxPoolSize ? 'ready' : 'starting')
  } else if (request.method == 'POST') {
    var body = '';

    request.on('data', function (data) {
      body += data;
      // too much POST data, kill the connection!
      if (body.length > maxCodeLen) {
        //log('too much data ' + body.length)
        response.end('too much data - ' + Math.round(body.length / 1024)+'k');
        request.connection.destroy();
      }
    });

    request.on('end', function () {
      //console.log('recv code to execute: ' + body)
      runCode(body, (output) => {
        //log('send output to client')
        response.end(output);
      })
    });
  } /*else {
    response.end('jaman')
  }*/
}

const server = http.createServer(requestHandler)

populatePool(true, function() {
})

server.listen(port, (err) => {
  if (err) {
    return console.log('something bad happened', err)
  }
  //console.log(`server is listening on ${port}`)
})

debug = debug || process.argv.length >= 2 && process.argv.slice(1).includes('debug')
