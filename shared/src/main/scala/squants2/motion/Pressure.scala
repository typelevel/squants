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

final case class Pressure[A: Numeric] private [squants2]  (value: A, unit: PressureUnit)
  extends Quantity[A, Pressure.type] {
  override type Q[B] = Pressure[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toPascals: A = to(Pascals)
  def toTorrs: A = to(Torrs)
  def toMillimetersOfMercury: A = to(MillimetersOfMercury)
  def toInchesOfMercury: A = to(InchesOfMercury)
  def toPoundsPerSquareInch: A = to(PoundsPerSquareInch)
  def toBars: A = to(Bars)
  def toStandardAtmospheres: A = to(StandardAtmospheres)
}

object Pressure extends Dimension("Pressure") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Pascals
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Pascals
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(Pascals, Torrs, MillimetersOfMercury, InchesOfMercury, PoundsPerSquareInch, Bars, StandardAtmospheres)

  implicit class PressureCons[A](a: A)(implicit num: Numeric[A]) {
    def pascals: Pressure[A] = Pascals(a)
    def torrs: Pressure[A] = Torrs(a)
    def millimetersOfMercury: Pressure[A] = MillimetersOfMercury(a)
    def inchesOfMercury: Pressure[A] = InchesOfMercury(a)
    def poundsPerSquareInch: Pressure[A] = PoundsPerSquareInch(a)
    def bars: Pressure[A] = Bars(a)
    def standardAtmospheres: Pressure[A] = StandardAtmospheres(a)
  }

  lazy val pascals: Pressure[Int] = Pascals(1)
  lazy val torrs: Pressure[Int] = Torrs(1)
  lazy val millimetersOfMercury: Pressure[Int] = MillimetersOfMercury(1)
  lazy val inchesOfMercury: Pressure[Int] = InchesOfMercury(1)
  lazy val poundsPerSquareInch: Pressure[Int] = PoundsPerSquareInch(1)
  lazy val bars: Pressure[Int] = Bars(1)
  lazy val standardAtmospheres: Pressure[Int] = StandardAtmospheres(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = PressureNumeric[A]()
  private case class PressureNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Pressure.type], y: Quantity[A, Pressure.type]): Quantity[A, Pressure.this.type] =
      Pascals(x.to(Pascals) * y.to(Pascals))
  }
}

abstract class PressureUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Pressure.type] {
  override def dimension: Pressure.type = Pressure
  override def apply[A: Numeric](value: A): Pressure[A] = Pressure(value, this)
}

case object Pascals extends PressureUnit("Pa", 1) with PrimaryUnit with SiUnit
case object Torrs extends PressureUnit("Torr", 133.32236842105263)
case object MillimetersOfMercury extends PressureUnit("mmHg", 133.322387415)
case object InchesOfMercury extends PressureUnit("inHg", 3386.389)
case object PoundsPerSquareInch extends PressureUnit("psi", 6894.757293168361)
case object Bars extends PressureUnit("bar", 100000.0)
case object StandardAtmospheres extends PressureUnit("atm", 101325.0)
