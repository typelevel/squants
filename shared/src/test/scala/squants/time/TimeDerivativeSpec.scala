/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.time

import squants.CustomMatchers
import squants.motion.{MetersPerSecond, UsMilesPerHour}
import squants.space.{Meters, UsMiles}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class TimeDerivativeSpec extends AnyFlatSpec with Matchers with CustomMatchers {

  behavior of "Time Derivatives and Integrals as implemented in Distance and Velocity"

  it should "satisfy Derivative = Integral / Time" in {
    implicit val tolerance = UsMilesPerHour(0.0000000000001)
    UsMilesPerHour(55) should beApproximately(UsMiles(55) / Hours(1))
  }

  it should "satisfy Integral = Derivative * Time" in {
    implicit val tolerance = UsMiles(0.0000000000001)
    UsMiles(110) should beApproximately(UsMilesPerHour(55) * Hours(2))
    UsMiles(110) should beApproximately(Hours(2) * UsMilesPerHour(55))
  }

  it should "satisfy Time = Integral / Derivative" in {
    implicit val tolerance = Hours(0.0000000000001)
    Hours(2) should beApproximately(UsMiles(110) / UsMilesPerHour(55))
  }

  it should "satisfy Derivative = Integral * Frequency" in {
    implicit val tolerance = UsMilesPerHour(0.0000000000001)
    UsMilesPerHour(55) should beApproximately(UsMiles(55) * 1 / Hours(1))
  }

  it should "satisfy Frequency = Derivative / Integral (Time value in hours)" in {
    implicit val tolerance = Hertz(0.0000000000001)
    Hertz(0.01) should beApproximately(UsMilesPerHour(72) / UsMiles(2))
  }

  it should "satisfy Frequency = Derivative / Integral (Time value in Seconds)" in {
    implicit val tolerance = Hertz(0.0000000000001)
    Hertz(55) should beApproximately(MetersPerSecond(110) / Meters(2))
  }
}
