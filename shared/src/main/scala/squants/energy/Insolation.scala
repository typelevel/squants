/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Measure Library and DSL                         **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      */
/*                                                                      */

package squants.energy

import squants._
import squants.space.{Meters, SquareMeters, CubicMeters, Litres}
import squants.time._
import squants.time.Time

/**
 * Represents a quantity of insolation (energy per unit area)
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.energy.JoulesPerSquareMeter]]
 */
final class Insolation private (val value: Double, val unit: InsolationUnit)
    extends Quantity[Insolation] {

  def dimension = Insolation

  def *(that: Area): Energy = Joules(this.toJoulesPerSquareMeter * that.toSquareMeters)
  def /(that: Area): Insolation = JoulesPerSquareMeter(this.toJoulesPerSquareMeter / that.toSquareMeters)

  def toJoulesPerSquareMeter = to(JoulesPerSquareMeter)
  def toMegajoulesPerSquareMeter = to(MegajoulesPerSquareMeter)
}

object Insolation extends Dimension[Insolation] {
  private[energy] def apply[A](n: A, unit: InsolationUnit)(implicit num: Numeric[A]) = new Insolation(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Insolation"
  def primaryUnit = JoulesPerSquareMeter
  def siUnit = JoulesPerSquareMeter
  def units = Set(JoulesPerSquareMeter, MegajoulesPerSquareMeter)
}

trait InsolationUnit extends UnitOfMeasure[Insolation] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Insolation(n, this)
}

object JoulesPerSquareMeter extends InsolationUnit with PrimaryUnit with SiUnit {
  val symbol = s"${Joules.symbol}/${SquareMeters.symbol}"
}

object MegajoulesPerSquareMeter extends InsolationUnit with SiUnit {
  val symbol = s"MJ/${SquareMeters.symbol}"
  val conversionFactor = MetricSystem.Mega
}

object InsolationConversions {
  lazy val joulePerSquareMeter = JoulesPerSquareMeter(1)
  lazy val megajoulePerSquareMeter = MegajoulesPerSquareMeter(1)

  implicit class InsolationConversions[A](n: A)(implicit num: Numeric[A]) {
    def joulesPerSquareMeter = JoulesPerSquareMeter(n)
    def megajoulesPerSquareMeter = MegajoulesPerSquareMeter(n)
  }

  implicit object InsolationNumeric extends AbstractQuantityNumeric[Insolation](Insolation.primaryUnit)
}
