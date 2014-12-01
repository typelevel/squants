/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import scala.language.implicitConversions
import squants._
import squants.time.TimeIntegral
import squants.motion._
import squants.energy.{ SpecificEnergy, Joules }
import squants.space.{ SquareMeters, CubicMeters }
import squants.motion.Force
import squants.Velocity
import squants.motion.MassFlowRate
import squants.Acceleration
import squants.motion.Momentum

/**
 * Represents a quantity of Mass
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value the value in the [[squants.mass.Grams]]
 */
final class Mass private (val value: Double) extends Quantity[Mass]
    with TimeIntegral[MassFlowRate] {

  def valueUnit = Mass.valueUnit
  protected def timeDerived = KilogramsPerSecond(toKilograms)
  protected def time = Seconds(1)

  def *(that: SpecificEnergy): Energy = Joules(toKilograms * that.toGrays)
  def *(that: Velocity): Momentum = Momentum(this, that)
  def *(that: Acceleration): Force = Newtons(toKilograms * that.toMetersPerSecondSquared)
  def /(that: Density): Volume = CubicMeters(toKilograms / that.toKilogramsPerCubicMeter)
  def /(that: Volume): Density = Density(this, that)
  def /(that: AreaDensity): Area = SquareMeters(toKilograms / that.toKilogramsPerSquareMeter)
  def /(that: Area): AreaDensity = KilogramsPerSquareMeter(toKilograms / that.toSquareMeters)

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
 * Factory singleton for [[squants.mass.Mass]] values
 */
object Mass extends QuantityCompanion[Mass] with BaseQuantity {
  private[mass] def apply[A](n: A)(implicit num: Numeric[A]) = new Mass(num.toDouble(n))
  def apply = parseString _
  def name = "Mass"
  def valueUnit = Grams
  def units = Set(Micrograms, Milligrams, Grams, Kilograms, Tonnes, Pounds, Ounces)
  def dimensionSymbol = "M"
  def baseUnit = Kilograms
}

/**
 * Base trait for units of [[squants.mass.Mass]]
 */
trait MassUnit extends UnitOfMeasure[Mass] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Mass(convertFrom(n))
}

object Grams extends MassUnit with ValueUnit {
  val symbol = "g"
}

object Micrograms extends MassUnit {
  val conversionFactor = MetricSystem.Micro
  val symbol = "mcg"
}

object Milligrams extends MassUnit {
  val conversionFactor = MetricSystem.Milli
  val symbol = "mg"
}

object Kilograms extends MassUnit with BaseUnit {
  val conversionFactor = MetricSystem.Kilo
  val symbol = "kg"
}

object Tonnes extends MassUnit {
  val conversionFactor = MetricSystem.Mega
  val symbol = "t"
}

object Pounds extends MassUnit {
  val conversionFactor = Kilograms.conversionFactor * 0.45359237
  val symbol = "lb"
}

object Ounces extends MassUnit {
  val conversionFactor = Pounds.conversionFactor / 16d
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

  implicit class MassConversions[A](n: A)(implicit num: Numeric[A]) {
    def mcg = Micrograms(n)
    def mg = Milligrams(n)
    def milligrams = mg
    def g = Grams(n)
    def grams = g
    def kg = Kilograms(n)
    def kilograms = kg
    def tonnes = Tonnes(n)
    def pounds = Pounds(n)
    def ounces = Ounces(n)
  }

  implicit class MassStringConversions(val s: String) {
    def toMass = Mass(s)
  }

  implicit object MassNumeric extends AbstractQuantityNumeric[Mass](Mass.valueUnit)
}

