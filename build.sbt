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
  tlBaseVersion := "2.0",
  organization := "org.typelevel",
  homepage := Some(url("http://www.squants.com/")),
  licenses := Seq("Apache 2.0" -> url("http://www.opensource.org/licenses/Apache-2.0")),
  tlCiReleaseBranches := Seq("master"),
  developers := List(
    Developer(
      "garyKeorkunian",
      "Gary Keorkunian",
      "unknown",
      url("http://www.linkedin.com/in/garykeorkunian")
    )
  ),
  crossScalaVersions := Versions.ScalaCross,
  githubWorkflowBuildMatrixExclusions +=
    MatrixExclude(Map("project" -> "rootNative", "scala" -> Versions.Scala3)),
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
    .enablePlugins(MdocPlugin, NoPublishPlugin)
    .settings(
      scalaVersion := Versions.Scala,
      mdocOut := (ThisBuild / baseDirectory).value,
      mdocAutoDependency := false,
    )

lazy val root = tlCrossRootProject
  .aggregate(squants)
  .settings(defaultSettings: _*)
