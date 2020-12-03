# ScalaTime - experiment

[![TravisCI](https://travis-ci.org/skytteren/scalatime.svg?branch=master)](https://travis-ci.org/skytteren/scalatime)
![CI](https://github.com/skytteren/scalatime/workflows/CI/badge.svg)

Playing around with a basic time API for Scala and Scala JS.
The java.time API works well on the JVM, but on JS platform it is rather limited without extra libraries.
This code is based on a need to have one basic library across the platforms. 

The scope of this library is to do basic date and time operations. 
That means that there is no support for location with respect to time zones - no day light saving.
Leap seconds are also out of scope (which it is for most libraries).

Please read the test to learn the API.

THIS CODE IS NOT PRODUCTION READY. USE WITH CARE!

To improve stacktraces in Scala.js `npm install source-map-support`
