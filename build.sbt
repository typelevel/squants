organization in ThisBuild := "com.squants"

name := "Squants"

version in ThisBuild := Versions.Squants

licenses := Seq("Apache 2.0" -> url("http://www.opensource.org/licenses/Apache-2.0"))

homepage := Some(url("http://www.squants.com/"))

scalaVersion in ThisBuild := Versions.Scala

crossScalaVersions := Versions.ScalaCross

scalacOptions ++= Seq("-feature", "-deprecation")

libraryDependencies ++= Seq(
  Dependencies.scalaTest,
  Dependencies.scalaCheck,
  Dependencies.json4s,
  Dependencies.spire
)

resolvers ++= Seq(
    Resolvers.typeSafeRepo,
    Resolvers.sonatypeNexusSnapshots,
    Resolvers.sonatypeNexusReleases,
    Resolvers.sonatypeNexusStaging
)

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <scm>
    <url>git@github.com:garyKeorkunian/squants.git</url>
    <connection>scm:git:git@github.com:garyKeorkunian/squants.git</connection>
  </scm>
  <developers>
    <developer>
      <id>garyKeorkunian</id>
      <name>Gary Keorkunian</name>
      <url>http://www.linkedin.com/in/garykeorkunian</url>
    </developer>
  </developers>
)

initialCommands in console := """import scala.language.postfixOps,
                                squants._,
                                squants.energy._,
                                squants.electro._,
                                squants.market._,
                                squants.mass._,
                                squants.motion._,
                                squants.photo._,
                                squants.radio._,
                                squants.space._,
                                squants.thermal._,
                                squants.time._,
                                squants.DimensionlessConversions._,
                                squants.electro.CapacitanceConversions._,
                                squants.electro.ConductivityConversions._,
                                squants.electro.ElectricalConductanceConversions._,
                                squants.electro.ElectricalResistanceConversions._,
                                squants.electro.ElectricChargeConversions._,
                                squants.electro.ElectricCurrentConversions._,
                                squants.electro.ElectricPotentialConversions._,
                                squants.electro.InductanceConversions._,
                                squants.electro.MagneticFluxConversions._,
                                squants.electro.MagneticFluxDensityConversions._,
                                squants.electro.ResistivityConversions._,
                                squants.energy.EnergyConversions._,
                                squants.energy.EnergyDensityConversions._,
                                squants.energy.PowerConversions._,
                                squants.energy.PowerRampConversions._,
                                squants.energy.SpecificEnergyConversions._,
                                squants.market.MoneyConversions._,
                                squants.mass.AreaDensityConversions._,
                                squants.mass.ChemicalAmountConversions._,
                                squants.mass.DensityConversions._,
                                squants.mass.MassConversions._,
                                squants.motion.AccelerationConversions._,
                                squants.motion.AngularVelocityConversions._,
                                squants.motion.ForceConversions._,
                                squants.motion.JerkConversions._,
                                squants.motion.MassFlowConversions._,
                                squants.motion.MomentumConversions._,
                                squants.motion.PressureConversions._,
                                squants.motion.VelocityConversions._,
                                squants.motion.VolumeFlowConversions._,
                                squants.motion.YankConversions._,
                                squants.photo.IlluminanceConversions._,
                                squants.photo.LuminanceConversions._,
                                squants.photo.LuminousEnergyConversions._,
                                squants.photo.LuminousExposureConversions._,
                                squants.photo.LuminousFluxConversions._,
                                squants.photo.LuminousIntensityConversions._,
                                squants.radio.IrradianceConversions._,
                                squants.radio.RadianceConversions._,
                                squants.radio.RadiantIntensityConversions._,
                                squants.radio.SpectralIntensityConversions._,
                                squants.radio.SpectralPowerConversions._,
                                squants.space.AngleConversions._,
                                squants.space.AreaConversions._,
                                squants.space.LengthConversions._,
                                squants.space.SolidAngleConversions._,
                                squants.space.VolumeConversions._,
                                squants.thermal.TemperatureConversions._,
                                squants.thermal.ThermalCapacityConversions._,
                                squants.time.FrequencyConversions._,
                                squants.time.TimeConversions._""".stripMargin
