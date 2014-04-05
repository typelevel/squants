/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.mass.Kilograms
import squants.time.Seconds
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MassFlowRateSpec extends FlatSpec with Matchers {

  behavior of "MassFlowRate and its Units of Measure"

  it should "create values using UOM factories" in {
    KilogramsPerSecond(1).toKilogramsPerSecond should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = KilogramsPerSecond(1)
    x.toKilogramsPerSecond should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    KilogramsPerSecond(1).toString should be("1.0 kg/s")
  }

  it should "return Mass when multiplied by Time" in {
    KilogramsPerSecond(1) * Seconds(1) should be(Kilograms(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = KilogramsPerSecond(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[MassFlowRate](ser)
    x should be(des)
  }
}
