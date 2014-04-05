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
 * @param radiantIntensity RadiantIntensity
 * @param length Length
 */
case class SpectralIntensity(radiantIntensity: RadiantIntensity, length: Length) extends Quantity[SpectralIntensity] {

  def value = toWattsPerSteradianPerMeter
  def valueUnit = WattsPerSteradianPerMeter

  def *(that: Length): RadiantIntensity = radiantIntensity * (that / length)
  def /(that: RadiantIntensity): Length = radiantIntensity / that * length

  def toWattsPerSteradianPerMeter = radiantIntensity.toWattsPerSteradian / length.toMeters
}

trait SpectralIntensityUnit extends UnitOfMeasure[SpectralIntensity]

object WattsPerSteradianPerMeter extends SpectralIntensityUnit with ValueUnit {
  val symbol = Watts.symbol + "/" + SquaredRadians.symbol + "/" + Meters.symbol
  def apply(d: Double) = SpectralIntensity(RadiantIntensity(Watts(d), SquaredRadians(1)), Meters(1))
}

object SpectralIntensityConversions {
  lazy val wattPerSteradianPerMeter = WattsPerSteradianPerMeter(1)

  implicit class SpectralIntensityConversions(val d: Double) {
    def wattsPerSteradianPerMeter = WattsPerSteradianPerMeter(d)
  }

  implicit object SpectralIntensityNumeric extends AbstractQuantityNumeric[SpectralIntensity](WattsPerSteradianPerMeter)
}

