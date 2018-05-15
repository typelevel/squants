package squants.electro

import squants.space.Meters
import squants.{AbstractQuantityNumeric, Area, Dimension, Length, PrimaryUnit, Quantity, SiUnit, UnitConverter, UnitOfMeasure}

/**
  *
  * @author Nicolas Vinuesa
  * @since 1.4
  *
  * @param value Double
  */
final class ElectricCurrentDensity private (val value: Double, val unit: ElectricCurrentDensityUnit)
  extends Quantity[ElectricCurrentDensity] {

  def dimension = ElectricCurrentDensity

  def *(that: Area): ElectricCurrent = Amperes(this.toAmperesPerSquareMeter * that.toSquareMeters)
  def *(that: Length): MagneticFieldStrength = AmperesPerMeter(this.toAmperesPerSquareMeter * that.toMeters)

  def toAmperesPerSquareMeter = to(AmperesPerSquareMeter)
}

object ElectricCurrentDensity extends Dimension[ElectricCurrentDensity] {
  private[electro] def apply[A](n: A, unit: ElectricCurrentDensityUnit)(implicit num: Numeric[A]) = new ElectricCurrentDensity(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "ElectricCurrentDensity"
  def primaryUnit = AmperesPerSquareMeter
  def siUnit = AmperesPerSquareMeter
  def units = Set(AmperesPerSquareMeter)
}

trait ElectricCurrentDensityUnit extends UnitOfMeasure[ElectricCurrentDensity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = ElectricCurrentDensity(n, this)
}

object AmperesPerSquareMeter extends ElectricCurrentDensityUnit with PrimaryUnit with SiUnit {
  val symbol = Amperes.symbol + "/" + Meters.symbol + "²"
}

object ElectricCurrentDensityConversions {
  lazy val amperePerSquareMeter = AmperesPerSquareMeter(1)

  implicit class ElectricCurrentDensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def amperesPerSquareMeter = AmperesPerSquareMeter(n)
  }

  implicit object ElectricCurrentDensityNumeric extends AbstractQuantityNumeric[ElectricCurrentDensity](ElectricCurrentDensity.primaryUnit)
}
