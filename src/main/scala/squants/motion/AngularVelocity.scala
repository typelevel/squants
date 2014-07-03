/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.space.{ Turns, Gradians, Degrees }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 *
 */
final class AngularVelocity private (val value: Double) extends Quantity[AngularVelocity] {
  // TODO - Make this a TimeDerivative of Angle
  def valueUnit = AngularVelocity.valueUnit

  def toRadiansPerSecond = to(RadiansPerSecond)
  def toDegreesPerSecond = to(DegreesPerSecond)
  def toGradsPerSecond = to(GradsPerSecond)
  def toTurnsPerSecond = to(TurnsPerSecond)
}

object AngularVelocity extends QuantityCompanion[AngularVelocity] {
  private[motion] def apply[A](n: A)(implicit num: Numeric[A]) = new AngularVelocity(num.toDouble(n))
  def apply = parseString _
  def name = "AngularVelocity"
  def valueUnit = RadiansPerSecond
  def units = Set(RadiansPerSecond, DegreesPerSecond, GradsPerSecond, TurnsPerSecond)
}

trait AngularVelocityUnit extends UnitOfMeasure[AngularVelocity] with UnitMultiplier {
  def apply[A](n: A)(implicit num: Numeric[A]) = AngularVelocity(convertFrom(n))
}

object RadiansPerSecond extends AngularVelocityUnit with ValueUnit {
  val symbol = "rad/s"
}

object DegreesPerSecond extends AngularVelocityUnit {
  val symbol = "°/s"
  val multiplier = Degrees.multiplier * Radians.multiplier
}

object GradsPerSecond extends AngularVelocityUnit {
  val symbol = "grad/s"
  val multiplier = Gradians.multiplier * Radians.multiplier
}

object TurnsPerSecond extends AngularVelocityUnit {
  val symbol = "turns/s"
  val multiplier = Turns.multiplier * Radians.multiplier
}

object AngularVelocityConversions {
  lazy val radianPerSecond = RadiansPerSecond(1)
  lazy val degreePerSecond = DegreesPerSecond(1)
  lazy val gradPerSecond = GradsPerSecond(1)
  lazy val turnPerSecond = TurnsPerSecond(1)

  implicit class AngularVelocityConversions[A](n: A)(implicit num: Numeric[A]) {
    def radiansPerSecond = RadiansPerSecond(n)
    def degreesPerSecond = DegreesPerSecond(n)
    def gradsPerSecond = GradsPerSecond(n)
    def turnsPerSecond = TurnsPerSecond(n)
  }

  implicit object AngularVelocityNumeric extends AbstractQuantityNumeric[AngularVelocity](AngularVelocity.valueUnit)
}
