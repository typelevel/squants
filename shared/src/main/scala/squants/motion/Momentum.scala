/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants.{AbstractQuantityNumeric, Dimension, PrimaryUnit, Quantity, SiUnit, UnitOfMeasure}
import squants.mass.{Kilograms, Mass}
import squants.time.{SecondTimeIntegral, Seconds, TimeIntegral, TimeSquared}

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Momentum private (val value: Double, val unit: MomentumUnit)
    extends Quantity[Momentum]
    with TimeIntegral[Force]
    with SecondTimeIntegral[Yank] {

  def dimension = Momentum

  protected def timeDerived = Newtons(toNewtonSeconds)
  protected def time = Seconds(1)

  def /(that: Velocity): Mass = Kilograms(this.toNewtonSeconds / that.toMetersPerSecond)
  def /(that: Mass): Velocity = MetersPerSecond(this.toNewtonSeconds / that.toKilograms)

  def /(that: TimeSquared): Yank = this / that.time1 / that.time2
  def /(that: Yank): TimeSquared = (this / that.timeIntegrated) * time

  def toNewtonSeconds = to(NewtonSeconds)
}

object Momentum extends Dimension[Momentum] {
  private[motion] def apply[A](n: A, unit: MomentumUnit)(implicit num: Numeric[A]) = new Momentum(num.toDouble(n), unit)
  def apply(m: Mass, v: Velocity): Momentum = NewtonSeconds(m.toKilograms * v.toMetersPerSecond)
  def apply(value: Any) = parse(value)
  def name = "Momentum"
  def primaryUnit = NewtonSeconds
  def siUnit = NewtonSeconds
  def units = Set(NewtonSeconds)
}

trait MomentumUnit extends UnitOfMeasure[Momentum] {
  def apply[A](n: A)(implicit num: Numeric[A]) = Momentum(n, this)
}

object NewtonSeconds extends MomentumUnit with PrimaryUnit with SiUnit {
  val symbol = "Ns"
}

object MomentumConversions {
  lazy val newtonSecond = NewtonSeconds(1)

  implicit class MomentumConversions[A](n: A)(implicit num: Numeric[A]) {
    def newtonSeconds = NewtonSeconds(n)
  }

  implicit object MomentumNumeric extends AbstractQuantityNumeric[Momentum](Momentum.primaryUnit)
}
