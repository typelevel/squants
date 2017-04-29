package squants.motion

import squants.mass.{MomentOfInertia, Pounds}
import squants.space.{Feet, Meters}
import squants.{Dimension, Energy, PrimaryUnit, Quantity, SiBaseUnit, UnitConverter, UnitOfMeasure}

/**
  *
  * @author paxelord
  * @since 1.2
  *
  * @param value Double
  */
final class Torque private (val value: Double, val unit: TorqueUnit)
    extends Quantity[Torque] {

  def dimension = Torque

  def toNewtonMeters = to(NewtonMeters)

  def / (that: MomentOfInertia): AngularAcceleration = {
    RadiansPerSecondSquared(toNewtonMeters / that.toKilogramsMetersSquared)
  }
}

object Torque extends Dimension[Torque] {
  private[motion] def apply[A](n: A, unit: TorqueUnit)(implicit num: Numeric[A]) = new Torque(num.toDouble(n), unit)
  def apply = parse _
  def name = "MomentOfInertia"
  def primaryUnit = NewtonMeters
  def siUnit = NewtonMeters
  def units = Set(NewtonMeters, PoundFeet)
}

trait TorqueUnit extends UnitOfMeasure[Torque] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = {
    Torque(num.toDouble(n), this)
  }
}

object NewtonMeters extends TorqueUnit with PrimaryUnit with  SiBaseUnit {
  val symbol = Newtons.symbol + "‧" + Meters.symbol
}

object PoundFeet extends TorqueUnit {
  val symbol = Pounds.symbol + "‧" + Feet.symbol
  val conversionFactor = Pounds.conversionFactor * Feet.conversionFactor
}