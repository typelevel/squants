/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Measure Library and DSL                         **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      */
/*                                                                      */

package squants.radio

import squants._
import squants.energy.{Grays, Joules}
import squants.mass.Kilograms
import squants.time._

/**
 * Represents a quantity of absorbed energy rate (energy per unit time)
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.radio.JoulesPerSecond]]
 */
final class AbsorbedEnergyRate private (val value: Double, val unit: AbsorbedEnergyRateUnit)
    extends Quantity[AbsorbedEnergyRate] {

  def dimension = AbsorbedEnergyRate

  def *(that: Time): Energy = Joules(this.toJoulesPerSecond * that.toSeconds)
  def /(that: Time): AbsorbedEnergyRate = JoulesPerSecond(this.toJoulesPerSecond / that.toSeconds)

  def toJoulesPerSecond = to(JoulesPerSecond)
  def toWatts = to(Watts)
}

object AbsorbedEnergyRate extends Dimension[AbsorbedEnergyRate] {
  private[radio] def apply[A](n: A, unit: AbsorbedEnergyRateUnit)(implicit num: Numeric[A]) = new AbsorbedEnergyRate(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "AbsorbedEnergyRate"
  def primaryUnit = JoulesPerSecond
  def siUnit = JoulesPerSecond
  def units = Set(JoulesPerSecond, Watts)
}

trait AbsorbedEnergyRateUnit extends UnitOfMeasure[AbsorbedEnergyRate] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = AbsorbedEnergyRate(n, this)
}

object JoulesPerSecond extends AbsorbedEnergyRateUnit with PrimaryUnit with SiUnit {
  val symbol = s"${Joules.symbol}/${Seconds.symbol}"
}

object Watts extends AbsorbedEnergyRateUnit with SiUnit {
  val symbol = "W"
  val conversionFactor = 1.0
}

object AbsorbedEnergyRateConversions {
  lazy val joulePerSecond = JoulesPerSecond(1)
  lazy val watt = Watts(1)

  implicit class AbsorbedEnergyRateConversions[A](n: A)(implicit num: Numeric[A]) {
    def joulesPerSecond = JoulesPerSecond(n)
    def watts = Watts(n)
  }

  implicit object AbsorbedEnergyRateNumeric extends AbstractQuantityNumeric[AbsorbedEnergyRate](AbsorbedEnergyRate.primaryUnit)
}
