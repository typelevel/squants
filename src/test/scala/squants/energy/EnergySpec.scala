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
import squants.time.Hours
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
    KilowattHours(1).toKilowattHours should be(1)
    MegawattHours(1).toMegawattHours should be(1)
    GigawattHours(1).toGigawattHours should be(1)

    Joules(1).toJoules should be(1)
    Picojoules(1).toPicojoules should be(1)
    Nanojoules(1).toNanojoules should be(1)
    Microjoules(1).toMicrojoules should be(1)
    Millijoules(1).toMillijoules should be(1)
    Kilojoules(1).toKilojoules should be(1)
    Megajoules(1).toMegajoules should be(1)
    Gigajoules(1).toGigajoules should be(1)
    Terajoules(1).toTerajoules should be(1)

    BritishThermalUnits(1).toBtus should be(1)
    MBtus(1).toMBtus should be(1)
    MMBtus(1).toMMBtus should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattHours(1)

    x.toWattHours should be(1)
    x.toKilowattHours should be(1 / MetricSystem.Kilo)
    x.toMegawattHours should be(1 / MetricSystem.Mega)
    x.toGigawattHours should be(1 / MetricSystem.Giga)

    x.toJoules should be(1 / Joules.multiplier)
    x.toPicojoules should be(1 / Picojoules.multiplier)
    x.toNanojoules should be(1 / Nanojoules.multiplier)
    x.toMicrojoules should be(1 / Microjoules.multiplier)
    x.toMillijoules should be(1 / Millijoules.multiplier)
    x.toKilojoules should be(1 / Kilojoules.multiplier)
    x.toMegajoules should be(1 / Megajoules.multiplier)
    x.toGigajoules should be(1 / Gigajoules.multiplier)

    x.toBtus should be(1 / BritishThermalUnits.multiplier)
    x.toMBtus should be(1 / MBtus.multiplier)
    x.toMMBtus should be(1 / MMBtus.multiplier)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    WattHours(1).toString(WattHours) should be("1.0 Wh")
    KilowattHours(1).toString(KilowattHours) should be("1.0 kWh")
    MegawattHours(1).toString(MegawattHours) should be("1.0 MWh")
    GigawattHours(1).toString(GigawattHours) should be("1.0 GWh")

    Joules(1).toString(Joules) should be("1.0 J")
    Picojoules(1).toString(Picojoules) should be("1.0 pJ")
    Nanojoules(1).toString(Nanojoules) should be("1.0 nJ")
    Microjoules(1).toString(Microjoules) should be("1.0 µJ")
    Millijoules(1).toString(Millijoules) should be("1.0 mJ")
    Kilojoules(1).toString(Kilojoules) should be("1.0 kJ")
    Megajoules(1).toString(Megajoules) should be("1.0 MJ")
    Gigajoules(1).toString(Gigajoules) should be("1.0 GJ")
    Terajoules(1).toString(Terajoules) should be("1.0 TJ")

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
    Joules(10) / JoulesPerKelvin(4) should be(Kelvin(2.5))
  }

  it should "return Temperature when divided by ThermalCapacity" in {
    Joules(10) / Kelvin(2) should be(JoulesPerKelvin(5))
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

    joule should be(Joules(1))
    picojoule should be(Picojoules(1))
    nanojoule should be(Nanojoules(1))
    microjoule should be(Microjoules(1))
    millijoule should be(Millijoules(1))
    kilojoule should be(Kilojoules(1))
    megajoule should be(Megajoules(1))
    gigajoule should be(Gigajoules(1))
    terajoule should be(Terajoules(1))

    btu should be(BritishThermalUnits(1))
    btuMultiplier should be(joule.value * 1055.05585262)
  }

  it should "provide implicit conversion from Double" in {
    import EnergyConversions._

    val d = 10D
    d.Wh should be(WattHours(d))
    d.kWh should be(KilowattHours(d))
    d.MWh should be(MegawattHours(d))
    d.GWh should be(GigawattHours(d))
    d.wattHours should be(WattHours(d))
    d.kilowattHours should be(KilowattHours(d))
    d.megawattHours should be(MegawattHours(d))
    d.gigawattHours should be(GigawattHours(d))

    d.J should be(Joules(d))
    d.joules should be(Joules(d))
    d.picojoules should be(Picojoules(d))
    d.nanojoules should be(Nanojoules(d))
    d.microjoules should be(Microjoules(d))
    d.milljoules should be(Millijoules(d))
    d.kilojoules should be(Kilojoules(d))
    d.megajoules should be(Megajoules(d))
    d.gigajoules should be(Gigajoules(d))

    d.Btu should be(BritishThermalUnits(d))
    d.MBtu should be(MBtus(d))
    d.MMBtu should be(MMBtus(d))
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

  it should "provide Numeric support" in {
    import EnergyConversions.EnergyNumeric

    val es = List(WattHours(100), KilowattHours(1))
    es.sum should be(KilowattHours(1.1))
  }
}
