import sbtcrossproject.CrossPlugin.autoImport.crossProject

enablePlugins(GitVersioning)
enablePlugins(GitBranchPrompt)

git.uncommittedSignifier := Some("DIRTY")

scalaVersion := "2.13.1"

lazy val scalatime = crossProject(JSPlatform, JVMPlatform /*, NativePlatform*/).in(file("."))
  .settings(
    scalaVersion := "2.13.1",
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.1.1" % "test",
    organization := "no.skytteren",
    scalacOptions += "-deprecation",
  )
  .jvmSettings(
    fork in Test := true,
    showTiming in Test := true,
  )

lazy val scalatimeJS     = scalatime.js
lazy val scalatimeJVM    = scalatime.jvm
//lazy val barNative = bar.native
