lazy val defaultSettings =
  Project.defaultSettings ++
  Compiler.defaultSettings ++
  Publish.defaultSettings ++
  Tests.defaultSettings ++
  Formatting.defaultSettings ++
  Console.defaultSettings ++
  Docs.defaultSettings

lazy val squants = crossProject
  .crossType(CrossType.Full)
  .in(file("."))
  .settings(defaultSettings: _*)
  .jvmSettings(
    osgiSettings,
    tutSettings,
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
  .aggregate(squantsJVM, squantsJS)

lazy val squantsJVM = squants.jvm.enablePlugins(SbtOsgi)
lazy val squantsJS = squants.js
