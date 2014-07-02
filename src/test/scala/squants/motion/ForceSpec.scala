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
import squants.energy.Joules
import squants.space.{ SquareMeters, Meters }
import squants.{ QuantityStringParseException, CustomMatchers }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ForceSpec extends FlatSpec with Matchers with CustomMatchers {

  behavior of "Force and its Units of Measure"

  it should "create values using UOM factories" in {
    Newtons(1).toNewtons should be(1)
    KilogramForce(1).toKilogramForce should be(1)
    PoundForce(1).toPoundForce should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Force("10.22 N").get should be(Newtons(10.22))
    Force("10.22 kgf").get should be(KilogramForce(10.22))
    Force("10.22 lbf").get should be(PoundForce(10.22))
    Force("10.22 zz").failed.get should be(QuantityStringParseException("Unable to parse Force", "10.22 zz"))
    Force("zz N").failed.get should be(QuantityStringParseException("Unable to parse Force", "zz N"))
  }

  it should "properly convert to all supported Units of Measure" in {
    implicit val tolerance = 0.0000000000001
    val x = Newtons(1)
    x.toNewtons should be(1)
    x.toKilogramForce should be(Kilograms(1).toKilograms * MetersPerSecondSquared(1).toEarthGravities)
    x.toPoundForce should beApproximately(Kilograms(1).toPounds * MetersPerSecondSquared(1).toEarthGravities)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Newtons(1).toString(Newtons) should be("1.0 N")
    KilogramForce(1).toString(KilogramForce) should be("1.0 kgf")
    PoundForce(1).toString(PoundForce) should be("1.0 lbf")
  }

  it should "return Momentum when multiplied by Time" in {
    Newtons(1) * Seconds(1) should be(NewtonSeconds(1))
  }

  it should "return Power when multiplied by Length" in {
    Newtons(1) * Meters(1) should be(Joules(1))
  }

  it should "return Acceleration when divided by Mass" in {
    Newtons(1) / Kilograms(1) should be(MetersPerSecondSquared(1))
  }

  it should "return Mass when divided by Acceleration" in {
    Newtons(1) / MetersPerSecondSquared(1) should be(Kilograms(1))
  }

  it should "return Pressure when divided by Area" in {
    Newtons(1) / SquareMeters(1) should be(Pascals(1))
  }

  it should "return Area when divided by Pressure" in {
    Newtons(1) / Pascals(1) should be(SquareMeters(1))
  }

  it should "return Time when divided by Yank" in {
    Newtons(1) / NewtonsPerSecond(1) should be(Seconds(1))
  }

  it should "return Yank when divided by Time" in {
    Newtons(1) / Seconds(1) should be(NewtonsPerSecond(1))
  }

  behavior of "ForceConversions"

  it should "provide aliases for single unit values" in {
    import ForceConversions._

    newton should be(Newtons(1))
    kilogramForce should be(KilogramForce(1))
    poundForce should be(PoundForce(1))
  }

  it should "provide implicit conversion from Double" in {
    import ForceConversions._

    val d = 10d
    d.newtons should be(Newtons(d))
    d.kilogramForce should be(KilogramForce(d))
    d.poundForce should be(PoundForce(1))
    d.lbf should be(PoundForce(1))
  }

  it should "provide Numeric support" in {
    import ForceConversions.ForceNumeric

    val fs = List(Newtons(100), Newtons(10))
    fs.sum should be(Newtons(110))
  }
}
