/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import squants.QuantityParseException
import squants.photo.{ Candelas, Lumens }
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class SolidAngleSpec extends AnyFlatSpec with Matchers {

  behavior of "SolidAngle and its Units of Measure"

  it should "create values using UOM factories" in {
    SquaredRadians(1).toSquaredRadians should be(1)
  }

  it should "create values from properly formatted Strings" in {
    SolidAngle("10.22 sr").get should be(SquaredRadians(10.22))
    SolidAngle("10.22 zz").failed.get should be(QuantityParseException("Unable to parse SolidAngle", "10.22 zz"))
    SolidAngle("ZZ sr").failed.get should be(QuantityParseException("Unable to parse SolidAngle", "ZZ sr"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = SquaredRadians(1)
    x.toSquaredRadians should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    SquaredRadians(1).toString should be("1.0 sr")
  }

  it should "return LuminousFlux when multiplied by LuminousIntensity" in {
    SquaredRadians(1) * Candelas(1) == Lumens(1)
  }

  behavior of "SolidAngleConversion"

  it should "provide aliases for single unit values" in {
    import SolidAngleConversions._

    squaredRadian should be(SquaredRadians(1))
    steradian should be(SquaredRadians(1))
  }
  it should "provide implicit conversion from Double" in {
    import SolidAngleConversions._

    val d = 10d
    d.squaredRadians should be(SquaredRadians(d))
    d.steradians should be(SquaredRadians(d))
  }

  it should "provide Numeric support" in {
    import SolidAngleConversions.SolidAngleNumeric

    val sas = List(SquaredRadians(100), SquaredRadians(1))
    sas.sum should be(SquaredRadians(101))
  }
}
