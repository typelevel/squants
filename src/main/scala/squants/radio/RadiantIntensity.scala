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
import squants.space.{ SquareMeters, SquaredRadians }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param power Power
 * @param solidAngle SolidAngle
 */
case class RadiantIntensity(power: Power, solidAngle: SolidAngle) extends Quantity[RadiantIntensity] {

  def value = power.toWatts / solidAngle.toSquaredRadians
  def valueUnit = WattsPerSteradian

  def *(that: SolidAngle): Power = power * (that / solidAngle)
  def /(that: Power): SolidAngle = power / that * solidAngle
  def /(that: Length): SpectralIntensity = SpectralIntensity(this, that)
  def /(that: SpectralIntensity): Length = Meters(toWattsPerSteradian / that.toWattsPerSteradianPerMeter)
  def /(that: Area): Radiance = Radiance(this, that)
  def /(that: Radiance): Area = SquareMeters(this.toWattsPerSteradian / that.toWattsPerSteradianPerSquareMeter)

  def toWattsPerSteradian = power.toWatts
}

trait RadiantIntensityUnit extends UnitOfMeasure[RadiantIntensity]

object WattsPerSteradian extends RadiantIntensityUnit with ValueUnit {
  val symbol = Watts.symbol + "/" + SquaredRadians.symbol
  def apply(d: Double) = RadiantIntensity(Watts(d), SquaredRadians(1))
}

object RadiantIntensityConversions {
  lazy val wattPerSteradian = WattsPerSteradian(1)

  implicit class RadiantIntensityConversions(val d: Double) {
    def wattsPerSteradian = WattsPerSteradian(d)
  }

  implicit object RadiantIntensityNumeric extends AbstractQuantityNumeric[RadiantIntensity](WattsPerSteradian)
}

