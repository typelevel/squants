/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.time.{ TimeUnit, Seconds }
import squants.Angle
import squants.space.{ Turns, Gradians, Degrees, AngleUnit }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param angle Angle
 * @param time Time
 */
case class AngularVelocity(angle: Angle, time: Time) extends Quantity[AngularVelocity] {

  def value = toRadiansPerSecond
  def valueUnit = RadiansPerSecond

  def toString(unit: AngularVelocityUnit) = to(unit) + " " + unit.symbol

  def to(unit: AngularVelocityUnit) = angle / unit.angleBase / time.to(unit.timeUnit)
  def toRadiansPerSecond = to(RadiansPerSecond)
  def toDegreesPerSecond = to(DegreesPerSecond)
  def toGradsPerSecond = to(GradsPerSecond)
  def toTurnsPerSecond = to(TurnsPerSecond)
}

trait AngularVelocityUnit extends UnitOfMeasure[AngularVelocity] {
  def angleUnit: AngleUnit
  def angleBase: Angle
  def timeUnit: TimeUnit
  def time: Time
  def apply(d: Double) = AngularVelocity(angleBase * d, time)
}

object RadiansPerSecond extends AngularVelocityUnit with ValueUnit {
  val angleUnit = Radians
  val angleBase = Radians(1)
  val timeUnit = Seconds
  val time = Seconds(1)
  val symbol = "rad/s"
}

object DegreesPerSecond extends AngularVelocityUnit {
  val angleUnit = Degrees
  val angleBase = Degrees(1)
  val timeUnit = Seconds
  val time = Seconds(1)
  val symbol = "°/s"
}

object GradsPerSecond extends AngularVelocityUnit {
  val angleUnit = Gradians
  val angleBase = Gradians(1)
  val timeUnit = Seconds
  val time = Seconds(1)
  val symbol = "grad/s"
}

object TurnsPerSecond extends AngularVelocityUnit {
  val angleUnit = Turns
  val angleBase = Turns(1)
  val timeUnit = Seconds
  val time = Seconds(1)
  val symbol = "turns/s"
}

object AngularVelocityConversions {
  lazy val radianPerSecond = RadiansPerSecond(1)
  lazy val degreePerSecond = DegreesPerSecond(1)
  lazy val gradPerSecond = GradsPerSecond(1)
  lazy val turnPerSecond = TurnsPerSecond(1)

  implicit class AngularVelocityConversions(val d: Double) {
    def radiansPerSecond = RadiansPerSecond(d)
    def degreesPerSecond = DegreesPerSecond(d)
    def gradsPerSecond = GradsPerSecond(d)
    def turnsPerSecond = TurnsPerSecond(d)
  }

  implicit object AngularVelocityNumeric extends AbstractQuantityNumeric[AngularVelocity](RadiansPerSecond)
}
