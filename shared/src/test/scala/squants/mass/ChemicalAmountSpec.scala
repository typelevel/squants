/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import squants.QuantityParseException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ChemicalAmountSpec extends AnyFlatSpec with Matchers {

  behavior of "ChemicalAmount and its Units of Measure"

  it should "create values using UOM factories" in {
    Moles(1).toMoles should be(1)
    PoundMoles(1).toPoundMoles should be(1)
  }

  it should "create values from properly formatted Strings" in {
    ChemicalAmount("10.22 mol").get should be(Moles(10.22))
    ChemicalAmount("10.22 lb-mol").get should be(PoundMoles(10.22))
    ChemicalAmount("10.45 zz").failed.get should be(QuantityParseException("Unable to parse ChemicalAmount", "10.45 zz"))
    ChemicalAmount("zz mol").failed.get should be(QuantityParseException("Unable to parse ChemicalAmount", "zz mol"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Moles(1)
    x.toMoles should be(1)
    x.toPoundMoles should be(1d / 453.59237)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Moles(1).toString(Moles) should be("1.0 mol")
    PoundMoles(1).toString(PoundMoles) should be("1.0 lb-mol")
  }

  behavior of "ChemicalAmountConversions"

  it should "provide aliases for single unit values" in {
    import ChemicalAmountConversions._

    mole should be(Moles(1))
    poundMole should be(PoundMoles(1))
  }

  it should "provide implicit conversion from Double" in {
    import ChemicalAmountConversions._

    val d = 10d
    d.moles should be(Moles(d))
    d.poundMoles should be(PoundMoles(d))
  }

  it should "provide Numeric support" in {
    import ChemicalAmountConversions.ChemicalAmountNumeric

    val cas = List(Moles(100), Moles(10))
    cas.sum should be(Moles(110))
  }
}
