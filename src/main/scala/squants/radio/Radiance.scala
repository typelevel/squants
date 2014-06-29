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
final class Radiance private (val value: Double) extends Quantity[Radiance] {

  def valueUnit = Radiance.valueUnit

  def *(that: Area): RadiantIntensity = WattsPerSteradian(toWattsPerSteradianPerSquareMeter * that.toSquareMeters)
  def /(that: RadiantIntensity): Area = SquareMeters(toWattsPerSteradianPerSquareMeter / that.toWattsPerSteradian)

  def toWattsPerSteradianPerSquareMeter = to(WattsPerSteradianPerSquareMeter)
}

object Radiance extends QuantityCompanion[Radiance] {
  private[radio] def apply[A](n: A)(implicit num: Numeric[A]) = new Radiance(num.toDouble(n))
  def apply(s: String) = parseString(s)
  def name = "Radiance"
  def valueUnit = WattsPerSteradianPerSquareMeter
  def units = Set(WattsPerSteradianPerSquareMeter)
}

trait RadianceUnit extends UnitOfMeasure[Radiance] {
  def apply[A](n: A)(implicit num: Numeric[A]) = Radiance(convertFrom(n))
}

object WattsPerSteradianPerSquareMeter extends RadianceUnit with ValueUnit {
  val symbol = Watts.symbol + "/" + SquaredRadians.symbol + "/" + SquareMeters.symbol
}

object RadianceConversions {
  lazy val wattPerSteradianPerSquareMeter = WattsPerSteradianPerSquareMeter(1)

  implicit class RadianceConversions[A](n: A)(implicit num: Numeric[A]) {
    def wattsPerSteradianPerSquareMeter = WattsPerSteradianPerSquareMeter(n)
  }

  implicit object RadianceNumeric extends AbstractQuantityNumeric[Radiance](Radiance.valueUnit)
}

