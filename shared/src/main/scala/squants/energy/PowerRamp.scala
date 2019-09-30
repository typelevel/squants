/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import squants.{ Time, _ }
import squants.time._

/**
 * Represents the rate of change of [[squants.energy.Power]] over time
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.energy.WattsPerHour]]
 */
final class PowerRamp private (val value: Double, val unit: PowerRampUnit)
    extends Quantity[PowerRamp]
    with TimeDerivative[Power]
    with SecondTimeDerivative[Energy] {

  def dimension = PowerRamp

  protected[squants] def timeIntegrated = Watts(toWattsPerHour)
  protected[squants] def time = Hours(1)

  def *(that: TimeSquared): Energy = this * that.time1 * that.time2

  def toWattsPerHour = to(WattsPerHour)
  def toWattsPerMinutes = to(WattsPerMinute)
  def toKilowattsPerHour = to(KilowattsPerHour)
  def toKilowattsPerMinute = to(KilowattsPerMinute)
  def toMegawattsPerHour = to(MegawattsPerHour)
  def toGigawattsPerHour = to(GigawattsPerHour)
}

object PowerRamp extends Dimension[PowerRamp] {
  private[energy] def apply[A](n: A, unit: PowerRampUnit)(implicit num: Numeric[A]) = new PowerRamp(num.toDouble(n), unit)
  def apply(change: Power, time: Time): PowerRamp = apply(change.toWatts / time.toHours, WattsPerHour)
  def apply(value: Any) = parse(value)
  def name = "PowerRamp"
  def primaryUnit = WattsPerHour
  def siUnit = WattsPerHour
  def units = Set(WattsPerHour, WattsPerMinute, KilowattsPerHour, KilowattsPerMinute, MegawattsPerHour, GigawattsPerHour)
}

trait PowerRampUnit extends UnitOfMeasure[PowerRamp] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = PowerRamp(n, this)
}

object WattsPerHour extends PowerRampUnit with PrimaryUnit with SiUnit {
  val symbol = "W/h"
}

object WattsPerMinute extends PowerRampUnit with SiUnit {
  val conversionFactor = WattsPerHour.conversionFactor / 60D
  val symbol = "W/m"
}

object KilowattsPerHour extends PowerRampUnit with SiUnit {
  val conversionFactor = MetricSystem.Kilo
  val symbol = "kW/h"
}

object KilowattsPerMinute extends PowerRampUnit with SiUnit {
  val conversionFactor = KilowattsPerHour.conversionFactor / 60D
  val symbol = "kW/m"
}

object MegawattsPerHour extends PowerRampUnit with SiUnit {
  val conversionFactor = MetricSystem.Mega
  val symbol = "MW/h"
}

object GigawattsPerHour extends PowerRampUnit with SiUnit {
  val conversionFactor = MetricSystem.Giga
  val symbol = "GW/h"
}

object PowerRampConversions {
  lazy val wattPerHour = WattsPerHour(1)
  lazy val Wph = wattPerHour
  lazy val wattPerMinute = WattsPerMinute(1)
  lazy val Wpm = wattPerMinute
  lazy val kilowattPerHour = KilowattsPerHour(1)
  lazy val kWph = kilowattPerHour
  lazy val kilowattPerMinute = KilowattsPerMinute(1)
  lazy val kWpm = kilowattPerMinute
  lazy val megawattPerHour = MegawattsPerHour(1)
  lazy val MWph = megawattPerHour
  lazy val gigawattPerHour = GigawattsPerHour(1)
  lazy val GWph = gigawattPerHour

  implicit class PowerRampConversions[A](n: A)(implicit num: Numeric[A]) {
    def Wph = WattsPerHour(n)
    def Wpm = WattsPerMinute(n)
    def kWph = KilowattsPerHour(n)
    def kWpm = KilowattsPerMinute(n)
    def MWph = MegawattsPerHour(n)
    def GWph = GigawattsPerHour(n)
  }

  implicit class PowerRampStringConversion(s: String) {
    def toPowerRamp = PowerRamp(s)
  }

  implicit object PowerRampNumeric extends AbstractQuantityNumeric[PowerRamp](PowerRamp.primaryUnit)
}
