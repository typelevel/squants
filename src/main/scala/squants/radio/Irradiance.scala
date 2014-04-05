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
  def apply(d: Double) = Irradiance(Watts(d), SquareMeters(1))
}

object IrradianceConversions {
  lazy val wattPerSquareMeter = WattsPerSquareMeter(1)

  implicit class IrradianceConversions(val d: Double) {
    def wattsPerSquareMeter = WattsPerSquareMeter(d)
  }

  implicit object IrradianceNumeric extends AbstractQuantityNumeric[Irradiance](WattsPerSquareMeter)
}
