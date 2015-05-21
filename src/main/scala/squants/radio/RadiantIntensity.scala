/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
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
final class RadiantIntensity private (val value: Double, val unit: RadiantIntensityUnit)
    extends Quantity[RadiantIntensity] {

  def dimension = RadiantIntensity

  def *(that: SolidAngle): Power = Watts(toWattsPerSteradian * that.toSquaredRadians)
  def /(that: Power): SolidAngle = SquareRadians(toWattsPerSteradian / that.toWatts)
  def /(that: Length): SpectralIntensity = WattsPerSteradianPerMeter(toWattsPerSteradian / that.toMeters)
  def /(that: SpectralIntensity): Length = Meters(toWattsPerSteradian / that.toWattsPerSteradianPerMeter)
  def /(that: Area): Radiance = WattsPerSteradianPerSquareMeter(toWattsPerSteradian / that.toSquareMeters)
  def /(that: Radiance): Area = SquareMeters(this.toWattsPerSteradian / that.toWattsPerSteradianPerSquareMeter)

  def toWattsPerSteradian = to(WattsPerSteradian)
}

object RadiantIntensity extends Dimension[RadiantIntensity] {
  private[radio] def apply[A](n: A, unit: RadiantIntensityUnit)(implicit num: Numeric[A]) = new RadiantIntensity(num.toDouble(n), unit)
  def apply = parse _
  def name = "RadiantIntensity"
  def primaryUnit = WattsPerSteradian
  def siUnit = WattsPerSteradian
  def units = Set(WattsPerSteradian)
}

trait RadiantIntensityUnit extends UnitOfMeasure[RadiantIntensity] {
  def apply[A](n: A)(implicit num: Numeric[A]) = RadiantIntensity(n, this)
}

object WattsPerSteradian extends RadiantIntensityUnit with PrimaryUnit with SiUnit {
  val symbol = Watts.symbol + "/" + SquaredRadians.symbol
}

object RadiantIntensityConversions {
  lazy val wattPerSteradian = WattsPerSteradian(1)

  implicit class RadiantIntensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def wattsPerSteradian = WattsPerSteradian(n)
  }

  implicit object RadiantIntensityNumeric extends AbstractQuantityNumeric[RadiantIntensity](RadiantIntensity.primaryUnit)
}

