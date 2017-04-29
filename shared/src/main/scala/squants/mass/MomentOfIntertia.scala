package squants.mass

import squants.motion.{AngularAcceleration, NewtonMeters, Torque}
import squants.space.{Feet, Meters}
import squants.{Dimension, PrimaryUnit, SiBaseUnit, StrictlyPositiveQuantity, UnitConverter, UnitOfMeasure}

final class MomentOfInertia private (val value: Double, val unit: MomentOfInertiaUnit)
  extends StrictlyPositiveQuantity[MomentOfInertia](value){

  def dimension = MomentOfInertia

  def toKilogramsMetersSquared: Double = to(KilogramsMetersSquared)
  def toPoundsSquareFeet: Double = to(PoundsSquareFeet)

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
  import Symbols._
  val symbol = Kilograms.symbol + Dot + Meters.symbol + SuperScript2
}

object PoundsSquareFeet extends MomentOfInertiaUnit {
  import Symbols._
  val symbol = Pounds.symbol + Dot + Feet.symbol + SuperScript2
  val conversionFactor = Pounds.conversionFactor * math.pow(Feet.conversionFactor, 2D)
}

object Symbols {
  val Dot = "‧"
  val SuperScript2 = "²"
}