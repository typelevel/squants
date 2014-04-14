/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import squants._
import squants.space.CubicMeters

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param mass Mass
 * @param volume Volume
 */
case class Density(mass: Mass, volume: Volume) extends Quantity[Density] {

  def valueUnit = KilogramsPerCubicMeter
  def value = toKilogramsPerCubicMeter

  def *(that: Volume): Mass = mass * (that / volume)

  def toKilogramsPerCubicMeter = mass.toKilograms / volume.toCubicMeters
}

trait DensityUnit extends UnitOfMeasure[Density] {
  def apply[A](n: A)(implicit num: Numeric[A]): Density
}

object KilogramsPerCubicMeter extends DensityUnit with ValueUnit {
  def apply[A](n: A)(implicit num: Numeric[A]) = Density(Kilograms(n), CubicMeters(1))
  val symbol = "kg/m³"
}

object DensityConversions {
  lazy val kilogramPerCubicMeter = KilogramsPerCubicMeter(1)

  implicit class AreaDensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def kilogramsPerCubicMeter = KilogramsPerCubicMeter(n)
  }

  implicit object DensityNumeric extends AbstractQuantityNumeric[Density](KilogramsPerCubicMeter)
}