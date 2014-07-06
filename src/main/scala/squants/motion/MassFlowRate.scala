/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.time.{ Seconds, TimeDerivative }
import squants.mass.Kilograms

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class MassFlowRate private (val value: Double) extends Quantity[MassFlowRate] with TimeDerivative[Mass] {

  def valueUnit = MassFlowRate.valueUnit
  def change = Kilograms(value)
  def time = Seconds(1)

  def toKilogramsPerSecond = to(KilogramsPerSecond)
}

object MassFlowRate extends QuantityCompanion[MassFlowRate] {
  private[motion] def apply[A](n: A)(implicit num: Numeric[A]) = new MassFlowRate(num.toDouble(n))
  def apply = parseString _
  def name = "MassFlowRate"
  def valueUnit = KilogramsPerSecond
  def units = Set(KilogramsPerSecond)
}

trait MassFlowRateUnit extends UnitOfMeasure[MassFlowRate] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = MassFlowRate(convertFrom(n))
}

object KilogramsPerSecond extends MassFlowRateUnit with ValueUnit {
  val symbol = "kg/s"
}

object MassFlowRateConversions {
  lazy val kilogramPerSecond = KilogramsPerSecond(1)

  implicit class MassFlowRateConversions[A](n: A)(implicit num: Numeric[A]) {
    def kilogramsPerSecond = KilogramsPerSecond(n)
  }

  implicit object MassFlowRateNumeric extends AbstractQuantityNumeric[MassFlowRate](MassFlowRate.valueUnit)
}
