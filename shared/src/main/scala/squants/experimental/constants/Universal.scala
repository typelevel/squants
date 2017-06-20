package squants.experimental.constants

import squants.motion.Velocity
import squants.motion.MetersPerSecond
import squants.electro.ElectricalResistance
import squants.electro.Ohms
import squants.electro.Permittivity
import squants.electro.FaradsPerMeter
import squants.mass.Mass
import squants.mass.Kilograms
import squants.thermal.Temperature
import squants.thermal.Kelvin
import squants.space.Length
import squants.space.Meters
import squants.time.Time
import squants.time.Seconds

import scala.math.Pi

trait Universal {
  // speed of light in vacuum
  val SpeedOfLight = new Constant[Velocity] {
    val value = MetersPerSecond(299792458)
    val symbol = "𝑐"
    val uncertainty = MetersPerSecond(0)
  }

  // Vacuum Permittivity, Permittivity of free space or Electric Constant
  val ElectricConstant = new Constant[Permittivity] {
    val value = FaradsPerMeter(8.854187817620e-12)
    val symbol = "ε₀"
    val uncertainty = FaradsPerMeter(0)
  }

  // Characteristic impedance of vacuum
  val ImpedanceOfVacuum = new Constant[ElectricalResistance] {
    val value = Ohms(119.9169832*Pi)
    val symbol = "Z₀"
    val uncertainty = Ohms(0)
  }

  val PlanckMass = new Constant[Mass] {
    val value = Kilograms(2.17647051e-8)
    val symbol = "𝑚ₚ"
    val uncertainty = Kilograms(7.5e-5)
  }

  val PlanckTemperature = new Constant[Temperature] {
    val value = Kelvin(1.41680833e32)
    val symbol = "𝑇ₚ"
    val uncertainty = Kelvin(7.5e-5)
  }

  val PlanckLength = new Constant[Length] {
    val value = Meters(1.6162412e-35)
    val symbol = "𝑙ₚ"
    val uncertainty = Meters(7.5e-5)
  }

  val PlanckTime = new Constant[Time] {
    val value = Seconds(5.3912140e-44)
    val symbol = "𝑡ₚ"
    val uncertainty = Seconds(7.5e-5)
  }
}