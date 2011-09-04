
This is a sandbox for experimenting with [Lift](http://liftweb.net/).

It is forked from [simply\_lift](https://github.com/dpp/simply_lift/).

Building
========

To build and test the application,

    ./sbt update `~jetty-restart`

You can now access it at [http://localhost:8080/](http://localhost:8080/).

`sbt` will watch for source changes and rebuild AND restart `jetty`, so you can
just leave this command running while you work. It's like magic.

