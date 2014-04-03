/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import scala.language.implicitConversions
import scala.Some
import squants._
import squants.time.TimeIntegral
import squants.motion.{ Momentum, Force, MassFlowRate }
import squants.energy.{ SpecificEnergy, Joules }
import squants.space.CubicMeters

/**
 * Represents a quantity of Mass
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value the value in the [[squants.mass.Grams]]
 */
final class Mass private (val value: Double) extends Quantity[Mass]
    with BaseQuantity with TimeIntegral[MassFlowRate] {

  def valueUnit = Grams
  def baseUnit = Kilograms

  def *(that: SpecificEnergy): Energy = Joules(toKilograms * that.toGrays)
  def *(that: Velocity): Momentum = Momentum(this, that)
  def *(that: Acceleration): Force = Force(this, that)
  def /(that: Density): Volume = CubicMeters(toKilograms / that.toKilogramsPerCubicMeter)
  def /(that: Volume): Density = Density(this, that)
  def /(that: MassFlowRate): Time = that.time * (this / that.change)
  def /(that: Time): MassFlowRate = MassFlowRate(this, that)
  def /(that: Area) = ??? // Creates AreaDensity

  def toMicrograms = to(Micrograms)
  def toMilligrams = to(Milligrams)
  def toGrams = to(Grams)
  def toKilograms = to(Kilograms)
  def toTonnes = to(Tonnes)
  def toPounds = to(Pounds)
  def toOunces = to(Ounces)

  override def toString = toString(this match {
    case Tonnes(t) if t >= 1.0      ⇒ Tonnes
    case Kilograms(kg) if kg >= 1.0 ⇒ Kilograms
    case Grams(g) if g >= 1.0       ⇒ Grams
    case _                          ⇒ Milligrams
  })
}

/**
 * Factory singleton for [[squants.mass.Mass]] quantities
 */
object Mass {
  private[mass] def apply(b: Double) = new Mass(b)
  def apply(s: String): Either[String, Mass] = {
    val regex = "([-+]?[0-9]*\\.?[0-9]+) *(mcg|mg|g|kg|t|tonnes|lb|oz)".r
    s match {
      case regex(value, Micrograms.symbol) ⇒ Right(Micrograms(value.toDouble))
      case regex(value, Milligrams.symbol) ⇒ Right(Milligrams(value.toDouble))
      case regex(value, Grams.symbol)      ⇒ Right(Grams(value.toDouble))
      case regex(value, Kilograms.symbol)  ⇒ Right(Kilograms(value.toDouble))
      case regex(value, Tonnes.symbol)     ⇒ Right(Tonnes(value.toDouble))
      case regex(value, "tonnes")          ⇒ Right(Tonnes(value.toDouble))
      case regex(value, Pounds.symbol)     ⇒ Right(Pounds(value.toDouble))
      case regex(value, Ounces.symbol)     ⇒ Right(Ounces(value.toDouble))
      case _                               ⇒ Left(s"Unable to parse $s as Mass")
    }
  }
}

/**
 * Base trait for units of [[squants.mass.Mass]]
 */
trait MassUnit extends BaseQuantityUnit[Mass] with UnitMultiplier {
  def dimensionSymbol = "M"
  def apply(n: Double) = Mass(convertFrom(n))
  def unapply(m: Mass) = Some(convertTo(m.value))
}

object Grams extends MassUnit with ValueUnit {
  val symbol = "g"
}

object Micrograms extends MassUnit {
  val multiplier = MetricSystem.Micro
  val symbol = "mcg"
}

object Milligrams extends MassUnit {
  val multiplier = MetricSystem.Milli
  val symbol = "mg"
}

object Kilograms extends MassUnit with BaseUnit {
  val multiplier = MetricSystem.Kilo
  val symbol = "kg"
}

object Tonnes extends MassUnit {
  val multiplier = MetricSystem.Mega
  val symbol = "t"
}

object Pounds extends MassUnit {
  val multiplier = Kilograms.multiplier * 0.45359237
  val symbol = "lb"
}

object Ounces extends MassUnit {
  val multiplier = Pounds.multiplier / 16d
  val symbol = "oz"
}

/**
 * Implicit conversions for [[squants.mass.Mass]]
 *
 * Provides support fot the DSL
 */
object MassConversions {
  lazy val microgram = Micrograms(1)
  lazy val milligram = Milligrams(1)
  lazy val gram = Grams(1)
  lazy val kilogram = Kilograms(1)
  lazy val tonne = Tonnes(1)
  lazy val pound = Pounds(1)
  lazy val ounce = Ounces(1)

  /**
   * Class for implicit conversions to [[squants.mass.Mass]]
   *
   * @param d Double
   */
  implicit class MassConversions(val d: Double) {
    def mcg = Micrograms(d)
    def mg = Milligrams(d)
    def milligrams = mg
    def g = Grams(d)
    def grams = g
    def kg = Kilograms(d)
    def kilograms = kg
    def tonnes = Tonnes(d)
    def pounds = Pounds(d)
    def ounces = Ounces(d)
  }

  implicit class MassStringConversions(val s: String) {
    def toMass = Mass(s)
  }

  implicit object MassNumeric extends AbstractQuantityNumeric[Mass](Grams)
}

