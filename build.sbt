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
  tlBaseVersion := "1.8",
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
  tlVersionIntroduced := Map("3" -> "1.8.3")
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
    tlVersionIntroduced ++= List("2.12", "2.13").map(_ -> "1.6.0").toMap,
  )
  .nativeSettings(
    crossScalaVersions := Versions.ScalaCross.filterNot(_.startsWith("3")),
    Compile / doc / sources := List(), // Can't build docs in native
    tlVersionIntroduced := List("2.12", "2.13").map(_ -> "1.7.2").toMap,
  )

lazy val docs =
  project.in(file("squants-docs"))
    .dependsOn(squants.jvm)
    .enablePlugins(MdocPlugin)
    .settings(
      scalaVersion := "2.13.7",
      mdocOut := (ThisBuild / baseDirectory).value,
      mdocAutoDependency := false,
    )

lazy val root = tlCrossRootProject
  .aggregate(squants)
  .settings(defaultSettings: _*)
