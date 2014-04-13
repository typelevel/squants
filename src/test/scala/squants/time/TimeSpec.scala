/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.time

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.motion.{ MetersPerSecond, MetersPerSecondCubed, MetersPerSecondSquared }
import squants.space.Meters
import scala.concurrent.duration.Duration
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class TimeSpec extends FlatSpec with Matchers {

  behavior of "Time and its Units of Measure"

  it should "create values using UOM factories" in {
    Microseconds(1).toMicroseconds should be(1)
    Milliseconds(1).toMilliseconds should be(1)
    Seconds(1).toSeconds should be(1)
    Minutes(1).toMinutes should be(1)
    Hours(1).toHours should be(1)
    Days(1).toDays should be(1)
  }

  it should "return equality for units in different units" in {
    Microseconds(1000) should be(Milliseconds(1))
    Milliseconds(1000) should be(Seconds(1))
    Seconds(60) should be(Minutes(1))
    Minutes(60) should be(Hours(1))
    Hours(24) should be(Days(1))
  }

  it should "return Time when added to Time" in {
    Seconds(60) + Minutes(1) should be(Minutes(2))
    Minutes(15) + Hours(1.75) should be(Hours(2))
  }

  it should "return Time when subtracted to Time" in {
    Minutes(5) - Seconds(30) should be(Minutes(4.5))
    Hours(5) - Minutes(30) should be(Hours(4.5))
    Days(5) - Hours(12) should be(Days(4.5))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Seconds(1)
    x.toMicroseconds should be(1000000d)
    x.toMilliseconds should be(1000)
    x.toSeconds should be(1)
    x.toMinutes should be(1d / 60)
    x.toHours should be(1d / 3600)
    x.toDays should be(1d / (3600 * 24))
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Microseconds(1).toString(Microseconds) should be("1.0 µs")
    Milliseconds(1).toString(Milliseconds) should be("1.0 ms")
    Seconds(1).toString(Seconds) should be("1.0 s")
    Minutes(1).toString(Minutes) should be("1.0 m")
    Hours(1).toString(Hours) should be("1.0 h")
    Days(1).toString(Days) should be("1.0 d")
  }

  it should "return TimeIntegral when multiplied by a TimeDerivative" in {
    Seconds(1) * MetersPerSecond(1) should be(Meters(1))
    Seconds(1) * MetersPerSecondSquared(1) should be(MetersPerSecond(1))
    Seconds(1) * MetersPerSecondCubed(1) should be(MetersPerSecondSquared(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Seconds(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Time](ser)
    x should be(des)
  }

  behavior of "TimeConversions"

  it should "provide aliases for single unit values" in {
    import TimeConversions._

    microsecond should be(Microseconds(1))
    millisecond should be(Milliseconds(1))
    second should be(Seconds(1))
    minute should be(Minutes(1))
    halfHour should be(Minutes(30))
    hour should be(Hours(1))
    day should be(Days(1))
  }

  it should "provide implicit conversion from Double" in {
    import TimeConversions._

    val coefficient = 10d
    coefficient.microseconds should be(Microseconds(coefficient))
    coefficient.milliseconds should be(Milliseconds(coefficient))
    coefficient.seconds should be(Seconds(coefficient))
    coefficient.minutes should be(Minutes(coefficient))
    coefficient.hours should be(Hours(coefficient))
    coefficient.days should be(Days(coefficient))
  }

  it should "provide implicit conversions from String" in {
    import TimeConversions._

    "10.22 ms".toTime.right.get should be(Milliseconds(10.22))
    "10.22 s".toTime.right.get should be(Seconds(10.22))
    "10.22 m".toTime.right.get should be(Minutes(10.22))
    "10.22 h".toTime.right.get should be(Hours(10.22))
    "10.22 d".toTime.right.get should be(Days(10.22))
    "10.22 z".toTime.left.get should be("Unable to parse 10.22 z as Time")
  }

  it should "convert a Scala Concurrent Duration to a Time" in {
    import TimeConversions._

    val duration: Duration = Duration(1, "second")
    Meters(1) / duration should be(MetersPerSecond(1))
  }

  it should "convert a Time to a Scala Concurrent Duration" in {
    import TimeConversions._

    val duration: Duration = Seconds(1)
    duration should be(Duration(1, "second"))
  }
}
