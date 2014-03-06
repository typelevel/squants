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
class PowerSpec extends FlatSpec with Matchers {

  behavior of "Power and its Units of Measure"

  it should "create values using UOM factories" in {

    assert(Watts(1).toWatts == 1)
    assert(Milliwatts(1).toMilliwatts == 1)
    assert(Kilowatts(1).toKilowatts == 1)
    assert(Megawatts(1).toMegawatts == 1)
    assert(Gigawatts(1).toGigawatts == 1)
    assert(BtusPerHour(1).toBtusPerHour == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Watts(1)

    assert(x.toWatts == 1)
    assert(x.toMilliwatts == 1 / MetricSystem.Milli)
    assert(x.toKilowatts == 1 / MetricSystem.Kilo)
    assert(x.toMegawatts == 1 / MetricSystem.Mega)
    assert(x.toGigawatts == 1 / MetricSystem.Giga)
    assert(x.toBtusPerHour == 1 / EnergyConversions.btuMultiplier)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Watts(1).toString(Watts) == "1.0 W")
    assert(Milliwatts(1).toString(Milliwatts) == "1.0 mW")
    assert(Kilowatts(1).toString(Kilowatts) == "1.0 kW")
    assert(Megawatts(1).toString(Megawatts) == "1.0 MW")
    assert(Gigawatts(1).toString(Gigawatts) == "1.0 GW")
    assert(BtusPerHour(1).toString(BtusPerHour) == "1.0 Btu/hr")
  }

  it should "return Energy when multiplied by Time" in {
    assert(Watts(1) * Hours(1) == WattHours(1))
  }

  behavior of "PowerConversions"

  it should "provide aliases for single unit values" in {
    import PowerConversions._

    assert(milliwatt == Milliwatts(1))
    assert(mW == Milliwatts(1))
    assert(watt == Watts(1))
    assert(W == Watts(1))
    assert(kilowatt == Kilowatts(1))
    assert(kW == Kilowatts(1))
    assert(megawatt == Megawatts(1))
    assert(MW == Megawatts(1))
    assert(gigawatt == Gigawatts(1))
    assert(GW == Gigawatts(1))
  }

  it should "provide implicit conversion from Double" in {
    import PowerConversions._

    val d = 10d
    assert(d.mW == Milliwatts(d))
    assert(d.W == Watts(d))
    assert(d.kW == Kilowatts(d))
    assert(d.MW == Megawatts(d))
    assert(d.GW == Gigawatts(d))
    assert(d.milliwatts == Milliwatts(d))
    assert(d.watts == Watts(d))
    assert(d.kilowatts == Kilowatts(d))
    assert(d.megawatts == Megawatts(d))
    assert(d.gigawatts == Gigawatts(d))
    assert(d.BTUph == BtusPerHour(d))
  }

  it should "provide implicit conversions from String" in {
    import PowerConversions._

    assert("10.22 mW".toPower.get == Milliwatts(10.22))
    assert("10.22 W".toPower.get == Watts(10.22))
    assert("10.22 kW".toPower.get == Kilowatts(10.22))
    assert("10.22 MW".toPower.get == Megawatts(10.22))
    assert("10.22 GW".toPower.get == Gigawatts(10.22))
    assert("10.22 Btu/hr".toPower.get == BtusPerHour(10.22))
  }
}
