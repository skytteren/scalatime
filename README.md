# ScalaTime - experiment

Playing around with a basic time API for Scala and Scala JS.
The java.time API works well on the JVM, but on JS platform it is rather limited without extra libraries.
This code is based on a need to have one basic library across the platforms. 

The scope of this library is to do basic date and time operations. 
That means that there is no support for location with respect to time zones - no day light saving.
Leap seconds are also out of scope (which it is for most libraries).

Please read the test to learn the API.

THIS CODE IS NOT PRODUCTION READY. USE WITH CARE!
