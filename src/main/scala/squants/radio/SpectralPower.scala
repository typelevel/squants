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
 * @param value Double
 */
final class SpectralPower private (val value: Double) extends Quantity[SpectralPower] {

  def valueUnit = SpectralPower.valueUnit

  def *(that: Length): Power = Watts(toWattsPerMeter * that.toMeters)
  def /(that: Power): Length = Meters(toWattsPerMeter / that.toWatts)

  def toWattsPerMeter = value
}

object SpectralPower extends QuantityCompanion[SpectralPower] {
  private[radio] def apply[A](n: A)(implicit num: Numeric[A]) = new SpectralPower(num.toDouble(n))
  def apply(s: String) = parseString(s)
  def name = "SpectralPower"
  def valueUnit = WattsPerMeter
  def units = Set(WattsPerMeter)
}

trait SpectralPowerUnit extends UnitOfMeasure[SpectralPower] {
  def apply[A](n: A)(implicit num: Numeric[A]) = SpectralPower(convertFrom(n))
}

object WattsPerMeter extends SpectralPowerUnit with ValueUnit {
  val symbol = Watts.symbol + "/" + Meters.symbol
}

object SpectralPowerConversions {
  lazy val wattPerMeter = WattsPerMeter(1)

  implicit class SpectralPowerConversions[A](n: A)(implicit num: Numeric[A]) {
    def wattsPerMeter = WattsPerMeter(n)
  }

  implicit object SpectralPowerNumeric extends AbstractQuantityNumeric[SpectralPower](SpectralPower.valueUnit)
}

