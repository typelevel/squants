/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import org.scalatest.{ Matchers, FlatSpec }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class AngleSpec extends FlatSpec with Matchers {

  behavior of "Angle and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(Radians(1).toRadians == 1)
    assert(Degrees(1).toDegrees == 1)
    assert(Gradians(1).toGradians == 1)
    assert(Turns(1).toTurns == 1)
    assert(Arcminutes(1).toArcminutes == 1)
    assert(Arcseconds(1).toArcseconds == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Radians(1)
    assert(x.toRadians == 1d)
    assert(x.toDegrees == 180d / math.Pi)
    assert(x.toGradians == 200d / math.Pi)
    assert(x.toTurns == 1d / (math.Pi * 2d))
    assert(x.toArcminutes == 1d / (math.Pi / 10800d))
    assert(x.toArcseconds == 1d / ((math.Pi / 10800d) / 60d))
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Radians(1).toString(Radians) == "1.0 rad")
    assert(Degrees(1).toString(Degrees) == "1.0 °")
    assert(Gradians(1).toString(Gradians) == "1.0 grad")
    assert(Turns(1).toString(Turns) == "1.0 turns")
    assert(Arcminutes(1).toString(Arcminutes) == "1.0 amin")
    assert(Arcseconds(1).toString(Arcseconds) == "1.0 asec")
  }

  it should "return the cos of an Angle" in {
    assert(Radians(1).cos == math.cos(1))
  }

  it should "return the sin of an Angle" in {
    assert(Radians(1).sin == math.sin(1))
  }

  it should "return the acos of an Angle" in {
    assert(Radians(1).acos == math.acos(1))
  }

  it should "return the asin of an Angle" in {
    assert(Radians(1).asin == math.asin(1))
  }

  behavior of "AngleConversion"

  it should "provide aliases for single unit values" in {
    import AngleConversions._

    assert(radian == Radians(1))
    assert(degree == Degrees(1))
    assert(gradian == Gradians(1))
    assert(turn == Turns(1))
    assert(arcminute == Arcminutes(1))
    assert(arcsecond == Arcseconds(1))
  }

  it should "provide implicit conversion from Double" in {
    import AngleConversions._

    val d = 10d
    assert(d.radians == Radians(d))
    assert(d.degrees == Degrees(d))
    assert(d.gradians == Gradians(d))
    assert(d.turns == Turns(d))
    assert(d.arcminutes == Arcminutes(d))
    assert(d.arcseconds == Arcseconds(d))
  }
}
