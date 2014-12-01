/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import squants.time.Hours
import squants.motion.Newtons
import squants.mass.Kilograms
import squants.space.{ CubicMeters, Meters }
import squants.electro.{ Coulombs, Volts, Amperes }
import squants.thermal.{ Kelvin, JoulesPerKelvin }
import squants.QuantityChecks

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
object EnergyChecks extends Properties("Energy") with QuantityChecks {

  override implicit val tolTime = Hours(tol)
  implicit val tolPower = Watts(tol)
  implicit val tolPowerRamp = WattsPerHour(tol)

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
    Joules(newtons * meters) == Newtons(newtons) * Meters(meters) &&
      Joules(newtons * meters) == Meters(meters) * Newtons(newtons) &&
      Meters(meters).plusOrMinus(Meters(tol)).contains(Joules(newtons * meters) / Newtons(newtons)) &&
      Newtons(newtons).plusOrMinus(Newtons(tol)).contains(Joules(newtons * meters) / Meters(meters))
  }

  property("Joules = Kilograms * Grays") = forAll(posNum, posNum) { (kilograms: TestData, grays: TestData) ⇒
    Joules(kilograms * grays) == Kilograms(kilograms) * Grays(grays) &&
      Joules(kilograms * grays) == Grays(grays) * Kilograms(kilograms) &&
      Grays(grays).plusOrMinus(Grays(tol)).contains(Joules(kilograms * grays) / Kilograms(kilograms)) &&
      Kilograms(kilograms).plusOrMinus(Kilograms(tol)).contains(Joules(kilograms * grays) / Grays(grays))
  }

  property("Joules = CubicMeters * JoulesPerCubicMeter") = forAll(posNum, posNum) { (cubicMeters: TestData, jpcm: TestData) ⇒
    Joules(cubicMeters * jpcm) == CubicMeters(cubicMeters) * JoulesPerCubicMeter(jpcm) &&
      Joules(cubicMeters * jpcm) == JoulesPerCubicMeter(jpcm) * CubicMeters(cubicMeters) &&
      JoulesPerCubicMeter(jpcm).plusOrMinus(JoulesPerCubicMeter(tol)).contains(Joules(cubicMeters * jpcm) / CubicMeters(cubicMeters)) &&
      CubicMeters(cubicMeters).plusOrMinus(CubicMeters(tol)).contains(Joules(cubicMeters * jpcm) / JoulesPerCubicMeter(jpcm))
  }

  property("Joules = JoulesPerKelvin * Kelvin") = forAll(posNum, posNum) { (joulesPerKelvin: TestData, kelvin: TestData) ⇒
    Joules(joulesPerKelvin * kelvin) == JoulesPerKelvin(joulesPerKelvin) * Kelvin(kelvin) &&
      Joules(joulesPerKelvin * kelvin) == Kelvin(kelvin) * JoulesPerKelvin(joulesPerKelvin) &&
      Kelvin(kelvin).plusOrMinus(Kelvin(tol)).contains(Joules(joulesPerKelvin * kelvin) / JoulesPerKelvin(joulesPerKelvin)) &&
      JoulesPerKelvin(joulesPerKelvin).plusOrMinus(JoulesPerKelvin(tol)).contains(Joules(joulesPerKelvin * kelvin) / Kelvin(kelvin))
  }

  property("Joules = Volt * Coulombs") = forAll(posNum, posNum) { (volts: TestData, coulombs: TestData) ⇒
    Joules(volts * coulombs) == Volts(volts) * Coulombs(coulombs) &&
      Joules(volts * coulombs) == Coulombs(coulombs) * Volts(volts) &&
      Coulombs(coulombs).plusOrMinus(Coulombs(tol)).contains(Joules(volts * coulombs) / Volts(volts)) &&
      Volts(volts).plusOrMinus(Volts(tol)).contains(Joules(volts * coulombs) / Coulombs(coulombs))
  }
}
