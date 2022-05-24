package squants.mass

import squants.motion.{AngularAcceleration, NewtonMeters, Torque}
import squants.space.{Feet, Meters}
import squants.{AbstractQuantityNumeric, Dimension, Length, PrimaryUnit, Quantity, SiBaseUnit, UnitConverter, UnitOfMeasure}

/**
  *
  * @author paxelord
  * @since 1.3
  *
  * @param value Double
  */
final class MomentOfInertia private (val value: Double, val unit: MomentOfInertiaUnit)
    extends Quantity[MomentOfInertia]{

  def dimension = MomentOfInertia

  def toKilogramsMetersSquared = to(KilogramsMetersSquared)
  def toPoundsSquareFeet = to(PoundsSquareFeet)

  def *(angularAcceleration: AngularAcceleration): Torque = {
    val radiansPerSecondSquared = angularAcceleration.toRadiansPerSecondSquared

    NewtonMeters(toKilogramsMetersSquared * radiansPerSecondSquared)
  }

  /**
    * For a point mass with the given MomentOfInertia rotating with a center of
    * rotation at the given radius, return the mass of the point mass
    * @param radius distance to axis of rotation
    * @return mass of point mass with given radius and MomentOfInertia
    */
  def atCenter(radius: Length): Mass = {
    Kilograms(toKilogramsMetersSquared / radius.squared.toSquareMeters)
  }
}

object MomentOfInertia extends Dimension[MomentOfInertia] {
  private[mass] def apply[A](n: A, unit: MomentOfInertiaUnit)(implicit num: Numeric[A]) = new MomentOfInertia(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
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

object MomentOfInertiaConversions {
  lazy val kilogramMetersSquared = KilogramsMetersSquared(1)
  lazy val poundSquareFeet = PoundsSquareFeet(1)

  implicit class MomentOfInertiaConversions[A](val n: A) extends AnyVal {
    def kilogramMetersSquared(implicit num: Numeric[A]) = KilogramsMetersSquared(n)
    def poundSquareFeet(implicit num: Numeric[A]) = PoundsSquareFeet(n)
  }

  implicit object MomentOfInertiaNumeric extends AbstractQuantityNumeric[MomentOfInertia](MomentOfInertia.primaryUnit)
}