package squants.motion

import squants.mass.MomentOfInertia
import squants.space._
import squants.time.{Seconds, Time, TimeDerivative}
import squants.{Dimension, Length, PrimaryUnit, Quantity, SiUnit, UnitConverter, UnitOfMeasure}

/**
  *
  * @author paxelord
  * @since 1.2
  *
  * @param value Double
  */
final class AngularAcceleration private (val value: Double, val unit: AngularAccelerationUnit)
    extends Quantity[AngularAcceleration] with TimeDerivative[AngularVelocity] {

  def dimension = AngularAcceleration

  def toRadiansPerSecondSquared = to(RadiansPerSecondSquared)

  def *(that: Length): Acceleration = {
    toRadiansPerSecondSquared * that / Seconds(1).squared
  }

  def *(that: MomentOfInertia): Torque = {
    NewtonMeters(toRadiansPerSecondSquared * that.toKilogramsMetersSquared)
  }

  override protected[squants] def timeIntegrated: AngularVelocity = RadiansPerSecond(toRadiansPerSecondSquared)

  override protected[squants] def time: Time = Seconds(1)
}


object AngularAcceleration extends Dimension[AngularAcceleration] {
  private[motion] def apply[A](n: A, unit: AngularAccelerationUnit)(implicit num: Numeric[A]) = new AngularAcceleration(num.toDouble(n), unit)
  def apply = parse _
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
  override val symbol: String = "rad/s²"
}

object DegreesPerSecondSquared extends AngularAccelerationUnit {
  val symbol = "°/s²"
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