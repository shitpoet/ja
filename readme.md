# ja

`ja` is a runner for small, single file Java programs.

It has client-server architecture. Server maintains pool of on-the-fly compilers for faster compilation. Client just makes requests for compilation. This makes frequent compilation of simple Java programs faster, because `java` loads very slowly, so we just preload a bunch of compilers and feed them with source code when needed. Each compiler executes one program only to avoid memory leaks.

Usage:

    cat 'App.java' | ja

