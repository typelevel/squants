/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.time.{ Seconds, TimeDerivative, TimeIntegral }

/**
 * Represents dynamic viscosity — pressure multiplied by time.
 * SI unit: Pa·s (Pascal-seconds)
 *
 * @author  garyKeorkunian
 * @since   1.5
 *
 * @param value value in [[squants.motion.PascalSeconds]]
 */
final class DynamicViscosity private (val value: Double, val unit: DynamicViscosityUnit)
    extends Quantity[DynamicViscosity]
    with TimeIntegral[PressureChange] {

  def dimension = DynamicViscosity

  protected def timeDerived = PressureChange(this.toPascalsPerSecond * Seconds(1))

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
  val symbol = "Pa·s"
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

  implicit object DynamicViscosityNumeric
    extends AbstractQuantityNumeric[DynamicViscosity](DynamicViscosity.primaryUnit)
}
