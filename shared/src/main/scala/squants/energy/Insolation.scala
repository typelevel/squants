/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import squants._
import squants.motion.{ Newtons, Pascals }
import squants.space.SquareMeters

/**
 * Represents insolation — energy flux density (energy per unit area).
 * Also known as irradiance in some contexts; here specifically for energy.
 * SI unit: J/m²
 *
 * @author  garyKeorkunian
 * @since   1.5
 *
 * @param value value in [[squants.energy.JoulesPerSquareMeter]]
 */
final class Insolation private (val value: Double, val unit: InsolationUnit)
    extends Quantity[Insolation] {

  def dimension = Insolation

  def *(that: Area): Energy = Joules(this.toJoulesPerSquareMeter * that.toSquareMeters)

  def toJoulesPerSquareMeter = to(JoulesPerSquareMeter)
}

object Insolation extends Dimension[Insolation] {
  private[energy] def apply[A](n: A, unit: InsolationUnit)(implicit num: Numeric[A]) = new Insolation(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Insolation"
  def primaryUnit = JoulesPerSquareMeter
  def siUnit = JoulesPerSquareMeter
  def units = Set(JoulesPerSquareMeter)
}

trait InsolationUnit extends UnitOfMeasure[Insolation] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Insolation(n, this)
}

object JoulesPerSquareMeter extends InsolationUnit with PrimaryUnit with SiUnit {
  val symbol = "J/m²"
}

object InsolationConversions {
  lazy val joulePerSquareMeter = JoulesPerSquareMeter(1)

  implicit class InsolationConversions[A](n: A)(implicit num: Numeric[A]) {
    def joulesPerSquareMeter = JoulesPerSquareMeter(n)
  }

  implicit object InsolationNumeric
    extends AbstractQuantityNumeric[Insolation](Insolation.primaryUnit)
}
