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

final case class ChemicalAmount[A: Numeric] private[squants2] (value: A, unit: ChemicalAmountUnit)
  extends Quantity[A, ChemicalAmount] {

  // BEGIN CUSTOM OPS

  //  def /[B](that: Volume[B])(implicit f: B => A): Nothing$ = ???
  // END CUSTOM OPS

  def toMoles[B: Numeric](implicit f: A => B): B = toNum[B](Moles)
  def toPoundMoles[B: Numeric](implicit f: A => B): B = toNum[B](PoundMoles)
}

object ChemicalAmount extends BaseDimension[ChemicalAmount]("Chemical Amount", "N") {

  override def primaryUnit: UnitOfMeasure[ChemicalAmount] with PrimaryUnit[ChemicalAmount] = Moles
  override def siUnit: UnitOfMeasure[ChemicalAmount] with SiBaseUnit[ChemicalAmount] = Moles
  override lazy val units: Set[UnitOfMeasure[ChemicalAmount]] = 
    Set(Moles, PoundMoles)

  implicit class ChemicalAmountCons[A](a: A)(implicit num: Numeric[A]) {
    def moles: ChemicalAmount[A] = Moles(a)
    def poundMoles: ChemicalAmount[A] = PoundMoles(a)
  }

  lazy val moles: ChemicalAmount[Int] = Moles(1)
  lazy val poundMoles: ChemicalAmount[Int] = PoundMoles(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, ChemicalAmount] = ChemicalAmountNumeric[A]()
  private case class ChemicalAmountNumeric[A: Numeric]() extends QuantityNumeric[A, ChemicalAmount](this) {
    override def times(x: Quantity[A, ChemicalAmount], y: Quantity[A, ChemicalAmount]): Quantity[A, ChemicalAmount] =
      Moles(x.to(Moles) * y.to(Moles))
  }
}

abstract class ChemicalAmountUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ChemicalAmount] {
  override def dimension: Dimension[ChemicalAmount] = ChemicalAmount
  override def apply[A: Numeric](value: A): ChemicalAmount[A] = ChemicalAmount(value, this)
}

case object Moles extends ChemicalAmountUnit("mol", 1) with PrimaryUnit[ChemicalAmount] with SiBaseUnit[ChemicalAmount]
case object PoundMoles extends ChemicalAmountUnit("lb-mol", 453.59237)
