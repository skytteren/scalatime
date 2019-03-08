import sbtcrossproject.CrossPlugin.autoImport.crossProject

enablePlugins(GitVersioning)
enablePlugins(GitBranchPrompt)

git.uncommittedSignifier := Some("DIRTY")

lazy val scalatime = crossProject(JSPlatform, JVMPlatform /*, NativePlatform*/).in(file("."))
  .settings(
    scalaVersion := "2.12.7",
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.5" % "test",
    organization := "no.skytteren",
  )
  .jvmSettings(
    fork in Test := true,
    showTiming in Test := true,
  )

lazy val scalatimeJS     = scalatime.js
lazy val scalatimeJVM    = scalatime.jvm
//lazy val barNative = bar.native
