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

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class PowerRampSpec extends FlatSpec with Matchers {

  behavior of "PowerRamp and its Units of Measure"

  it should "create values using UOM factories" in {

    assert(WattsPerHour(1).toWattsPerHour == 1)
    assert(KilowattsPerHour(1).toKilowattsPerHour == 1)
    assert(MegawattsPerHour(1).toMegawattsPerHour == 1)
    assert(GigawattsPerHour(1).toGigawattsPerHour == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattsPerHour(1)

    assert(x.toWattsPerHour == 1)
    assert(x.toKilowattsPerHour == 1 / MetricSystem.Kilo)
    assert(x.toMegawattsPerHour == 1 / MetricSystem.Mega)
    assert(x.toGigawattsPerHour == 1 / MetricSystem.Giga)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(WattsPerHour(1).toString(WattsPerHour) == "1.0 W/h")
    assert(KilowattsPerHour(1).toString(KilowattsPerHour) == "1.0 kW/h")
    assert(MegawattsPerHour(1).toString(MegawattsPerHour) == "1.0 MW/h")
    assert(GigawattsPerHour(1).toString(GigawattsPerHour) == "1.0 GW/h")
  }

  it should "return Power when multiplied by Time" in {
    assert(WattsPerHour(1) * Hours(1) == Watts(1))
  }

  behavior of "PowerRampConversions"

  it should "provide aliases for single unit values" in {
    import PowerRampConversions._

    assert(wattsPerHour == WattsPerHour(1))
    assert(Wph == WattsPerHour(1))
    assert(kilowattsPerHour == KilowattsPerHour(1))
    assert(kWph == KilowattsPerHour(1))
    assert(megawattsPerHour == MegawattsPerHour(1))
    assert(MWph == MegawattsPerHour(1))
    assert(gigawattsPerHour == GigawattsPerHour(1))
    assert(GWph == GigawattsPerHour(1))
  }

  it should "provide implicit conversion from Double" in {
    import PowerRampConversions._

    val d = 10d
    assert(d.Wph == WattsPerHour(d))
    assert(d.kWph == KilowattsPerHour(d))
    assert(d.MWph == MegawattsPerHour(d))
    assert(d.GWph == GigawattsPerHour(d))
  }

  it should "provide implicit conversions from String" in {
    import PowerRampConversions._

    assert("10.22 W/h".toPowerRamp.get == WattsPerHour(10.22))
    assert("10.22 kW/h".toPowerRamp.get == KilowattsPerHour(10.22))
    assert("10.22 MW/h".toPowerRamp.get == MegawattsPerHour(10.22))
    assert("10.22 GW/h".toPowerRamp.get == GigawattsPerHour(10.22))
  }
}
