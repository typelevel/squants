/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import org.scalatest.{ Matchers, FlatSpec }
import squants.photo.{ Lumens, Candelas }
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class SolidAngleSpec extends FlatSpec with Matchers {

  behavior of "SolidAngle and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(SquaredRadians(1).toSquaredRadians == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = SquaredRadians(1)
    assert(x.toSquaredRadians == 1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(SquaredRadians(1).toString == "1.0 sr")
  }

  it should "return LuminousFlux when multiplied by LuminousIntensity" in {
    SquaredRadians(1) * Candelas(1) == Lumens(1)
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = SquaredRadians(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[SolidAngle](ser)
    assert(x == des)
  }

  behavior of "SolidAngleConversion"

  it should "provide aliases for single unit values" in {
    import SolidAngleConversions._

    assert(squaredRadian == SquaredRadians(1))
    assert(steradian == SquaredRadians(1))
  }
  it should "provide implicit conversion from Double" in {
    import SolidAngleConversions._

    val d = 10d
    assert(d.squaredRadians == SquaredRadians(d))
    assert(d.steradians == SquaredRadians(d))
  }
}
