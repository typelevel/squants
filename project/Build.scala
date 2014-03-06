import sbt._

object Versions {
  val Squants = "0.1-SNAPSHOT"
  val Scala = "2.10.2"
  val ScalaTest = "2.0"
  val ScalaCheck = "1.11.3"
}

object Resolvers {
  val typeSafeRepo = "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
}

object Compiler {
  val defaultSettings = Seq()
}

object Publish {
  val defaultSettings = Seq()
}

object Tests {
  val defaultSettings = Seq()
}

object Formatting {
  import com.typesafe.sbt.SbtScalariform._

  lazy val defaultSettings = scalariformSettings ++ Seq(
    ScalariformKeys.preferences in Compile := defaultPreferences,
    ScalariformKeys.preferences in Test := defaultPreferences
  )

  val defaultPreferences = {
    import scalariform.formatter.preferences._
    FormattingPreferences()
      .setPreference(AlignParameters, false)
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(CompactControlReadability, true)
      .setPreference(CompactStringConcatenation, false)
      .setPreference(DoubleIndentClassDeclaration, true)
      .setPreference(FormatXml, true)
      .setPreference(IndentLocalDefs, false)
      .setPreference(IndentPackageBlocks, true)
      .setPreference(IndentSpaces, 2)
      .setPreference(MultilineScaladocCommentsStartOnFirstLine, false)
      .setPreference(PreserveSpaceBeforeArguments, false)
      .setPreference(PreserveDanglingCloseParenthesis, false)
      .setPreference(RewriteArrowSymbols, true)
      .setPreference(SpaceBeforeColon, false)
      .setPreference(SpaceInsideBrackets, false)
      .setPreference(SpacesWithinPatternBinders, true)
  }
}

object SquantsBuild extends Build {

  lazy val defaultSettings =
    Defaults.defaultSettings ++
      Compiler.defaultSettings ++
      Publish.defaultSettings ++
      Tests.defaultSettings ++
      Formatting.defaultSettings

  lazy val squants = Project (
    id = "Squants",
    base = file("."),
    settings = defaultSettings
  )
}

