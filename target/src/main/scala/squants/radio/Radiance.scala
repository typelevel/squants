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
 * @param radiantIntensity RadiantIntensity
 * @param area Area
 */
case class Radiance(radiantIntensity: RadiantIntensity, area: Area) extends Quantity[Radiance] {

  def value = toWattsPerSteradianPerSquareMeter
  def valueUnit = WattsPerSteradianPerSquareMeter

  def *(that: Area): RadiantIntensity = radiantIntensity * (that / area)
  def /(that: RadiantIntensity): Area = radiantIntensity / that * area

  def toWattsPerSteradianPerSquareMeter = radiantIntensity.toWattsPerSteradian / area.toSquareMeters
}

trait RadianceUnit extends UnitOfMeasure[Radiance]

object WattsPerSteradianPerSquareMeter extends RadianceUnit {
  val symbol = Watts.symbol + "/" + SquaredRadians.symbol + "/" + SquareMeters.symbol
  def apply(d: Double) = Radiance(RadiantIntensity(Watts(d), SquaredRadians(1)), SquareMeters(1))
}
