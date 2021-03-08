import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

lazy val defaultSettings =
  Project.defaultSettings ++
  Compiler.defaultSettings ++
  Publish.defaultSettings ++
  Formatting.defaultSettings ++
  Console.defaultSettings ++
  Docs.defaultSettings

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
    _.enablePlugins(MdocPlugin, SbtOsgi)
  )
  .jvmSettings(
    osgiSettings,
    Test / parallelExecution := false,
  )
  .jvmSettings(Tests.defaultSettings: _*)
  .jsSettings(
    skip in test := isDotty.value,
    parallelExecution in Test := false,
    excludeFilter in Test := "*Serializer.scala" || "*SerializerSpec.scala",
    Test / sources := { if (isDotty.value) Seq() else (Test / sources).value }
  )
  .jsSettings(libraryDependencies ++= { if (isDotty.value) Seq() else Dependencies.scalaTest.value ++ Dependencies.scalaCheck.value})
  .nativeSettings(
    publish / skip := true,
    Compile / doc / sources := List(), // Can't build docs in native
    Compile / test / sources := List() // Can't yet compile in native
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
