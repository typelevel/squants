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
import squants.space.CubicMeters
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class EnergyDensitySpec extends FlatSpec with Matchers {

  behavior of "EnergyDensity and its Units of Measure"

  it should "create values using UOM factories" in {

    assert(JoulesPerCubicMeter(1).toJoulesPerCubicMeter == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = JoulesPerCubicMeter(1)

    assert(x.toJoulesPerCubicMeter == 1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(JoulesPerCubicMeter(1).toString(JoulesPerCubicMeter) == "1.0 j/m³")
  }

  it should "return Energy when multiplied by Volume" in {
    assert(JoulesPerCubicMeter(1) * CubicMeters(10) == Joules(10))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = JoulesPerCubicMeter(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[EnergyDensity](ser)
    assert(x == des)
  }

  behavior of "Conversions"

  it should "provide aliases for single unit values" is pending

  it should "provide implicit conversion from Double" is pending
}
