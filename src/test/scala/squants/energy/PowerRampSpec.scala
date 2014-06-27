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
import squants.{ QuantityStringParseException, MetricSystem }
import squants.time.Hours

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class PowerRampSpec extends FlatSpec with Matchers {

  behavior of "PowerRamp and its Units of Measure"

  it should "create values using UOM factories" in {
    WattsPerHour(1).toWattsPerHour should be(1)
    KilowattsPerHour(1).toKilowattsPerHour should be(1)
    MegawattsPerHour(1).toMegawattsPerHour should be(1)
    GigawattsPerHour(1).toGigawattsPerHour should be(1)
  }

  it should "create values from properly formatted Strings" in {
    PowerRamp("10.22 W/h").get should be(WattsPerHour(10.22))
    PowerRamp("10.22 kW/h").get should be(KilowattsPerHour(10.22))
    PowerRamp("10.22 MW/h").get should be(MegawattsPerHour(10.22))
    PowerRamp("10.22 GW/h").get should be(GigawattsPerHour(10.22))
    PowerRamp("10.22 zz").failed.get should be(QuantityStringParseException("Unable to parse PowerRamp", "10.22 zz"))
    PowerRamp("ZZ W/h").failed.get should be(QuantityStringParseException("Unable to parse PowerRamp", "ZZ W/h"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattsPerHour(1)

    x.toWattsPerHour should be(1)
    x.toKilowattsPerHour should be(1 / MetricSystem.Kilo)
    x.toMegawattsPerHour should be(1 / MetricSystem.Mega)
    x.toGigawattsPerHour should be(1 / MetricSystem.Giga)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    WattsPerHour(1).toString(WattsPerHour) should be("1.0 W/h")
    KilowattsPerHour(1).toString(KilowattsPerHour) should be("1.0 kW/h")
    MegawattsPerHour(1).toString(MegawattsPerHour) should be("1.0 MW/h")
    GigawattsPerHour(1).toString(GigawattsPerHour) should be("1.0 GW/h")
  }

  it should "return Power when multiplied by Time" in {
    WattsPerHour(1) * Hours(1) should be(Watts(1))
  }

  behavior of "PowerRampConversions"

  it should "provide aliases for single unit values" in {
    import PowerRampConversions._

    wattsPerHour should be(WattsPerHour(1))
    Wph should be(WattsPerHour(1))
    kilowattsPerHour should be(KilowattsPerHour(1))
    kWph should be(KilowattsPerHour(1))
    megawattsPerHour should be(MegawattsPerHour(1))
    MWph should be(MegawattsPerHour(1))
    gigawattsPerHour should be(GigawattsPerHour(1))
    GWph should be(GigawattsPerHour(1))
  }

  it should "provide implicit conversion from Double" in {
    import PowerRampConversions._

    val d = 10d
    d.Wph should be(WattsPerHour(d))
    d.kWph should be(KilowattsPerHour(d))
    d.MWph should be(MegawattsPerHour(d))
    d.GWph should be(GigawattsPerHour(d))
  }

  it should "provide implicit conversions from String" in {
    import PowerRampConversions._

    "10.22 W/h".toPowerRamp.get should be(WattsPerHour(10.22))
    "10.22 kW/h".toPowerRamp.get should be(KilowattsPerHour(10.22))
    "10.22 MW/h".toPowerRamp.get should be(MegawattsPerHour(10.22))
    "10.22 GW/h".toPowerRamp.get should be(GigawattsPerHour(10.22))
    "10.22 zz".toPowerRamp.failed.get should be(QuantityStringParseException("Unable to parse PowerRamp", "10.22 zz"))
    "ZZ W/h".toPowerRamp.failed.get should be(QuantityStringParseException("Unable to parse PowerRamp", "ZZ W/h"))
  }

  it should "provide Numeric support" in {
    import PowerRampConversions.PowerRampNumeric

    val prs = List(WattsPerHour(100), KilowattsPerHour(1))
    prs.sum should be(KilowattsPerHour(1.1))
  }
}
