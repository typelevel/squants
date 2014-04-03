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
  def valueUnit = LumenSeconds

  def /(that: Time): LuminousFlux = Lumens(value / that.toSeconds)
  def /(that: LuminousFlux): Time = Seconds(value / that.toLumens)

  def toLumenSeconds = to(LumenSeconds)
}

object LuminousEnergy {
  private[photo] def apply(value: Double) = new LuminousEnergy(value)
}

trait LuminousEnergyUnit extends UnitOfMeasure[LuminousEnergy] with UnitMultiplier

object LumenSeconds extends LuminousEnergyUnit with ValueUnit {
  def apply(d: Double) = LuminousEnergy(d)
  val symbol = "lm⋅s"
}

object LuminousEnergyConversions {
  lazy val lumenSecond = LumenSeconds(1)

  implicit class LuminousEnergyConversions(d: Double) {
    def lumenSeconds = LumenSeconds(d)
  }

  implicit object LuminousEnergyNumeric extends AbstractQuantityNumeric[LuminousEnergy](LumenSeconds)
}
