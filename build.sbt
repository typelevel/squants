organization in ThisBuild := "com.viridityenergy"

name := "Squants"

version in ThisBuild := Versions.Squants

scalaVersion in ThisBuild := Versions.Scala

scalacOptions ++= Seq("-feature", "-deprecation")

libraryDependencies ++= Seq(
  "org.scalatest"      %% "scalatest"   % Versions.ScalaTest       % "test",
  "org.scalacheck"     %% "scalacheck"  % Versions.ScalaCheck      % "test"
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
                                squants.energy.PowerConversions._,
                                squants.energy.PowerRampConversions._,
                                squants.energy.SpecificEnergyConversions._,
                                squants.market.MoneyConversions._,
                                squants.mass.MassConversions._,
                                squants.mass.ChemicalAmountConversions._,
                                squants.motion.VelocityConversions._,
                                squants.motion.AccelerationConversions._,
                                squants.motion.ForceConversions._,
                                squants.motion.MomentumConversions._,
                                squants.motion.PressureConversions._,
                                squants.photo.IlluminanceConversions._,
                                squants.photo.LuminousEnergyConversions._,
                                squants.photo.LuminousFluxConversions._,
                                squants.photo.LuminousIntensityConversions._,
                                squants.space.AngleConversions._,
                                squants.space.AreaConversions._,
                                squants.space.LengthConversions._,
                                squants.space.SolidAngleConversions._,
                                squants.space.VolumeConversions._,
                                squants.thermal.TemperatureConversions._,
                                squants.thermal.ThermalCapacityConversions._,
                                squants.time.FrequencyConversions._,
                                squants.time.TimeConversions._""".stripMargin
