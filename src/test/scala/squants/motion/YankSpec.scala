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
import squants.time.Seconds
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class YankSpec extends FlatSpec with Matchers {

  behavior of "Yank and its Units of Measure"

  it should "create values using UOM factories" in {
    NewtonsPerSecond(1).toNewtonsPerSecond should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = NewtonsPerSecond(1)
    x.toNewtonsPerSecond should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    NewtonsPerSecond(1).toString(NewtonsPerSecond) should be("1.0 N/s")
  }

  it should "return Force when multiplied by Time" in {
    NewtonsPerSecond(1) * Seconds(10) should be(Newtons(10))
    NewtonsPerSecond(10) * Seconds(1) should be(Newtons(10))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = NewtonsPerSecond(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Yank](ser)
    x should be(des)
  }
}
