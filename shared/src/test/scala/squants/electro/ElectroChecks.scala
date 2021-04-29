/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants.mass.Kilograms
import squants.energy.{ Grays, Joules, Watts }
import squants.motion.Newtons
import squants.space.{ SquareMeters, Meters }
import squants.time.Seconds
import squants.energy.Energy
import org.scalacheck.Properties
import org.scalacheck.Prop._
import squants.QuantityChecks

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
object ElectroChecks extends Properties("Electro") with QuantityChecks {

  implicit val tolCurrent: ElectricCurrent = Amperes(tol)
  implicit val tolPotentional: ElectricPotential = Volts(tol)
  implicit val tolEnergy: Energy = Joules(1e-12)

  property("Volts = Amps * Ohms (Ohm's Law)") = forAll(posNum, posNum) { (amps: TestData, ohms: TestData) ⇒
    Volts(amps * ohms) == Amperes(amps) * Ohms(ohms) &&
      Volts(amps * ohms) == Ohms(ohms) * Amperes(amps) &&
      Amperes(amps) == Volts(amps * ohms) / Ohms(ohms) &&
      Ohms(ohms) == Volts(amps * ohms) / Amperes(amps)
  }

  property("Watts = Volts * Amps") = forAll(posNum, posNum) { (volts: TestData, amps: TestData) ⇒
    Watts(volts * amps) == Volts(volts) * Amperes(amps) &&
      Watts(volts * amps) == Amperes(amps) * Volts(volts) &&
      Volts(volts) == Watts(volts * amps) / Amperes(amps) &&
      Amperes(amps) == Watts(volts * amps) / Volts(volts)
  }

  property("Coulombs = Amps * Seconds") = forAll(posNum, posNum) { (amps: TestData, seconds: TestData) ⇒
    Coulombs(amps * seconds) == Amperes(amps) * Seconds(seconds) &&
      Coulombs(amps * seconds) == Seconds(seconds) * Amperes(amps) &&
      Amperes(amps) =~ Coulombs(amps * seconds) / Seconds(seconds) &&
      Seconds(seconds) =~ Coulombs(amps * seconds) / Amperes(amps)
  }

  property("Coulombs = Farads * Volts") = forAll(posNum, posNum) { (farads: TestData, volts: TestData) ⇒
    Coulombs(farads * volts) == Farads(farads) * Volts(volts) &&
      Coulombs(farads * volts) == Volts(volts) * Farads(farads) &&
      Farads(farads) == Coulombs(farads * volts) / Volts(volts) &&
      Volts(volts) == Coulombs(farads * volts) / Farads(farads)
  }

  property("Joules = Newtons * Meters") = forAll(posNum, posNum) { (newtons: TestData, meters: TestData) ⇒
    Joules(newtons * meters) =~ Newtons(newtons) * Meters(meters) &&
      Joules(newtons * meters) =~ Meters(meters) * Newtons(newtons) &&
      Newtons(newtons).plusOrMinus(Newtons(tol)).contains(Joules(newtons * meters) / Meters(meters)) &&
      Meters(meters).plusOrMinus(Meters(tol)).contains(Joules(newtons * meters) / Newtons(newtons))
  }

  property("Joules = Kilograms * Grays") = forAll(posNum, posNum) { (kilograms: TestData, grays: TestData) ⇒
    Joules(kilograms * grays) =~ Kilograms(kilograms) * Grays(grays) &&
      Joules(kilograms * grays) =~ Grays(grays) * Kilograms(kilograms) &&
      Kilograms(kilograms).plusOrMinus(Kilograms(tol)).contains(Joules(kilograms * grays) / Grays(grays)) &&
      Grays(grays).plusOrMinus(Grays(tol)).contains(Joules(kilograms * grays) / Kilograms(kilograms))
  }

  property("Siemens = SiemensPerMeter * Meters") = forAll(posNum, posNum) { (conductivity: TestData, length: TestData) ⇒
    Siemens(conductivity * length) == SiemensPerMeter(conductivity) * Meters(length) &&
      Siemens(conductivity * length) == Meters(length) * SiemensPerMeter(conductivity) &&
      Meters(length) == Siemens(conductivity * length) / SiemensPerMeter(conductivity) &&
      SiemensPerMeter(conductivity) == Siemens(conductivity * length) / Meters(length)
  }

  property("OhmMeters = Ohms * Meters") = forAll(posNum, posNum) { (ohms: TestData, meters: TestData) ⇒
    OhmMeters(ohms * meters) == Ohms(ohms) * Meters(meters) &&
      OhmMeters(ohms * meters) == Meters(meters) * Ohms(ohms) &&
      Meters(meters) == OhmMeters(ohms * meters) / Ohms(ohms) &&
      Ohms(ohms) == OhmMeters(ohms * meters) / Meters(meters)
  }

  property("Ohms = 1 / Siemens") = forAll(posNum) { (ohms: TestData) ⇒
    Ohms(ohms).inSiemens.plusOrMinus(Siemens(tol)).contains(Siemens(1.0 / ohms)) &&
      Siemens(1.0 / ohms).inOhms.plusOrMinus(Ohms(tol)).contains(Ohms(ohms))
  }

  property("OhmMeters = 1 / SiemensPerMeter") = forAll(posNum) { (ohmMeters: TestData) ⇒
    OhmMeters(ohmMeters).inSiemensPerMeter.plusOrMinus(SiemensPerMeter(tol)).contains(SiemensPerMeter(1.0 / ohmMeters)) &&
      SiemensPerMeter(1.0 / ohmMeters).inOhmMeters.plusOrMinus(OhmMeters(tol)).contains(OhmMeters(ohmMeters))
  }

  property("Webers = Henrys * Amperes") = forAll(posNum, posNum) { (henry: TestData, amps: TestData) ⇒
    Webers(henry * amps) == Henry(henry) * Amperes(amps) &&
      Webers(henry * amps) == Amperes(amps) * Henry(henry) &&
      Amperes(amps) == Webers(henry * amps) / Henry(henry) &&
      Henry(henry) == Webers(henry * amps) / Amperes(amps)
  }

  property("Webers = Volts * Seconds") = forAll(posNum, posNum) { (volts: TestData, seconds: TestData) ⇒
    Webers(volts * seconds) == Volts(volts) * Seconds(seconds) &&
      Webers(volts * seconds) == Seconds(seconds) * Volts(volts) &&
      Seconds(seconds) =~ Webers(volts * seconds) / Volts(volts) &&
      Volts(volts) =~ Webers(volts * seconds) / Seconds(seconds)
  }

  property("Webers = Teslas * SquareMeters") = forAll(posNum, posNum) { (teslas: TestData, sqMeters: TestData) ⇒
    Webers(teslas * sqMeters) == Teslas(teslas) * SquareMeters(sqMeters) &&
      Webers(teslas * sqMeters) == SquareMeters(sqMeters) * Teslas(teslas) &&
      SquareMeters(sqMeters) == Webers(teslas * sqMeters) / Teslas(teslas) &&
      Teslas(teslas) == Webers(teslas * sqMeters) / SquareMeters(sqMeters)
  }
}

