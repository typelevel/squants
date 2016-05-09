/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import squants.TypeLevelInt._
import squants.energy.{ Energy, Joules, SpecificEnergy }
import squants.motion.{ Force, MassFlow, Momentum, _ }
import squants.space.{ CubicMeters, SquareMeters }
import squants.time.TimeIntegral
import squants.{ Acceleration, Energy ⇒ _, Velocity, _ }

/**
 * Represents a quantity of Mass
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value the value in the [[squants.mass.Grams]]
 */
final class Mass private (val value: Double, val unit: MassUnit)
    extends Quantity[Mass]
    with DimensionType
    with TimeIntegral[MassFlow] {

  type Dimension = (Mass, _1) :: HNil
  def dimension = Mass

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
  def toOunces = to(Ounces)
  def toPounds = to(Pounds)
  def toKilopounds = to(Kilopounds)
  def toMegapounds = to(Megapounds)
}

/**
 * Factory singleton for [[squants.mass.Mass]] values
 */
object Mass extends Dimension[Mass] with BaseDimension {
  private[mass] def apply[A](n: A, unit: MassUnit)(implicit num: Numeric[A]) = new Mass(num.toDouble(n), unit)
  def apply = parse _
  def name = "Mass"
  def primaryUnit = Grams
  def siUnit = Kilograms
  def units = Set(Micrograms, Milligrams, Grams, Kilograms, Tonnes, Ounces, Pounds, Kilopounds, Megapounds)
  def dimensionSymbol = "M"
}

/**
 * Base trait for units of [[squants.mass.Mass]]
 */
trait MassUnit extends UnitOfMeasure[Mass] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Mass(n, this)
}

object Grams extends MassUnit with PrimaryUnit {
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

object Kilograms extends MassUnit with SiBaseUnit {
  val conversionFactor = MetricSystem.Kilo
  val symbol = "kg"
}

object Tonnes extends MassUnit {
  val conversionFactor = MetricSystem.Mega
  val symbol = "t"
}

object Ounces extends MassUnit {
  val conversionFactor = Pounds.conversionFactor / 16d
  val symbol = "oz"
}

object Pounds extends MassUnit {
  val conversionFactor = Kilograms.conversionFactor * 4.5359237e-1
  val symbol = "lb"
}

object Kilopounds extends MassUnit {
  val conversionFactor = Pounds.conversionFactor * MetricSystem.Kilo
  val symbol = "klb"
}

object Megapounds extends MassUnit {
  val conversionFactor = Pounds.conversionFactor * MetricSystem.Kilo
  val symbol = "Mlb"
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
  lazy val ounce = Ounces(1)
  lazy val pound = Pounds(1)
  lazy val kilopound = Kilopounds(1)
  lazy val megapound = Megapounds(1)

  implicit class MassConversions[A](n: A)(implicit num: Numeric[A]) {
    def mcg = Micrograms(n)
    def mg = Milligrams(n)
    def milligrams = mg
    def g = Grams(n)
    def grams = g
    def kg = Kilograms(n)
    def kilograms = kg
    def tonnes = Tonnes(n)
    def ounces = Ounces(n)
    def pounds = Pounds(n)
    def kilopounds = Kilopounds(n)
    def megapounds = Megapounds(n)
  }

  implicit class MassStringConversions(val s: String) {
    def toMass = Mass(s)
  }

  implicit object MassNumeric extends AbstractQuantityNumeric[Mass](Mass.primaryUnit)
}

