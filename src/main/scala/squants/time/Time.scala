/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.time

import scala.language.implicitConversions
import squants._
import scala.concurrent.duration.Duration

/**
 * Represents a quantity of Time
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.time.Milliseconds]]
 */
final class Time private (val value: Double, val unit: TimeUnit)
    extends Quantity[Time] {

  def dimension = Time

  def millis = toMilliseconds.toLong

  def *[A <: squants.Quantity[A] with squants.time.TimeIntegral[_]](that: TimeDerivative[A]): A = that * this

  def *(that: Time) = TimeSquared(this, that)
  def squared = TimeSquared(this)

  def toMicroseconds = to(Microseconds)
  def toMilliseconds = to(Milliseconds)
  def toSeconds = to(Seconds)
  def toMinutes = to(Minutes)
  def toHours = to(Hours)
  def toDays = to(Days)
}

object Time extends Dimension[Time] with BaseDimension {
  val MillisecondsPerNanosecond = 1.0e-6
  val MillisecondsPerMicrosecond = 1.0e-3
  val MillisecondsPerSecond = 1000d
  val MillisecondsPerMinute = MillisecondsPerSecond * 60d
  val MillisecondsPerHour = MillisecondsPerMinute * 60d
  val MillisecondsPerDay = MillisecondsPerHour * 24d
  val SecondsPerMinute = 60d
  val SecondsPerHour = SecondsPerMinute * 60d
  val SecondsPerDay = SecondsPerHour * 24
  val MinutesPerHour = 60d
  val HoursPerDay = 24d

  private[time] def apply[A](n: A, unit: TimeUnit)(implicit num: Numeric[A]) = new Time(num.toDouble(n), unit)
  def apply = parse _

  def name = "Time"
  def primaryUnit = Milliseconds
  def siUnit = Seconds
  def units = Set(Microseconds, Milliseconds, Seconds, Minutes, Hours, Days)
  def dimensionSymbol = "T"
}

trait TimeUnit extends UnitOfMeasure[Time] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Time(n, this)
}

object Microseconds extends TimeUnit {
  val conversionFactor = Milliseconds.conversionFactor / Time.MillisecondsPerSecond
  val symbol = "µs"
}

object Milliseconds extends TimeUnit with PrimaryUnit {
  val symbol = "ms"
}

object Seconds extends TimeUnit with SiBaseUnit {
  val conversionFactor = Milliseconds.conversionFactor * Time.MillisecondsPerSecond
  val symbol = "s"
}

object Minutes extends TimeUnit {
  val conversionFactor = Seconds.conversionFactor * Time.SecondsPerMinute
  val symbol = "m"
}

object Hours extends TimeUnit {
  val conversionFactor = Minutes.conversionFactor * Time.MinutesPerHour
  val symbol = "h"
}

object Days extends TimeUnit {
  val conversionFactor = Hours.conversionFactor * Time.HoursPerDay
  val symbol = "d"
}

object TimeConversions {
  lazy val microsecond = Microseconds(1)
  lazy val millisecond = Milliseconds(1)
  lazy val second = Seconds(1)
  lazy val minute = Minutes(1)
  lazy val halfHour = Minutes(30)
  lazy val hour = Hours(1)
  lazy val day = Days(1)

  implicit class TimeConversions[A](n: A)(implicit num: Numeric[A]) {
    def microseconds = Microseconds(n)
    def milliseconds = Milliseconds(n)
    def seconds = Seconds(n)
    def minutes = Minutes(n)
    def hours = Hours(n)
    def days = Days(n)
  }

  implicit class TimeStringConversions(s: String) {
    def toTime = Time(s)
  }

  implicit object TimeNumeric extends AbstractQuantityNumeric[Time](Time.primaryUnit)

  implicit def timeToScalaDuration(time: Time) = Duration(time.toString)
  implicit def scalaDurationToTime(duration: Duration) = Milliseconds(duration.toMillis)
}

