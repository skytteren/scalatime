import sbtcrossproject.CrossPlugin.autoImport.crossProject

enablePlugins(GitVersioning)
enablePlugins(GitBranchPrompt)

git.uncommittedSignifier := Some("DIRTY")

ThisBuild / scalaVersion := "0.27.0-RC1"

lazy val scalatime = crossProject(JSPlatform, JVMPlatform /*, NativePlatform*/).in(file("."))
  .settings(
    libraryDependencies += ("org.scalatest" %%% "scalatest" % "3.1.1" % "test").withDottyCompat(scalaVersion.value),
    organization := "no.skytteren",
    scalacOptions += "-deprecation",
    scalacOptions ++= { if (isDotty.value) Seq("-source:3.0-migration") else Nil },
  )
  .jvmSettings(
    fork in Test := true,
    showTiming in Test := true,
  )

lazy val scalatimeJS     = scalatime.js
lazy val scalatimeJVM    = scalatime.jvm
//lazy val barNative = bar.native
