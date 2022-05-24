/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.mass

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class ChemicalAmount[A: Numeric] private [squants2]  (value: A, unit: ChemicalAmountUnit)
  extends Quantity[A, ChemicalAmount.type] {
  override type Q[B] = ChemicalAmount[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toMoles: A = to(Moles)
  def toPoundMoles: A = to(PoundMoles)
}

object ChemicalAmount extends BaseDimension("Chemical Amount", "N") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Moles
  override def siUnit: UnitOfMeasure[this.type] with SiBaseUnit = Moles
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(Moles, PoundMoles)

  implicit class ChemicalAmountCons[A](a: A)(implicit num: Numeric[A]) {
    def moles: ChemicalAmount[A] = Moles(a)
    def poundMoles: ChemicalAmount[A] = PoundMoles(a)
  }

  lazy val moles: ChemicalAmount[Int] = Moles(1)
  lazy val poundMoles: ChemicalAmount[Int] = PoundMoles(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = ChemicalAmountNumeric[A]()
  private case class ChemicalAmountNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, ChemicalAmount.type], y: Quantity[A, ChemicalAmount.type]): Quantity[A, ChemicalAmount.this.type] =
      Moles(x.to(Moles) * y.to(Moles))
  }
}

abstract class ChemicalAmountUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ChemicalAmount.type] {
  override lazy val dimension: ChemicalAmount.type = ChemicalAmount
  override def apply[A: Numeric](value: A): ChemicalAmount[A] = ChemicalAmount(value, this)
}

case object Moles extends ChemicalAmountUnit("mol", 1) with PrimaryUnit with SiBaseUnit
case object PoundMoles extends ChemicalAmountUnit("lb-mol", 453.59237)
