package squants.motion

import squants.mass.MomentOfInertia
import squants.space._
import squants.time.{Seconds, Time, TimeDerivative}
import squants.{AbstractQuantityNumeric, Dimension, Length, PrimaryUnit, Quantity, SiUnit, SquantifiedDouble, UnitConverter, UnitOfMeasure}

/**
  *
  * @author paxelord
  * @since 1.3
  *
  * @param value Double
  */
final class AngularAcceleration private (val value: Double, val unit: AngularAccelerationUnit)
    extends Quantity[AngularAcceleration] with TimeDerivative[AngularVelocity] {

  def dimension = AngularAcceleration

  def toRadiansPerSecondSquared = to(RadiansPerSecondSquared)
  def toDegreesPerSecondSquared = to(DegreesPerSecondSquared)
  def toGradsPerSecondSquared = to(GradiansPerSecondSquared)
  def toTurnsPerSecondSquared = to(TurnsPerSecondSquared)
  def toArcminutesPerSecondSquared = to(ArcminutesPerSecondSquared)
  def toArcsecondsPerSecondSquared = to(ArcsecondsPerSecondSquared)

  /**
    * linear acceleration of an object rotating with this angular acceleration
    * and the given radius from the center of rotation
    * @param radius the distance from the center of rotation
    * @return linear acceleration with given angular acceleration and radius
    */
  def onRadius(radius: Length): Acceleration = toRadiansPerSecondSquared * radius / Seconds(1).squared


  def *(that: MomentOfInertia): Torque = {
    NewtonMeters(this.toRadiansPerSecondSquared * that.toKilogramsMetersSquared)
  }

  override protected[squants] def timeIntegrated: AngularVelocity = RadiansPerSecond(toRadiansPerSecondSquared)

  override protected[squants] def time: Time = Seconds(1)
}

object AngularAcceleration extends Dimension[AngularAcceleration] {
  private[motion] def apply[A](n: A, unit: AngularAccelerationUnit)(implicit num: Numeric[A]) = new AngularAcceleration(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "AngularAcceleration"
  def primaryUnit = RadiansPerSecondSquared
  def siUnit = RadiansPerSecondSquared
  def units = Set(
    RadiansPerSecondSquared,
    DegreesPerSecondSquared,
    GradiansPerSecondSquared,
    TurnsPerSecondSquared,
    ArcminutesPerSecondSquared,
    ArcsecondsPerSecondSquared)
}

trait AngularAccelerationUnit extends UnitOfMeasure[AngularAcceleration] with
                                      UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = {
    AngularAcceleration(num.toDouble(n), this)
  }

  val conversionFactor: Double
}

object RadiansPerSecondSquared extends AngularAccelerationUnit with PrimaryUnit with SiUnit{
  val symbol = Radians.symbol + "/s²"
}

object DegreesPerSecondSquared extends AngularAccelerationUnit {
  val symbol = Degrees.symbol + "/s²"
  val conversionFactor = Degrees.conversionFactor
}

object GradiansPerSecondSquared extends AngularAccelerationUnit {
  val symbol = Gradians.symbol + "/s²"
  val conversionFactor = Gradians.conversionFactor
}

object TurnsPerSecondSquared extends AngularAccelerationUnit {
  val symbol = Turns.symbol + "/s²"
  val conversionFactor = Turns.conversionFactor
}

object ArcminutesPerSecondSquared extends AngularAccelerationUnit {
  val symbol = Arcminutes.symbol + "/s²"
  val conversionFactor = Arcminutes.conversionFactor
}

object ArcsecondsPerSecondSquared extends AngularAccelerationUnit{
  val symbol = Arcseconds.symbol + "/s²"
  val conversionFactor = Arcseconds.conversionFactor
}

object AngularAccelerationConversions {
  lazy val radianPerSecondSquared = RadiansPerSecondSquared(1)
  lazy val degreePerSecondSquared = DegreesPerSecondSquared(1)
  lazy val gradPerSecondSquared = GradiansPerSecondSquared(1)
  lazy val turnPerSecondSquared = TurnsPerSecondSquared(1)

  implicit class AngularAccelerationConversions[A](val n: A) extends AnyVal {
    def radiansPerSecondSquared(implicit num: Numeric[A]) = RadiansPerSecondSquared(n)
    def degreesPerSecondSquared(implicit num: Numeric[A]) = DegreesPerSecondSquared(n)
    def gradsPerSecondSquared(implicit num: Numeric[A]) = GradiansPerSecondSquared(n)
    def turnsPerSecondSquared(implicit num: Numeric[A]) = TurnsPerSecondSquared(n)
  }

  implicit object AngularAccelerationNumeric extends AbstractQuantityNumeric[AngularAcceleration](AngularAcceleration.primaryUnit)
}