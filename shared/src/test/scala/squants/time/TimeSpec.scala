/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.time

import squants.{ Dimension, QuantityParseException }
import squants.motion.{ MetersPerSecond, MetersPerSecondCubed, MetersPerSecondSquared }
import squants.space.{ Meters, SquareMeters }
import squants.radio.SquareMeterSeconds

import scala.concurrent.duration.{ DAYS, Duration, HOURS, MICROSECONDS, MILLISECONDS, MINUTES, NANOSECONDS, SECONDS }
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class TimeSpec extends AnyFlatSpec with Matchers {

  behavior of "Time and its Units of Measure"

  it should "create values using UOM factories" in {
    Nanoseconds(1).toNanoseconds should be(1)
    Microseconds(1).toMicroseconds should be(1)
    Milliseconds(1).toMilliseconds should be(1)
    Seconds(1).toSeconds should be(1)
    Minutes(1).toMinutes should be(1)
    Hours(1).toHours should be(1)
    Days(1).toDays should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Time("10.22 ns").get should be(Nanoseconds(10.22))
    Time("10.22 µs").get should be(Microseconds(10.22))
    Time("10.22 ms").get should be(Milliseconds(10.22))
    Time("10.22 s").get should be(Seconds(10.22))
    Time("10.22 m").get should be(Minutes(10.22))
    Time("10.22 h").get should be(Hours(10.22))
    Time("10.22 d").get should be(Days(10.22))
    Time("10.22 z").failed.get should be(QuantityParseException("Unable to parse Time", "10.22 z"))
    Time("ZZ ms").failed.get should be(QuantityParseException("Unable to parse Time", "ZZ ms"))
  }

  it should "create values from Scala Durations" in {
    Time(Duration(10, NANOSECONDS)) should be(Nanoseconds(10))
    Time(Duration(10, MICROSECONDS)) should be(Microseconds(10))
    Time(Duration(10, MILLISECONDS)) should be(Milliseconds(10))
    Time(Duration(10, SECONDS)) should be(Seconds(10))
    Time(Duration(10, MINUTES)) should be(Minutes(10))
    Time(Duration(10, HOURS)) should be(Hours(10))
    Time(Duration(10, DAYS)) should be(Days(10))
  }

  it should "return equality for units in different units" in {
    Nanoseconds(3000) should be(Microseconds(3))
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
    x.toNanoseconds should be(1e9)
    x.toMicroseconds should be(1e6)
    x.toMilliseconds should be(1000)
    x.toSeconds should be(1)
    x.toMinutes should be(1d / 60)
    x.toHours should be(1d / 3600)
    x.toDays should be(1d / (3600 * 24))
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Nanoseconds(1).toString(Nanoseconds) should be("1.0 ns")
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

  it should "return AreaTime when multiplied by an Area" in {
    Seconds(1) * SquareMeters(1) should be(SquareMeterSeconds(1))
  }

  behavior of "TimeConversions"

  it should "provide aliases for single unit values" in {
    import TimeConversions._
    nanosecond should be(Nanoseconds(1))
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
    coefficient.nanoseconds should be(Nanoseconds(coefficient))
    coefficient.microseconds should be(Microseconds(coefficient))
    coefficient.milliseconds should be(Milliseconds(coefficient))
    coefficient.seconds should be(Seconds(coefficient))
    coefficient.minutes should be(Minutes(coefficient))
    coefficient.hours should be(Hours(coefficient))
    coefficient.days should be(Days(coefficient))
  }

  it should "provide implicit conversions from String" in {
    import TimeConversions._

    "10.22 ns".toTime.get should be(Nanoseconds(10.22))
    "10.22 µs".toTime.get should be(Microseconds(10.22))
    "10.22 ms".toTime.get should be(Milliseconds(10.22))
    "10.22 s".toTime.get should be(Seconds(10.22))
    "10.22 m".toTime.get should be(Minutes(10.22))
    "10.22 h".toTime.get should be(Hours(10.22))
    "10.22 d".toTime.get should be(Days(10.22))
    "10.22 z".toTime.failed.get should be(QuantityParseException("Unable to parse Time", "10.22 z"))
    "ZZ ms".toTime.failed.get should be(QuantityParseException("Unable to parse Time", "ZZ ms"))
  }

  it should "convert a Scala Concurrent Duration to a Time with the same units" in {
    import TimeConversions._

    val nanoseconds: Time = Duration(1, NANOSECONDS)
    nanoseconds.value should be(1)
    nanoseconds.unit should be(Nanoseconds)

    val microseconds: Time = Duration(1, MICROSECONDS)
    microseconds.value should be(1)
    microseconds.unit should be(Microseconds)

    val milliseconds: Time = Duration(1, MILLISECONDS)
    milliseconds.value should be(1)
    milliseconds.unit should be(Milliseconds)

    val seconds: Time = Duration(1, SECONDS)
    seconds.value should be(1)
    seconds.unit should be(Seconds)

    val minutes: Time = Duration(1, MINUTES)
    minutes.value should be(1)
    minutes.unit should be(Minutes)

    val hours: Time = Duration(1, HOURS)
    hours.value should be(1)
    hours.unit should be(Hours)

    val days: Time = Duration(1, DAYS)
    days.value should be(1)
    days.unit should be(Days)
  }

  it should "implicitly convert a Scala Concurrent Duration to a Time in expressions that require it" in {
    import TimeConversions._

    val duration: Duration = Duration(1, "second")
    Meters(1) / duration should be(MetersPerSecond(1))
  }

  it should "convert a Time to a Scala Concurrent Duration with the same units" in {
    import TimeConversions._

    val nanoseconds: Duration = Nanoseconds(1)
    nanoseconds.length should be(1)
    nanoseconds.unit should be(NANOSECONDS)

    val microseconds: Duration = Microseconds(1)
    microseconds.length should be(1)
    microseconds.unit should be(MICROSECONDS)

    val milliseconds: Duration = Milliseconds(1)
    milliseconds.length should be(1)
    milliseconds.unit should be(MILLISECONDS)

    val seconds: Duration = Seconds(1)
    seconds.length should be(1)
    seconds.unit should be(SECONDS)

    val minutes: Duration = Minutes(1)
    minutes.length should be(1)
    minutes.unit should be(MINUTES)

    val hours: Duration = Hours(1)
    hours.length should be(1)
    hours.unit should be(HOURS)

    val days: Duration = Days(1)
    days.length should be(1)
    days.unit should be(DAYS)
  }

  it should "convert a Time to a Scala Concurrent Duration in expressions that require it" in {
    import TimeConversions._

    def doSomethingWithDuration(duration: Duration): Unit = duration should be(Duration(10, SECONDS))

    doSomethingWithDuration(Seconds(10))
  }

  it should "provide implicit instance for Dimension" in {
    implicitly[Dimension[Time]] shouldBe Time
  }
}
