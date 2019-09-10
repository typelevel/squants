/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import java.util.Objects

import scala.math.BigDecimal.RoundingMode
import scala.math.BigDecimal.RoundingMode.RoundingMode

/**
 * A base class for measurable quantities, instances of which contain a value and a unit
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
   * The Unit of Measure the value represents
   * @return UnitOfMeasure[A]
   */
  def unit: UnitOfMeasure[A]

  /**
   * The Dimension this quantity represents
   * @return
   */
  def dimension: Dimension[A]

  /**
   * Add two like quantities
   * @param that Quantity
   * @return Quantity
   */
  def plus(that: A): A = unit(this.value + that.to(unit))
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
  def times(that: Double): A = unit(this.value * that)
  def *(that: Double): A = times(that)

  def *(that: Price[A]): Money = that * this

  /**
   * Divide this quantity by some number
   * @param that Double
   * @return Quantity
   */
  def divide(that: Double): A = unit(this.value / that)
  def /(that: Double): A = divide(that)

  /**
   * Divide this quantity by a like quantity
   * @param that Quantity
   * @return Double
   */
  def divide(that: A): Double = this.value / that.to(unit)
  def /(that: A): Double = divide(that)

  /**
   * Returns the remainder of a division by a number
   * @param that Quantity
   * @return Quantity
   */
  def remainder(that: Double): A = unit(this.value % that)
  def %(that: Double): A = remainder(that)

  /**
   * Returns the remainder of a division by a like quantity
   * @param that Quantity
   * @return Double
   */
  def remainder(that: A): Double = this.value % that.to(unit)
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
   * Returns the smallest (closest to negative infinity) Quantity value that is greater than or equal to the argument and is equal to a mathematical integer.
   *
   * @see java.lang.Math#ceil(double)
   * @return Quantity
   */
  def ceil: A = unit(math.ceil(value))

  /**
   * Returns the largest (closest to positive infinity) Quantity value that is less than or equal to the argument and is equal to a mathematical integer
   *
   * @see java.lang.Math#floor(double)
   * @return Quantity
   */
  def floor: A = unit(math.floor(value))

  /**
   * Returns the Quantity value that is closest in value to the argument and is equal to a mathematical integer.
   *
   * @see java.lang.Math#rint(double)
   * @return Quantity
   */
  def rint: A = unit(math.rint(value))

  /**
    * Returns the Quantity with its coefficient value rounded using scale and mode.  The unit is maintained.
    *
    * @param scale Int - scale of the value to be returned
    * @param mode RoundingMode - defaults to HALF_EVEN
    * @return Quantity
    */
  def rounded(scale: Int, mode: RoundingMode = RoundingMode.HALF_EVEN): A = unit(BigDecimal(value).setScale(scale, mode))

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
  override def hashCode() = {
    Objects.hash(dimension, Double.box(to(dimension.primaryUnit)))
  }

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
  def compare(that: A) = if (this.value > that.to(unit)) 1 else if (this.value < that.to(unit)) -1 else 0

  /**
   * Returns the max of this and that Quantity
   * @param that Quantity
   * @return Quantity
   */
  def max(that: A): A = if (this.value >= that.to(unit)) this else that

  /**
   * Returns the min of this and that Quantity
   * @param that Quantity
   * @return Quantity
   */
  def min(that: A): A = if (this.value <= that.to(unit)) this else that

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
   * @param uom UnitOfMeasure[A]
   * @return Double
   */
  def to(uom: UnitOfMeasure[A]): Double = uom match {
    case u if u == this.unit ⇒ value
    case _                   ⇒ uom.convertTo(this.unit.convertFrom(value))
  }

  /**
   * Returns an equivalent Quantity boxed with the supplied Unit
   * @param uom UnitOfMeasure[A]
   * @return Quantity
   */
  def in(uom: UnitOfMeasure[A]) = uom match {
    case u if u == this.unit ⇒ this
    case _                   ⇒ uom(uom.convertTo(this.unit.convertFrom(value)))
  }

  /**
   * Returns a string representing the quantity's value in unit
   * @return String
   */
  override def toString: String = toString(unit)

  /**
   * Returns a string representing the quantity's value in the given `unit`
   * @param uom UnitOfMeasure[A] with UnitConverter
   * @return String
   */
  def toString(uom: UnitOfMeasure[A]): String = s"${Platform.crossFormat(to(uom))} ${uom.symbol}"

  /**
   * Returns a string representing the quantity's value in the given `unit` in the given `format`
   * @param uom UnitOfMeasure[A] with UnitConverter
   * @param format String containing the format for the value (ie "%.3f")
   * @return String
   */
  def toString(uom: UnitOfMeasure[A], format: String): String = "%s %s".format(format.format(to(uom)), uom.symbol)

  /**
   * Returns a tuple representing the numeric value and the unit's symbol
   * @return
   */
  def toTuple: (Double, String) = (value, unit.symbol)

  /**
   * Returns a pair representing the numeric value and the uom's symbol
   * @param uom UnitOfMeasure[A]
   * @return
   */
  def toTuple(uom: UnitOfMeasure[A]): (Double, String) = (to(uom), uom.symbol)

  /**
   * Applies a function to the underlying value of the Quantity, returning a new Quantity in the same unit
   * @param f Double => Double function
   * @return
   */
  def map(f: Double ⇒ Double): A = unit(f(value))
}

