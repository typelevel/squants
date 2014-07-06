/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants.time._
import squants.space._
import squants.time.Seconds
import squants._
import squants.Time
import squants.space.Length
import squants.Meters

/**
 * Represents a quantify of Velocity
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Velocity private (val value: Double) extends Quantity[Velocity]
    with TimeIntegral[Acceleration] {

  def valueUnit = Velocity.valueUnit
  def change = Seconds(1)

  // TODO - Remove once TimeDerivative pairing of Length -> Velocity is fixed
  def *(that: Time): Length = Meters(toMetersPerSecond * that.toSeconds)

  def *(that: Mass): Momentum = NewtonSeconds(toMetersPerSecond * that.toKilograms)
  def /(that: Time): Acceleration = MetersPerSecondSquared(toMetersPerSecond / that.toSeconds)
  def /(that: Acceleration): Time = that.time * (this / that.change)

  def toFeetPerSecond = to(FeetPerSecond)
  def toMetersPerSecond = to(MetersPerSecond)
  def toKilometersPerHour = to(KilometersPerHour)
  def toUsMilesPerHour = to(UsMilesPerHour)
  def toInternationalMilesPerHour = to(InternationalMilesPerHour)
  def toKnots = to(Knots)
}

object Velocity extends QuantityCompanion[Velocity] {
  private[motion] def apply[A](n: A)(implicit num: Numeric[A]) = new Velocity(num.toDouble(n))
  def apply(l: Length, t: Time) = MetersPerSecond(l.toMeters / t.toSeconds)
  def apply = parseString _
  def name = "Velocity"
  def valueUnit = MetersPerSecond
  def units = Set(MetersPerSecond, FeetPerSecond, KilometersPerHour, UsMilesPerHour,
    InternationalMilesPerHour, Knots)
}

trait VelocityUnit extends UnitOfMeasure[Velocity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]): Velocity = Velocity(convertFrom(n))
}

object FeetPerSecond extends VelocityUnit {
  val symbol = "ft/s"
  val conversionFactor = Feet.conversionFactor * Meters.conversionFactor
}

object MetersPerSecond extends VelocityUnit with ValueUnit {
  val symbol = "m/s"
}

object KilometersPerHour extends VelocityUnit {
  val symbol = "km/s"
  val conversionFactor = (Kilometers.conversionFactor / Meters.conversionFactor) / 3600d
}

object UsMilesPerHour extends VelocityUnit {
  val symbol = "mph"
  val conversionFactor = (UsMiles.conversionFactor * Meters.conversionFactor) / 3600d
}

object InternationalMilesPerHour extends VelocityUnit {
  val symbol = "imph"
  val conversionFactor = (InternationalMiles.conversionFactor / Meters.conversionFactor) / 3600d
}

object Knots extends VelocityUnit {
  val symbol = "kn"
  val conversionFactor = (NauticalMiles.conversionFactor / Meters.conversionFactor) / 3600d
}

object VelocityConversions {
  lazy val footPerSecond = FeetPerSecond(1)
  lazy val meterPerSecond = MetersPerSecond(1)
  lazy val kilometerPerSecond = KilogramsPerSecond(1)
  lazy val milePerHour = UsMilesPerHour(1)
  lazy val knot = Knots(1)

  implicit class VelocityConversions[A](n: A)(implicit num: Numeric[A]) {
    def fps = FeetPerSecond(n)
    def mps = MetersPerSecond(n)
    def kph = KilometersPerHour(n)
    def mph = UsMilesPerHour(n)
    def knots = Knots(n)
  }

  implicit object VelocityNumeric extends AbstractQuantityNumeric[Velocity](Velocity.valueUnit)
}
