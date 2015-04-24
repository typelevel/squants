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
final class Irradiance private (val value: Double, val unit: IrradianceUnit)
    extends Quantity[Irradiance] {

  def dimension = Irradiance

  def *(that: Area): Power = Watts(toWattsPerSquareMeter * that.toSquareMeters)
  def /(that: Power): Area = SquareMeters(toWattsPerSquareMeter / that.toWatts)

  def toWattsPerSquareMeter = to(WattsPerSquareMeter)
}

object Irradiance extends Dimension[Irradiance] {
  private[radio] def apply[A](n: A, unit: IrradianceUnit)(implicit num: Numeric[A]) = new Irradiance(num.toDouble(n), unit)
  def apply = parseString _
  def name = "Irradiance"
  def primaryUnit = WattsPerSquareMeter
  def siUnit = WattsPerSquareMeter
  def units = Set(WattsPerSquareMeter)
}

trait IrradianceUnit extends UnitOfMeasure[Irradiance] {
  def apply[A](n: A)(implicit num: Numeric[A]) = Irradiance(n, this)
}

object WattsPerSquareMeter extends IrradianceUnit with PrimaryUnit with SiUnit {
  val symbol = Watts.symbol + "/" + SquareMeters.symbol
}

object IrradianceConversions {
  lazy val wattPerSquareMeter = WattsPerSquareMeter(1)

  implicit class IrradianceConversions[A](n: A)(implicit num: Numeric[A]) {
    def wattsPerSquareMeter = WattsPerSquareMeter(n)
  }

  implicit object IrradianceNumeric extends AbstractQuantityNumeric[Irradiance](Irradiance.primaryUnit)
}
