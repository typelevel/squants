/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants.time.{ Hours, Seconds, TimeDerivative }
import squants.mass.{ Pounds, Mass, Kilograms }
import squants._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class MassFlowRate private (val value: Double) extends Quantity[MassFlowRate] with TimeDerivative[Mass] {

  def valueUnit = MassFlowRate.valueUnit
  protected def timeIntegrated = Kilograms(toKilogramsPerSecond)
  protected[squants] def time = Seconds(1)

  def toKilogramsPerSecond = to(KilogramsPerSecond)
  def toPoundsPerSecond = to(PoundsPerSecond)
  def toKilopoundsPerHour = to(KilopoundsPerHour)
}

object MassFlowRate extends QuantityCompanion[MassFlowRate] {
  private[motion] def apply[A](n: A)(implicit num: Numeric[A]) = new MassFlowRate(num.toDouble(n))
  def apply = parseString _
  def name = "MassFlowRate"
  def valueUnit = KilogramsPerSecond
  def units = Set(KilogramsPerSecond, PoundsPerSecond, KilopoundsPerHour)
}

trait MassFlowRateUnit extends UnitOfMeasure[MassFlowRate] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = MassFlowRate(convertFrom(n))
}

object KilogramsPerSecond extends MassFlowRateUnit with ValueUnit {
  val symbol = "kg/s"
}

object PoundsPerSecond extends MassFlowRateUnit {
  val symbol = "lbs/s"
  val conversionFactor = Pounds.conversionFactor / Kilograms.conversionFactor
}

object KilopoundsPerHour extends MassFlowRateUnit {
  val symbol = "Mlbs/hr"
  val conversionFactor = PoundsPerSecond.conversionFactor * 1000 / 3600
}

object MassFlowRateConversions {
  lazy val kilogramPerSecond = KilogramsPerSecond(1)
  lazy val poundsPerSecond = PoundsPerSecond(1)
  lazy val kilopoundsPerHour = KilopoundsPerHour(1)

  implicit class MassFlowRateConversions[A](n: A)(implicit num: Numeric[A]) {
    def kilogramsPerSecond = KilogramsPerSecond(n)
    def poundsPerSecond = PoundsPerSecond(n)
    def kilopoundsPerHour = KilopoundsPerHour(n)
  }

  implicit object MassFlowRateNumeric extends AbstractQuantityNumeric[MassFlowRate](MassFlowRate.valueUnit)
}
