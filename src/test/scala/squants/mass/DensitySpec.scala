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
import squants.space.CubicMeters
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class DensitySpec extends FlatSpec with Matchers {

  behavior of "Density and its Units of Measure"

  it should "create values using UOM factories" in {
    KilogramsPerCubicMeter(1).toKilogramsPerCubicMeter should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = KilogramsPerCubicMeter(1)
    x.toKilogramsPerCubicMeter should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    KilogramsPerCubicMeter(1).toString should be("1.0 kg/m³")
  }

  it should "return Mass when multiplied by Volume" in {
    KilogramsPerCubicMeter(1) * CubicMeters(1) should be(Kilograms(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = KilogramsPerCubicMeter(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Density](ser)
    x should be(des)
  }
}
