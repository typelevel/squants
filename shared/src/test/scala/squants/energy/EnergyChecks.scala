/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import squants.QuantityChecks
import squants.electro.{ Amperes, Coulombs, Volts }
import squants.mass.Kilograms
import squants.motion.Newtons
import squants.space.{ CubicMeters, Meters }
import squants.thermal.{ JoulesPerKelvin, Kelvin }
import squants.time.Hours
import squants._
import squants.electro.ElectricPotential
import squants.electro.ElectricCharge
import squants.thermal.ThermalCapacity

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
object EnergyChecks extends Properties("Energy") with QuantityChecks {

  override val tol = 1e-12
  override implicit val tolTime = Hours(tol)
  implicit val tolPower: Power = Watts(tol)
  implicit val tolPowerRamp: PowerRamp = WattsPerHour(tol)
  implicit val tolEnergy: Energy = Joules(tol)
  implicit val tolLength: Length = Meters(tol)
  implicit val tolForce: Force = Newtons(tol)
  implicit val tolSpecificEnergy: SpecificEnergy= Grays(tol)
  implicit val tolMass: Mass = Kilograms(tol)
  implicit val tolEnergyDensity: EnergyDensity = JoulesPerCubicMeter(tol)
  implicit val tolTemp: Temperature = Kelvin(tol)
  implicit val tolElectricCharge: ElectricCharge = Coulombs(tol)
  implicit val tolVolume: Volume = CubicMeters(tol)
  implicit val tolThermalCap: ThermalCapacity = JoulesPerKelvin(tol)
  implicit val tolElectricPotential: ElectricPotential = Volts(tol)

  property("WattHours = Watts * Hours") = forAll(posNum, posNum) { (watts: TestData, hours: TestData) ⇒
    WattHours(watts * hours) == Watts(watts) * Hours(hours) &&
      WattHours(watts * hours) == Hours(hours) * Watts(watts) &&
      Hours(hours) =~ WattHours(watts * hours) / Watts(watts) &&
      Watts(watts) =~ WattHours(watts * hours) / Hours(hours)
  }

  property("Watts = WattsPerHour * Hours") = forAll(posNum, posNum) { (wattsPerHour: TestData, hours: TestData) ⇒
    Watts(wattsPerHour * hours) == WattsPerHour(wattsPerHour) * Hours(hours) &&
      Watts(wattsPerHour * hours) == Hours(hours) * WattsPerHour(wattsPerHour) &&
      Hours(hours) =~ Watts(wattsPerHour * hours) / WattsPerHour(wattsPerHour) &&
      WattsPerHour(wattsPerHour) =~ Watts(wattsPerHour * hours) / Hours(hours)
  }

  property("Watts = Volts * Amps") = forAll(posNum, posNum) { (volts: TestData, amps: TestData) ⇒
    Watts(volts * amps) == Volts(volts) * Amperes(amps) &&
      Watts(volts * amps) == Amperes(amps) * Volts(volts) &&
      Amperes(amps) == Watts(volts * amps) / Volts(volts) &&
      Volts(volts) == Watts(volts * amps) / Amperes(amps)
  }

  property("Joules = Newtons * Meters") = forAll(posNum, posNum) { (newtons: TestData, meters: TestData) ⇒
    Joules(newtons * meters) =~ (Newtons(newtons) * Meters(meters)) &&
      Joules(newtons * meters) =~ (Meters(meters) * Newtons(newtons)) &&
      Meters(meters) =~ (Joules(newtons * meters) / Newtons(newtons)) &&
      Newtons(newtons) =~ (Joules(newtons * meters) / Meters(meters))
  }

  property("Joules = Kilograms * Grays") = forAll(posNum, posNum) { (kilograms: TestData, grays: TestData) ⇒
    Joules(kilograms * grays) =~ (Kilograms(kilograms) * Grays(grays)) &&
      Joules(kilograms * grays) =~ (Grays(grays) * Kilograms(kilograms)) &&
      Grays(grays) =~ (Joules(kilograms * grays) / Kilograms(kilograms)) &&
      Kilograms(kilograms) =~ (Joules(kilograms * grays) / Grays(grays))
  }

  property("Joules = CubicMeters * JoulesPerCubicMeter") = forAll(posNum, posNum) { (cubicMeters: TestData, jpcm: TestData) ⇒
    Joules(cubicMeters * jpcm) =~ (CubicMeters(cubicMeters) * JoulesPerCubicMeter(jpcm)) &&
      Joules(cubicMeters * jpcm) =~ (JoulesPerCubicMeter(jpcm) * CubicMeters(cubicMeters)) &&
      JoulesPerCubicMeter(jpcm) =~ (Joules(cubicMeters * jpcm) / CubicMeters(cubicMeters)) &&
      CubicMeters(cubicMeters) =~ (Joules(cubicMeters * jpcm) / JoulesPerCubicMeter(jpcm))
  }

  property("Joules = JoulesPerKelvin * Kelvin") = forAll(posNum, posNum) { (joulesPerKelvin: TestData, kelvin: TestData) ⇒
    Joules(joulesPerKelvin * kelvin) =~ (JoulesPerKelvin(joulesPerKelvin) * Kelvin(kelvin)) &&
      Joules(joulesPerKelvin * kelvin) =~ (Kelvin(kelvin) * JoulesPerKelvin(joulesPerKelvin)) &&
      Kelvin(kelvin) =~ (Joules(joulesPerKelvin * kelvin) / JoulesPerKelvin(joulesPerKelvin)) &&
      JoulesPerKelvin(joulesPerKelvin) =~ (Joules(joulesPerKelvin * kelvin) / Kelvin(kelvin))
  }

  property("Joules = Volt * Coulombs") = forAll(posNum, posNum) { (volts: TestData, coulombs: TestData) ⇒
    Joules(volts * coulombs) =~ (Volts(volts) * Coulombs(coulombs)) &&
      Joules(volts * coulombs) =~ (Coulombs(coulombs) * Volts(volts)) &&
      Coulombs(coulombs) =~ (Joules(volts * coulombs) / Volts(volts)) &&
      Volts(volts) =~ (Joules(volts * coulombs) / Coulombs(coulombs))
  }
}
