/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import squants.energy.{ Kilowatts, PowerUnit, Watts }
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class UnitOfMeasureSpec extends AnyFlatSpec with Matchers {

  /*
    Create a new UnitOfMeasure for an existing Quantity Type
   */
  object HorsePower extends PowerUnit {
    val symbol = "hp"
    val conversionFactor = Watts.conversionFactor * 746d
  }

  behavior of "UnitOfMeasure when extending for an existing Quantity"

  it should "create Quantities with the expected values" in {
    HorsePower(1).toWatts should be(746d)
  }

  it should "convert Quantities to this Unit" in {
    Watts(1492).to(HorsePower) should be(2d)
    Kilowatts(1.492).to(HorsePower) should be(2d)
  }

  it should "compare Quantities of different units" in {
    Watts(746) should be(HorsePower(1))
    Kilowatts(0.746) should be(HorsePower(1))
  }

  it should "return a properly formatted string" in {
    HorsePower(3.4).toString(HorsePower) should be("3.4 hp")
    Watts(746).toString(HorsePower) should be("1.0 hp")
  }
}
