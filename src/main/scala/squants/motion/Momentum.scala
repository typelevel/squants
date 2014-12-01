/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.time.{ SecondTimeIntegral, TimeSquared, Seconds, TimeIntegral }
import squants.mass.Kilograms

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Momentum private (val value: Double)
    extends Quantity[Momentum]
    with TimeIntegral[Force]
    with SecondTimeIntegral[Yank] {

  def valueUnit = Momentum.valueUnit
  protected def timeDerived = Newtons(toNewtonSeconds)
  protected def time = Seconds(1)

  def /(that: Velocity): Mass = Kilograms(toNewtonSeconds / that.toMetersPerSecond)
  def /(that: Mass): Velocity = MetersPerSecond(toNewtonSeconds / that.toKilograms)

  def /(that: TimeSquared): Yank = this / that.time1 / that.time2

  def toNewtonSeconds = to(NewtonSeconds)
}

object Momentum extends QuantityCompanion[Momentum] {
  private[motion] def apply[A](n: A)(implicit num: Numeric[A]) = new Momentum(num.toDouble(n))
  def apply(m: Mass, v: Velocity) = new Momentum(m.toKilograms * v.toMetersPerSecond)
  def apply = parseString _
  def name = "Momentum"
  def valueUnit = NewtonSeconds
  def units = Set(NewtonSeconds)
}

trait MomentumUnit extends UnitOfMeasure[Momentum] {
  def apply[A](n: A)(implicit num: Numeric[A]) = Momentum(convertFrom(n))
}

object NewtonSeconds extends MomentumUnit with ValueUnit {
  val symbol = "Ns"
}

object MomentumConversions {
  lazy val newtonSecond = NewtonSeconds(1)

  implicit class MomentumConversions[A](n: A)(implicit num: Numeric[A]) {
    def newtonSeconds = NewtonSeconds(n)
  }

  implicit object MomentumNumeric extends AbstractQuantityNumeric[Momentum](Momentum.valueUnit)
}