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
import squants.Time
import squants.time.Seconds
import scala.Some

/**
 * Represents a quantity of acceleration
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param change the change in velocity
 * @param time the time interval
 */
case class Acceleration(change: Velocity, time: Time) extends Quantity[Acceleration]
    with TimeDerivative[Velocity] with TimeIntegral[Jerk] {

  def valueUnit = MetersPerSecondSquared
  def value = change.to(MetersPerSecond) / time.to(Seconds)

  def *(that: Mass): Force = Force(that, this)
  def /(that: Time): Jerk = Jerk(this, that)
  def /(that: Jerk): Time = that.time * (this / that.change)

  def toString(unit: AccelerationUnit) = to(unit) + " " + unit.symbol

  def to(unit: AccelerationUnit): Double = change.to(unit.changeUnit) / unit.change.to(unit.changeUnit) / time.to(unit.timeUnit)
  def toFeetPerSecondSquared = to(FeetPerSecondSquared)
  def toMetersPerSecondSquared = to(MetersPerSecondSquared)
  def toUsMilesPerHourSquared = to(UsMilesPerHourSquared)
  def toEarthGravities = to(EarthGravities)
}

/**
 * Base trait for units of [[squants.motion.Acceleration]]
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
trait AccelerationUnit extends UnitOfMeasure[Acceleration] {
  def changeUnit: VelocityUnit
  def change: Velocity
  def timeUnit: TimeUnit
  def time: Time

  def apply(value: Double) = Acceleration(change * value, time)
  def unapply(acceleration: Acceleration) = Some(acceleration.to(this))

  protected def converterTo: Double ⇒ Double = ???
  protected def converterFrom: Double ⇒ Double = ???
}

// TODO - How about direction?

object MetersPerSecondSquared extends AccelerationUnit with ValueUnit {
  val changeUnit = MetersPerSecond
  val change = MetersPerSecond(1)
  val timeUnit = Seconds
  val time = Seconds(1)
  val symbol = "m/s²"
}

object FeetPerSecondSquared extends AccelerationUnit {
  val changeUnit = FeetPerSecond
  val change = FeetPerSecond(1)
  val timeUnit = Seconds
  val time = Seconds(1)
  val symbol = "ft/s²"
}

object UsMilesPerHourSquared extends AccelerationUnit {
  val changeUnit = UsMilesPerHour
  val change = UsMilesPerHour(1)
  val timeUnit = Hours
  val time = Hours(1)
  val symbol = "mph²"
}

/**
 * Represents acceleration in Earth gravities also knows as G-Force, or g's
 */
object EarthGravities extends AccelerationUnit {
  val changeUnit = MetersPerSecond
  val change = squants.motion.StandardEarthGravity * Seconds(1)
  val timeUnit = Seconds
  val time = Seconds(1)
  val symbol = "g"
  //  def apply(g: Double) = MetersPerSecondSquared(squants.StandardEarthGravity.toMetersPerSecondSquared * g)
}

object AccelerationConversions {

  implicit class AccelerationConversions(d: Double) {
    def mpss = MetersPerSecondSquared(d)
    def fpss = FeetPerSecondSquared(d)
  }

  implicit object AccelerationNumeric extends AbstractQuantityNumeric[Acceleration](MetersPerSecondSquared)
}