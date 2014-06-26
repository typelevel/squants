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
    CubicMetersPerSecond(1).toCubicMetersPerSecond should be(1)
    GallonsPerDay(1).toGallonsPerDay should be(1)
    GallonsPerHour(1).toGallonsPerHour should be(1)
    GallonsPerMinute(1).toGallonsPerMinute should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = CubicMetersPerSecond(1)
    x.toCubicMetersPerSecond should be(1)
    x.toGallonsPerDay should be(CubicMeters(1).toUsGallons / Seconds(1).toDays)
    x.toGallonsPerHour should be(CubicMeters(1).toUsGallons / Seconds(1).toHours)
    x.toGallonsPerMinute should be(CubicMeters(1).toUsGallons / Seconds(1).toMinutes)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    CubicMetersPerSecond(1).toString(CubicMetersPerSecond) should be("1.0 m³/s")
    GallonsPerDay(1).toString(GallonsPerDay) should be("1.0 GPD")
    GallonsPerHour(1).toString(GallonsPerHour) should be("1.0 GPH")
    GallonsPerMinute(1).toString(GallonsPerMinute) should be("1.0 GPM")
  }

  it should "return Volume when multiplied by Time" in {
    CubicMetersPerSecond(1) * Seconds(1) should be(CubicMeters(1))
  }

  behavior of "VolumeFlowRateConversions"

  it should "provide aliases for single unit values" in {
    import VolumeFlowRateConversions._

    cubicMeterPerSecond should be(CubicMetersPerSecond(1))
    gallonPerDay should be(GallonsPerDay(1))
    gallonPerHour should be(GallonsPerHour(1))
    gallonPerMinute should be(GallonsPerMinute(1))
  }

  it should "provide implicit conversion from Double" in {
    import VolumeFlowRateConversions._

    val d = 10.22d
    d.cubicMetersPerSecond should be(CubicMetersPerSecond(d))
    d.gallonsPerDay should be(GallonsPerDay(d))
    d.gallonsPerHour should be(GallonsPerHour(d))
    d.gallonsPerMinute should be(GallonsPerMinute(d))
  }

  it should "provide Numeric support" in {
    import VolumeFlowRateConversions.VolumeFlowRateNumeric

    val vfrs = List(GallonsPerDay(24), GallonsPerHour(1))
    vfrs.sum should be(GallonsPerDay(48))
  }
}
