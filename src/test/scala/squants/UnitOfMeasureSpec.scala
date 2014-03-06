/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import org.scalatest.{ Matchers, FlatSpec }
import squants.energy.{ Kilowatts, Watts, PowerUnit }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class UnitOfMeasureSpec extends FlatSpec with Matchers {

  /*
    Create a new UnitOfMeasure for an existing Quantity Type
   */
  object HorsePower extends PowerUnit {
    val symbol = "hp"
    val multiplier = Watts.multiplier * 746d
  }

  behavior of "UnitOfMeasure when extending for an existing Quantity"

  it should "create Quantities with the expected values" in {
    assert(HorsePower(1).toWatts == 746d)
  }

  it should "convert Quantities to this Unit" in {
    assert(Watts(1492).to(HorsePower) == 2d)
    assert(Kilowatts(1.492).to(HorsePower) == 2d)
  }

  it should "compare Quantities of different units" in {
    assert(Watts(746) == HorsePower(1))
    assert(Kilowatts(0.746) == HorsePower(1))
  }

  it should "return a properly formatted string" in {
    assert(HorsePower(3.4).toString(HorsePower) == "3.4 hp")
    assert(Watts(746).toString(HorsePower) == "1.0 hp")
  }
}
