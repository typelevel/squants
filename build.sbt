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
    tutSourceDirectory := file("shared") / "src" / "main" / "tut"
  )
  .jvmSettings(Tests.defaultSettings: _*)
  .jsSettings(Tests.defaultSettings: _*)
  .jsSettings(
    parallelExecution in Test := false,
    excludeFilter in Test := "*Serializer.scala" || "*SerializerSpec.scala",
    scalacOptions in Tut --= Seq("-Ywarn-unused-import", "-Ywarn-unused:imports"),
    sources in (Compile, test) := List() // This is a pity but we can't reliable compile on 1.0.0-M8
  )
  .nativeSettings(
    sources in (Compile, doc) := List() // Can't build docs in native
  )

lazy val root = project.in(file("."))
  .settings(defaultSettings: _*)
  .settings(
    name := "squants",
    publish := {},
    publishLocal := {},
    useGpg := true
  )
  .aggregate(squants.jvm, squants.js, squants.native)
