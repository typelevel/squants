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
import squants.mass.Kilograms
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MomentumSpec extends FlatSpec with Matchers {

  behavior of "Momentum and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(NewtonSeconds(1).toNewtonSeconds == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = NewtonSeconds(1)
    assert(x.toNewtonSeconds == 1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(NewtonSeconds(1).toString == "1.0 Ns")
  }

  it should "return Force when divided by Time" in {
    assert(NewtonSeconds(1) / Seconds(1) == Newtons(1))
  }

  it should "return Time when divided by Force" in {
    assert(NewtonSeconds(1) / Newtons(1) == Seconds(1))
  }

  it should "return Mass when divided by Velocity" in {
    assert(NewtonSeconds(1) / MetersPerSecond(1) == Kilograms(1))
  }

  it should "return Velocity when divided by Mass" in {
    assert(NewtonSeconds(1) / Kilograms(1) == MetersPerSecond(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = NewtonSeconds(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Momentum](ser)
    assert(x == des)
  }

  behavior of "MomentumConversions"

  it should "provide aliases for single unit values" in {
    import MomentumConversions._

    assert(newtonSecond == NewtonSeconds(1))
  }

  it should "provide implicit conversion from Double" in {
    import MomentumConversions._

    val d = 10d
    assert(d.newtonSeconds == NewtonSeconds(d))
  }
}
