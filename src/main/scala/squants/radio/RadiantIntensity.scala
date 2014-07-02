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
 * @param value Double
 */
final class RadiantIntensity private (val value: Double) extends Quantity[RadiantIntensity] {

  def valueUnit = RadiantIntensity.valueUnit

  def *(that: SolidAngle): Power = Watts(toWattsPerSteradian * that.toSquaredRadians)
  def /(that: Power): SolidAngle = SquareRadians(toWattsPerSteradian / that.toWatts)
  def /(that: Length): SpectralIntensity = WattsPerSteradianPerMeter(toWattsPerSteradian / that.toMeters)
  def /(that: SpectralIntensity): Length = Meters(toWattsPerSteradian / that.toWattsPerSteradianPerMeter)
  def /(that: Area): Radiance = WattsPerSteradianPerSquareMeter(toWattsPerSteradian / that.toSquareMeters)
  def /(that: Radiance): Area = SquareMeters(this.toWattsPerSteradian / that.toWattsPerSteradianPerSquareMeter)

  def toWattsPerSteradian = to(WattsPerSteradian)
}

object RadiantIntensity extends QuantityCompanion[RadiantIntensity] {
  private[radio] def apply[A](n: A)(implicit num: Numeric[A]) = new RadiantIntensity(num.toDouble(n))
  def apply(s: String) = parseString(s)
  def name = "RadiantIntensity"
  def valueUnit = WattsPerSteradian
  def units = Set(WattsPerSteradian)
}

trait RadiantIntensityUnit extends UnitOfMeasure[RadiantIntensity] {
  def apply[A](n: A)(implicit num: Numeric[A]) = RadiantIntensity(convertFrom(n))
}

object WattsPerSteradian extends RadiantIntensityUnit with ValueUnit {
  val symbol = Watts.symbol + "/" + SquaredRadians.symbol
}

object RadiantIntensityConversions {
  lazy val wattPerSteradian = WattsPerSteradian(1)

  implicit class RadiantIntensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def wattsPerSteradian = WattsPerSteradian(n)
  }

  implicit object RadiantIntensityNumeric extends AbstractQuantityNumeric[RadiantIntensity](RadiantIntensity.valueUnit)
}

