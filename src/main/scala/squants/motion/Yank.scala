/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.time.{ TimeUnit, TimeDerivative }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param force Force
 * @param time Time
 */
case class Yank(force: Force, time: Time) extends Quantity[Yank]
    with TimeDerivative[Force] {
  def change = force

  def valueUnit = NewtonsPerSecond
  def value = toNewtonsPerSecond

  def toString(unit: YankUnit) = to(unit) + " " + unit.symbol

  def to(unit: YankUnit) = force.to(unit.forceUnit) / unit.forceBase.to(unit.forceUnit) / time.to(unit.timeUnit)
  def toNewtonsPerSecond = force.toNewtons / time.toSeconds
}

trait YankUnit extends UnitOfMeasure[Yank] {
  def forceUnit: ForceUnit
  def forceBase: Force
  def timeUnit: TimeUnit
  def timeBase: Time
  def apply(d: Double) = Yank(forceBase * d, timeBase)
  def unapply(yank: Yank) = Some(yank.to(this))
}

object NewtonsPerSecond extends YankUnit with ValueUnit {
  val forceUnit = Newtons
  val forceBase = Newtons(1)
  val timeUnit = Seconds
  val timeBase = Seconds(1)
  val symbol = "N/s"
}

object YankConversions {
  lazy val newtonPerSecond = NewtonsPerSecond(1)

  implicit class YankConversions(val d: Double) {
    def newtonsPerSecond = NewtonsPerSecond(d)
  }

  implicit object YankNumeric extends AbstractQuantityNumeric[Yank](NewtonsPerSecond)
}
