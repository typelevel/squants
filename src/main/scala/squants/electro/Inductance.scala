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
final class Inductance private (val value: Double) extends Quantity[Inductance]
    with PhysicalQuantity {

  def valueUnit = Inductance.valueUnit

  def *(that: ElectricCurrent): MagneticFlux = Webers(toHenry * that.toAmperes)
  def /(that: Length) = ??? // returns Permeability

  def toHenry = to(Henry)
}

object Inductance extends QuantityCompanion[Inductance] {
  private[electro] def apply[A](n: A)(implicit num: Numeric[A]) = new Inductance(num.toDouble(n))
  def apply = parseString _
  def name = "Inductance"
  def valueUnit = Henry
  def units = Set(Henry)
}

trait InductanceUnit extends UnitOfMeasure[Inductance] with UnitMultiplier

object Henry extends InductanceUnit with ValueUnit {
  val symbol = "H"
  def apply[A](n: A)(implicit num: Numeric[A]) = Inductance(n)
}

object InductanceConversions {
  lazy val henry = Henry(1)

  implicit class InductanceConversions[A](n: A)(implicit num: Numeric[A]) {
    def henry = Henry(n)
  }

  implicit object InductanceNumeric extends AbstractQuantityNumeric[Inductance](Inductance.valueUnit)
}