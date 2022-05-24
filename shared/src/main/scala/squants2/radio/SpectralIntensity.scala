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

final case class SpectralIntensity[A: Numeric] private [squants2]  (value: A, unit: SpectralIntensityUnit)
  extends Quantity[A, SpectralIntensity.type] {
  override type Q[B] = SpectralIntensity[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toWattsPerSteradianPerMeter: A = to(WattsPerSteradianPerMeter)
}

object SpectralIntensity extends Dimension("SpectralIntensity") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = WattsPerSteradianPerMeter
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = WattsPerSteradianPerMeter
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(WattsPerSteradianPerMeter)

  implicit class SpectralIntensityCons[A](a: A)(implicit num: Numeric[A]) {
    def wattsPerSteradianPerMeter: SpectralIntensity[A] = WattsPerSteradianPerMeter(a)
  }

  lazy val wattsPerSteradianPerMeter: SpectralIntensity[Int] = WattsPerSteradianPerMeter(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = SpectralIntensityNumeric[A]()
  private case class SpectralIntensityNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, SpectralIntensity.type], y: Quantity[A, SpectralIntensity.type]): Quantity[A, SpectralIntensity.this.type] =
      WattsPerSteradianPerMeter(x.to(WattsPerSteradianPerMeter) * y.to(WattsPerSteradianPerMeter))
  }
}

abstract class SpectralIntensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[SpectralIntensity.type] {
  override lazy val dimension: SpectralIntensity.type = SpectralIntensity
  override def apply[A: Numeric](value: A): SpectralIntensity[A] = SpectralIntensity(value, this)
}

case object WattsPerSteradianPerMeter extends SpectralIntensityUnit("W/sr/m", 1.0) with PrimaryUnit with SiUnit
