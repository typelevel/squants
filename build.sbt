import sbtcrossproject.{crossProject, CrossType}

lazy val defaultSettings =
  Project.defaultSettings ++
  Compiler.defaultSettings ++
  Publish.defaultSettings ++
  Tests.defaultSettings ++
  Formatting.defaultSettings ++
  Console.defaultSettings ++
  Docs.defaultSettings

lazy val squants = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .in(file("."))
  .settings(defaultSettings: _*)
  .jvmSettings(
    osgiSettings,
    tutTargetDirectory := file("."),
    tutSourceDirectory := file("shared") / "src" / "main" / "tut"
  )
  .jsSettings(
    parallelExecution in Test := false,
    excludeFilter in Test := "*Serializer.scala" || "*SerializerSpec.scala"
  )

lazy val root = project.in(file("."))
  .settings(defaultSettings: _*)
  .settings(
    name := "squants",
    publish := {},
    publishLocal := {},
    publishArtifact := false
  )
  .aggregate(squantsJVM, squantsJS, squantsNative)

lazy val squantsJVM = squants.jvm.enablePlugins(TutPlugin, SbtOsgi)
lazy val squantsJS = squants.js
lazy val squantsNative = squants.native
