/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import scala.util.{ Failure, Success, Try }

/**
 * A base trait for measurable quantities
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
abstract class Quantity[A <: Quantity[A]] extends Serializable with Ordered[A] { self: A ⇒

  /**
   * The value of the quantity given the unit
   * @return Double
   */
  def value: Double

  /**
   * The Unit of Measure used for the quantity's underlying value
   * @return UnitOfMeasure[A]
   */
  def unit: UnitOfMeasure[A]

  /**
   * The Dimension this quantity contains a measure for
   * @return
   */
  def dimension: Dimension[A]

  /**
   * Add two like quantities
   * @param that Quantity
   * @return Quantity
   */
  def plus(that: A): A = unit(value + that.to(unit))
  def +(that: A): A = plus(that)

  /**
   * Subtract two like quantities
   * @param that Quantity
   * @return Quantity
   */
  def minus(that: A): A = plus(that.negate)
  def -(that: A): A = minus(that)

  /**
   * Multiply this quantity by some number
   * @param that Double
   * @return Quantity
   */
  def times(that: Double): A = unit(value * that)
  def *(that: Double): A = times(that)

  /**
   * Divide this quantity by some number
   * @param that Double
   * @return Quantity
   */
  def divide(that: Double): A = unit(value / that)
  def /(that: Double): A = divide(that)

  /**
   * Divide this quantity by a like quantity
   * @param that Quantity
   * @return Double
   */
  def divide(that: A): Double = value / that.to(unit)
  def /(that: A): Double = divide(that)

  /**
   * Returns the remainder of a division by a number
   * @param that Quantity
   * @return Quantity
   */
  def remainder(that: Double): A = unit(value % that)
  def %(that: Double): A = remainder(that)

  /**
   * Returns the remainder of a division by a like quantity
   * @param that Quantity
   * @return Double
   */
  def remainder(that: A): Double = value % that.to(unit)
  def %(that: A): Double = remainder(that)

  /**
   * Returns a Pair that includes the result of divideToInteger and remainder
   * @param that Double
   * @return (Quantity, Quantity)
   */
  def divideAndRemainder(that: Double): (A, A) = BigDecimal(value) /% that match {
    case (q, r) ⇒ (unit(q.toDouble), unit(r.toDouble))
  }
  def /%(that: Double) = divideAndRemainder(that)

  /**
   * Returns a Pair that includes the result of divideToInteger and remainder
   * @param that Quantity
   * @return (Double, Quantity)
   */
  def divideAndRemainder(that: A): (Double, A) = BigDecimal(value) /% that.to(unit) match {
    case (q, r) ⇒ (q.toDouble, unit(r.toDouble))
  }
  def /%(that: A) = divideAndRemainder(that)

  /**
   * Returns the negative value of this Quantity
   * @return Quantity
   */
  def negate: A = unit(-value)
  def unary_-(): A = negate

  /**
   * Returns the absolute value of this Quantity
   * @return Quantity
   */
  def abs: A = unit(math.abs(value))

  /**
   * Override of equals method
   *
   * @param that must be of matching value and unit
   * @return
   */
  override def equals(that: Any) = that match {
    case x: Quantity[A] if x.dimension == dimension ⇒ value == x.to(unit)
    case _ ⇒ false
  }

  /**
   * Override of hashCode
   *
   * @return
   */
  override def hashCode() = toString.hashCode()

  /**
   * Returns boolean result of approximate equality comparison
   * @param that Quantity
   * @param tolerance Quantity
   * @return
   */
  def approx(that: A)(implicit tolerance: A) = that within this.plusOrMinus(tolerance)
  /** approx */
  def =~(that: A)(implicit tolerance: A) = approx(that)
  /** approx */
  def ≈(that: A)(implicit tolerance: A) = approx(that)
  /** approx */
  def ~=(that: A)(implicit tolerance: A) = approx(that)

  /**
   * Implements Ordered.compare
   * @param that Quantity
   * @return Int
   */
  def compare(that: A) = if (value > that.to(unit)) 1 else if (value < that.to(unit)) -1 else 0

  /**
   * Returns the max of this and that Quantity
   * @param that Quantity
   * @return Quantity
   */
  def max(that: A): A = if (value >= that.to(unit)) this else that

  /**
   * Returns the min of this and that Quantity
   * @param that Quantity
   * @return Quantity
   */
  def min(that: A): A = if (value <= that.to(unit)) this else that

  /**
   * Returns a QuantityRange representing the range for this value +- that
   * @param that Quantity
   * @return QuantityRange
   */
  def plusOrMinus(that: A): QuantityRange[A] = QuantityRange(this - that, this + that)
  def +-(that: A) = plusOrMinus(that)

  /**
   * Returns a QuantityRange that goes from this to that
   * @param that Quantity
   * @return QuantityRange
   */
  def to(that: A): QuantityRange[A] = QuantityRange(this / 1, that)

  /**
   * Returns true if this value is within (contains) the range
   * @param range QuantityRange
   * @return Boolean
   */
  def within(range: QuantityRange[A]) = range.contains(self)

  /**
   * Returns true if this value is not within (contains) the range
   * @param range QuantityRange
   * @return Boolean
   */
  def notWithin(range: QuantityRange[A]) = !range.contains(self)

  /**
   * Returns a Double representing the quantity in terms of the supplied unit
   * {{{
   *   val d = Feet(3)
   *   (d to Inches) should be(36)
   * }}}
   * @param unit UnitOfMeasure[A]
   * @return Double
   */
  def to(unit: UnitOfMeasure[A]): Double = unit match {
    case u if u == this.unit ⇒ value
    case _                   ⇒ unit.convertTo(this.unit.convertFrom(value))
  }

  /**
   * Returns an equivalent Quantity boxed with the supplied Unit
   *
   * This is really only useful for Quantity classes that box at the UOM level
   * e.g. Temperature and currently Time
   *
   * @param unit UnitOfMeasure[A]
   * @return Quantity
   */
  def in(unit: UnitOfMeasure[A]) = unit match {
    case u if u == this.unit ⇒ this
    case _                   ⇒ unit(unit.convertTo(this.unit.convertFrom(value)))
  }

  /**
   * Returns a string representing the quantity's value in valueUnits
   * @return String
   */
  override def toString = value + " " + unit.symbol

  /**
   * Returns a string representing the quantity's value in the given `unit`
   * @param unit UnitOfMeasure[A] with UnitConverter
   * @return String
   */
  def toString(unit: UnitOfMeasure[A]): String = to(unit) + " " + unit.symbol

  /**
   * Returns a string representing the quantity's value in the given `unit` in the given `format`
   * @param unit UnitOfMeasure[A] with UnitConverter
   * @param format String containing the format for the value (ie "%.3f")
   * @return String
   */
  def toString(unit: UnitOfMeasure[A], format: String): String = "%s %s".format(format.format(to(unit)), unit.symbol)
}

/**
 * Base class for creating objects to manage quantities as Numeric.
 *
 * One limitation is the `times` operation which is not supported by every quantity type
 *
 * @tparam A Quantity type
 */
abstract class AbstractQuantityNumeric[A <: Quantity[A]](val unit: UnitOfMeasure[A] with PrimaryUnit) extends Numeric[A] {
  def plus(x: A, y: A) = x + y
  def minus(x: A, y: A) = x - y

  /**
   * `times` is not a supported Numeric operation for Quantities.
   * It is not possible to multiply a dimensional quantity by a like quantity and get another like quantity.
   * Applying this class in a way that uses this method will result in an UnsupportedOperationException being thrown.
   *
   * @param x Quantity[A]
   * @param y Quantity[A]
   * @return
   * @throws UnsupportedOperationException for most types
   */
  def times(x: A, y: A): A = throw new UnsupportedOperationException("Numeric.times not supported for Quantities")
  def negate(x: A) = -x
  def fromInt(x: Int) = unit(x)
  def toInt(x: A) = x.to(unit).toInt
  def toLong(x: A) = x.to(unit).toLong
  def toFloat(x: A) = x.to(unit).toFloat
  def toDouble(x: A) = x.to(unit)
  def compare(x: A, y: A) = if (x.to(unit) > y.to(unit)) 1 else if (x.to(unit) < y.to(unit)) -1 else 0
}

case class QuantityStringParseException(message: String, expression: String) extends Exception

/**
 * Represents a Physical Dimension
 *
 * This trait should be mixed into the Companion Objects of specific Quantity Types.
 *
 * @tparam A Quantity Type
 */
trait Dimension[A <: Quantity[A]] {
  def name: String
  def primaryUnit: UnitOfMeasure[A] with PrimaryUnit
  def siUnit: UnitOfMeasure[A] with SiUnit
  def units: Set[UnitOfMeasure[A]]

  def symbolToUnit(symbol: String): Option[UnitOfMeasure[A]] = units.find(u ⇒ u.symbol == symbol)

  private lazy val QuantityString = ("([-+]?[0-9]*\\.?[0-9]+) *(" + units.map { u: UnitOfMeasure[A] ⇒ u.symbol }.reduceLeft(_ + "|" + _) + ")").r
  protected def parseString(s: String): Try[A] = {
    s match {
      case QuantityString(value, symbol) ⇒ Success(symbolToUnit(symbol).get(BigDecimal(value)))
      case _                             ⇒ Failure(QuantityStringParseException(s"Unable to parse $name", s))
    }
  }
}

/**
 * SI Base Quantity
 */
trait BaseDimension { self: Dimension[_] ⇒
  /**
   * SI Base Unit for this Quantity
   * @return
   */
  def siUnit: SiBaseUnit

  /**
   * SI Dimension Symbol
   * @return
   */
  def dimensionSymbol: String
}
