/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants.time.Seconds
import squants.mass.Kilograms
import squants.QuantityParseException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MomentumSpec extends AnyFlatSpec with Matchers {

  behavior of "Momentum and its Units of Measure"

  it should "create values using UOM factories" in {
    NewtonSeconds(1).toNewtonSeconds should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Momentum("10.22 Ns").get should be(NewtonSeconds(10.22))
    Momentum("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Momentum", "10.22 zz"))
    Momentum("zz Ns").failed.get should be(QuantityParseException("Unable to parse Momentum", "zz Ns"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = NewtonSeconds(1)
    x.toNewtonSeconds should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    NewtonSeconds(1).toString should be("1.0 Ns")
  }

  it should "return Force when divided by Time" in {
    NewtonSeconds(1) / Seconds(1) should be(Newtons(1))
  }

  it should "return Time when divided by Force" in {
    NewtonSeconds(1) / Newtons(1) should be(Seconds(1))
  }

  it should "return Mass when divided by Velocity" in {
    NewtonSeconds(1) / MetersPerSecond(1) should be(Kilograms(1))
  }

  it should "return Velocity when divided by Mass" in {
    NewtonSeconds(1) / Kilograms(1) should be(MetersPerSecond(1))
  }

  behavior of "MomentumConversions"

  it should "provide aliases for single unit values" in {
    import MomentumConversions._

    newtonSecond should be(NewtonSeconds(1))
  }

  it should "provide implicit conversion from Double" in {
    import MomentumConversions._

    val d = 10d
    d.newtonSeconds should be(NewtonSeconds(d))
  }

  it should "provide Numeric support" in {
    import MomentumConversions.MomentumNumeric

    val ms = List(NewtonSeconds(100), NewtonSeconds(10))
    ms.sum should be(NewtonSeconds(110))
  }
}
