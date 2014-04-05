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
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class EnergySpec extends FlatSpec with Matchers {

  behavior of "Energy and its Units of Measure"

  it should "create values using UOM factories" in {

    WattHours(1).toWattHours should be(1)
    Joules(1).toJoules should be(1)
    KilowattHours(1).toKilowattHours should be(1)
    MegawattHours(1).toMegawattHours should be(1)
    GigawattHours(1).toGigawattHours should be(1)
    BritishThermalUnits(1).toBtus should be(1)
    MBtus(1).toMBtus should be(1)
    MMBtus(1).toMMBtus should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattHours(1)

    x.toWattHours should be(1)
    x.toJoules should be(1 / Joules.multiplier)
    x.toKilowattHours should be(1 / MetricSystem.Kilo)
    x.toMegawattHours should be(1 / MetricSystem.Mega)
    x.toGigawattHours should be(1 / MetricSystem.Giga)
    x.toBtus should be(1 / BritishThermalUnits.multiplier)
    x.toMBtus should be(1 / MBtus.multiplier)
    x.toMMBtus should be(1 / MMBtus.multiplier)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    WattHours(1).toString(WattHours) should be("1.0 Wh")
    Joules(1).toString(Joules) should be("1.0 J")
    KilowattHours(1).toString(KilowattHours) should be("1.0 kWh")
    MegawattHours(1).toString(MegawattHours) should be("1.0 MWh")
    GigawattHours(1).toString(GigawattHours) should be("1.0 GWh")
    BritishThermalUnits(1).toString(BritishThermalUnits) should be("1.0 Btu")
    MBtus(1).toString(MBtus) should be("1.0 MBtu")
    MMBtus(1).toString(MMBtus) should be("1.0 MMBtu")
  }

  it should "return Power when divided by Time" in {
    WattHours(1) / Hours(1) should be(Watts(1))
  }

  it should "return Time when divided by Power" in {
    WattHours(1) / Watts(1) should be(Hours(1))
  }

  it should "return ElectricalPotential when divided by ElectricalCharge" in {
    Joules(1) / Coulombs(1) should be(Volts(1))
  }

  it should "return Force when divided by Length" in {
    Joules(1) / Meters(1) should be(Newtons(1))
  }

  it should "return Mass when divided by SpecificEnergy" in {
    Joules(1) / Grays(1) should be(Kilograms(1))
  }

  it should "return SpecificEnergy when divided by Mass" in {
    Joules(1) / Kilograms(1) should be(Grays(1))
  }

  it should "return Volume when divided by EnergyDensity" in {
    Joules(1) / JoulesPerCubicMeter(1) should be(CubicMeters(1))
  }

  it should "return EnergyDensity when divided by Volume" in {
    Joules(1) / CubicMeters(1) should be(JoulesPerCubicMeter(1))
  }

  it should "return ThermalCapacity when divided by Temperature" in {
    Joules(1) / JoulesPerKelvin(1) should be(Kelvin(1))
  }

  it should "return Temperature when divided by ThermalCapacity" in {
    Joules(1) / Kelvin(1) should be(JoulesPerKelvin(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = KilowattHours(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Energy](ser)
    x should be(des)
  }

  behavior of "EnergyConversions"

  it should "provide aliases for single unit values" in {
    import EnergyConversions._

    wattHour should be(WattHours(1))
    Wh should be(WattHours(1))
    kilowattHour should be(KilowattHours(1))
    kWh should be(KilowattHours(1))
    megawattHour should be(MegawattHours(1))
    MWh should be(MegawattHours(1))
    gigawattHour should be(GigawattHours(1))
    GWh should be(GigawattHours(1))
    btu should be(BritishThermalUnits(1))
    joule should be(Watts(1) * Seconds(1))
    btuMultiplier should be(joule.value * 1055.05585262)
  }

  it should "provide implicit conversion from Double" in {
    import EnergyConversions._

    val d = 10D
    d.J should be(Joules(d))
    d.joules should be(Joules(d))
    d.Wh should be(WattHours(d))
    d.kWh should be(KilowattHours(d))
    d.MWh should be(MegawattHours(d))
    d.GWh should be(GigawattHours(d))
    d.Btu should be(BritishThermalUnits(d))
    d.MBtu should be(MBtus(d))
    d.MMBtu should be(MMBtus(d))
    d.wattHours should be(WattHours(d))
    d.kilowattHours should be(KilowattHours(d))
    d.megawattHours should be(MegawattHours(d))
    d.gigawattHours should be(GigawattHours(d))
  }

  it should "provide implicit conversions from String" in {
    import EnergyConversions._

    "10.22 J".toEnergy.right.get should be(Joules(10.22))
    "10.22 Wh".toEnergy.right.get should be(WattHours(10.22))
    "10.22 kWh".toEnergy.right.get should be(KilowattHours(10.22))
    "10.22 MWh".toEnergy.right.get should be(MegawattHours(10.22))
    "10.22 GWh".toEnergy.right.get should be(GigawattHours(10.22))
    "10.22 Btu".toEnergy.right.get should be(BritishThermalUnits(10.22))
    "10.22 MBtu".toEnergy.right.get should be(MBtus(10.22))
    "10.22 MMBtu".toEnergy.right.get should be(MMBtus(10.22))
    "10.22 zz".toEnergy.left.get should be("Unable to parse 10.22 zz as Energy")
  }
}
