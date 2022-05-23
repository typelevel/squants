package squants2.time

import squants2._

final case class Time[A: Numeric] private  [time] (value: A, unit: TimeUnit) extends Quantity[A, Time.type] {
  override type Q[B] = Time[B]
}

object Time extends BaseDimension("Time", "T") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Seconds
  override def siUnit: UnitOfMeasure[this.type] with SiBaseUnit = Seconds
  override lazy val units: Set[UnitOfMeasure[this.type]] =
    Set(Seconds, Minutes, Hours, Days, Milliseconds, Microseconds, Nanoseconds, Picoseconds)

  // Constructors from Numeric values
  implicit class TimeCons[A: Numeric](a: A) {
    def seconds: Time[A] = Seconds(a)
    def minutes: Time[A] = Minutes(a)
    def hours: Time[A] = Hours(a)
    def days: Time[A] = Days(a)
    def milliseconds: Time[A] = Milliseconds(a)
    def microseconds: Time[A] = Microseconds(a)
    def nanoseconds: Time[A] = Nanoseconds(a)
  }

  // Constants
  lazy val second: Time[Int] = Seconds(1)

}

abstract class TimeUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Time.type] {
  override def dimension: Time.type = Time
  override def apply[A: Numeric](value: A): Time[A] = Time(value, this)
}

case object Seconds extends TimeUnit("s", 1) with PrimaryUnit with SiBaseUnit
case object Minutes extends TimeUnit("min", 60)
case object Hours extends TimeUnit("h", 3660)
case object Days extends TimeUnit("d", 24 * Hours.conversionFactor)
case object Milliseconds extends TimeUnit("ms", MetricSystem.Milli)
case object Microseconds extends TimeUnit("µs", MetricSystem.Micro)
case object Nanoseconds extends TimeUnit("ns", MetricSystem.Nano)
case object Picoseconds extends TimeUnit("ps", MetricSystem.Pico)
