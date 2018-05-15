/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.radio

import squants._
import squants.energy.{ErgsPerSecond, Watts}
import squants.space._

/**
 * @author  florianNussberger
 * @since   0.6
 *
 * @param value Double
 */
final class SpectralIrradiance private (val value: Double, val unit: SpectralIrradianceUnit)
    extends Quantity[SpectralIrradiance] {

  def dimension = SpectralIrradiance

  def toWattsPerCubicMeter = to(WattsPerCubicMeter)
  def toWattsPerSquareMeterPerNanometer = to(WattsPerSquareMeterPerNanometer)
  def toWattsPerSquareMeterPerMicron = to(WattsPerSquareMeterPerMicron)
  def toErgsPerSecondPerSquareCentimeterPerAngstrom = to(ErgsPerSecondPerSquareCentimeterPerAngstrom)
}

object SpectralIrradiance extends Dimension[SpectralIrradiance] {
  private[radio] def apply[A](n: A, unit: SpectralIrradianceUnit)(implicit num: Numeric[A]) = new SpectralIrradiance(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "SpectralIrradiance"
  def primaryUnit = WattsPerCubicMeter
  def siUnit = WattsPerCubicMeter
  def units = Set(WattsPerCubicMeter, WattsPerSquareMeterPerMicron, WattsPerSquareMeterPerNanometer, ErgsPerSecondPerSquareCentimeterPerAngstrom)
}

trait SpectralIrradianceUnit extends UnitOfMeasure[SpectralIrradiance] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = SpectralIrradiance(n, this)
}

object WattsPerCubicMeter extends SpectralIrradianceUnit with PrimaryUnit with SiUnit {
  val symbol = Watts.symbol + "/" + CubicMeters.symbol
}

object WattsPerSquareMeterPerNanometer extends SpectralIrradianceUnit with SiUnit {
  val conversionFactor = 1 / MetricSystem.Nano
  val symbol = Watts.symbol + "/" + SquareMeters.symbol + "/" + Nanometers.symbol
 }

object WattsPerSquareMeterPerMicron extends SpectralIrradianceUnit with SiUnit {
  val conversionFactor = 1 / MetricSystem.Micro
  val symbol = Watts.symbol + "/" + SquareMeters.symbol + "/" + Microns.symbol
}

object ErgsPerSecondPerSquareCentimeterPerAngstrom extends SpectralIrradianceUnit {
  val conversionFactor = ErgsPerSecond.conversionFactor / SquareCentimeters.conversionFactor / Angstroms.conversionFactor
  val symbol = ErgsPerSecond.symbol + "/" + SquareCentimeters.symbol + "/" + Angstroms.symbol
}

object SpectralIrradianceConversions {
  lazy val wattPerCubicMeter = WattsPerCubicMeter(1)
  lazy val wattPerSquareMeterPerNanometer = WattsPerSquareMeterPerNanometer(1)
  lazy val wattPerSquareMeterPerMicron = WattsPerSquareMeterPerMicron(1)
  lazy val ergPerSecondPerSquareCentimeterPerAngstrom = ErgsPerSecondPerSquareCentimeterPerAngstrom(1)

  implicit class SpectralIrradianceConversions[A](n: A)(implicit num: Numeric[A]) {
    def wattsPerCubicMeter = WattsPerCubicMeter(n)
    def wattsPerSquareMeterPerNanometer = WattsPerSquareMeterPerNanometer(n)
    def wattsPerSquareMeterPerMicron = WattsPerSquareMeterPerMicron(n)
    def ergsPerSecondPerSquareCentimeterPerAngstrom = ErgsPerSecondPerSquareCentimeterPerAngstrom(n)
  }

  implicit object SpectralIrradianceNumeric extends AbstractQuantityNumeric[SpectralIrradiance](SpectralIrradiance.primaryUnit)
}
