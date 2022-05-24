/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.motion

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class PressureChange[A: Numeric] private [squants2]  (value: A, unit: PressureChangeUnit)
  extends Quantity[A, PressureChange.type] {
  override type Q[B] = PressureChange[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toPascalsPerSecond: A = to(PascalsPerSecond)
  def toPoundsPerSquareInchPerSecond: A = to(PoundsPerSquareInchPerSecond)
  def toBarsPerSecond: A = to(BarsPerSecond)
  def toStandardAtmospheresPerSecond: A = to(StandardAtmospheresPerSecond)
}

object PressureChange extends Dimension("Pressure Change") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = PascalsPerSecond
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = PascalsPerSecond
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(PascalsPerSecond, PoundsPerSquareInchPerSecond, BarsPerSecond, StandardAtmospheresPerSecond)

  implicit class PressureChangeCons[A](a: A)(implicit num: Numeric[A]) {
    def pascalsPerSecond: PressureChange[A] = PascalsPerSecond(a)
    def poundsPerSquareInchPerSecond: PressureChange[A] = PoundsPerSquareInchPerSecond(a)
    def barsPerSecond: PressureChange[A] = BarsPerSecond(a)
    def standardAtmospheresPerSecond: PressureChange[A] = StandardAtmospheresPerSecond(a)
  }

  lazy val pascalsPerSecond: PressureChange[Int] = PascalsPerSecond(1)
  lazy val poundsPerSquareInchPerSecond: PressureChange[Int] = PoundsPerSquareInchPerSecond(1)
  lazy val barsPerSecond: PressureChange[Int] = BarsPerSecond(1)
  lazy val standardAtmospheresPerSecond: PressureChange[Int] = StandardAtmospheresPerSecond(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = PressureChangeNumeric[A]()
  private case class PressureChangeNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, PressureChange.type], y: Quantity[A, PressureChange.type]): Quantity[A, PressureChange.this.type] =
      PascalsPerSecond(x.to(PascalsPerSecond) * y.to(PascalsPerSecond))
  }
}

abstract class PressureChangeUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[PressureChange.type] {
  override def dimension: PressureChange.type = PressureChange
  override def apply[A: Numeric](value: A): PressureChange[A] = PressureChange(value, this)
}

case object PascalsPerSecond extends PressureChangeUnit("Pa/s", 1) with PrimaryUnit with SiUnit
case object PoundsPerSquareInchPerSecond extends PressureChangeUnit("psi/s", 6894.757293168361)
case object BarsPerSecond extends PressureChangeUnit("bar/s", 100000.0)
case object StandardAtmospheresPerSecond extends PressureChangeUnit("atm/s", 101325.0)
