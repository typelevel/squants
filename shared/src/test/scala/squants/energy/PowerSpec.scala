/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import squants.time.Hours
import squants.{ MetricSystem, QuantityParseException }
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class PowerSpec extends AnyFlatSpec with Matchers {

  behavior of "Power and its Units of Measure"

  it should "create values using UOM factories" in {
    Watts(1).toWatts should be(1)
    Milliwatts(1).toMilliwatts should be(1)
    Kilowatts(1).toKilowatts should be(1)
    Megawatts(1).toMegawatts should be(1)
    Gigawatts(1).toGigawatts should be(1)
    BtusPerHour(1).toBtusPerHour should be(1)
    ErgsPerSecond(1).toErgsPerSecond should be(1)
    SolarLuminosities(1).toSolarLuminosities should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Power("10.22 mW").get should be(Milliwatts(10.22))
    Power("10.22 W").get should be(Watts(10.22))
    Power("10.22 kW").get should be(Kilowatts(10.22))
    Power("10.22 MW").get should be(Megawatts(10.22))
    Power("10.22 GW").get should be(Gigawatts(10.22))
    Power("10.22 Btu/hr").get should be(BtusPerHour(10.22))
    Power("10.22 erg/s").get should be(ErgsPerSecond(10.22))
    Power("10.22 L☉").get should be(SolarLuminosities(10.22))
    Power("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Power", "10.22 zz"))
    Power("ZZ W").failed.get should be(QuantityParseException("Unable to parse Power", "ZZ W"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Watts(1)

    x.toWatts should be(1)
    x.toMilliwatts should be(1 / MetricSystem.Milli)
    x.toKilowatts should be(1 / MetricSystem.Kilo)
    x.toMegawatts should be(1 / MetricSystem.Mega)
    x.toGigawatts should be(1 / MetricSystem.Giga)
    x.toBtusPerHour should be(1 / EnergyConversions.btuMultiplier)
    x.toErgsPerSecond should be(1 / 1e-7)
    x.toSolarLuminosities should be(1 / 3.828e26)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Watts(1).toString(Watts) should be("1.0 W")
    Milliwatts(1).toString(Milliwatts) should be("1.0 mW")
    Kilowatts(1).toString(Kilowatts) should be("1.0 kW")
    Megawatts(1).toString(Megawatts) should be("1.0 MW")
    Gigawatts(1).toString(Gigawatts) should be("1.0 GW")
    BtusPerHour(1).toString(BtusPerHour) should be("1.0 Btu/hr")
    ErgsPerSecond(1).toString(ErgsPerSecond) should be("1.0 erg/s")
    SolarLuminosities(1).toString(SolarLuminosities) should be("1.0 L☉")
  }

  it should "return Energy when multiplied by Time" in {
    Watts(1) * Hours(1) should be(WattHours(1))
  }

  behavior of "PowerConversions"

  it should "provide aliases for single unit values" in {
    import PowerConversions._

    milliwatt should be(Milliwatts(1))
    mW should be(Milliwatts(1))
    watt should be(Watts(1))
    W should be(Watts(1))
    kilowatt should be(Kilowatts(1))
    kW should be(Kilowatts(1))
    megawatt should be(Megawatts(1))
    MW should be(Megawatts(1))
    gigawatt should be(Gigawatts(1))
    GW should be(Gigawatts(1))
    solarLuminosity should be(SolarLuminosities(1))
  }

  it should "provide implicit conversion from Double" in {
    import PowerConversions._

    val d = 10d
    d.mW should be(Milliwatts(d))
    d.W should be(Watts(d))
    d.kW should be(Kilowatts(d))
    d.MW should be(Megawatts(d))
    d.GW should be(Gigawatts(d))
    d.milliwatts should be(Milliwatts(d))
    d.watts should be(Watts(d))
    d.kilowatts should be(Kilowatts(d))
    d.megawatts should be(Megawatts(d))
    d.gigawatts should be(Gigawatts(d))
    d.BTUph should be(BtusPerHour(d))
    d.ergsPerSecond should be(ErgsPerSecond(d))
    d.solarLuminosities should be(SolarLuminosities(d))
  }

  it should "provide implicit conversions from String" in {
    import PowerConversions._

    "10.22 mW".toPower.get should be(Milliwatts(10.22))
    "10.22 W".toPower.get should be(Watts(10.22))
    "10.22 kW".toPower.get should be(Kilowatts(10.22))
    "10.22 MW".toPower.get should be(Megawatts(10.22))
    "10.22 GW".toPower.get should be(Gigawatts(10.22))
    "10.22 Btu/hr".toPower.get should be(BtusPerHour(10.22))
    "10.22 erg/s".toPower.get should be(ErgsPerSecond(10.22))
    "10.22 L☉".toPower.get should be(SolarLuminosities(10.22))
    "10.22 zz".toPower.failed.get should be(QuantityParseException("Unable to parse Power", "10.22 zz"))
    "ZZ W".toPower.failed.get should be(QuantityParseException("Unable to parse Power", "ZZ W"))
  }

  it should "provide Numeric support in" in {
    import PowerConversions.PowerNumeric

    val ps = List(Watts(1000), Kilowatts(10), Megawatts(.1))
    ps.sum should be(Kilowatts(111))
  }
}
