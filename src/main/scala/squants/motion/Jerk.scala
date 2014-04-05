/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.time.{ TimeUnit, Seconds, TimeDerivative }

/**
 * Represents the third time derivative of position after Velocity and Acceleration
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param change Acceleration change
 * @param time Time
 */
case class Jerk(change: Acceleration, time: Time) extends Quantity[Jerk]
    with TimeDerivative[Acceleration] {

  def valueUnit = MetersPerSecondCubed
  def value = toMetersPerSecondCubed

  def toString(unit: JerkUnit) = to(unit) + " " + unit.symbol

  def to(unit: JerkUnit) = change.to(unit.changeUnit) / unit.change.to(unit.changeUnit) / time.to(unit.timeUnit)
  def toMetersPerSecondCubed: Double = change.toMetersPerSecondSquared / time.toSeconds
  def toFeetPerSecondCubed: Double = change.toFeetPerSecondSquared / time.toSeconds
}

trait JerkUnit extends UnitOfMeasure[Jerk] {
  def changeUnit: AccelerationUnit
  def change: Acceleration
  def timeUnit: TimeUnit
  def time: Time

  def apply(value: Double) = Jerk(change * value, time)
  def unapply(jerk: Jerk) = Some(jerk.to(this))
}

object MetersPerSecondCubed extends JerkUnit with ValueUnit {
  val changeUnit = MetersPerSecondSquared
  val change = MetersPerSecondSquared(1)
  val timeUnit = Seconds
  val time = Seconds(1)
  val symbol = "m/s³"
}
object FeetPerSecondCubed extends JerkUnit {
  val changeUnit = FeetPerSecondSquared
  val change = FeetPerSecondSquared(1)
  val timeUnit = Seconds
  val time = Seconds(1)
  val symbol = "ft/s³"
}

object JerkConversions {
  lazy val meterPerSecondCubed = MetersPerSecondCubed(1)
  lazy val footPerSecondCubed = FeetPerSecondCubed(1)

  implicit class JerkConversions(val d: Double) {
    def metersPerSecondCubed = MetersPerSecondCubed(d)
    def feetPerSecondCubed = FeetPerSecondCubed(d)
  }

  implicit object JerkNumeric extends AbstractQuantityNumeric[Jerk](MetersPerSecondCubed)
}
