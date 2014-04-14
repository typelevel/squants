/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.time._
import squants.space._
import squants.Meters
import squants.Time
import squants.time.Seconds
import scala.Some

/**
 * Represents a quantify of Velocity
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param distance The distance traveled
 * @param time The travel time
 */
case class Velocity(distance: Distance, time: Time) extends Quantity[Velocity]
    with TimeDerivative[Distance] with TimeIntegral[Acceleration] {

  def valueUnit = MetersPerSecond
  def value = toMetersPerSeconds
  def change = distance

  def *(that: Mass): Momentum = Momentum(that, this)
  def /(that: Time): Acceleration = Acceleration(this, that)
  def /(that: Acceleration): Time = that.time * (this / that.change)

  def toString(unit: VelocityUnit) = to(unit) + " " + unit.symbol

  def to(unit: VelocityUnit) = distance.to(unit.distanceUnit) / time.to(unit.timeUnit)
  def toFeetPerSecond = to(FeetPerSecond)
  def toMetersPerSeconds = to(MetersPerSecond)
  def toKilometersPerHour = to(KilometersPerHour)
  def toUsMilesPerHour = to(UsMilesPerHour)
  def toInternationalMilesPerHour = to(InternationalMilesPerHour)
  def toKnots = to(Knots)
}

trait VelocityUnit extends UnitOfMeasure[Velocity] {
  def distanceUnit: DistanceUnit
  def timeInterval: Time
  def timeUnit: TimeUnit
  def apply[A](n: A)(implicit num: Numeric[A]) = Velocity(distanceUnit(n), timeInterval)
  def unapply(velocity: Velocity) = Some(velocity.to(this))

  protected def converterFrom: Double ⇒ Double = ???
  protected def converterTo: Double ⇒ Double = ???
}

object FeetPerSecond extends VelocityUnit {
  val distanceUnit = Feet
  val timeInterval = Seconds(1)
  val timeUnit = Seconds
  val symbol = "ft/s"
}

object MetersPerSecond extends VelocityUnit with ValueUnit {
  val distanceUnit = Meters
  val timeInterval = Seconds(1)
  val timeUnit = Seconds
  val symbol = "m/s"
}

object KilometersPerHour extends VelocityUnit {
  val distanceUnit = Kilometers
  val timeInterval = Hours(1)
  val timeUnit = Hours
  val symbol = "km/s"
}

object UsMilesPerHour extends VelocityUnit {
  val distanceUnit = UsMiles
  val timeInterval = Hours(1)
  val timeUnit = Hours
  val symbol = "mph"
}

object InternationalMilesPerHour extends VelocityUnit {
  val distanceUnit = InternationalMiles
  val timeInterval = Hours(1)
  val timeUnit = Hours
  val symbol = "mph"
}

object Knots extends VelocityUnit {
  val distanceUnit = NauticalMiles
  val timeInterval = Hours(1)
  val timeUnit = Hours
  val symbol = "kn"
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

  implicit object VelocityNumeric extends AbstractQuantityNumeric[Velocity](MetersPerSecond)
}
