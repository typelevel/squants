/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.MetricSystem
import squants.time.{ Seconds, Hours }
import squants.electro.{ Coulombs, Volts }
import squants.space.{ CubicMeters, Meters }
import squants.motion.Newtons
import squants.mass.Kilograms
import squants.thermal.{ Kelvin, JoulesPerKelvin }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class EnergySpec extends FlatSpec with Matchers {

  behavior of "Energy and its Units of Measure"

  it should "create values using UOM factories" in {

    assert(WattHours(1).toWattHours == 1)
    assert(Joules(1).toJoules == 1)
    assert(KilowattHours(1).toKilowattHours == 1)
    assert(MegawattHours(1).toMegawattHours == 1)
    assert(GigawattHours(1).toGigawattHours == 1)
    assert(BritishThermalUnits(1).toBtus == 1)
    assert(MBtus(1).toMBtus == 1)
    assert(MMBtus(1).toMMBtus == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattHours(1)

    assert(x.toWattHours == 1)
    assert(x.toJoules == 1 / Joules.multiplier)
    assert(x.toKilowattHours == 1 / MetricSystem.Kilo)
    assert(x.toMegawattHours == 1 / MetricSystem.Mega)
    assert(x.toGigawattHours == 1 / MetricSystem.Giga)
    assert(x.toBtus == 1 / BritishThermalUnits.multiplier)
    assert(x.toMBtus == 1 / MBtus.multiplier)
    assert(x.toMMBtus == 1 / MMBtus.multiplier)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(WattHours(1).toString(WattHours) == "1.0 Wh")
    assert(Joules(1).toString(Joules) == "1.0 J")
    assert(KilowattHours(1).toString(KilowattHours) == "1.0 kWh")
    assert(MegawattHours(1).toString(MegawattHours) == "1.0 MWh")
    assert(GigawattHours(1).toString(GigawattHours) == "1.0 GWh")
    assert(BritishThermalUnits(1).toString(BritishThermalUnits) == "1.0 Btu")
    assert(MBtus(1).toString(MBtus) == "1.0 MBtu")
    assert(MMBtus(1).toString(MMBtus) == "1.0 MMBtu")
  }

  it should "return Power when divided by Time" in {
    assert(WattHours(1) / Hours(1) == Watts(1))
  }

  it should "return Time when divided by Power" in {
    assert(WattHours(1) / Watts(1) == Hours(1))
  }

  it should "return ElectricalPotential when divided by ElectricalCharge" in {
    assert(Joules(1) / Coulombs(1) == Volts(1))
  }

  it should "return Force when divided by Length" in {
    assert(Joules(1) / Meters(1) == Newtons(1))
  }

  it should "return Mass when divided by SpecificEnergy" in {
    assert(Joules(1) / Grays(1) == Kilograms(1))
  }

  it should "return SpecificEnergy when divided by Mass" in {
    assert(Joules(1) / Kilograms(1) == Grays(1))
  }

  it should "return Volume when divided by EnergyDensity" in {
    assert(Joules(1) / JoulesPerCubicMeter(1) == CubicMeters(1))
  }

  it should "return EnergyDensity when divided by Volume" in {
    assert(Joules(1) / CubicMeters(1) == JoulesPerCubicMeter(1))
  }

  it should "return ThermalCapacity when divided by Temperature" in {
    assert(Joules(1) / JoulesPerKelvin(1) == Kelvin(1))
  }

  it should "return Temperature when divided by ThermalCapacity" in {
    assert(Joules(1) / Kelvin(1) == JoulesPerKelvin(1))
  }

  behavior of "EnergyConversions"

  it should "provide aliases for single unit values" in {
    import EnergyConversions._

    assert(wattHour == WattHours(1))
    assert(Wh == WattHours(1))
    assert(kilowattHour == KilowattHours(1))
    assert(kWh == KilowattHours(1))
    assert(megawattHour == MegawattHours(1))
    assert(MWh == MegawattHours(1))
    assert(gigawattHour == GigawattHours(1))
    assert(GWh == GigawattHours(1))
    assert(btu == BritishThermalUnits(1))
    assert(joule == Watts(1) * Seconds(1))
    assert(btuMultiplier == joule.value * 1055.05585262)
  }

  it should "provide implicit conversion from Double" in {
    import EnergyConversions._

    val d = 10D
    assert(d.J == Joules(d))
    assert(d.joules == Joules(d))
    assert(d.Wh == WattHours(d))
    assert(d.kWh == KilowattHours(d))
    assert(d.MWh == MegawattHours(d))
    assert(d.GWh == GigawattHours(d))
    assert(d.Btu == BritishThermalUnits(d))
    assert(d.MBtu == MBtus(d))
    assert(d.MMBtu == MMBtus(d))
    assert(d.wattHours == WattHours(d))
    assert(d.kilowattHours == KilowattHours(d))
    assert(d.megawattHours == MegawattHours(d))
    assert(d.gigawattHours == GigawattHours(d))
  }

  it should "provide implicit conversions from String" in {
    import EnergyConversions._

    assert("10.22 Wh".toEnergy.get == WattHours(10.22))
    assert("10.22 kWh".toEnergy.get == KilowattHours(10.22))
    assert("10.22 MWh".toEnergy.get == MegawattHours(10.22))
    assert("10.22 GWh".toEnergy.get == GigawattHours(10.22))
    assert("10.22 Btu".toEnergy.get == BritishThermalUnits(10.22))
    assert("10.22 MBtu".toEnergy.get == MBtus(10.22))
    assert("10.22 MMBtu".toEnergy.get == MMBtus(10.22))
  }
}
