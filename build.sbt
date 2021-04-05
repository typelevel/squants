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

val customScalaJSVersion = Option(System.getenv("SCALAJS_VERSION"))

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
    _.enablePlugins(TutPlugin, SbtOsgi)
  )
  .jvmSettings(
    osgiSettings,
    Tut / scalacOptions --= Seq("-Ywarn-unused-import", "-Ywarn-unused:imports"),
    tutTargetDirectory := file("."),
    tutSourceDirectory := file("shared") / "src" / "main" / "tut",
    Test / parallelExecution := false,
    publish / skip := customScalaJSVersion.isDefined
  )
  .jvmSettings(Tests.defaultSettings: _*)
  .jsSettings(
    Test / parallelExecution := false,
    Test / excludeFilter := "*Serializer.scala" || "*SerializerSpec.scala",
    Tut / scalacOptions --= Seq("-Ywarn-unused-import", "-Ywarn-unused:imports"),
  )
  .jsSettings(Tests.defaultSettings: _*)
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
