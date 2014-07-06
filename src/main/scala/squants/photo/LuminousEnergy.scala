/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */
package squants.photo

import squants._
import squants.time.{ Seconds, Time, TimeIntegral }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.photo.LumenSeconds]]
 */
final class LuminousEnergy private (val value: Double)
    extends Quantity[LuminousEnergy]
    with TimeIntegral[LuminousFlux] {

  def valueUnit = LuminousEnergy.valueUnit

  def /(that: Time): LuminousFlux = Lumens(value / that.toSeconds)
  def /(that: LuminousFlux): Time = Seconds(value / that.toLumens)

  def toLumenSeconds = to(LumenSeconds)
}

object LuminousEnergy extends QuantityCompanion[LuminousEnergy] {
  private[photo] def apply[A](n: A)(implicit num: Numeric[A]) = new LuminousEnergy(num.toDouble(n))
  def apply = parseString _
  def name = "LuminousEnergy"
  def valueUnit = LumenSeconds
  def units = Set(LumenSeconds)
}

trait LuminousEnergyUnit extends UnitOfMeasure[LuminousEnergy] with UnitConverter

object LumenSeconds extends LuminousEnergyUnit with ValueUnit {
  def apply[A](n: A)(implicit num: Numeric[A]) = LuminousEnergy(n)
  val symbol = "lm⋅s"
}

object LuminousEnergyConversions {
  lazy val lumenSecond = LumenSeconds(1)

  implicit class LuminousEnergyConversions[A](n: A)(implicit num: Numeric[A]) {
    def lumenSeconds = LumenSeconds(n)
  }

  implicit object LuminousEnergyNumeric extends AbstractQuantityNumeric[LuminousEnergy](LuminousEnergy.valueUnit)
}
