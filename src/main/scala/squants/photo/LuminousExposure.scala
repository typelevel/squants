/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.photo

import squants._
import squants.time.TimeIntegral

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.photo.LuxSeconds]]
 */
final class LuminousExposure private (val value: Double) extends Quantity[LuminousExposure]
    with PhysicalQuantity
    with TimeIntegral[Illuminance] {

  def valueUnit = LuxSeconds

  def /(that: Time): Illuminance = Lux(value / that.toSeconds)
  def /(that: Illuminance): Time = Seconds(value / that.toLux)

  def toLuxSeconds = to(LuxSeconds)
}

object LuminousExposure {
  private[photo] def apply(value: Double) = new LuminousExposure(value)
}

trait LuminousExposureUnit extends UnitOfMeasure[LuminousExposure] with UnitMultiplier {
  def apply(d: Double) = LuminousExposure(convertFrom(d))
}

object LuxSeconds extends LuminousExposureUnit with ValueUnit {
  val symbol = "lx⋅s"
}

object LuminousExposureConversions {
  lazy val luxSecond = LuxSeconds(1)

  implicit class LuminousExposureConversions(val d: Double) {
    def luxSeconds = LuxSeconds(d)
  }

  implicit object LuminousExposureNumeric extends AbstractQuantityNumeric[LuminousExposure](LuxSeconds)
}