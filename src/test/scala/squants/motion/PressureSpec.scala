/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.space.SquareMeters
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class PressureSpec extends FlatSpec with Matchers {

  behavior of "Pressure and its Units of Measure"

  it should "create values using UOM factories" in {
    Pascals(10).toPascals should be(10)
    Bars(10).toBars should be(10)
    PoundsPerSquareInch(1).toPoundsPerSquareInch should be(1)
    StandardAtmospheres(1).toStandardAtmospheres should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Pascals(1)
    x.toPascals should be(1)
    x.toBars should be(.01)
    x.toPoundsPerSquareInch should be(Newtons(1).toPoundForce / SquareMeters(1).toSquareInches)
    x.toStandardAtmospheres should be(1d / 101325d)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Pascals(1).toString(Pascals) should be("1.0 Pa")
    Bars(1).toString(Bars) should be("1.0 bar")
    PoundsPerSquareInch(1).toString(PoundsPerSquareInch) should be("1.0 psi")
    StandardAtmospheres(1).toString(StandardAtmospheres) should be("1.0 atm")
  }

  it should "return Force when multiplied by Area" in {
    Pascals(1) * SquareMeters(1) should be(Newtons(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Pascals(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Pressure](ser)
    x should be(des)
  }

  behavior of "PressureConversions"

  it should "provide aliases for single unit values" in {
    import PressureConversions._

    pascal should be(Pascals(1))
    bar should be(Bars(1))
    psi should be(PoundsPerSquareInch(1))
    atm should be(StandardAtmospheres(1))
  }
  it should "provide implicit conversion from Double" in {
    import PressureConversions._

    val d = 10d
    d.pascals should be(Pascals(d))
    d.bars should be(Bars(d))
    d.psi should be(PoundsPerSquareInch(d))
    d.atm should be(StandardAtmospheres(d))
  }
}
