/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */
package squants.photo

import squants._
import squants.time.{ Seconds, TimeIntegral }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.photo.LumenSeconds]]
 */
final class LuminousEnergy private (val value: Double, val unit: LuminousEnergyUnit)
    extends Quantity[LuminousEnergy]
    with TimeIntegral[LuminousFlux] {

  def dimension = LuminousEnergy

  protected def timeDerived = Lumens(toLumenSeconds)
  protected[squants] def time = Seconds(1)

  def toLumenSeconds = to(LumenSeconds)
}

object LuminousEnergy extends Dimension[LuminousEnergy] {
  private[photo] def apply[A](n: A, unit: LuminousEnergyUnit)(implicit num: Numeric[A]) = new LuminousEnergy(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "LuminousEnergy"
  def primaryUnit = LumenSeconds
  def siUnit = LumenSeconds
  def units = Set(LumenSeconds)
}

trait LuminousEnergyUnit extends UnitOfMeasure[LuminousEnergy] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = LuminousEnergy(num.toDouble(n), this)
}

object LumenSeconds extends LuminousEnergyUnit with PrimaryUnit with SiUnit {
  val symbol = "lm⋅s"
}

object LuminousEnergyConversions {
  lazy val lumenSecond = LumenSeconds(1)

  implicit class LuminousEnergyConversions[A](n: A)(implicit num: Numeric[A]) {
    def lumenSeconds = LumenSeconds(n)
  }

  implicit object LuminousEnergyNumeric extends AbstractQuantityNumeric[LuminousEnergy](LuminousEnergy.primaryUnit)
}
