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
import squants.space.SquareMeters

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Irradiance private (val value: Double) extends Quantity[Irradiance] {

  def valueUnit = Irradiance.valueUnit

  def *(that: Area): Power = Watts(toWattsPerSquareMeter * that.toSquareMeters)
  def /(that: Power): Area = SquareMeters(toWattsPerSquareMeter / that.toWatts)

  def toWattsPerSquareMeter = to(WattsPerSquareMeter)
}

object Irradiance extends QuantityCompanion[Irradiance] {
  private[radio] def apply[A](n: A)(implicit num: Numeric[A]) = new Irradiance(num.toDouble(n))
  def apply = parseString _
  def name = "Irradiance"
  def valueUnit = WattsPerSquareMeter
  def units = Set(WattsPerSquareMeter)
}

trait IrradianceUnit extends UnitOfMeasure[Irradiance] {
  def apply[A](n: A)(implicit num: Numeric[A]) = Irradiance(convertFrom(n))
}

object WattsPerSquareMeter extends IrradianceUnit with ValueUnit {
  val symbol = Watts.symbol + "/" + SquareMeters.symbol
}

object IrradianceConversions {
  lazy val wattPerSquareMeter = WattsPerSquareMeter(1)

  implicit class IrradianceConversions[A](n: A)(implicit num: Numeric[A]) {
    def wattsPerSquareMeter = WattsPerSquareMeter(n)
  }

  implicit object IrradianceNumeric extends AbstractQuantityNumeric[Irradiance](Irradiance.valueUnit)
}
