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
final class AreaElectricChargeDensity private (val value: Double, val unit: AreaElectricChargeDensityUnit)
  extends Quantity[AreaElectricChargeDensity] {

  def dimension = AreaElectricChargeDensity

  def *(that: Area): ElectricCharge = Coulombs(this.toCoulombsSquareMeters * that.toSquareMeters)
  def *(that: Length): LinearElectricChargeDensity = CoulombsPerMeter(this.toCoulombsSquareMeters * that.toMeters)

  def toCoulombsSquareMeters = to(CoulombsPerSquareMeter)
}

object AreaElectricChargeDensity extends Dimension[AreaElectricChargeDensity] {
  private[electro] def apply[A](n: A, unit: AreaElectricChargeDensityUnit)(implicit num: Numeric[A]) = new AreaElectricChargeDensity(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "AreaElectricChargeDensity"
  def primaryUnit = CoulombsPerSquareMeter
  def siUnit = CoulombsPerSquareMeter
  def units = Set(CoulombsPerSquareMeter)
}

trait AreaElectricChargeDensityUnit extends UnitOfMeasure[AreaElectricChargeDensity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = AreaElectricChargeDensity(n, this)
}

object CoulombsPerSquareMeter extends AreaElectricChargeDensityUnit with PrimaryUnit with SiUnit {
  val symbol = Coulombs.symbol + "/" + Meters.symbol + "²"
}

object AreaElectricChargeDensityConversions {
  lazy val coulombPerSquareMeter = CoulombsPerSquareMeter(1)

  implicit class AreaElectricChargeDensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def coulombsPerSquareMeter = CoulombsPerSquareMeter(n)
  }

  implicit object AreaElectricChargeDensityNumeric extends AbstractQuantityNumeric[AreaElectricChargeDensity](AreaElectricChargeDensity.primaryUnit)
}