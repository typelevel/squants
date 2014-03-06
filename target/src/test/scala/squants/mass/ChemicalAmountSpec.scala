/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ChemicalAmountSpec extends FlatSpec with Matchers {

  behavior of "ChemicalAmount and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(Moles(1).toMoles == 1)
    assert(PoundMoles(1).toPoundMoles == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Moles(1)
    assert(x.toMoles == 1)
    assert(x.toPoundMoles == 1d / 453.59237)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Moles(1).toString(Moles) == "1.0 mol")
    assert(PoundMoles(1).toString(PoundMoles) == "1.0 lb-mol")
  }

  behavior of "ChemicalAmountConversions"

  it should "provide aliases for single unit values" in {
    import ChemicalAmountConversions._

    assert(mole == Moles(1))
    assert(poundMole == PoundMoles(1))
  }

  it should "provide implicit conversion from Double" in {
    import ChemicalAmountConversions._

    val d = 10d
    assert(d.moles == Moles(d))
    assert(d.poundMoles == PoundMoles(d))
  }
}
