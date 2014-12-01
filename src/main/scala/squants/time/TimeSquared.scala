/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.time

/**
 * Represents an intermediate value used in 2nd derivative time calculations
 *
 * Create objects by calling the Time.squared method.
 *
 * q1 / TimeSquared(t1, t2) == q1 / t1 / t2 == q1 / (t1 * t2)
 *
 * q2 * TimeSquared(t1, t2) == q2 * t1 * t2
 *
 * q1 / t1.squared == q1 / t1 / t1
 *
 * q2 * t1.squared == q2 * t1 * t1
 *
 * where q1 is a second degree time integral
 * and q2 is a second degree time derivative
 *
 * @param time1 Time
 * @param time2 Time
 *
 * @author garyKeorkunian
 * @since 0.5.1
 */
case class TimeSquared(time1: Time, time2: Time)
object TimeSquared {
  def apply(time: Time): TimeSquared = TimeSquared(time, time)
}

trait TimeSquaredUnit {
  protected def timeUnit: TimeUnit
  def apply(value: Double): TimeSquared = TimeSquared(timeUnit(value), timeUnit(value))
}
object SecondsSquared extends TimeSquaredUnit {
  protected def timeUnit = Seconds
}
object MinutesSquared extends TimeSquaredUnit {
  protected def timeUnit = Minutes
}
object HoursSquared extends TimeSquaredUnit {
  protected def timeUnit = Hours
}
