/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants.space.Meters
import squants.time.Seconds
import squants.QuantityParseException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class JerkSpec extends AnyFlatSpec with Matchers {

  behavior of "Jerk and its Units of Measure"

  it should "create values using UOM factories" in {
    MetersPerSecondCubed(1).toMetersPerSecondCubed should be(1)
    FeetPerSecondCubed(1).toFeetPerSecondCubed should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Jerk("10.22 m/s³").get should be(MetersPerSecondCubed(10.22))
    Jerk("10.22 ft/s³").get should be(FeetPerSecondCubed(10.22))
    Jerk("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Jerk", "10.22 zz"))
    Jerk("zz m/s³").failed.get should be(QuantityParseException("Unable to parse Jerk", "zz m/s³"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = MetersPerSecondCubed(1)
    x.toMetersPerSecondCubed should be(1)
    x.toFeetPerSecondCubed should be(Meters(1).toFeet)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    MetersPerSecondCubed(1).toString(MetersPerSecondCubed) should be("1.0 m/s³")
    FeetPerSecondCubed(1).toString(FeetPerSecondCubed) should be("1.0 ft/s³")
  }

  it should "return Acceleration when multiplied by Time" in {
    MetersPerSecondCubed(1) * Seconds(1) should be(MetersPerSecondSquared(1))
  }

  behavior of "JerkConversions"

  it should "provide aliases for single unit values" in {
    import JerkConversions._

    meterPerSecondCubed should be(MetersPerSecondCubed(1))
    footPerSecondCubed should be(FeetPerSecondCubed(1))
  }

  it should "provide implicit conversion from Double" in {
    import JerkConversions._

    val d = 10.22d
    d.metersPerSecondCubed should be(MetersPerSecondCubed(d))
    d.feetPerSecondCubed should be(FeetPerSecondCubed(d))
  }

  it should "provide Numeric support" in {
    import JerkConversions.JerkNumeric

    val js = List(MetersPerSecondCubed(100), MetersPerSecondCubed(10))
    js.sum should be(MetersPerSecondCubed(110))
  }
}
