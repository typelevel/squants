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

final case class Radiance[A: Numeric] private [squants2]  (value: A, unit: RadianceUnit)
  extends Quantity[A, Radiance.type] {
  override type Q[B] = Radiance[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toWattsPerSteradianPerSquareMeter: A = to(WattsPerSteradianPerSquareMeter)
}

object Radiance extends Dimension("Radiance") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = WattsPerSteradianPerSquareMeter
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = WattsPerSteradianPerSquareMeter
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(WattsPerSteradianPerSquareMeter)

  implicit class RadianceCons[A](a: A)(implicit num: Numeric[A]) {
    def wattsPerSteradianPerSquareMeter: Radiance[A] = WattsPerSteradianPerSquareMeter(a)
  }

  lazy val wattsPerSteradianPerSquareMeter: Radiance[Int] = WattsPerSteradianPerSquareMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = RadianceNumeric[A]()
  private case class RadianceNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, Radiance.type], y: Quantity[A, Radiance.type]): Quantity[A, Radiance.this.type] =
      WattsPerSteradianPerSquareMeter(x.to(WattsPerSteradianPerSquareMeter) * y.to(WattsPerSteradianPerSquareMeter))
  }
}

abstract class RadianceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[Radiance.type] {
  override lazy val dimension: Radiance.type = Radiance
  override def apply[A: Numeric](value: A): Radiance[A] = Radiance(value, this)
}

case object WattsPerSteradianPerSquareMeter extends RadianceUnit("W/sr/m²", 1.0) with PrimaryUnit with SiUnit
