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

final case class RadiantIntensity[A: Numeric] private [squants2]  (value: A, unit: RadiantIntensityUnit)
  extends Quantity[A, RadiantIntensity.type] {
  override type Q[B] = RadiantIntensity[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toWattsPerSteradian: A = to(WattsPerSteradian)
}

object RadiantIntensity extends Dimension("Radiant Intensity") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = WattsPerSteradian
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = WattsPerSteradian
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(WattsPerSteradian)

  implicit class RadiantIntensityCons[A](a: A)(implicit num: Numeric[A]) {
    def wattsPerSteradian: RadiantIntensity[A] = WattsPerSteradian(a)
  }

  lazy val wattsPerSteradian: RadiantIntensity[Int] = WattsPerSteradian(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = RadiantIntensityNumeric[A]()
  private case class RadiantIntensityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, RadiantIntensity.type], y: Quantity[A, RadiantIntensity.type]): Quantity[A, RadiantIntensity.this.type] =
      WattsPerSteradian(x.to(WattsPerSteradian) * y.to(WattsPerSteradian))
  }
}

abstract class RadiantIntensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[RadiantIntensity.type] {
  override lazy val dimension: RadiantIntensity.type = RadiantIntensity
  override def apply[A: Numeric](value: A): RadiantIntensity[A] = RadiantIntensity(value, this)
}

case object WattsPerSteradian extends RadiantIntensityUnit("W/sr", 1) with PrimaryUnit with SiUnit
