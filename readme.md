# ja

`ja` is a runner for small, single file Java programs.

It has client-server architecture. Server maintains pool of on-the-fly compilers for faster compilation. Client just makes requests for compilation. This makes frequent compilation of simple Java programs faster, because `java` loads very slowly, so we just preload a bunch of compilers and feed them with source code when needed. Each compiler executes one program only to avoid memory leaks.

## Usage

    cat 'App.java' | ja

## Limitations

  * single file programs
  * `package` is not supported
  * obviously no support for build tools

## Notes

In principle and with modifications we can even evaluate Java expressions, becaue each subsequent compilation in compiler is very fast (down to 10ms!), and allows class redefinition (this surprised me), but it can be in theory leaky or have other problems, so in current implementation I avoid it.

## Links

  * http://javapracs.blogspot.de/2011/06/dynamic-in-memory-compilation-using.html
