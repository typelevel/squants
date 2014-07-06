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

  def valueUnit = LuminousExposure.valueUnit

  def /(that: Time): Illuminance = Lux(value / that.toSeconds)
  def /(that: Illuminance): Time = Seconds(value / that.toLux)

  def toLuxSeconds = to(LuxSeconds)
}

object LuminousExposure extends QuantityCompanion[LuminousExposure] {
  private[photo] def apply[A](n: A)(implicit num: Numeric[A]) = new LuminousExposure(num.toDouble(n))
  def apply = parseString _
  def name = "LuminousExposure"
  def valueUnit = LuxSeconds
  def units = Set(LuxSeconds)
}

trait LuminousExposureUnit extends UnitOfMeasure[LuminousExposure] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = LuminousExposure(convertFrom(n))
}

object LuxSeconds extends LuminousExposureUnit with ValueUnit {
  val symbol = "lx⋅s"
}

object LuminousExposureConversions {
  lazy val luxSecond = LuxSeconds(1)

  implicit class LuminousExposureConversions[A](n: A)(implicit num: Numeric[A]) {
    def luxSeconds = LuxSeconds(n)
  }

  implicit object LuminousExposureNumeric extends AbstractQuantityNumeric[LuminousExposure](LuminousExposure.valueUnit)
}