import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

lazy val defaultSettings =
  Project.defaultSettings ++
  Compiler.defaultSettings ++
  Publish.defaultSettings ++
  Formatting.defaultSettings ++
  Console.defaultSettings ++
  Docs.defaultSettings ++
  Tests.defaultSettings

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / turbo := true

inThisBuild(List(
  organization := "org.typelevel",
  homepage := Some(url("http://www.squants.com/")),
  licenses := Seq("Apache 2.0" -> url("http://www.opensource.org/licenses/Apache-2.0")),
  developers := List(
    Developer(
      "garyKeorkunian",
      "Gary Keorkunian",
      "unknown",
      url("http://www.linkedin.com/in/garykeorkunian")
    )
  )
))

lazy val squants =
  crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .in(file("."))
  .settings(defaultSettings: _*)
  .jvmConfigure(
    _.enablePlugins(SbtOsgi)
  )
  .jvmSettings(
    osgiSettings,
    Test / parallelExecution := false,
  )
  .jvmSettings(Tests.defaultSettings: _*)
  .jsSettings(
    Test / parallelExecution := false,
    Test / excludeFilter := "*Serializer.scala" || "*SerializerSpec.scala",
  )
  .nativeSettings(
    crossScalaVersions := Versions.ScalaCross.filterNot(_.startsWith("3")),
    Compile / doc / sources := List(), // Can't build docs in native
  )

lazy val docs =
  project.in(file("squants-docs"))
    .dependsOn(squants.jvm)
    .enablePlugins(MdocPlugin)
    .settings(
      scalaVersion := "2.13.6",
      mdocOut := (ThisBuild / baseDirectory).value
      mdocAutoDependency := false,
    )

lazy val root = project.in(file("."))
  .settings(defaultSettings: _*)
  .settings(noPublishSettings)
  .settings(
    name := "squants",
  )
  .aggregate(squants.jvm, squants.js, squants.native)

lazy val noPublishSettings = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false,
  publish / skip := true
)
