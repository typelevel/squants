/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.radio

import squants2._

final case class SpectralIrradiance[A: Numeric] private[squants2] (value: A, unit: SpectralIrradianceUnit)
  extends Quantity[A, SpectralIrradiance] {

  // BEGIN CUSTOM OPS

  // END CUSTOM OPS

  def toWattsPerCubicMeter[B: Numeric](implicit f: A => B): B = toNum[B](WattsPerCubicMeter)
  def toWattsPerSquareMeterPerMicron[B: Numeric](implicit f: A => B): B = toNum[B](WattsPerSquareMeterPerMicron)
  def toErgsPerSecondPerSquareCentimeterPerAngstrom[B: Numeric](implicit f: A => B): B = toNum[B](ErgsPerSecondPerSquareCentimeterPerAngstrom)
  def toWattsPerSquareMeterPerNanometer[B: Numeric](implicit f: A => B): B = toNum[B](WattsPerSquareMeterPerNanometer)
}

object SpectralIrradiance extends Dimension[SpectralIrradiance]("Spectral Irradiance") {

  override def primaryUnit: UnitOfMeasure[SpectralIrradiance] with PrimaryUnit[SpectralIrradiance] = WattsPerCubicMeter
  override def siUnit: UnitOfMeasure[SpectralIrradiance] with SiUnit[SpectralIrradiance] = WattsPerCubicMeter
  override lazy val units: Set[UnitOfMeasure[SpectralIrradiance]] = 
    Set(WattsPerCubicMeter, WattsPerSquareMeterPerMicron, ErgsPerSecondPerSquareCentimeterPerAngstrom, WattsPerSquareMeterPerNanometer)

  implicit class SpectralIrradianceCons[A](a: A)(implicit num: Numeric[A]) {
    def wattsPerCubicMeter: SpectralIrradiance[A] = WattsPerCubicMeter(a)
    def wattsPerSquareMeterPerMicron: SpectralIrradiance[A] = WattsPerSquareMeterPerMicron(a)
    def ergsPerSecondPerSquareCentimeterPerAngstrom: SpectralIrradiance[A] = ErgsPerSecondPerSquareCentimeterPerAngstrom(a)
    def wattsPerSquareMeterPerNanometer: SpectralIrradiance[A] = WattsPerSquareMeterPerNanometer(a)
  }

  lazy val wattsPerCubicMeter: SpectralIrradiance[Int] = WattsPerCubicMeter(1)
  lazy val wattsPerSquareMeterPerMicron: SpectralIrradiance[Int] = WattsPerSquareMeterPerMicron(1)
  lazy val ergsPerSecondPerSquareCentimeterPerAngstrom: SpectralIrradiance[Int] = ErgsPerSecondPerSquareCentimeterPerAngstrom(1)
  lazy val wattsPerSquareMeterPerNanometer: SpectralIrradiance[Int] = WattsPerSquareMeterPerNanometer(1)

}

abstract class SpectralIrradianceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[SpectralIrradiance] {
  override def dimension: Dimension[SpectralIrradiance] = SpectralIrradiance
  override def apply[A: Numeric](value: A): SpectralIrradiance[A] = SpectralIrradiance(value, this)
}

case object WattsPerCubicMeter extends SpectralIrradianceUnit("W/m³", 1) with PrimaryUnit[SpectralIrradiance] with SiUnit[SpectralIrradiance]
case object WattsPerSquareMeterPerMicron extends SpectralIrradianceUnit("W/m²/µm", MetricSystem.Mega) with SiUnit[SpectralIrradiance]
case object ErgsPerSecondPerSquareCentimeterPerAngstrom extends SpectralIrradianceUnit("erg/s/cm²/Å", 9999999.999999998)
case object WattsPerSquareMeterPerNanometer extends SpectralIrradianceUnit("W/m²/nm", 9.999999999999999E8) with SiUnit[SpectralIrradiance]
