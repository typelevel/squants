/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants.time.{ Time, Seconds, TimeDerivative }
import squants.mass.{ Pounds, Kilograms }
import squants._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class MassFlow private (val value: Double, val unit: MassFlowUnit)
    extends Quantity[MassFlow]
    with TimeDerivative[Mass] {

  def dimension = MassFlow

  protected[squants] def timeIntegrated = Kilograms(toKilogramsPerSecond)
  protected[squants] def time = Seconds(1)

  def toKilogramsPerSecond = to(KilogramsPerSecond)
  def toPoundsPerSecond = to(PoundsPerSecond)
  def toKilopoundsPerHour = to(KilopoundsPerHour)
}

object MassFlow extends Dimension[MassFlow] {
  private[motion] def apply[A](n: A, unit: MassFlowUnit)(implicit num: Numeric[A]) = new MassFlow(num.toDouble(n), unit)
  def apply = parse _
  def name = "MassFlow"
  def primaryUnit = KilogramsPerSecond
  def siUnit = KilogramsPerSecond
  def units = Set(KilogramsPerSecond, PoundsPerSecond, KilopoundsPerHour)
}

trait MassFlowUnit extends UnitOfMeasure[MassFlow] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = MassFlow(n, this)
}

object KilogramsPerSecond extends MassFlowUnit with PrimaryUnit with SiUnit {
  val symbol = "kg/s"
}

object PoundsPerSecond extends MassFlowUnit {
  val symbol = "lbs/s"
  val conversionFactor = Pounds.conversionFactor / Kilograms.conversionFactor
}

object KilopoundsPerHour extends MassFlowUnit {
  val symbol = "Mlbs/hr"
  val conversionFactor = PoundsPerSecond.conversionFactor * MetricSystem.Kilo / Time.SecondsPerHour
}

object MassFlowConversions {
  lazy val kilogramPerSecond = KilogramsPerSecond(1)
  lazy val poundsPerSecond = PoundsPerSecond(1)
  lazy val kilopoundsPerHour = KilopoundsPerHour(1)

  implicit class MassFlowConversions[A](n: A)(implicit num: Numeric[A]) {
    def kilogramsPerSecond = KilogramsPerSecond(n)
    def poundsPerSecond = PoundsPerSecond(n)
    def kilopoundsPerHour = KilopoundsPerHour(n)
  }

  implicit object MassFlowNumeric extends AbstractQuantityNumeric[MassFlow](MassFlow.primaryUnit)
}
