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

final case class SpectralIrradiance[A: Numeric] private [squants2]  (value: A, unit: SpectralIrradianceUnit)
  extends Quantity[A, SpectralIrradiance.type] {
  override type Q[B] = SpectralIrradiance[B]

  // BEGIN CUSTOM OPS
  // END CUSTOM OPS

  def toWattsPerCubicMeter: A = to(WattsPerCubicMeter)
  def toWattsPerSquareMeterPerMicron: A = to(WattsPerSquareMeterPerMicron)
  def toErgsPerSecondPerSquareCentimeterPerAngstrom: A = to(ErgsPerSecondPerSquareCentimeterPerAngstrom)
  def toWattsPerSquareMeterPerNanometer: A = to(WattsPerSquareMeterPerNanometer)
}

object SpectralIrradiance extends Dimension("Spectral Irradiance") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = WattsPerCubicMeter
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = WattsPerCubicMeter
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
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

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = SpectralIrradianceNumeric[A]()
  private case class SpectralIrradianceNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, SpectralIrradiance.type], y: Quantity[A, SpectralIrradiance.type]): Quantity[A, SpectralIrradiance.this.type] =
      WattsPerCubicMeter(x.to(WattsPerCubicMeter) * y.to(WattsPerCubicMeter))
  }
}

abstract class SpectralIrradianceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[SpectralIrradiance.type] {
  override def dimension: SpectralIrradiance.type = SpectralIrradiance
  override def apply[A: Numeric](value: A): SpectralIrradiance[A] = SpectralIrradiance(value, this)
}

case object WattsPerCubicMeter extends SpectralIrradianceUnit("W/m³", 1) with PrimaryUnit with SiUnit
case object WattsPerSquareMeterPerMicron extends SpectralIrradianceUnit("W/m²/µm", MetricSystem.Mega) with SiUnit
case object ErgsPerSecondPerSquareCentimeterPerAngstrom extends SpectralIrradianceUnit("erg/s/cm²/Å", 9999999.999999998)
case object WattsPerSquareMeterPerNanometer extends SpectralIrradianceUnit("W/m²/nm", 9.999999999999999E8) with SiUnit
