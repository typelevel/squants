/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import org.scalatest.{ Matchers, FlatSpec }
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class InductanceSpec extends FlatSpec with Matchers {

  behavior of "Inductance and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(Henry(1).toHenry == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Henry(1)
    assert(x.toHenry == 1.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Henry(1).toString(Henry) == "1.0 H")
  }

  it should "return MagneticFlux when multiplied by ElectricCurrent" in {
    assert(Henry(1) * Amperes(1) == Webers(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Henry(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Inductance](ser)
    assert(x == des)
  }

  behavior of "InductanceConversions"

  it should "provide aliases for single unit values" in {
    import InductanceConversions._

    assert(henry == Henry(1))
  }

  it should "provide implicit conversion from Double" in {
    import InductanceConversions._

    val d = 10d
    assert(d.henry == Henry(d))
  }
}
