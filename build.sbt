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
    scalacOptions in Tut --= Seq("-Ywarn-unused-import", "-Ywarn-unused:imports"),
    tutTargetDirectory := file("."),
    tutSourceDirectory := file("shared") / "src" / "main" / "tut",
    parallelExecution in Test := false,
    skip.in(publish) := customScalaJSVersion.isDefined
  )
  .jvmSettings(Tests.defaultSettings: _*)
  .jsSettings(
    parallelExecution in Test := false,
    excludeFilter in Test := "*Serializer.scala" || "*SerializerSpec.scala",
    scalacOptions in Tut --= Seq("-Ywarn-unused-import", "-Ywarn-unused:imports"),
  )
  .jsSettings(Tests.defaultSettings: _*)
  .nativeSettings(
    skip in publish := true,
    sources in (Compile, doc) := List(), // Can't build docs in native
    sources in (Compile, test) := List() // Can't yet compile in native
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
  skip in publish := true
)
