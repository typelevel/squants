/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants2.time

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
 * @author garyKeorkunian
 * @since 0.5.1
 */
case class TimeSquared[A](time1: Time[A], time2: Time[A]) {
//  def squareRoot: Time[A] = time1.unit(math.sqrt(time1.value * time2.to(time1.unit).toDouble))
//  def *[Q[N] <: SecondTimeIntegral[N, Q]](that: SecondTimeDerivative[A, Q]): Q[A] = that * (this.time1 * this.time2)
}

object TimeSquared {
  def apply[A](time: Time[A]): TimeSquared[A] = TimeSquared(time, time)
}

trait TimeSquaredUnit {
  protected def timeUnit: TimeUnit
  def apply[A: Numeric](value: A): TimeSquared[A] = TimeSquared(timeUnit(value), timeUnit(value))
}
object SecondsSquared extends TimeSquaredUnit {
  protected def timeUnit: Seconds.type = Seconds
}
object MinutesSquared extends TimeSquaredUnit {
  protected def timeUnit: Minutes.type = Minutes
}
object HoursSquared extends TimeSquaredUnit {
  protected def timeUnit: Hours.type = Hours
}
