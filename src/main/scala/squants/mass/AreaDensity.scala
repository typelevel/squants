/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import squants._
import squants.space.{ SquareMeters, CubicMeters }

/**
 * @author  garyKeorkunian
 * @since   0.2.3
 *
 * @param mass Mass
 * @param area Area
 */
case class AreaDensity(mass: Mass, area: Area) extends Quantity[AreaDensity] {

  def valueUnit = KilogramsPerSquareMeter
  def value = toKilogramsPerSquareMeter

  def *(that: Area): Mass = mass * (that / area)

  def toKilogramsPerSquareMeter = mass.toKilograms / area.toSquareMeters
}

trait AreaDensityUnit extends UnitOfMeasure[AreaDensity] {
}

object KilogramsPerSquareMeter extends AreaDensityUnit with ValueUnit {
  def apply[A](n: A)(implicit num: Numeric[A]) = AreaDensity(Kilograms(n), SquareMeters(1))
  val symbol = "kg/m²"
}

object AreaDensityConversions {
  lazy val kilogramPerSquareMeter = KilogramsPerSquareMeter(1)

  implicit class AreaDensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def kilogramsPerSquareMeter = KilogramsPerSquareMeter(n)
  }

  implicit object AreaDensityNumeric extends AbstractQuantityNumeric[AreaDensity](KilogramsPerSquareMeter)
}