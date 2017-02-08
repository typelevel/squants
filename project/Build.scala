import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._
import com.typesafe.sbt.osgi.SbtOsgi
import com.typesafe.sbt.osgi.SbtOsgi.autoImport._

object Versions {
  val Squants = "1.2.0-SNAPSHOT"
  val Scala = "2.11.8"
  val ScalaCross = Seq("2.12.1", "2.11.8", "2.10.6")

  val ScalaTest = "3.0.1"
  val ScalaCheck = "1.13.4"
  val Json4s = "3.5.0"
}

object Dependencies {
  val scalaTest = Def.setting(Seq("org.scalatest" %%% "scalatest" % Versions.ScalaTest % Test))
  val scalaCheck = Def.setting(Seq("org.scalacheck" %%% "scalacheck" % Versions.ScalaCheck % Test))
  val json4s = Def.setting(Seq("org.json4s" %% "json4s-native" % Versions.Json4s % Test))
}

object Resolvers {
  val typeSafeRepo = "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
  val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  val sonatypeNexusReleases = "Sonatype Nexus Releases" at "https://oss.sonatype.org/content/repositories/releases"
  val sonatypeNexusStaging = "Sonatype Nexus Staging" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
}

object Project {
  val defaultSettings = Seq(
    organization in ThisBuild := "org.typelevel",

    name := "Squants",

    version in ThisBuild := Versions.Squants,

    licenses := Seq("Apache 2.0" -> url("http://www.opensource.org/licenses/Apache-2.0")),

    homepage := Some(url("http://www.squants.com/")),

    autoAPIMappings := true,

    resolvers ++= Seq(
        Resolvers.typeSafeRepo,
        Resolvers.sonatypeNexusSnapshots,
        Resolvers.sonatypeNexusReleases,
        Resolvers.sonatypeNexusStaging
    ),

    OsgiKeys.exportPackage := Seq("squants.*"),

    OsgiKeys.privatePackage := Seq() // No private packages
  )
}

object Compiler {
  val defaultSettings = Seq(
    scalacOptions in ThisBuild ++= Seq(
      "-feature",
      "-deprecation",
      "-encoding", "UTF-8",       // yes, this is 2 args
      "-Xfatal-warnings"
    ),

    scalaVersion in ThisBuild := Versions.Scala,

    crossScalaVersions := Versions.ScalaCross
  )
}
object Publish {
  val defaultSettings = Seq(
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },

    publishMavenStyle := true,

    publishArtifact in Test := false,

    pomIncludeRepository := { _ => false },

    pomExtra := <scm>
      <url>git@github.com:typelevel/squants.git</url>
      <connection>scm:git:git@github.com:typelevel/squants.git</connection>
    </scm>
      <developers>
        <developer>
          <id>garyKeorkunian</id>
          <name>Gary Keorkunian</name>
          <url>http://www.linkedin.com/in/garykeorkunian</url>
        </developer>
      </developers>
  )
}

object Tests {
  val defaultSettings = Seq(
    libraryDependencies ++=
      Dependencies.scalaTest.value ++
      Dependencies.scalaCheck.value ++
      Dependencies.json4s.value
  )
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
      .setPreference(RewriteArrowSymbols, true)
      .setPreference(SpaceBeforeColon, false)
      .setPreference(SpaceInsideBrackets, false)
      .setPreference(SpacesWithinPatternBinders, true)
  }
}

object Docs {
  private def gitHash = sys.process.Process("git rev-parse HEAD").lines_!.head
  val defaultSettings = Seq(
    scalacOptions in (Compile, doc) ++= {
      val (bd, v) = ((baseDirectory in LocalRootProject).value, version.value)
      val tagOrBranch = if(v endsWith "SNAPSHOT") gitHash else "v" + v
      Seq("-sourcepath", bd.getAbsolutePath, "-doc-source-url", "https://github.com/garyKeorkunian/squants/tree/" + tagOrBranch + "€{FILE_PATH}.scala")
    }
  )
}
