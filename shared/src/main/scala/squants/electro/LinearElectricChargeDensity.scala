package squants.electro

import squants.space.Meters
import squants.{AbstractQuantityNumeric, Dimension, Length, PrimaryUnit, Quantity, SiUnit, UnitConverter, UnitOfMeasure}

/**
  *
  * @author Nicolas Vinuesa
  * @since 1.4
  *
  * @param value Double
  */
final class LinearElectricChargeDensity private (val value: Double, val unit: LinearElectricChargeDensityUnit)
  extends Quantity[LinearElectricChargeDensity] {

  def dimension = LinearElectricChargeDensity

  def *(that: Length): ElectricCharge = Coulombs(this.toCoulombsMeters * that.toMeters)
  def /(that: Length): AreaElectricChargeDensity = CoulombsPerSquareMeter(this.toCoulombsMeters / that.toMeters)

  def toCoulombsMeters = to(CoulombsPerMeter)
}

object LinearElectricChargeDensity extends Dimension[LinearElectricChargeDensity] {
  private[electro] def apply[A](n: A, unit: LinearElectricChargeDensityUnit)(implicit num: Numeric[A]) = new LinearElectricChargeDensity(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "LinearElectricChargeDensity"
  def primaryUnit = CoulombsPerMeter
  def siUnit = CoulombsPerMeter
  def units = Set(CoulombsPerMeter)
}

trait LinearElectricChargeDensityUnit extends UnitOfMeasure[LinearElectricChargeDensity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = LinearElectricChargeDensity(n, this)
}

object CoulombsPerMeter extends LinearElectricChargeDensityUnit with PrimaryUnit with SiUnit {
  val symbol = Coulombs.symbol + "/" + Meters.symbol
}

object LinearElectricChargeDensityConversions {
  lazy val coulombPerMeter = CoulombsPerMeter(1)

  implicit class LinearElectricChargeDensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def coulombsPerMeter = CoulombsPerMeter(n)
  }

  implicit object LinearElectricChargeDensityNumeric extends AbstractQuantityNumeric[LinearElectricChargeDensity](LinearElectricChargeDensity.primaryUnit)
}