/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

/**
 * A base trait for measurable quantities
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
trait Quantity[A <: Quantity[A]] extends Any with Ordered[A] with Serializable { self: A ⇒

  /**
   * The value of the quantity given the valueUnits
   * @return Double
   */
  def value: Double

  /**
   * The Unit of Measure used for the quantity's underlying value
   * @return UnitOfMeasure[A]
   */
  def valueUnit: UnitOfMeasure[A]

  /**
   * Add two like quantities
   * @param that Quantity
   * @return Quantity
   */
  def plus(that: A): A = valueUnit(value + that.value)
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
  def times(that: Double): A = valueUnit(value * that)
  def *(that: Double): A = times(that)

  /**
   * Divide this quantity by some number
   * @param that Double
   * @return Quantity
   */
  def divide(that: Double): A = valueUnit(value / that)
  def /(that: Double): A = divide(that)

  /**
   * Divide this quantity by a like quantity
   * @param that Quantity
   * @return Double
   */
  def divide(that: A): Double = value / that.value
  def /(that: A): Double = divide(that)

  /**
   * Returns the remainder of a division by a number
   * @param that Quantity
   * @return Quantity
   */
  def remainder(that: Double): A = valueUnit(value % that)
  def %(that: Double): A = remainder(that)

  /**
   * Returns the remainder of a division by a like quantity
   * @param that Quantity
   * @return Double
   */
  def remainder(that: A): Double = value % that.value
  def %(that: A): Double = remainder(that)

  /**
   * Returns a Pair that includes the result of divideToInteger and remainder
   * @param that Double
   * @return (Quantity, Quantity)
   */
  def divideAndRemainder(that: Double): (A, A) = BigDecimal(value) /% that match {
    case (q, r) ⇒ (valueUnit(q.toDouble), valueUnit(r.toDouble))
  }
  def /%(that: Double) = divideAndRemainder(that)

  /**
   * Returns a Pair that includes the result of divideToInteger and remainder
   * @param that Quantity
   * @return (Double, Quantity)
   */
  def divideAndRemainder(that: A): (Double, A) = BigDecimal(value) /% that.value match {
    case (q, r) ⇒ (q.toDouble, valueUnit(r.toDouble))
  }
  def /%(that: A) = divideAndRemainder(that)

  /**
   * Returns the negative value of this Quantity
   * @return Quantity
   */
  def negate: A = valueUnit(-value)
  def unary_-(): A = negate

  /**
   * Returns the absolute value of this Quantity
   * @return Quantity
   */
  def abs: A = valueUnit(math.abs(value))

  /**
   * Returns boolean result of equality between this and that
   * @param that Quantity
   * @return Boolean
   */
  // TODO: Improve comparisons for types that use multiple Quantity type classes (ie, Temp)
  def ==(that: A) = value == that.value

  /**
   * Return boolean result of inequality between this and that
   * @param that Quantity
   * @return Boolean
   */
  def !=(that: A) = value != that.value

  /**
   * Implements Ordered.compare
   * @param that Quantity
   * @return Int
   */
  def compare(that: A) = if (value > that.value) 1 else if (value < that.value) -1 else 0

  /**
   * Returns the max of this and that Quantity
   * @param that Quantity
   * @return Quantity
   */
  def max(that: A): Quantity[A] = if (value >= that.value) this else that

  /**
   * Returns the min of this and that Quantity
   * @param that Quantity
   * @return Quantity
   */
  def min(that: A): Quantity[A] = if (value <= that.value) this else that

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
   *   assert((d to Inches) == 36)
   * }}}
   * @param unit UnitOfMeasure[A] with UnitConverter
   * @return Double
   */
  def to(unit: UnitOfMeasure[A] with UnitConverter): Double = unit.convertTo(value)

  /**
   * Returns an equivalent Quantity boxed with the supplied Unit
   *
   * This is really only useful for Quantity classes that box at the UOM level
   * e.g. Temperature and currently Time
   *
   * @param unit UnitOfMeasure[A] with UnitConverter
   * @return Quantity
   */
  def in(unit: UnitOfMeasure[A] with UnitConverter) = unit(unit.convertTo(value))

  /**
   * Returns a string representing the quantity's value in valueUnits
   * @return String
   */
  override def toString = value + " " + valueUnit.symbol

  /**
   * Returns a string representing the quantity's value in the given `unit`
   * @param unit UnitOfMeasure[A] with UnitConverter
   * @return String
   */
  def toString(unit: UnitOfMeasure[A] with UnitConverter): String = to(unit) + " " + unit.symbol
}

/**
 * A base trait for measurable quantities found within physical systems
 */
trait PhysicalQuantity extends Any { self: Quantity[_] ⇒ }

/**
 * Base Quantities are the basic, directly measurable, fundamental quantities: Mass, Length, Time, etc.
 */
trait BaseQuantity extends Any { self: Quantity[_] ⇒
  def baseUnit: BaseUnit
}

/**
 * Derived Quantities are usually based on two more underlying quantities (base or other derived)
 */
trait DerivedQuantity extends Any { self: Quantity[_] ⇒ }

/**
 * Base trait for creating objects to manage quantities as Numeric.
 *
 * One limitation is the `times` operation which is not supported by every quantity type
 *
 * @tparam A Quantity type
 */
abstract class AbstractQuantityNumeric[A <: Quantity[A]](val valueUnit: UnitOfMeasure[A] with ValueUnit) extends Numeric[A] {
  def plus(x: A, y: A) = x + y
  def minus(x: A, y: A) = x - y
  def times(x: A, y: A): A = throw new UnsupportedOperationException("Numeric.times not supported for Quantities")
  def negate(x: A) = -x
  def fromInt(x: Int) = valueUnit(x)
  def toInt(x: A) = x.value.toInt
  def toLong(x: A) = x.value.toLong
  def toFloat(x: A) = x.value.toFloat
  def toDouble(x: A) = x.value
  def compare(x: A, y: A) = if (x.value > y.value) 1 else if (x.value < y.value) -1 else 0
}
