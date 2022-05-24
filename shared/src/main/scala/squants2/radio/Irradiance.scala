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

final case class Irradiance[A: Numeric] private [squants2]  (value: A, unit: IrradianceUnit)
  extends Quantity[A, Irradiance.type] {
  override type Q[B] = Irradiance[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toErgsPerSecondPerSquareCentimeter: A = to(ErgsPerSecondPerSquareCentimeter)
  def toWattsPerSquareMeter: A = to(WattsPerSquareMeter)
}

object Irradiance extends Dimension("Irradiance") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = WattsPerSquareMeter
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = WattsPerSquareMeter
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(ErgsPerSecondPerSquareCentimeter, WattsPerSquareMeter)

  implicit class IrradianceCons[A](a: A)(implicit num: Numeric[A]) {
    def ergsPerSecondPerSquareCentimeter: Irradiance[A] = ErgsPerSecondPerSquareCentimeter(a)
    def wattsPerSquareMeter: Irradiance[A] = WattsPerSquareMeter(a)
  }

  lazy val ergsPerSecondPerSquareCentimeter: Irradiance[Int] = ErgsPerSecondPerSquareCentimeter(1)
  lazy val wattsPerSquareMeter: Irradiance[Int] = WattsPerSquareMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = IrradianceNumeric[A]()
  private case class IrradianceNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Irradiance.type], y: Quantity[A, Irradiance.type]): Quantity[A, Irradiance.this.type] =
      WattsPerSquareMeter(x.to(WattsPerSquareMeter) * y.to(WattsPerSquareMeter))
  }
}

abstract class IrradianceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Irradiance.type] {
  override lazy val dimension: Irradiance.type = Irradiance
  override def apply[A: Numeric](value: A): Irradiance[A] = Irradiance(value, this)
}

case object ErgsPerSecondPerSquareCentimeter extends IrradianceUnit("erg/s/cm²", 9.999999999999998E-4)
case object WattsPerSquareMeter extends IrradianceUnit("W/m²", 1) with PrimaryUnit with SiUnit
