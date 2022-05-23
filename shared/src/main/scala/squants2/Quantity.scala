package squants2


import java.util.Objects
import scala.math.BigDecimal.RoundingMode
import scala.math.BigDecimal.RoundingMode.RoundingMode
import scala.math.Numeric.Implicits.infixNumericOps
import scala.math.Ordered.orderingToOrdered

/**
 * A base class for measurable quantities, instances of which contain a value and a unit
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
abstract class Quantity[A, D <: Dimension](implicit num: Numeric[A]) extends Serializable with Ordered[Quantity[A, D]] {
  type Q[B] <: Quantity[B, D]

  /**
   * The value of the quantity given the unit
   * @return Double
   */
  def value: A

  /**
   * The Unit of Measure the value represents
   * @return UnitOfMeasure[A]
   */
  def unit: UnitOfMeasure[D]

  /**
   * The Dimension this quantity represents
   * @return
   */
  lazy val dimension: D = unit.dimension

  /**
   * Add two like quantities
   * @param that Quantity
   * @return Quantity
   */
  def plus[B](that: Quantity[B, D])(implicit f: B => A): Q[A] = unit(value + f(that.to(unit))).asInstanceOf[Q[A]]
  def +[B](that: Quantity[B, D])(implicit f: B => A): Q[A] = plus(that)

  /**
   * Subtract two like quantities
   * @param that Quantity
   * @return Quantity
   */
  def minus[B](that: Quantity[B, D])(implicit f: B => A): Q[A] = unit(value - f(that.to(unit))).asInstanceOf[Q[A]]
  def -[B](that: Quantity[B, D])(implicit f: B => A): Q[A] = minus(that)

  /**
   * Multiply this quantity by some number
   * @param that Double
   * @return Quantity
   */
  def times[B](that: B)(implicit f: B => A): Q[A] = unit(value * f(that)).asInstanceOf[Q[A]]
  def *[B](that: B)(implicit f: B => A): Q[A] = times(that)

  // TODO - def * (that: Price[Quantity[_, D])

  /**
   * Divide this quantity by some number
   * @param that Double
   * @return Quantity
   */
  def divide[B](that: B)(implicit f: B => A): Q[A] = unit(value / f(that)).asInstanceOf[Q[A]]
  def /[B](that: B)(implicit f: B => A): Q[A] = divide(that)

  /**
   * Divide this quantity by a like quantity
   * @param that Quantity
   * @return Double
   */
  def divide[B](that: Quantity[B, D])(implicit f: B => A): A = value / that.asNum[A].to(unit)
  def /[B](that: Quantity[B, D])(implicit f: B => A): A = divide(that)

  /**
   * Returns the remainder of a division by a number
   * @param that Quantity
   * @return Quantity
   */
  def remainder[B](that: B)(implicit f: B => A): Q[A] = (value % f(that)).asInstanceOf[Q[A]]
  def %[B](that: B)(implicit f: B => A): Q[A] = remainder(that)

  /**
   * Returns the remainder of a division by a like quantity
   * @param that Quantity
   * @return Double
   */
  def remainder[B](that: Quantity[B, D])(implicit f: B => A): A = value % that.asNum[A].to(unit)
  def %[B](that: Quantity[B, D])(implicit f: B => A): A = remainder(that)

  /**
   * Returns a Pair that includes the result of divideToInteger and remainder
   * @param that Double
   * @return (Quantity, Quantity)
   */
  def divideAndRemainder[B](that: B)(implicit f: B => A): (Q[A], Q[A]) = {
    val (q, r) = value /% f(that)
    (unit(q).asInstanceOf[Q[A]], unit(r).asInstanceOf[Q[A]])
  }
  def /%[B](that: B)(implicit f: B => A): (Q[A], Q[A]) = divideAndRemainder(that)

  /**
   * Returns a Pair that includes the result of divideToInteger and remainder
   * @param that Quantity
   * @return (Double, Quantity)
   */
  def divideAndRemainder[B](that: Quantity[B, D])(implicit f: B => A): (A, Q[A]) = {
    val (q, r) = value /% that.asNum[A].to(unit)
    (q, unit(r).asInstanceOf[Q[A]])
  }
  def /%[B](that: Quantity[B, D])(implicit f: B => A): (A, Q[A]) = divideAndRemainder(that)

  /**
   * Returns the negative value of this Quantity
   * @return Quantity
   */
  def negate: Q[A] = map(num.negate)
  def unary_- : Q[A] = negate

  /**
   * Returns the absolute value of this Quantity
   * @return Quantity
   */
  def abs: Q[A] = map(num.abs)

  /**
   * Returns the smallest (closest to negative infinity) Quantity value that is greater than or equal to the argument and is equal to a mathematical integer.
   *
   * @see java.lang.Math#ceil(double)
   * @return Quantity
   */
  def ceil: Q[A] = rounded(0, RoundingMode.CEILING)

  /**
   * Returns the largest (closest to positive infinity) Quantity value that is less than or equal to the argument and is equal to a mathematical integer
   *
   * @see java.lang.Math#floor(double)
   * @return Quantity
   */
  def floor: Q[A] = rounded(0, RoundingMode.FLOOR)

  /**
   * Returns the Quantity value that is closest in value to the argument and is equal to a mathematical integer.
   *
   * @see java.lang.Math#rint(double)
   * @return Quantity
   */
  def rint: Q[A] = rounded(0, RoundingMode.HALF_EVEN)

  /**
   * Returns the Quantity with its coefficient value rounded using scale and mode.  The unit is maintained.
   *
   * @param scale Int - scale of the value to be returned
   * @param mode RoundingMode - defaults to HALF_EVEN
   * @return Quantity
   */
  def rounded(scale: Int, mode: RoundingMode = RoundingMode.HALF_EVEN): Q[A] =
    unit(value.rounded(scale, mode)).asInstanceOf[Q[A]]

  /**
   * Override of equals method
   *
   * @param that must be of matching value and unit
   * @return
   */
  override def equals(that: Any): Boolean = that match {
    case q: Quantity[_, _] if q.dimension == dimension => value == q.asInstanceOf[Quantity[_, D]].to(unit)
    case _ => false
  }

  /**
   * Override of hashCode
   *
   * @return
   */
  override def hashCode(): Int = Objects.hash(dimension, to(dimension.primaryUnit.asInstanceOf[UnitOfMeasure[D]]).hashCode())

  /**
   * Implements Ordered.compare
   * @param that Quantity
   * @return Int
   */
  override def compare(that: Quantity[A, D]): Int = {
    if (this.value > that.to(unit)) 1
    else if (this.value < that.to(unit)) -1
    else 0
  }

  /**
   * Returns the max of this and that Quantity
   * @param that Quantity
   * @return Quantity
   */
  def max[B](that: Quantity[B, D])(implicit f: B => A): Quantity[A, D] = if (this.value <= f(that.to(unit))) this else that.asNum[A]

  /**
   * Returns the min of this and that Quantity
   * @param that Quantity
   * @return Quantity
   */
  def min[B](that: Quantity[B, D])(implicit f: B => A): Quantity[A, D] = if (this.value >= f(that.to(unit))) this else that.asNum[A]

  /**
   * Returns boolean result of approximate equality comparison
   * @param that Quantity
   * @param tolerance Quantity
   * @return
   */
  def approx[B, T](that: Quantity[B, D])(implicit tolerance: Quantity[T, D], f: B => A, t2a: T => A): Boolean =
    that.asNum[A] within this.plusOrMinus(tolerance.asNum[A])
  /** approx */
  def =~[B, T](that: Quantity[B, D])(implicit tolerance: Quantity[T, D], f: B => A, t2a: T => A): Boolean = approx(that)
  /** approx */
  def ≈[B, T](that: Quantity[B, D])(implicit tolerance: Quantity[T, D], f: B => A, t2a: T => A): Boolean = approx(that)
  /** approx */
  def ~=[B, T](that: Quantity[B, D])(implicit tolerance: Quantity[T, D], f: B => A, t2a: T => A): Boolean = approx(that)

  /**
   * Returns a QuantityRange representing the range for this value +- that
   * @param that Quantity
   * @return QuantityRange
   */
  def plusOrMinus[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] = QuantityRange(this - that, this + that)
  def +-[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] = plusOrMinus(that)

  /**
   * Returns a QuantityRange that goes from this to that
   * @param that Quantity
   * @return QuantityRange
   */
  def to[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] = QuantityRange(this, that.asNum[A])

  /**
   * Returns true if this value is within (contains) the range
   * @param range QuantityRange
   * @return Boolean
   */
  def within[B](range: QuantityRange[B, D])(implicit f: B => A): Boolean = range.asNum[A].contains(this)

  /**
   * Returns true if this value is not within (contains) the range
   * @param range QuantityRange
   * @return Boolean
   */
  def notWithin[B](range: QuantityRange[B, D])(implicit f: B => A): Boolean = !range.asNum[A].contains(this)

  /**
   * Returns a Double representing the quantity in terms of the supplied unit
   * {{{
   *   val d = Feet(3)
   *   (d to Inches) should be(36)
   * }}}
   * @param uom UnitOfMeasure[A]
   * @return Double
   */
  def to(uom: UnitOfMeasure[D]): A = unit.convertTo(this, uom).value

  /**
   * Returns an equivalent Quantity boxed with the supplied Unit
   * @param uom UnitOfMeasure[A]
   * @return Quantity
   */
  def in(uom: UnitOfMeasure[D]): Q[A] = unit.convertTo(this, uom).asInstanceOf[Q[A]]

  /**
   * Convert this Quantity to one with the Numeric type B
   * @param f an implicit function that converts A to B
   * @tparam B the target Numeric type
   * @return
   */
  def asNum[B: Numeric](implicit f: A => B): Q[B] = unit(f(value)).asInstanceOf[Q[B]]

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
  def toString(uom: UnitOfMeasure[D]): String = s"${to(uom)} ${uom.symbol}" // TODO - Platform.crossFormat

  /**
   * Returns a string representing the quantity's value in the given `unit` in the given `format`
   * @param uom UnitOfMeasure[A] with UnitConverter
   * @param formatString String containing the format for the value (ie "%.3f")
   * @return String
   */
  def toString(uom: UnitOfMeasure[D], formatString: String): String = s"${formatString.format(to(uom))} ${uom.symbol}"

  /**
   * Returns a tuple representing the numeric value and the unit's symbol
   * @return
   */
  def toTuple: (A, String) = (value, unit.symbol)

  /**
   * Returns a pair representing the numeric value and the unit's symbol
   * @param uom UnitOfMeasure[A]
   * @return
   */
  def toTuple(uom: UnitOfMeasure[D]): (A, String) = (to(uom), uom.symbol)

  /**
   * Applies a function to the underlying value of the Quantity, returning a new Quantity in the same unit,
   * but with a any Numeric type
   * @param f Double => Double function
   * @return
   */
  def map[B: Numeric](f: A => B): Q[B] = unit(f(value)).asInstanceOf[Q[B]]

  /**
   * EXPERIMENTAL - Transform a Quantity to new Quantity of any Dimension, Unit and Numeric type
   * @param f the function applied to the Quantity
   * @tparam B the Numeric type for the result Quantity
   * @tparam E the Dimension for the result Quantity
   * @return
   */
  def flatMap[B, E <: Dimension](f: Quantity[A, D] => Quantity[B, E]): Quantity[B, E] = f(this)

}
