/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.time

import squants2._

final case class Time[A: Numeric] private[squants2] (value: A, unit: TimeUnit)
  extends Quantity[A, Time] {

  // BEGIN CUSTOM OPS

  //  def millis[B]()(implicit f: B => A): Long = ???
  //  def *[B](that: Area[B])(implicit f: B => A): AreaTime[A] = ???
  // END CUSTOM OPS
  def *[D[N] <: Quantity[N, D] with TimeDerivative[A, I], I[N] <: Quantity[N, I] with TimeIntegral[A, D]](that: D[A]): I[A] =
    that.asInstanceOf[TimeDerivative[A, I]] * this

  def toNanoseconds[B: Numeric](implicit f: A => B): B = toNum[B](Nanoseconds)
  def toMicroseconds[B: Numeric](implicit f: A => B): B = toNum[B](Microseconds)
  def toMilliseconds[B: Numeric](implicit f: A => B): B = toNum[B](Milliseconds)
  def toSeconds[B: Numeric](implicit f: A => B): B = toNum[B](Seconds)
  def toMinutes[B: Numeric](implicit f: A => B): B = toNum[B](Minutes)
  def toHours[B: Numeric](implicit f: A => B): B = toNum[B](Hours)
  def toDays[B: Numeric](implicit f: A => B): B = toNum[B](Days)
}

object Time extends BaseDimension[Time]("Time", "T") {

  override def primaryUnit: UnitOfMeasure[Time] with PrimaryUnit[Time] = Milliseconds
  override def siUnit: UnitOfMeasure[Time] with SiBaseUnit[Time] = Seconds
  override lazy val units: Set[UnitOfMeasure[Time]] = 
    Set(Nanoseconds, Microseconds, Milliseconds, Seconds, Minutes, Hours, Days)

  implicit class TimeCons[A](a: A)(implicit num: Numeric[A]) {
    def nanoseconds: Time[A] = Nanoseconds(a)
    def microseconds: Time[A] = Microseconds(a)
    def milliseconds: Time[A] = Milliseconds(a)
    def seconds: Time[A] = Seconds(a)
    def minutes: Time[A] = Minutes(a)
    def hours: Time[A] = Hours(a)
    def days: Time[A] = Days(a)
  }

  lazy val nanoseconds: Time[Int] = Nanoseconds(1)
  lazy val microseconds: Time[Int] = Microseconds(1)
  lazy val milliseconds: Time[Int] = Milliseconds(1)
  lazy val seconds: Time[Int] = Seconds(1)
  lazy val minutes: Time[Int] = Minutes(1)
  lazy val hours: Time[Int] = Hours(1)
  lazy val days: Time[Int] = Days(1)

}

abstract class TimeUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Time] {
  override def dimension: Dimension[Time] = Time
  override def apply[A: Numeric](value: A): Time[A] = Time(value, this)
}

case object Nanoseconds extends TimeUnit("ns", MetricSystem.Micro) with SiUnit[Time]
case object Microseconds extends TimeUnit("µs", MetricSystem.Milli) with SiUnit[Time]
case object Milliseconds extends TimeUnit("ms", 1) with PrimaryUnit[Time] with SiUnit[Time]
case object Seconds extends TimeUnit("s", MetricSystem.Kilo) with SiBaseUnit[Time]
case object Minutes extends TimeUnit("min", 60000)
case object Hours extends TimeUnit("h", 3600000)
case object Days extends TimeUnit("d", 86400000)
