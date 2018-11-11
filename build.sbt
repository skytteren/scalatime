import sbtcrossproject.CrossPlugin.autoImport.crossProject


lazy val root = crossProject(JSPlatform, JVMPlatform /*, NativePlatform*/).in(file("."))
  .settings(
    scalaVersion := "2.12.7",
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.5" % "test",
  )
  .jvmSettings(
    fork in Test := true,
    showTiming in Test := true,
  )

lazy val JS     = root.js
lazy val JVM    = root.jvm
//lazy val barNative = bar.native
