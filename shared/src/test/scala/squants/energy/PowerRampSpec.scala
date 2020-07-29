/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import squants.{ QuantityParseException, MetricSystem }
import squants.time.Hours
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class PowerRampSpec extends AnyFlatSpec with Matchers {

  behavior of "PowerRamp and its Units of Measure"

  it should "create values using UOM factories" in {
    WattsPerHour(1).toWattsPerHour should be(1)
    WattsPerMinute(1).toWattsPerMinutes should be(1)
    KilowattsPerHour(1).toKilowattsPerHour should be(1)
    KilowattsPerMinute(1).toKilowattsPerMinute should be(1)
    MegawattsPerHour(1).toMegawattsPerHour should be(1)
    GigawattsPerHour(1).toGigawattsPerHour should be(1)
  }

  it should "create values from properly formatted Strings" in {
    PowerRamp("10.22 W/h").get should be(WattsPerHour(10.22))
    PowerRamp("10.22 W/m").get should be(WattsPerMinute(10.22))
    PowerRamp("10.22 kW/h").get should be(KilowattsPerHour(10.22))
    PowerRamp("10.22 kW/m").get should be(KilowattsPerMinute(10.22))
    PowerRamp("10.22 MW/h").get should be(MegawattsPerHour(10.22))
    PowerRamp("10.22 GW/h").get should be(GigawattsPerHour(10.22))
    PowerRamp("10.22 zz").failed.get should be(QuantityParseException("Unable to parse PowerRamp", "10.22 zz"))
    PowerRamp("ZZ W/h").failed.get should be(QuantityParseException("Unable to parse PowerRamp", "ZZ W/h"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattsPerHour(1)

    x.toWattsPerHour should be(1)
    x.toWattsPerMinutes should be(60)
    x.toKilowattsPerHour should be(1 / MetricSystem.Kilo)
    x.toKilowattsPerMinute should be(60 / MetricSystem.Kilo)
    x.toMegawattsPerHour should be(1 / MetricSystem.Mega)
    x.toGigawattsPerHour should be(1 / MetricSystem.Giga)

    WattsPerHour(1) should be(WattsPerMinute(60))
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    WattsPerHour(1).toString(WattsPerHour) should be("1.0 W/h")
    WattsPerMinute(1).toString(WattsPerMinute) should be("1.0 W/m")
    KilowattsPerHour(1).toString(KilowattsPerHour) should be("1.0 kW/h")
    KilowattsPerMinute(1).toString(KilowattsPerMinute) should be("1.0 kW/m")
    MegawattsPerHour(1).toString(MegawattsPerHour) should be("1.0 MW/h")
    GigawattsPerHour(1).toString(GigawattsPerHour) should be("1.0 GW/h")
  }

  it should "return Power when multiplied by Time" in {
    WattsPerHour(1) * Hours(1) should be(Watts(1))
  }

  behavior of "PowerRampConversions"

  it should "provide aliases for single unit values" in {
    import PowerRampConversions._

    wattPerHour should be(WattsPerHour(1))
    Wph should be(WattsPerHour(1))
    wattPerMinute should be(WattsPerMinute(1))
    Wpm should be(WattsPerMinute(1))
    kilowattPerHour should be(KilowattsPerHour(1))
    kWph should be(KilowattsPerHour(1))
    kilowattPerMinute should be(KilowattsPerMinute(1))
    kWpm should be(KilowattsPerMinute(1))
    megawattPerHour should be(MegawattsPerHour(1))
    MWph should be(MegawattsPerHour(1))
    gigawattPerHour should be(GigawattsPerHour(1))
    GWph should be(GigawattsPerHour(1))
  }

  it should "provide implicit conversion from Double" in {
    import PowerRampConversions._

    val d = 10d
    d.Wph should be(WattsPerHour(d))
    d.Wpm should be(WattsPerMinute(d))
    d.kWph should be(KilowattsPerHour(d))
    d.kWpm should be(KilowattsPerMinute(d))
    d.MWph should be(MegawattsPerHour(d))
    d.GWph should be(GigawattsPerHour(d))
  }

  it should "provide implicit conversions from String" in {
    import PowerRampConversions._

    "10.22 W/h".toPowerRamp.get should be(WattsPerHour(10.22))
    "10.22 W/m".toPowerRamp.get should be(WattsPerMinute(10.22))
    "10.22 kW/h".toPowerRamp.get should be(KilowattsPerHour(10.22))
    "10.22 kW/m".toPowerRamp.get should be(KilowattsPerMinute(10.22))
    "10.22 MW/h".toPowerRamp.get should be(MegawattsPerHour(10.22))
    "10.22 GW/h".toPowerRamp.get should be(GigawattsPerHour(10.22))
    "10.22 zz".toPowerRamp.failed.get should be(QuantityParseException("Unable to parse PowerRamp", "10.22 zz"))
    "ZZ W/h".toPowerRamp.failed.get should be(QuantityParseException("Unable to parse PowerRamp", "ZZ W/h"))
  }

  it should "provide Numeric support" in {
    import PowerRampConversions.PowerRampNumeric

    val prs = List(WattsPerHour(100), KilowattsPerHour(1))
    prs.sum should be(KilowattsPerHour(1.1))
  }
}
