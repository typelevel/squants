package squants.mass

import squants.motion.{AngularAcceleration, NewtonMeters, Torque}
import squants.space.{Feet, Meters}
import squants.{Dimension, PrimaryUnit, SiBaseUnit, StrictlyPositiveQuantity, UnitConverter, UnitOfMeasure}

/**
  *
  * @author paxelord
  * @since 1.2
  *
  * @param value Double
  */
final class MomentOfInertia private (val value: Double, val unit: MomentOfInertiaUnit)
    extends StrictlyPositiveQuantity[MomentOfInertia](value){

  def dimension = MomentOfInertia

  def toKilogramsMetersSquared = to(KilogramsMetersSquared)
  def toPoundsSquareFeet = to(PoundsSquareFeet)

  def *(angularAcceleration: AngularAcceleration): Torque = {
    val kilogramsMetersSquared = toKilogramsMetersSquared
    val radiansPerSecondSquared = angularAcceleration.toRadiansPerSecondSquared

    NewtonMeters(kilogramsMetersSquared * radiansPerSecondSquared)
  }
}

object MomentOfInertia extends Dimension[MomentOfInertia] {
  private[mass] def apply[A](n: A, unit: MomentOfInertiaUnit)(implicit num: Numeric[A]) = new MomentOfInertia(num.toDouble(n), unit)
  def apply = parse _
  def name = "MomentOfInertia"
  def primaryUnit = KilogramsMetersSquared
  def siUnit = KilogramsMetersSquared
  def units = Set(KilogramsMetersSquared, PoundsSquareFeet)
}

trait MomentOfInertiaUnit extends UnitOfMeasure[MomentOfInertia] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = {
    MomentOfInertia(num.toDouble(n), this)
  }
}

object KilogramsMetersSquared extends MomentOfInertiaUnit with PrimaryUnit with SiBaseUnit {
  val symbol = Kilograms.symbol + "‧" + Meters.symbol + "²"
}

object PoundsSquareFeet extends MomentOfInertiaUnit {
  val symbol = Pounds.symbol + "‧" + Feet.symbol + "²"
  val conversionFactor = Pounds.conversionFactor * math.pow(Feet.conversionFactor, 2D)
}