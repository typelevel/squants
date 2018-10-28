import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

lazy val defaultSettings =
  Project.defaultSettings ++
  Compiler.defaultSettings ++
  Publish.defaultSettings ++
  Formatting.defaultSettings ++
  Console.defaultSettings ++
  Docs.defaultSettings

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
    tutTargetDirectory := file("."),
    tutSourceDirectory := file("shared") / "src" / "main" / "tut"
  )
  .jvmSettings(Tests.defaultSettings: _*)
  .jsSettings(
    parallelExecution in Test := false,
    excludeFilter in Test := "*Serializer.scala" || "*SerializerSpec.scala"
  )
  .jsSettings(Tests.defaultSettings: _*)

lazy val root = project.in(file("."))
  .settings(defaultSettings: _*)
  .settings(
    name := "squants",
    publish := {},
    publishLocal := {},
    publishArtifact := false
  )
  .aggregate(squants.jvm, squants.js, squants.native)
