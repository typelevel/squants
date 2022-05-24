/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.electro

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class Resistivity[A: Numeric] private [squants2]  (value: A, unit: ResistivityUnit)
  extends Quantity[A, Resistivity.type] {
  override type Q[B] = Resistivity[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toOhmMeters: A = to(OhmMeters)
}

object Resistivity extends Dimension("Resistivity") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = OhmMeters
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = OhmMeters
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(OhmMeters)

  implicit class ResistivityCons[A](a: A)(implicit num: Numeric[A]) {
    def ohmMeters: Resistivity[A] = OhmMeters(a)
  }

  lazy val ohmMeters: Resistivity[Int] = OhmMeters(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = ResistivityNumeric[A]()
  private case class ResistivityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Resistivity.type], y: Quantity[A, Resistivity.type]): Quantity[A, Resistivity.this.type] =
      OhmMeters(x.to(OhmMeters) * y.to(OhmMeters))
  }
}

abstract class ResistivityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Resistivity.type] {
  override def dimension: Resistivity.type = Resistivity
  override def apply[A: Numeric](value: A): Resistivity[A] = Resistivity(value, this)
}

case object OhmMeters extends ResistivityUnit("Ω⋅m", 1) with PrimaryUnit with SiUnit
