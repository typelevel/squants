/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.radio

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class Dose[A: Numeric] private [squants2]  (value: A, unit: DoseUnit)
  extends Quantity[A, Dose.type] {
  override type Q[B] = Dose[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toRems: A = to(Rems)
  def toSieverts: A = to(Sieverts)
}

object Dose extends Dimension("Dose") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Sieverts
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Sieverts
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(Rems, Sieverts)

  implicit class DoseCons[A](a: A)(implicit num: Numeric[A]) {
    def rems: Dose[A] = Rems(a)
    def sieverts: Dose[A] = Sieverts(a)
  }

  lazy val rems: Dose[Int] = Rems(1)
  lazy val sieverts: Dose[Int] = Sieverts(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = DoseNumeric[A]()
  private case class DoseNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Dose.type], y: Quantity[A, Dose.type]): Quantity[A, Dose.this.type] =
      Sieverts(x.to(Sieverts) * y.to(Sieverts))
  }
}

abstract class DoseUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Dose.type] {
  override def dimension: Dose.type = Dose
  override def apply[A: Numeric](value: A): Dose[A] = Dose(value, this)
}

case object Rems extends DoseUnit("rem", 0.01)
case object Sieverts extends DoseUnit("Sv", 1) with PrimaryUnit with SiUnit
