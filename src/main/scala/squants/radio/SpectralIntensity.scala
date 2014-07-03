/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.radio

import squants._
import squants.energy.Watts
import squants.space.{ Meters, SquaredRadians }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class SpectralIntensity private (val value: Double) extends Quantity[SpectralIntensity] {

  def valueUnit = SpectralIntensity.valueUnit

  def *(that: Length): RadiantIntensity = WattsPerSteradian(toWattsPerSteradianPerMeter * that.toMeters)
  def /(that: RadiantIntensity): Length = Meters(toWattsPerSteradianPerMeter / that.toWattsPerSteradian)

  def toWattsPerSteradianPerMeter = to(WattsPerSteradianPerMeter)
}

object SpectralIntensity extends QuantityCompanion[SpectralIntensity] {
  private[radio] def apply[A](n: A)(implicit num: Numeric[A]) = new SpectralIntensity(num.toDouble(n))
  def apply = parseString _
  def name = "SpectralIntensity"
  def valueUnit = WattsPerSteradianPerMeter
  def units = Set(WattsPerSteradianPerMeter)
}

trait SpectralIntensityUnit extends UnitOfMeasure[SpectralIntensity] {
  def apply[A](n: A)(implicit num: Numeric[A]) = SpectralIntensity(convertFrom(n))
}

object WattsPerSteradianPerMeter extends SpectralIntensityUnit with ValueUnit {
  val symbol = Watts.symbol + "/" + SquaredRadians.symbol + "/" + Meters.symbol
}

object SpectralIntensityConversions {
  lazy val wattPerSteradianPerMeter = WattsPerSteradianPerMeter(1)

  implicit class SpectralIntensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def wattsPerSteradianPerMeter = WattsPerSteradianPerMeter(n)
  }

  implicit object SpectralIntensityNumeric extends AbstractQuantityNumeric[SpectralIntensity](SpectralIntensity.valueUnit)
}

