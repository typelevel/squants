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
 * @param power Power
 * @param area Area
 */
case class Irradiance(power: Power, area: Area) extends Quantity[Irradiance] {

  def value = power.toWatts / area.toSquareMeters
  def valueUnit = WattsPerSquareMeter

  def *(that: Area): Power = power * (that / area)
  def /(that: Power): Area = power / that * area

  def toWattsPerSquareMeter = value
}

trait IrradianceUnit extends UnitOfMeasure[Irradiance]

object WattsPerSquareMeter extends IrradianceUnit with ValueUnit {
  val symbol = Watts.symbol + "/" + SquareMeters.symbol
  def apply[A](n: A)(implicit num: Numeric[A]) = Irradiance(Watts(n), SquareMeters(1))
}

object IrradianceConversions {
  lazy val wattPerSquareMeter = WattsPerSquareMeter(1)

  implicit class IrradianceConversions[A](n: A)(implicit num: Numeric[A]) {
    def wattsPerSquareMeter = WattsPerSquareMeter(n)
  }

  implicit object IrradianceNumeric extends AbstractQuantityNumeric[Irradiance](WattsPerSquareMeter)
}
