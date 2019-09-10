/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.mass.{ Kilograms, Pounds }
import squants.time.{ Seconds, Time, TimeDerivative }

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
  def toPoundsPerHour = to(PoundsPerHour)
  def toKilopoundsPerHour = to(KilopoundsPerHour)
  def toMegapoundsPerHour = to(MegapoundsPerHour)
}

object MassFlow extends Dimension[MassFlow] {
  private[motion] def apply[A](n: A, unit: MassFlowUnit)(implicit num: Numeric[A]) = new MassFlow(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "MassFlow"
  def primaryUnit = KilogramsPerSecond
  def siUnit = KilogramsPerSecond
  def units = Set(KilogramsPerSecond, PoundsPerSecond, PoundsPerHour, KilopoundsPerHour, MegapoundsPerHour)
}

trait MassFlowUnit extends UnitOfMeasure[MassFlow] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = MassFlow(n, this)
}

object KilogramsPerSecond extends MassFlowUnit with PrimaryUnit with SiUnit {
  val symbol = "kg/s"
}

object PoundsPerSecond extends MassFlowUnit {
  val symbol = "lb/s"
  val conversionFactor = Pounds.conversionFactor / Kilograms.conversionFactor
}

object PoundsPerHour extends MassFlowUnit {
  val symbol = "lb/hr"
  val conversionFactor = PoundsPerSecond.conversionFactor / Time.SecondsPerHour
}

object KilopoundsPerHour extends MassFlowUnit {
  val symbol = "klb/hr"
  val conversionFactor = PoundsPerHour.conversionFactor * MetricSystem.Kilo
}

object MegapoundsPerHour extends MassFlowUnit {
  val symbol = "Mlb/hr"
  val conversionFactor = PoundsPerHour.conversionFactor * MetricSystem.Mega
}

object MassFlowConversions {
  lazy val kilogramPerSecond = KilogramsPerSecond(1)
  lazy val poundPerSecond = PoundsPerSecond(1)
  lazy val poundPerHour = PoundsPerHour(1)
  lazy val kilopoundPerHour = KilopoundsPerHour(1)
  lazy val megapoundPerHour = MegapoundsPerHour(1)

  implicit class MassFlowConversions[A](n: A)(implicit num: Numeric[A]) {
    def kilogramsPerSecond = KilogramsPerSecond(n)
    def poundsPerSecond = PoundsPerSecond(n)
    def poundsPerHour = PoundsPerHour(n)
    def kilopoundsPerHour = KilopoundsPerHour(n)
    def megapoundsPerHour = MegapoundsPerHour(n)
  }

  implicit object MassFlowNumeric extends AbstractQuantityNumeric[MassFlow](MassFlow.primaryUnit)
}
