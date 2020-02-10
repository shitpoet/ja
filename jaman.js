const port = 5404
const maxCodeLen = 32768
const poolSize = 10
//const idleWarmUp = 15*60*1000 // every 15 mins

const http = require('http')
const fs = require('fs')
const { spawn } = require('child_process');

let maxPoolSize = 0

let pool = {
}

let nextChildId = 0

function createChild(id, warmup) {
  //log(`creating child process ${id}`)
  const javaOpts = '-Xmx256m'
  const cmd = `java ${javaOpts} ja.java ${warmup ? ' warmup' : ''}`
  let child = {
    state: 'starting',
    output: ''
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
    //process.stdout.write(`stdout: ${data}`);
    let s = '' + data

    if (child.state != 'ready') {
      child.state = 'ready'
      //log(`child ${id} ready`)
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
    //process.stdout.write(`stderr: ${data}`);
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

function populatePool(warmup) {
  //log('populate runners pool')
  let n = getPoolSize()
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
        populatePool(true)
      }, 5000)
    } else { // already working for long time
      populatePool(true)
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
  //console.log(request.url)
  if (request.method == 'POST') {
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
  } else {
    response.end('hi from ja)')
  }
}

const server = http.createServer(requestHandler)

server.listen(port, (err) => {
  if (err) {
    return console.log('something bad happened', err)
  }
  //console.log(`server is listening on ${port}`)
  populatePool(false)
})

