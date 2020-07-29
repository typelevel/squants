/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import squants.QuantityParseException
import squants.motion.RadiansPerSecond
import squants.time.Seconds
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class AngleSpec extends AnyFlatSpec with Matchers {

  behavior of "Angle and its Units of Measure"

  it should "create values using UOM factories" in {
    Radians(1).toRadians should be(1)
    Degrees(1).toDegrees should be(1)
    Gradians(1).toGradians should be(1)
    Turns(1).toTurns should be(1)
    Arcminutes(1).toArcminutes should be(1)
    Arcseconds(1).toArcseconds should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Angle("10.22 rad").get should be(Radians(10.22))
    Angle("10.22°").get should be(Degrees(10.22))
    Angle("10.22 grad").get should be(Gradians(10.22))
    Angle("10.22 turns").get should be(Turns(10.22))
    Angle("10.22 amin").get should be(Arcminutes(10.22))
    Angle("10.22 asec").get should be(Arcseconds(10.22))
    Angle("10.33 zz").failed.get should be(QuantityParseException("Unable to parse Angle", "10.33 zz"))
    Angle("ZZ rad").failed.get should be(QuantityParseException("Unable to parse Angle", "ZZ rad"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Radians(1)
    x.toRadians should be(1d)
    x.toDegrees should be(180d / math.Pi)
    x.toGradians should be(200d / math.Pi)
    x.toTurns should be(1d / (math.Pi * 2d))
    x.toArcminutes should be(1d / (math.Pi / 10800d))
    x.toArcseconds should be(1d / ((math.Pi / 10800d) / 60d))
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Radians(1).toString(Radians) should be("1.0 rad")
    Degrees(1).toString(Degrees) should be("1.0 °")
    Gradians(1).toString(Gradians) should be("1.0 grad")
    Turns(1).toString(Turns) should be("1.0 turns")
    Arcminutes(1).toString(Arcminutes) should be("1.0 amin")
    Arcseconds(1).toString(Arcseconds) should be("1.0 asec")
  }

  it should "return the cos of an Angle" in {
    Radians(1).cos should be(math.cos(1))
  }

  it should "return the sin of an Angle" in {
    Radians(1).sin should be(math.sin(1))
  }

  it should "return the acos of an Angle" in {
    Radians(1).acos should be(math.acos(1))
  }

  it should "return the asin of an Angle" in {
    Radians(1).asin should be(math.asin(1))
  }

  it should "return AngularVelocity when divided by Time" in {
    Radians(1) / Seconds(1) should be(RadiansPerSecond(1))
  }

  it should "return Time when divided by AngularVelocity" in {
    Radians(1) / RadiansPerSecond(1) should be(Seconds(1))
  }

  it should "return Length when angle is arc on circle" in {
    Radians(1) onRadius Meters(1) should be(Meters(1))
  }

  behavior of "AngleConversion"

  it should "provide aliases for single unit values" in {
    import AngleConversions._

    radian should be(Radians(1))
    degree should be(Degrees(1))
    gradian should be(Gradians(1))
    turn should be(Turns(1))
    arcminute should be(Arcminutes(1))
    arcsecond should be(Arcseconds(1))
  }

  it should "provide implicit conversion from Double" in {
    import AngleConversions._

    val d = 10d
    d.radians should be(Radians(d))
    d.degrees should be(Degrees(d))
    d.gradians should be(Gradians(d))
    d.turns should be(Turns(d))
    d.arcminutes should be(Arcminutes(d))
    d.arcseconds should be(Arcseconds(d))
  }

  it should "provide Numeric support" in {
    import AngleConversions.AngleNumeric

    val as = List(Radians(100), Radians(1))
    as.sum should be(Radians(101))
  }
}
