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
    assert(Pascals(10).toPascals == 10)
    assert(Bars(10).toBars == 10)
    assert(PoundsPerSquareInch(1).toPoundsPerSquareInch == 1)
    assert(StandardAtmospheres(1).toStandardAtmospheres == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Pascals(1)
    assert(x.toPascals == 1)
    assert(x.toBars == .01)
    assert(x.toPoundsPerSquareInch == Newtons(1).toPoundForce / SquareMeters(1).toSquareInches)
    assert(x.toStandardAtmospheres == 1d / 101325d)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Pascals(1).toString(Pascals) == "1.0 Pa")
    assert(Bars(1).toString(Bars) == "1.0 bar")
    assert(PoundsPerSquareInch(1).toString(PoundsPerSquareInch) == "1.0 psi")
    assert(StandardAtmospheres(1).toString(StandardAtmospheres) == "1.0 atm")
  }

  it should "return Force when multiplied by Area" in {
    assert(Pascals(1) * SquareMeters(1) == Newtons(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Pascals(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Pressure](ser)
    assert(x == des)
  }

  behavior of "PressureConversions"

  it should "provide aliases for single unit values" in {
    import PressureConversions._

    assert(pascal == Pascals(1))
    assert(bar == Bars(1))
    assert(psi == PoundsPerSquareInch(1))
    assert(atm == StandardAtmospheres(1))
  }
  it should "provide implicit conversion from Double" in {
    import PressureConversions._

    val d = 10d
    assert(d.pascals == Pascals(d))
    assert(d.bars == Bars(d))
    assert(d.psi == PoundsPerSquareInch(d))
    assert(d.atm == StandardAtmospheres(d))
  }
}
