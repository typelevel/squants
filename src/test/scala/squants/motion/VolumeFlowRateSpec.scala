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
import squants.space.CubicMeters
import squants.time.Seconds

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class VolumeFlowRateSpec extends FlatSpec with Matchers {

  behavior of "VolumeFlowRate and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(CubicMetersPerSecond(1).toCubicMetersPerSecond == 1)
    assert(GallonsPerDay(1).toGallonsPerDay == 1)
    assert(GallonsPerHour(1).toGallonsPerHour == 1)
    assert(GallonsPerMinute(1).toGallonsPerMinute == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = CubicMetersPerSecond(1)
    assert(x.toCubicMetersPerSecond == 1)
    assert(x.toGallonsPerDay == CubicMeters(1).toUsGallons / Seconds(1).toDays)
    assert(x.toGallonsPerHour == CubicMeters(1).toUsGallons / Seconds(1).toHours)
    assert(x.toGallonsPerMinute == CubicMeters(1).toUsGallons / Seconds(1).toMinutes)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(CubicMetersPerSecond(1).toString(CubicMetersPerSecond) == "1.0 m³/s")
    assert(GallonsPerDay(1).toString(GallonsPerDay) == "1.0 GPD")
    assert(GallonsPerHour(1).toString(GallonsPerHour) == "1.0 GPH")
    assert(GallonsPerMinute(1).toString(GallonsPerMinute) == "1.0 GPM")
  }

  it should "return Volume when multiplied by Time" in {
    assert(CubicMetersPerSecond(1) * Seconds(1) == CubicMeters(1))
  }
}
