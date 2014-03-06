/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.mass.Kilograms

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class SpecificEnergySpec extends FlatSpec with Matchers {

  behavior of "SpecificEnergy and its Units of Measure"

  it should "create values using UOM factories" in {

    assert(Grays(1).toGrays == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Grays(1)

    assert(x.toGrays == 1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Grays(1).toString(Grays) == "1.0 Gy")
  }

  it should "return Energy when multiplied by Mass" in {
    assert(Grays(1) * Kilograms(10) == Joules(10))
  }

  behavior of "Conversions"

  it should "provide aliases for single unit values" in {
    import SpecificEnergyConversions._

    assert(gray == SpecificEnergy(1))
  }

  it should "provide implicit conversion from Double" in {
    import SpecificEnergyConversions._

    val d = 10d
    assert(d.grays == SpecificEnergy(d))
  }
}
