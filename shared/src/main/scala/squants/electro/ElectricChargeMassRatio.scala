package squants.electro

import squants.mass.Kilograms
import squants.{AbstractQuantityNumeric, Dimension, Mass, PrimaryUnit, Quantity, SiUnit, UnitConverter, UnitOfMeasure}

/**
  *
  * @author Nicolas Vinuesa
  * @since 1.4
  *
  * @param value Double
  */
final class ElectricChargeMassRatio private (val value: Double, val unit: ElectricChargeMassRatioUnit)
  extends Quantity[ElectricChargeMassRatio] {

  def dimension = ElectricChargeMassRatio

  def *(that: Mass): ElectricCharge = Coulombs(this.toCoulombsKilograms * that.toKilograms)

  def toCoulombsKilograms = to(CoulombsPerKilogram)
}

object ElectricChargeMassRatio extends Dimension[ElectricChargeMassRatio] {
  private[electro] def apply[A](n: A, unit: ElectricChargeMassRatioUnit)(implicit num: Numeric[A]) = new ElectricChargeMassRatio(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "ElectricChargeMassRatio"
  def primaryUnit = CoulombsPerKilogram
  def siUnit = CoulombsPerKilogram
  def units = Set(CoulombsPerKilogram)
}

trait ElectricChargeMassRatioUnit extends UnitOfMeasure[ElectricChargeMassRatio] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = ElectricChargeMassRatio(n, this)
}

object CoulombsPerKilogram extends ElectricChargeMassRatioUnit with PrimaryUnit with SiUnit {
  val symbol = Coulombs.symbol + "/" + Kilograms.symbol
}

object ElectricChargeMassRatioConversions {
  lazy val coulombPerKilogram = CoulombsPerKilogram(1)

  implicit class ElectricChargeMassRatioConversions[A](n: A)(implicit num: Numeric[A]) {
    def coulombsPerKilogram = CoulombsPerKilogram(n)
  }

  implicit object ElectricChargeMassRatioNumeric extends AbstractQuantityNumeric[ElectricChargeMassRatio](ElectricChargeMassRatio.primaryUnit)
}