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
    Henry(1).toHenry should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Henry(1)
    x.toHenry should be(1.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Henry(1).toString(Henry) should be("1.0 H")
  }

  it should "return MagneticFlux when multiplied by ElectricCurrent" in {
    Henry(1) * Amperes(1) should be(Webers(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Henry(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Inductance](ser)
    x should be(des)
  }

  behavior of "InductanceConversions"

  it should "provide aliases for single unit values" in {
    import InductanceConversions._

    henry should be(Henry(1))
  }

  it should "provide implicit conversion from Double" in {
    import InductanceConversions._

    val d = 10d
    d.henry should be(Henry(d))
  }
}
