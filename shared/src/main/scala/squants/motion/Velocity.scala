/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants.{ Time, _ }
import squants.space.{ Feet, InternationalMiles, Kilometers, Millimeters, NauticalMiles, UsMiles }
import squants.time.{ Seconds, _ }

/**
 * Represents a quantify of Velocity
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Velocity private (val value: Double, val unit: VelocityUnit)
    extends Quantity[Velocity]
    with TimeIntegral[Acceleration]
    with SecondTimeIntegral[Jerk]
    with TimeDerivative[Length] {

  def dimension = Velocity

  def timeDerived = MetersPerSecondSquared(toMetersPerSecond)
  protected[squants] def timeIntegrated = Meters(toMetersPerSecond)
  protected[squants] def time = Seconds(1)

  def *(that: Mass): Momentum = NewtonSeconds(this.toMetersPerSecond * that.toKilograms)

  def /(that: TimeSquared): Jerk = this / that.time1 / that.time2
  def /(that: Jerk): TimeSquared = (this / that.timeIntegrated) * this.time

  def toFeetPerSecond = to(FeetPerSecond)
  def toMillimetersPerSecond = to(MillimetersPerSecond)
  def toMetersPerSecond = to(MetersPerSecond)
  def toKilometersPerSecond = to(KilometersPerSecond)
  def toKilometersPerHour = to(KilometersPerHour)
  def toUsMilesPerHour = to(UsMilesPerHour)
  def toInternationalMilesPerHour = to(InternationalMilesPerHour)
  def toKnots = to(Knots)
}

object Velocity extends Dimension[Velocity] {
  private[motion] def apply[A](n: A, unit: VelocityUnit)(implicit num: Numeric[A]) = new Velocity(num.toDouble(n), unit)
  def apply(l: Length, t: Time) = MetersPerSecond(l.toMeters / t.toSeconds)
  def apply(value: Any) = parse(value)
  def name = "Velocity"
  def primaryUnit = MetersPerSecond
  def siUnit = MetersPerSecond
  def units = Set(MetersPerSecond, FeetPerSecond, MillimetersPerSecond, KilometersPerSecond, KilometersPerHour,
    UsMilesPerHour, InternationalMilesPerHour, Knots)
}

trait VelocityUnit extends UnitOfMeasure[Velocity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]): Velocity = Velocity(n, this)
}

object FeetPerSecond extends VelocityUnit {
  val symbol = "ft/s"
  val conversionFactor = Feet.conversionFactor / Meters.conversionFactor
}

object MillimetersPerSecond extends VelocityUnit with SiUnit {
  val symbol = "mm/s"
  val conversionFactor = Millimeters.conversionFactor / Meters.conversionFactor
}

object MetersPerSecond extends VelocityUnit with PrimaryUnit with SiUnit {
  val symbol = "m/s"
}

object KilometersPerSecond extends VelocityUnit with SiUnit {
  val symbol = "km/s"
  val conversionFactor = Kilometers.conversionFactor / Meters.conversionFactor
}

object KilometersPerHour extends VelocityUnit {
  val symbol = "km/h"
  val conversionFactor = (Kilometers.conversionFactor / Meters.conversionFactor) / Time.SecondsPerHour
}

object UsMilesPerHour extends VelocityUnit {
  val symbol = "mph"
  val conversionFactor = (UsMiles.conversionFactor / Meters.conversionFactor) / Time.SecondsPerHour
}

object InternationalMilesPerHour extends VelocityUnit {
  val symbol = "imph"
  val conversionFactor = (InternationalMiles.conversionFactor / Meters.conversionFactor) / Time.SecondsPerHour
}

object Knots extends VelocityUnit {
  val symbol = "kn"
  val conversionFactor = (NauticalMiles.conversionFactor / Meters.conversionFactor) / Time.SecondsPerHour
}

object VelocityConversions {
  lazy val footPerSecond = FeetPerSecond(1)
  lazy val millimeterPerSecond = MillimetersPerSecond(1)
  lazy val meterPerSecond = MetersPerSecond(1)
  lazy val kilometerPerSecond = KilometersPerSecond(1)
  lazy val kilometerPerHour = KilometersPerHour(1)
  lazy val milePerHour = UsMilesPerHour(1)
  lazy val knot = Knots(1)

  implicit class VelocityConversions[A](n: A)(implicit num: Numeric[A]) {
    def fps = FeetPerSecond(n)
    def mps = MetersPerSecond(n)
    def kps = KilometersPerSecond(n)
    def kph = KilometersPerHour(n)
    def mph = UsMilesPerHour(n)
    def knots = Knots(n)
  }

  implicit object VelocityNumeric extends AbstractQuantityNumeric[Velocity](Velocity.primaryUnit)
}
