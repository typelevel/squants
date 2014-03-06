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
case class Density(mass: Mass, volume: Volume) extends Quantity[Density] with DerivedQuantity {

  def valueUnit = KilogramsPerCubicMeter
  def value = toKilogramsPerCubicMeter

  def *(that: Volume): Mass = mass * (that / volume)

  def toKilogramsPerCubicMeter = mass.toKilograms / volume.toCubicMeters
}

trait DensityUnit extends UnitOfMeasure[Density] {
  def apply(d: Double): Density
}

object KilogramsPerCubicMeter extends DensityUnit {
  def apply(d: Double) = Density(Kilograms(d), CubicMeters(1))
  val symbol = "kg/m³"
}