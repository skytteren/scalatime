import sbtcrossproject.CrossPlugin.autoImport.crossProject

enablePlugins(GitVersioning)
enablePlugins(GitBranchPrompt)

git.uncommittedSignifier := Some("DIRTY")

ThisBuild / scalaVersion := "3.3.1"

lazy val scalatime = crossProject(JSPlatform, JVMPlatform /*, NativePlatform*/).in(file("."))
  .settings(
    libraryDependencies += ("org.scalatest" %%% "scalatest" % "3.2.12" % "test"),
    organization := "no.skytteren",
    scalacOptions += "-deprecation",
    scalacOptions ++= Seq(
      "-source:3.0-migration",
      "-rewrite"
    )
  )
  .jvmSettings(
    Test / fork := true,
  )

lazy val scalatimeJS = scalatime.js
lazy val scalatimeJVM = scalatime.jvm
//lazy val barNative = bar.native
