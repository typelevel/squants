import sbtcrossproject.{CrossType, crossProject}

import scala.scalanative.optimizer.pass
import scala.scalanative.sbtplugin.ScalaNativePluginInternal.{NativeTest, nativeConfig, nativeOptimizerDriver}
import scala.scalanative.tools

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
    tutSettings,
    tutTargetDirectory := file("."),
    tutSourceDirectory := file("shared") / "src" / "main" / "tut"
  )
  .jsSettings(
    parallelExecution in Test := false,
    excludeFilter in Test := "*Serializer.scala" || "*SerializerSpec.scala"
  ).nativeSettings(
    excludeFilter in Test := "*Serializer.scala" || "*SerializerSpec.scala" || "QuantitySpec.scala" || "MoneySpec.scala",
    nativeOptimizerDriver in NativeTest := {
      val orig = tools.OptimizerDriver((nativeConfig in NativeTest).value)
      orig.withPasses(orig.passes.filterNot(_ == pass.GlobalBoxingElimination))
    }
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

lazy val squantsJVM = squants.jvm.enablePlugins(SbtOsgi)
lazy val squantsJS = squants.js
lazy val squantsNative = squants.native
