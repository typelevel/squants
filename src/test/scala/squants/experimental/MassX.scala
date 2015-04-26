/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.experimental

import scala.language.implicitConversions
import squants._

/**
 * Represents a quantity of Mass
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value the value in the [[squants.mass.Grams]]
 */
final class MassX private (val value: Double) extends QuantityX[MassX] {

  def valueUnit = MassX.valueUnit

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
object MassX extends QuantityCompanionX[MassX] with BaseQuantityX {
  private[experimental] def apply[A](n: A)(implicit num: Numeric[A]) = new MassX(num.toDouble(n))
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
trait MassUnitX extends UnitOfMeasureX[MassX] with UnitConverterX {
  def apply[A](n: A)(implicit num: Numeric[A]) = MassX(convertFrom(n))
}

object Grams extends MassUnitX with ValueUnitX {
  val symbol = "g"
}

object Micrograms extends MassUnitX {
  val conversionFactor = MetricSystem.Micro
  val symbol = "mcg"
}

object Milligrams extends MassUnitX {
  val conversionFactor = MetricSystem.Milli
  val symbol = "mg"
}

object Kilograms extends MassUnitX with BaseUnitX {
  val conversionFactor = MetricSystem.Kilo
  val symbol = "kg"
}

object Tonnes extends MassUnitX {
  val conversionFactor = MetricSystem.Mega
  val symbol = "t"
}

object Pounds extends MassUnitX {
  val conversionFactor = Kilograms.conversionFactor * 0.45359237
  val symbol = "lb"
}

object Ounces extends MassUnitX {
  val conversionFactor = Pounds.conversionFactor / 16d
  val symbol = "oz"
}

/**
 * Implicit conversions for [[squants.mass.Mass]]
 *
 * Provides support fot the DSL
 */
object MassConversionsX {
  lazy val microgram = Micrograms(1)
  lazy val milligram = Milligrams(1)
  lazy val gram = Grams(1)
  lazy val kilogram = Kilograms(1)
  lazy val tonne = Tonnes(1)
  lazy val pound = Pounds(1)
  lazy val ounce = Ounces(1)

  implicit class MassConversionsX[A](n: A)(implicit num: Numeric[A]) {
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

  implicit class MassStringConversionsX(val s: String) {
    def toMass = MassX(s)
  }

  implicit object MassNumericX extends AbstractQuantityNumericX[MassX](MassX.valueUnit)
}

