/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import squants._
import squants.time.{ TimeDerivative, Hours }
import scala.util.{ Failure, Success, Try }

/**
 * Represents the rate of change of [[squants.energy.Power]] over time
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.energy.WattsPerHour]]
 */
final class PowerRamp private (val value: Double)
    extends Quantity[PowerRamp] with PhysicalQuantity
    with TimeDerivative[Power] {

  def valueUnit = WattsPerHour
  def change = Watts(value)
  def time = Hours(1)

  def toWattsPerHour = to(WattsPerHour)
  def toKilowattsPerHour = to(KilowattsPerHour)
  def toMegawattsPerHour = to(MegawattsPerHour)
  def toGigawattsPerHour = to(GigawattsPerHour)

  override def toString = toString(this match {
    case GigawattsPerHour(gwh) if gwh > 1.0 ⇒ GigawattsPerHour
    case MegawattsPerHour(mwh) if mwh > 1.0 ⇒ MegawattsPerHour
    case KilowattsPerHour(kwh) if kwh > 1.0 ⇒ KilowattsPerHour
    case _                                  ⇒ WattsPerHour
  })
}

object PowerRamp {
  private[energy] def apply[A](n: A)(implicit num: Numeric[A]) = new PowerRamp(num.toDouble(n))
  def apply(change: Power, time: Time): PowerRamp = apply(change.toWatts / time.toHours)
  def apply(s: String): Try[PowerRamp] = {
    val regex = "([-+]?[0-9]*\\.?[0-9]+) *(W/h|kW/h|MW/h|GW/h)".r
    s match {
      case regex(value, WattsPerHour.symbol)     ⇒ Success(WattsPerHour(value.toDouble))
      case regex(value, KilowattsPerHour.symbol) ⇒ Success(KilowattsPerHour(value.toDouble))
      case regex(value, MegawattsPerHour.symbol) ⇒ Success(MegawattsPerHour(value.toDouble))
      case regex(value, GigawattsPerHour.symbol) ⇒ Success(GigawattsPerHour(value.toDouble))
      case _                                     ⇒ Failure(QuantityStringParseException("Unable to parse PowerRamp", s))
    }
  }
}

trait PowerRampUnit extends UnitOfMeasure[PowerRamp] with UnitMultiplier {
  def apply[A](n: A)(implicit num: Numeric[A]) = PowerRamp(convertFrom(n))
  def unapply(ramp: PowerRamp) = Some(convertTo(ramp.value))
}

object WattsPerHour extends PowerRampUnit with ValueUnit {
  val symbol = "W/h"
}

object KilowattsPerHour extends PowerRampUnit {
  val multiplier = MetricSystem.Kilo
  val symbol = "kW/h"
}

object MegawattsPerHour extends PowerRampUnit {
  val multiplier = MetricSystem.Mega
  val symbol = "MW/h"
}

object GigawattsPerHour extends PowerRampUnit {
  val multiplier = MetricSystem.Giga
  val symbol = "GW/h"
}

object PowerRampConversions {
  lazy val wattsPerHour = WattsPerHour(1)
  lazy val Wph = wattsPerHour
  lazy val kilowattsPerHour = KilowattsPerHour(1)
  lazy val kWph = kilowattsPerHour
  lazy val megawattsPerHour = MegawattsPerHour(1)
  lazy val MWph = megawattsPerHour
  lazy val gigawattsPerHour = GigawattsPerHour(1)
  lazy val GWph = gigawattsPerHour

  implicit class PowerRampConversions[A](n: A)(implicit num: Numeric[A]) {
    def Wph = WattsPerHour(n)
    def kWph = KilowattsPerHour(n)
    def MWph = MegawattsPerHour(n)
    def GWph = GigawattsPerHour(n)
  }

  implicit class PowerRampStringConversion(s: String) {
    def toPowerRamp = PowerRamp(s)
  }

  implicit object PowerRampNumeric extends AbstractQuantityNumeric[PowerRamp](WattsPerHour)
}
