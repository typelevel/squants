/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import org.scalatest.{ Matchers, FlatSpec }
import squants.QuantityStringParseException

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

  it should "create values from properly formatted Strings" in {
    Inductance("10.22 H").get should be(Henry(10.22))
    Inductance("10.22 zz").failed.get should be(QuantityStringParseException("Unable to parse Inductance", "10.22 zz"))
    Inductance("zz H").failed.get should be(QuantityStringParseException("Unable to parse Inductance", "zz H"))
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

  it should "provide Numeric support" in {
    import InductanceConversions.InductanceNumeric

    val is = List(Henry(100), Henry(10))
    is.sum should be(Henry(110))
  }
}
