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
  @deprecated(message = "Potentially confusing naming. Use toGradiansPerSecond instead.", since = "Squants 1.3")
  def toGradsPerSecond = to(GradiansPerSecond)
  def toGradiansPerSecond = to(GradiansPerSecond)
  def toTurnsPerSecond = to(TurnsPerSecond)

  /**
    * linear velocity of an object rotating with this angular velocity
    * and the given radius from the center of rotation
    * @param radius the distance from the center of rotation
    * @return linear velocity with given angular velocity and radius
    */
  def onRadius(radius: Length): Velocity = toRadiansPerSecond * radius / Seconds(1)

  protected[squants] def timeIntegrated: Angle = Radians(toRadiansPerSecond)

  protected[squants] def timeDerived: AngularAcceleration = RadiansPerSecondSquared(toRadiansPerSecond)

  protected[squants] def time: Time = Seconds(1)
}

object AngularVelocity extends Dimension[AngularVelocity] {
  private[motion] def apply[A](n: A, unit: AngularVelocityUnit)(implicit num: Numeric[A]) = new AngularVelocity(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "AngularVelocity"
  def primaryUnit = RadiansPerSecond
  def siUnit = RadiansPerSecond
  def units = Set(RadiansPerSecond, DegreesPerSecond, GradiansPerSecond, TurnsPerSecond)
}

trait AngularVelocityUnit extends UnitOfMeasure[AngularVelocity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = AngularVelocity(n, this)
}

object RadiansPerSecond extends AngularVelocityUnit with PrimaryUnit with SiUnit {
  val symbol = "rad/s"
}

object DegreesPerSecond extends AngularVelocityUnit {
  val symbol = "°/s"
  val conversionFactor = Degrees.conversionFactor / Radians.conversionFactor
}

object GradiansPerSecond extends AngularVelocityUnit {
  val symbol = "grad/s"
  val conversionFactor = Gradians.conversionFactor / Radians.conversionFactor
}

@deprecated(message = "Potentially confusing naming. Use GradiansPerSecond instead.", since = "Squants 1.3")
object GradsPerSecond extends AngularVelocityUnit {
  val symbol = "grad/s"
  val conversionFactor = Gradians.conversionFactor / Radians.conversionFactor
}

object TurnsPerSecond extends AngularVelocityUnit {
  val symbol = "turns/s"
  val conversionFactor = Turns.conversionFactor / Radians.conversionFactor
}

object AngularVelocityConversions {
  lazy val radianPerSecond = RadiansPerSecond(1)
  lazy val degreePerSecond = DegreesPerSecond(1)
  lazy val gradPerSecond = GradiansPerSecond(1)
  lazy val gradiansPerSecond = GradiansPerSecond(1)
  lazy val turnPerSecond = TurnsPerSecond(1)

  implicit class AngularVelocityConversions[A](n: A)(implicit num: Numeric[A]) {
    def radiansPerSecond = RadiansPerSecond(n)
    def degreesPerSecond = DegreesPerSecond(n)
    @deprecated(message = "Potentially confusing naming. Use gradiansPerSecond instead.", since = "Squants 1.3")
    def gradsPerSecond = GradiansPerSecond(n)
    def gradiansPerSecond = GradiansPerSecond(n)
    def turnsPerSecond = TurnsPerSecond(n)
  }

  implicit object AngularVelocityNumeric extends AbstractQuantityNumeric[AngularVelocity](AngularVelocity.primaryUnit)
}
