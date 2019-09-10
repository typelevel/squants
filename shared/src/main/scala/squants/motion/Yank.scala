/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.time.{ SecondTimeDerivative, TimeDerivative, TimeSquared }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Yank private (val value: Double, val unit: YankUnit)
    extends Quantity[Yank]
    with TimeDerivative[Force]
    with SecondTimeDerivative[Momentum] {

  def dimension = Yank

  protected[squants] def timeIntegrated = Newtons(toNewtonsPerSecond)
  protected[squants] def time = Seconds(1)

  def *(that: TimeSquared): Momentum = this * that.time1 * that.time2

  def toNewtonsPerSecond = to(NewtonsPerSecond)
}

object Yank extends Dimension[Yank] {
  private[motion] def apply[A](n: A, unit: YankUnit)(implicit num: Numeric[A]) = new Yank(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Yank"
  def primaryUnit = NewtonsPerSecond
  def siUnit = NewtonsPerSecond
  def units = Set(NewtonsPerSecond)
}

trait YankUnit extends UnitOfMeasure[Yank] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Yank(n, this)
}

object NewtonsPerSecond extends YankUnit with PrimaryUnit with SiUnit {
  val symbol = "N/s"
}

object YankConversions {
  lazy val newtonPerSecond = NewtonsPerSecond(1)

  implicit class YankConversions[A](n: A)(implicit num: Numeric[A]) {
    def newtonsPerSecond = NewtonsPerSecond(n)
  }

  implicit object YankNumeric extends AbstractQuantityNumeric[Yank](Yank.primaryUnit)
}
