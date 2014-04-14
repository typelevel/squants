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
import squants.space.Meters

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param power Power
 * @param length Length
 */
case class SpectralPower(power: Power, length: Length) extends Quantity[SpectralPower] {

  def value = power.toWatts / length.toMeters
  def valueUnit = WattsPerMeter

  def *(that: Length): Power = power * (that / length)
  def /(that: Power): Length = Meters(toWattsPerMeter / that.toWatts)

  def toWattsPerMeter = value
}

trait SpectralPowerUnit extends UnitOfMeasure[SpectralPower]

object WattsPerMeter extends SpectralPowerUnit with ValueUnit {
  val symbol = Watts.symbol + "/" + Meters.symbol
  def apply[A](n: A)(implicit num: Numeric[A]) = SpectralPower(Watts(num.toDouble(n)), Meters(1))
}

object SpectralPowerConversions {
  lazy val wattPerMeter = WattsPerMeter(1)

  implicit class SpectralPowerConversions[A](n: A)(implicit num: Numeric[A]) {
    def wattsPerMeter = WattsPerMeter(n)
  }

  implicit object SpectralPowerNumeric extends AbstractQuantityNumeric[SpectralPower](WattsPerMeter)
}

