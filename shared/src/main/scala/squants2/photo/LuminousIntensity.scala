package squants2.photo

import squants2._

final case class LuminousIntensity[A: Numeric : Converter] private [photo] (value: A, unit: LuminousIntensityUnit) extends Quantity[A, LuminousIntensity.type] {
  override type Q[B] = LuminousIntensity[B]
}

object LuminousIntensity extends BaseDimension("LuminousIntensity", "J") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Candelas
  override def siUnit: UnitOfMeasure[this.type] with SiBaseUnit = Candelas
  override lazy val units: Set[UnitOfMeasure[this.type]] = Set(Candelas)

  // Constructors from Numeric values
  implicit class LuminousIntensityCons[A: Numeric : Converter](a: A) {
    def candelas: LuminousIntensity[A] = Candelas(a)
  }

  // Constants
  lazy val candela: LuminousIntensity[Int] = Candelas(1)

}

abstract class LuminousIntensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[LuminousIntensity.type] {
  override def dimension: LuminousIntensity.type = LuminousIntensity
  override def apply[A: Numeric : Converter](value: A): LuminousIntensity[A] = LuminousIntensity(value, this)
}

case object Candelas extends LuminousIntensityUnit("cd", 1) with PrimaryUnit with SiBaseUnit
