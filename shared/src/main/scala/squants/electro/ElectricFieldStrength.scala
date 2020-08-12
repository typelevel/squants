package squants.electro

import squants.space.{Length, Meters}
import squants.{AbstractQuantityNumeric, Dimension, PrimaryUnit, Quantity, SiUnit, UnitConverter, UnitOfMeasure}

/**
  *
  * @author Nicolas Vinuesa
  * @since 1.4
  *
  * @param value Double
  */
final class ElectricFieldStrength private (val value: Double, val unit: ElectricFieldStrengthUnit)
  extends Quantity[ElectricFieldStrength] {

  def dimension = ElectricFieldStrength

  def *(that: Length): ElectricPotential = Volts(this.toVoltsPerMeter * that.toMeters)

  def toVoltsPerMeter = to(VoltsPerMeter)
}

object ElectricFieldStrength extends Dimension[ElectricFieldStrength] {
  private[electro] def apply[A](n: A, unit: ElectricFieldStrengthUnit)(implicit num: Numeric[A]) = new ElectricFieldStrength(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "ElectricFieldStrength"
  def primaryUnit = VoltsPerMeter
  def siUnit = VoltsPerMeter
  def units = Set(VoltsPerMeter)
}

trait ElectricFieldStrengthUnit extends UnitOfMeasure[ElectricFieldStrength] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = ElectricFieldStrength(n, this)
}

object VoltsPerMeter extends ElectricFieldStrengthUnit with PrimaryUnit with SiUnit {
  val symbol = Volts.symbol + "/" + Meters.symbol
}

object ElectricFieldStrengthConversions {
  lazy val voltPerMeter = VoltsPerMeter(1)

  implicit class ElectricFieldStrengthConversions[A](n: A)(implicit num: Numeric[A]) {
    def voltsPerMeter = VoltsPerMeter(n)
  }

  implicit object ElectricFieldStrengthNumeric extends AbstractQuantityNumeric[ElectricFieldStrength](ElectricFieldStrength.primaryUnit)
}
