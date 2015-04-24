/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.electro.Henry]]
 */
final class Inductance private (val value: Double, val unit: InductanceUnit)
    extends Quantity[Inductance] {

  def dimension = Inductance

  def *(that: ElectricCurrent): MagneticFlux = Webers(toHenry * that.toAmperes)
  def /(that: Length) = ??? // returns Permeability

  def toHenry = to(Henry)
}

object Inductance extends Dimension[Inductance] {
  private[electro] def apply[A](n: A, unit: InductanceUnit)(implicit num: Numeric[A]) = new Inductance(num.toDouble(n), unit)
  def apply = parseString _
  def name = "Inductance"
  def primaryUnit = Henry
  def siUnit = Henry
  def units = Set(Henry)
}

trait InductanceUnit extends UnitOfMeasure[Inductance] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Inductance(n, this)
}

object Henry extends InductanceUnit with PrimaryUnit with SiUnit {
  val symbol = "H"
}

object InductanceConversions {
  lazy val henry = Henry(1)

  implicit class InductanceConversions[A](n: A)(implicit num: Numeric[A]) {
    def henry = Henry(n)
  }

  implicit object InductanceNumeric extends AbstractQuantityNumeric[Inductance](Inductance.primaryUnit)
}