/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.thermal

import squants.energy.Joules
import squants.QuantityParseException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ThermalCapacitySpec extends AnyFlatSpec with Matchers {

  behavior of "ThermalCapacity and its Units of Measure"

  it should "create values using UOM factories" in {
    JoulesPerKelvin(1).toJoulesPerKelvin should be(1)
  }

  it should "create values from properly formatted Strings" in {
    ThermalCapacity("10.22 J/K").get should be(JoulesPerKelvin(10.22))
    ThermalCapacity("10.22 zz").failed.get should be(QuantityParseException("Unable to parse ThermalCapacity", "10.22 zz"))
    ThermalCapacity("zz J/K").failed.get should be(QuantityParseException("Unable to parse ThermalCapacity", "zz J/K"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = JoulesPerKelvin(1)
    x.toJoulesPerKelvin should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    JoulesPerKelvin(1).toString should be("1.0 J/K")
  }

  it should "return Energy when multiplied by Temperature" in {
    JoulesPerKelvin(1) * Kelvin(1) should be(Joules(1))
  }

  behavior of "ThermalCapacityConversions"

  it should "provide aliases for single unit values" in {
    import ThermalCapacityConversions._

    joulePerKelvin should be(JoulesPerKelvin(1))
  }

  it should "implicit conversion from Double" in {
    import ThermalCapacityConversions._

    val d = 10d
    d.joulesPerKelvin should be(JoulesPerKelvin(d))
  }

  it should "provide Numeric support" in {
    import ThermalCapacityConversions.ThermalCapacityNumeric

    val tcs = List(JoulesPerKelvin(100), JoulesPerKelvin(1))
    tcs.sum should be(JoulesPerKelvin(101))
  }
}
