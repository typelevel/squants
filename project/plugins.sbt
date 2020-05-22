addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject"      % "1.0.0")
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % "1.0.0")
val scalaJSVersion =
  Option(System.getenv("SCALAJS_VERSION")).getOrElse("1.1.0")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion)
addSbtPlugin("org.scala-native"   % "sbt-scala-native"              % "0.3.9")

addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.3")

addSbtPlugin("com.typesafe.sbt" % "sbt-osgi" % "0.9.5")

addSbtPlugin("org.tpolecat" % "tut-plugin" % "0.6.13")

addSbtPlugin("com.geirsson" % "sbt-ci-release" % "1.5.3")
