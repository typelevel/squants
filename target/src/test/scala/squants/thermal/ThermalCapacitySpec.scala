/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.thermal

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.energy.Joules

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ThermalCapacitySpec extends FlatSpec with Matchers {

  behavior of "ThermalCapacity and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(JoulesPerKelvin(1).toJoulesPerKelvin == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = JoulesPerKelvin(1)
    assert(x.toJoulesPerKelvin == 1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(JoulesPerKelvin(1).toString == "1.0 J/K")
  }

  it should "return Energy when multiplied by Temperature" in {
    assert(JoulesPerKelvin(1) * Kelvin(1) == Joules(1))
  }

  behavior of "ThermalCapacityConversions"

  it should "provide aliases for single unit values" in {
    import ThermalCapacityConversions._

    assert(joulePerKelvin == JoulesPerKelvin(1))
  }

  it should "implicit conversion from Double" in {
    import ThermalCapacityConversions._

    val d = 10d
    assert(d.joulesPerKelvin == JoulesPerKelvin(d))
  }
}
