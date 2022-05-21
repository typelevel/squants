package squants2.mass

import squants2._

final case class ChemicalAmount[A: Numeric] private [mass] (value: A, unit: ChemicalAmountUnit) extends Quantity[A, ChemicalAmount.type] {
  override type Q[B] = ChemicalAmount[B]
}

object ChemicalAmount extends BaseDimension("Chemical Amount", "N") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Moles
  override def siUnit: UnitOfMeasure[this.type] with SiBaseUnit = Moles
  override lazy val units: Set[UnitOfMeasure[this.type]] = Set(Moles, PoundMoles)

  // Constructors from Numeric values
  implicit class ChemicalAmountCons[A: Numeric](a: A) {
    def moles: ChemicalAmount[A] = Moles(a)
  }

  // Constants
  lazy val mole: ChemicalAmount[Int] = Moles(1)
  lazy val poundMole: ChemicalAmount[Int] = PoundMoles(1)

}

abstract class ChemicalAmountUnit(val symbol: String, val conversionFactor: Double) extends UnitOfMeasure[ChemicalAmount.type] {
  override def dimension: ChemicalAmount.type = ChemicalAmount
  override def apply[A: Numeric](value: A): ChemicalAmount[A] = ChemicalAmount(value, this)
}

case object Moles extends ChemicalAmountUnit("mol", 1) with PrimaryUnit with SiBaseUnit
case object PoundMoles extends ChemicalAmountUnit("lb-mol", Pounds.conversionFactor)

