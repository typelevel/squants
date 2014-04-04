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
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ForceSpec extends FlatSpec with Matchers {

  behavior of "Force and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(Newtons(1).toNewtons == 1)
    assert(KilogramForce(1).toKilogramForce == 1)
    assert(PoundForce(1).toPoundForce == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Newtons(1)
    assert(x.toNewtons == 1)
    assert(x.toKilogramForce == Kilograms(1).toKilograms * MetersPerSecondSquared(1).toEarthGravities)
    assert(x.toPoundForce == Kilograms(1).toPounds * MetersPerSecondSquared(1).toEarthGravities)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Newtons(1).toString(Newtons) == "1.0 N")
    assert(KilogramForce(1).toString(KilogramForce) == "1.0 kgf")
    assert(PoundForce(1).toString(PoundForce) == "1.0 lbf")
  }

  it should "return Momentum when multiplied by Time" in {
    assert(Newtons(1) * Seconds(1) == NewtonSeconds(1))
  }

  it should "return Power when multiplied by Length" in {
    assert(Newtons(1) * Meters(1) == Joules(1))
  }

  it should "return Acceleration when divided by Mass" in {
    assert(Newtons(1) / Kilograms(1) == MetersPerSecondSquared(1))
  }

  it should "return Mass when divided by Acceleration" in {
    assert(Newtons(1) / MetersPerSecondSquared(1) == Kilograms(1))
  }

  it should "return Pressure when divided by Area" in {
    assert(Newtons(1) / SquareMeters(1) == Pascals(1))
  }

  it should "return Area when divided by Pressure" in {
    assert(Newtons(1) / Pascals(1) == SquareMeters(1))
  }

  it should "return Time when divided by Yank" in {
    assert(Newtons(1) / NewtonsPerSecond(1) == Seconds(1))
  }

  it should "return Yank when divided by Time" in {
    assert(Newtons(1) / Seconds(1) == NewtonsPerSecond(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Newtons(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Force](ser)
    assert(x == des)
  }

  behavior of "ForceConversions"

  it should "provide aliases for single unit values" in {
    import ForceConversions._

    assert(newton == Newtons(1))
    assert(kilogramForce == KilogramForce(1))
    assert(poundForce == PoundForce(1))
  }

  it should "provide implicit conversion from Double" in {
    import ForceConversions._

    val d = 10d
    assert(d.newtons == Newtons(d))
    assert(d.kilogramForce == KilogramForce(d))
    assert(d.poundForce == PoundForce(1))
    assert(d.lbf == PoundForce(1))
  }
}
