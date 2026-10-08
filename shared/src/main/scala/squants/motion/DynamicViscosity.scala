/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.space.{CubicMeters, Volume}

/**
 * Represents a quantity of dynamic viscosity.
 * Dynamic viscosity is defined as Pressure * Time.
 * SI unit: Pa·s (Pascal-second).
 *
 * @author  mithunbabbira
 * @since   2.0
 *
 * @param value value in [[squants.motion.PascalSeconds]]
 */
final class DynamicViscosity private (val value: Double, val unit: DynamicViscosityUnit)
    extends Quantity[DynamicViscosity] {

  def dimension = DynamicViscosity

  def *(that: Area): Force = Newtons(this.toPascalSeconds * that.toSquareMeters)

  def toPascalSeconds = to(PascalSeconds)
}

object DynamicViscosity extends Dimension[DynamicViscosity] {
  private[motion] def apply[A](n: A, unit: DynamicViscosityUnit)(implicit num: Numeric[A]) = new DynamicViscosity(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "DynamicViscosity"
  def primaryUnit = PascalSeconds
  def siUnit = PascalSeconds
  def units = Set(PascalSeconds)
}

trait DynamicViscosityUnit extends UnitOfMeasure[DynamicViscosity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = DynamicViscosity(n, this)
}

object PascalSeconds extends DynamicViscosityUnit with PrimaryUnit with SiUnit {
  val symbol = s"${Pascals.symbol}s"
}

object DynamicViscosityConversions {
  lazy val pascalSecond = PascalSeconds(1)

  implicit class DynamicViscosityConversions[A](n: A)(implicit num: Numeric[A]) {
    def pascalSeconds = PascalSeconds(n)
  }

  implicit object DynamicViscosityNumeric extends AbstractQuantityNumeric[DynamicViscosity](DynamicViscosity.primaryUnit)
}
