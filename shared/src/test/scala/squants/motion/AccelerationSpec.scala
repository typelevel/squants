/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants.space.Meters
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
class AccelerationSpec extends AnyFlatSpec with Matchers {

  behavior of "Acceleration and its Units of Measure"

  it should "create values using UOM factories" in {
    MillimetersPerSecondSquared(1).toMillimetersPerSecondSquared should be(1)
    MetersPerSecondSquared(1).toMetersPerSecondSquared should be(1)
    FeetPerSecondSquared(1).toFeetPerSecondSquared should be(1)
    UsMilesPerHourSquared(1).toUsMilesPerHourSquared should be(1)
    EarthGravities(1).toEarthGravities should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Acceleration("10.22 mm/s²").get should be(MillimetersPerSecondSquared(10.22))
    Acceleration("10.22 m/s²").get should be(MetersPerSecondSquared(10.22))
    Acceleration("10.22 ft/s²").get should be(FeetPerSecondSquared(10.22))
    Acceleration("10.22 mph²").get should be(UsMilesPerHourSquared(10.22))
    Acceleration("10.22 g").get should be(EarthGravities(10.22))
    Acceleration("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Acceleration", "10.22 zz"))
    Acceleration("zz g").failed.get should be(QuantityParseException("Unable to parse Acceleration", "zz g"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = MetersPerSecondSquared(1)
    x.toMetersPerSecondSquared should be(1)
    x.toFeetPerSecondSquared should be(Meters(1).toFeet)
    x.toMillimetersPerSecondSquared should be(Meters(1).toMillimeters)
    x.toUsMilesPerHourSquared should be(Meters(1).toUsMiles / (Seconds(1).toHours * Seconds(1).toHours))
    x.toEarthGravities should be(1d / squants.motion.StandardEarthGravity.toMetersPerSecondSquared)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    MetersPerSecondSquared(1).toString(MetersPerSecondSquared) should be("1.0 m/s²")
    MillimetersPerSecondSquared(1).toString(MillimetersPerSecondSquared) should be("1.0 mm/s²")
    FeetPerSecondSquared(1).toString(FeetPerSecondSquared) should be("1.0 ft/s²")
    UsMilesPerHourSquared(1).toString(UsMilesPerHourSquared) should be("1.0 mph²")
    EarthGravities(1).toString(EarthGravities) should be("1.0 g")
  }

  it should "return Velocity when multiplied by Time" in {
    MetersPerSecondSquared(1) * Seconds(1) should be(MetersPerSecond(1))
  }

  it should "Force when multiplied by Mass" in {
    MetersPerSecondSquared(1) * Kilograms(1) should be(Newtons(1))
  }

  it should "return Jerk when divided by Time" in {
    MetersPerSecondSquared(1) / Seconds(1) should be(MetersPerSecondCubed(1))
  }

  it should "return Time when divided by Jerk" in {
    MetersPerSecondSquared(1) / MetersPerSecondCubed(1) should be(Seconds(1))
  }

  it should "return proper results on analyze" is pending

  behavior of "VelocityConversions"

  it should "provide implicit conversion from Double" in {
    import AccelerationConversions._

    val d = 10d
    d.mpss should be(MetersPerSecondSquared(d))
    d.fpss should be(FeetPerSecondSquared(d))
  }

  it should "provide Numeric support" in {
    import AccelerationConversions.AccelerationNumeric

    val as = List(MetersPerSecondSquared(100), MetersPerSecondSquared(10))
    as.sum should be(MetersPerSecondSquared(110))
  }
}
