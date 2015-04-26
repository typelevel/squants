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
final class MassFlowRate private (val value: Double, val unit: MassFlowRateUnit)
    extends Quantity[MassFlowRate]
    with TimeDerivative[Mass] {

  def dimension = MassFlowRate

  protected[squants] def timeIntegrated = Kilograms(toKilogramsPerSecond)
  protected[squants] def time = Seconds(1)

  def toKilogramsPerSecond = to(KilogramsPerSecond)
  def toPoundsPerSecond = to(PoundsPerSecond)
  def toKilopoundsPerHour = to(KilopoundsPerHour)
}

object MassFlowRate extends Dimension[MassFlowRate] {
  private[motion] def apply[A](n: A, unit: MassFlowRateUnit)(implicit num: Numeric[A]) = new MassFlowRate(num.toDouble(n), unit)
  def apply = parseString _
  def name = "MassFlowRate"
  def primaryUnit = KilogramsPerSecond
  def siUnit = KilogramsPerSecond
  def units = Set(KilogramsPerSecond, PoundsPerSecond, KilopoundsPerHour)
}

trait MassFlowRateUnit extends UnitOfMeasure[MassFlowRate] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = MassFlowRate(n, this)
}

object KilogramsPerSecond extends MassFlowRateUnit with PrimaryUnit with SiUnit {
  val symbol = "kg/s"
}

object PoundsPerSecond extends MassFlowRateUnit {
  val symbol = "lbs/s"
  val conversionFactor = Pounds.conversionFactor / Kilograms.conversionFactor
}

object KilopoundsPerHour extends MassFlowRateUnit {
  val symbol = "Mlbs/hr"
  val conversionFactor = PoundsPerSecond.conversionFactor * MetricSystem.Kilo / Time.SecondsPerHour
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

  implicit object MassFlowRateNumeric extends AbstractQuantityNumeric[MassFlowRate](MassFlowRate.primaryUnit)
}
