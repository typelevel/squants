/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.motion

import squants2._
import squants2.space._
import squants2.time._

final case class Velocity[A: Numeric] private[squants2] (value: A, unit: VelocityUnit)
  extends Quantity[A, Velocity] with TimeDerivative[A, Length] with TimeIntegral[A, Acceleration] {

  override protected[squants2] def timeDerived: Acceleration[A] = MetersPerSecondSquared(num.one)
  override protected[squants2] def integralTime: Time[A] = Seconds(num.one)
  override protected[squants2] def timeIntegrated: Length[A] = Meters(num.one)
  override protected[squants2] def derivativeTime: Time[A] = Seconds(num.one)

  // BEGIN CUSTOM OPS

  //  def /[B](that: Quantity[B])(implicit f: B => A): Frequency[A] = ???
  //  def per[B](that: TimeSquared[B])(implicit f: B => A): SecondTimeDerivative[A] = ???
  //  def *[B](that: Mass[B])(implicit f: B => A): Momentum[A] = ???
  //  def /[B](that: TimeSquared[B])(implicit f: B => A): Jerk[A] = ???
  //  def /[B](that: TimeSquared[B])(implicit f: B => A): SecondTimeDerivative[A] = ???
  // END CUSTOM OPS

  def toMillimetersPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](MillimetersPerSecond)
  def toKilometersPerHour[B: Numeric](implicit f: A => B): B = toNum[B](KilometersPerHour)
  def toFeetPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](FeetPerSecond)
  def toInternationalMilesPerHour[B: Numeric](implicit f: A => B): B = toNum[B](InternationalMilesPerHour)
  def toUsMilesPerHour[B: Numeric](implicit f: A => B): B = toNum[B](UsMilesPerHour)
  def toKnots[B: Numeric](implicit f: A => B): B = toNum[B](Knots)
  def toMetersPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](MetersPerSecond)
  def toKilometersPerSecond[B: Numeric](implicit f: A => B): B = toNum[B](KilometersPerSecond)
}

object Velocity extends Dimension[Velocity]("Velocity") {

  override def primaryUnit: UnitOfMeasure[Velocity] with PrimaryUnit[Velocity] = MetersPerSecond
  override def siUnit: UnitOfMeasure[Velocity] with SiUnit[Velocity] = MetersPerSecond
  override lazy val units: Set[UnitOfMeasure[Velocity]] = 
    Set(MillimetersPerSecond, KilometersPerHour, FeetPerSecond, InternationalMilesPerHour, UsMilesPerHour, Knots, MetersPerSecond, KilometersPerSecond)

  implicit class VelocityCons[A](a: A)(implicit num: Numeric[A]) {
    def millimetersPerSecond: Velocity[A] = MillimetersPerSecond(a)
    def kilometersPerHour: Velocity[A] = KilometersPerHour(a)
    def feetPerSecond: Velocity[A] = FeetPerSecond(a)
    def internationalMilesPerHour: Velocity[A] = InternationalMilesPerHour(a)
    def usMilesPerHour: Velocity[A] = UsMilesPerHour(a)
    def knots: Velocity[A] = Knots(a)
    def metersPerSecond: Velocity[A] = MetersPerSecond(a)
    def kilometersPerSecond: Velocity[A] = KilometersPerSecond(a)
  }

  lazy val millimetersPerSecond: Velocity[Int] = MillimetersPerSecond(1)
  lazy val kilometersPerHour: Velocity[Int] = KilometersPerHour(1)
  lazy val feetPerSecond: Velocity[Int] = FeetPerSecond(1)
  lazy val internationalMilesPerHour: Velocity[Int] = InternationalMilesPerHour(1)
  lazy val usMilesPerHour: Velocity[Int] = UsMilesPerHour(1)
  lazy val knot: Velocity[Int] = Knots(1)
  lazy val metersPerSecond: Velocity[Int] = MetersPerSecond(1)
  lazy val kilometersPerSecond: Velocity[Int] = KilometersPerSecond(1)

}

abstract class VelocityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Velocity] {
  override def dimension: Dimension[Velocity] = Velocity
  override def apply[A: Numeric](value: A): Velocity[A] = Velocity(value, this)
}

case object MillimetersPerSecond extends VelocityUnit("mm/s", MetricSystem.Milli) with SiUnit[Velocity]
case object KilometersPerHour extends VelocityUnit("km/h", 0.2777777777777778)
case object FeetPerSecond extends VelocityUnit("ft/s", 0.3048006096)
case object InternationalMilesPerHour extends VelocityUnit("imph", 0.44704)
case object UsMilesPerHour extends VelocityUnit("mph", 0.44704089408)
case object Knots extends VelocityUnit("kn", 0.5144444444444445)
case object MetersPerSecond extends VelocityUnit("m/s", 1) with PrimaryUnit[Velocity] with SiUnit[Velocity]
case object KilometersPerSecond extends VelocityUnit("km/s", MetricSystem.Kilo) with SiUnit[Velocity]
