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

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class TimeSpec extends FlatSpec with Matchers {

  behavior of "Time and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(Microseconds(1).toMicroseconds == 1)
    assert(Milliseconds(1).toMilliseconds == 1)
    assert(Seconds(1).toSeconds == 1)
    assert(Minutes(1).toMinutes == 1)
    assert(Hours(1).toHours == 1)
    assert(Days(1).toDays == 1)
  }

  it should "return equality for units in different units" in {
    assert(Microseconds(1000) == Milliseconds(1))
    assert(Milliseconds(1000) == Seconds(1))
    assert(Seconds(60) == Minutes(1))
    assert(Minutes(60) == Hours(1))
    assert(Hours(24) == Days(1))
  }

  it should "return Time when added to Time" in {
    assert(Seconds(60) + Minutes(1) == Minutes(2))
    assert(Minutes(15) + Hours(1.75) == Hours(2))
  }

  it should "return Time when subtracted to Time" in {
    assert(Minutes(5) - Seconds(30) == Minutes(4.5))
    assert(Hours(5) - Minutes(30) == Hours(4.5))
    assert(Days(5) - Hours(12) == Days(4.5))
  }

  it should "return a Double when divided be Time" in {
    assert(Seconds(60) / Seconds(15) == 4)
    assert(Minutes(1) / Seconds(15) == 4)
    assert(Hours(2) / Minutes(30) == 4)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Seconds(1)
    assert(x.toMicroseconds == 1000000d)
    assert(x.toMilliseconds == 1000)
    assert(x.toSeconds == 1)
    assert(x.toMinutes == 1d / 60)
    assert(x.toHours == 1d / 3600)
    assert(x.toDays == 1d / (3600 * 24))
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Microseconds(1).toString(Microseconds) == "1.0 µs")
    assert(Milliseconds(1).toString(Milliseconds) == "1.0 ms")
    assert(Seconds(1).toString(Seconds) == "1.0 s")
    assert(Minutes(1).toString(Minutes) == "1.0 m")
    assert(Hours(1).toString(Hours) == "1.0 h")
    assert(Days(1).toString(Days) == "1.0 d")
  }

  it should "return TimeIntegral when multiplied by a TimeDerivative" in {
    assert(Seconds(1) * MetersPerSecond(1) == Meters(1))
    assert(Seconds(1) * MetersPerSecondSquared(1) == MetersPerSecond(1))
    assert(Seconds(1) * MetersPerSecondCubed(1) == MetersPerSecondSquared(1))
  }

  behavior of "TimeConversions"

  it should "provide aliases for single unit values" in {
    import TimeConversions._

    assert(microsecond == Microseconds(1))
    assert(millisecond == Milliseconds(1))
    assert(second == Seconds(1))
    assert(minute == Minutes(1))
    assert(halfHour == Minutes(30))
    assert(hour == Hours(1))
    assert(day == Days(1))
  }

  it should "provide implicit conversion from Double" in {
    import TimeConversions._

    val coefficient = 10d
    assert(coefficient.microseconds == Microseconds(coefficient))
    assert(coefficient.milliseconds == Milliseconds(coefficient))
    assert(coefficient.seconds == Seconds(coefficient))
    assert(coefficient.minutes == Minutes(coefficient))
    assert(coefficient.hours == Hours(coefficient))
    assert(coefficient.days == Days(coefficient))
  }

  it should "convert a Scala Concurrent Duration to a Time" in {
    import TimeConversions._

    val duration: Duration = Duration(1, "second")
    assert(Meters(1) / duration == MetersPerSecond(1))
  }

  it should "convert a Time to a Scala Concurrent Duration" in {
    import TimeConversions._

    val duration: Duration = Seconds(1)
    assert(duration == Duration(1, "second"))
  }
}
