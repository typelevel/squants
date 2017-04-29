/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.space.{Degrees, Gradians, Turns}
import squants.time.{TimeDerivative, TimeIntegral}

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 *
 */
final class AngularVelocity private (val value: Double, val unit: AngularVelocityUnit)
    extends Quantity[AngularVelocity] with TimeDerivative[Angle] with TimeIntegral[AngularAcceleration]{
  def dimension = AngularVelocity

  def toRadiansPerSecond = to(RadiansPerSecond)
  def toDegreesPerSecond = to(DegreesPerSecond)
  def toGradsPerSecond = to(GradsPerSecond)
  def toTurnsPerSecond = to(TurnsPerSecond)

  def onRadius(that: Length): Velocity = {
    toRadiansPerSecond * that / Seconds(1)
  }

  protected[squants] def timeIntegrated: Angle = Radians(toRadiansPerSecond)

  protected[squants] def timeDerived: AngularAcceleration = RadiansPerSecondSquared(toRadiansPerSecond)

  protected[squants] def time: Time = Seconds(1)
}

object AngularVelocity extends Dimension[AngularVelocity] {
  private[motion] def apply[A](n: A, unit: AngularVelocityUnit)(implicit num: Numeric[A]) = new AngularVelocity(num.toDouble(n), unit)
  def apply = parse _
  def name = "AngularVelocity"
  def primaryUnit = RadiansPerSecond
  def siUnit = RadiansPerSecond
  def units = Set(RadiansPerSecond, DegreesPerSecond, GradsPerSecond, TurnsPerSecond)
}

trait AngularVelocityUnit extends UnitOfMeasure[AngularVelocity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = AngularVelocity(n, this)
}

object RadiansPerSecond extends AngularVelocityUnit with PrimaryUnit with SiUnit {
  val symbol = "rad/s"
}

object DegreesPerSecond extends AngularVelocityUnit {
  val symbol = "°/s"
  val conversionFactor = Degrees.conversionFactor * Radians.conversionFactor
}

object GradsPerSecond extends AngularVelocityUnit {
  val symbol = "grad/s"
  val conversionFactor = Gradians.conversionFactor * Radians.conversionFactor
}

object TurnsPerSecond extends AngularVelocityUnit {
  val symbol = "turns/s"
  val conversionFactor = Turns.conversionFactor * Radians.conversionFactor
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

  implicit object AngularVelocityNumeric extends AbstractQuantityNumeric[AngularVelocity](AngularVelocity.primaryUnit)
}
