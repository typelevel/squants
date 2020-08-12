/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import squants.space.CubicMeters
import squants.QuantityParseException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
  * @author  Nicolas Vinuesa
  * @since   1.4
  *
  */
class PowerDensitySpec extends AnyFlatSpec with Matchers {

  behavior of "PowerDensity and its Units of Measure"

  it should "create values using UOM factories" in {
    WattsPerCubicMeter(1).toWattsPerCubicMeter should be(1)
  }

  it should "create values from properly formatted Strings" in {
    PowerDensity("10.22 W/m³").get should be(WattsPerCubicMeter(10.22))
    PowerDensity("10.22 zz").failed.get should be(QuantityParseException("Unable to parse PowerDensity", "10.22 zz"))
    PowerDensity("ZZ W/m³").failed.get should be(QuantityParseException("Unable to parse PowerDensity", "ZZ W/m³"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattsPerCubicMeter(1)

    x.toWattsPerCubicMeter should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    WattsPerCubicMeter(1).toString(WattsPerCubicMeter) should be("1.0 W/m³")
  }

  it should "return Power when multiplied by Volume" in {
    WattsPerCubicMeter(1) * CubicMeters(10) should be(Watts(10))
  }

  behavior of "PowerDensityConversions"

  it should "provide aliases for single unit values" in {
    import PowerDensityConversions._

    wattPerCubicMeter should be(WattsPerCubicMeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import PowerDensityConversions._

    val d = 10.22d
    d.wattsPerCubicMeter should be(WattsPerCubicMeter(d))
  }

  it should "provide Numeric support" in {
    import PowerDensityConversions.PowerDensityNumeric

    val eds = List(WattsPerCubicMeter(10), WattsPerCubicMeter(100))
    eds.sum should be(WattsPerCubicMeter(110))
  }
}
