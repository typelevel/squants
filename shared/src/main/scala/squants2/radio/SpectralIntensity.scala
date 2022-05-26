/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.radio

import squants2._

final case class SpectralIntensity[A: Numeric] private[squants2] (value: A, unit: SpectralIntensityUnit)
  extends Quantity[A, SpectralIntensity] {

  // BEGIN CUSTOM OPS

  //  def *[B](that: Length[B])(implicit f: B => A): RadiantIntensity[A] = ???
  //  def /[B](that: RadiantIntensity[B])(implicit f: B => A): Length[A] = ???
  // END CUSTOM OPS

  def toWattsPerSteradianPerMeter[B: Numeric](implicit f: A => B): B = toNum[B](WattsPerSteradianPerMeter)
}

object SpectralIntensity extends Dimension[SpectralIntensity]("Spectral Intensity") {

  override def primaryUnit: UnitOfMeasure[SpectralIntensity] with PrimaryUnit[SpectralIntensity] = WattsPerSteradianPerMeter
  override def siUnit: UnitOfMeasure[SpectralIntensity] with SiUnit[SpectralIntensity] = WattsPerSteradianPerMeter
  override lazy val units: Set[UnitOfMeasure[SpectralIntensity]] = 
    Set(WattsPerSteradianPerMeter)

  implicit class SpectralIntensityCons[A](a: A)(implicit num: Numeric[A]) {
    def wattsPerSteradianPerMeter: SpectralIntensity[A] = WattsPerSteradianPerMeter(a)
  }

  lazy val wattsPerSteradianPerMeter: SpectralIntensity[Int] = WattsPerSteradianPerMeter(1)

}

abstract class SpectralIntensityUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[SpectralIntensity] {
  override def dimension: Dimension[SpectralIntensity] = SpectralIntensity
  override def apply[A: Numeric](value: A): SpectralIntensity[A] = SpectralIntensity(value, this)
}

case object WattsPerSteradianPerMeter extends SpectralIntensityUnit("W/sr/m", 1) with PrimaryUnit[SpectralIntensity] with SiUnit[SpectralIntensity]
