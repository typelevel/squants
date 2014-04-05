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
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ThermalCapacitySpec extends FlatSpec with Matchers {

  behavior of "ThermalCapacity and its Units of Measure"

  it should "create values using UOM factories" in {
    JoulesPerKelvin(1).toJoulesPerKelvin should be(1)
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

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = JoulesPerKelvin(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[ThermalCapacity](ser)
    x should be(des)
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
