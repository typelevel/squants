/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Measure Library and DSL                         **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      */
/*                                                                      */

package squants.motion

import squants._
import squants.space.{SquareMeters, SquareFeet}
import squants.mass.Kilograms
import squants.time._

/**
 * Represents a quantity of dynamic viscosity (pressure * time)
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.motion.PascalSeconds]]
 */
final class DynamicViscosity private (val value: Double, val unit: DynamicViscosityUnit)
    extends Quantity[DynamicViscosity] {

  def dimension = DynamicViscosity

  def *(that: PressureChange): Pressure = Pascals(this.toPascalSeconds * that.toPascalsPerSecond)

  def toPascalSeconds = to(PascalSeconds)
  def toPoise = to(Poise)
}

object DynamicViscosity extends Dimension[DynamicViscosity] {
  private[motion] def apply[A](n: A, unit: DynamicViscosityUnit)(implicit num: Numeric[A]) = new DynamicViscosity(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "DynamicViscosity"
  def primaryUnit = PascalSeconds
  def siUnit = PascalSeconds
  def units = Set(PascalSeconds, Poise)
}

trait DynamicViscosityUnit extends UnitOfMeasure[DynamicViscosity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = DynamicViscosity(n, this)
}

object PascalSeconds extends DynamicViscosityUnit with PrimaryUnit with SiUnit {
  val symbol = s"${Pascals.symbol}·${Seconds.symbol}"
}

object Poise extends DynamicViscosityUnit with SiUnit {
  val symbol = "P"
  val conversionFactor = 0.1
}

object DynamicViscosityConversions {
  lazy val pascalSecond = PascalSeconds(1)
  lazy val poise = Poise(1)

  implicit class DynamicViscosityConversions[A](n: A)(implicit num: Numeric[A]) {
    def pascalSeconds = PascalSeconds(n)
    def poises = Poise(n)
  }

  implicit object DynamicViscosityNumeric extends AbstractQuantityNumeric[DynamicViscosity](DynamicViscosity.primaryUnit)
}
