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

  def valueUnit = Henry

  def *(that: ElectricCurrent): MagneticFlux = Webers(toHenry * that.toAmperes)
  def /(that: Length) = ??? // returns Permeability

  def toHenry = to(Henry)
}

object Inductance {
  private[electro] def apply(value: Double) = new Inductance(value)
}

trait InductanceUnit extends UnitOfMeasure[Inductance] with UnitMultiplier

object Henry extends InductanceUnit with ValueUnit {
  val symbol = "H"
  def apply(d: Double) = Inductance(d)
}

object InductanceConversions {
  lazy val henry = Henry(1)

  implicit class InductanceConversions(d: Double) {
    def henry = Henry(d)
  }
}