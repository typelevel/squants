/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.experimental

/**
 * A base class for measurable quantities, instances of which contain a value and a unit
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
abstract class Quantity[A <: Quantity[A, _], N](implicit val sqNum: SquantsNumeric[N]) extends Serializable with Ordered[Quantity[A, N]] { self: Quantity[A, N] ⇒

  import sqNum.mkSquantsNumericOps

  type AN = Quantity[A, N]

  /**
   * The value of the quantity given the unit
   * @return N
   */
  def value: N

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
  def plus(that: AN): A = unit(sqNum.plus(value, that.to(unit))).asInstanceOf[A]
  def +(that: AN): A = plus(that)

  /**
   * Subtract two like quantities
   * @param that Quantity
   * @return Quantity
   */
  def minus(that: AN): A = unit(sqNum.minus(value, that.to(unit))).asInstanceOf[A]
  def -(that: AN): A = minus(that)

  /**
   * Multiply this quantity by some number
   * @param that N
   * @return Quantity
   */
  def times(that: N): A = unit(value * that).asInstanceOf[A]
  def *(that: N): A = times(that)

  /**
   * Divide this quantity by some number
   * @param that N
   * @return Quantity
   */
  def divide(that: N): A = unit(value / that).asInstanceOf[A]
  def /(that: N): A = divide(that)

  /**
   * Divide this quantity by a like quantity
   * @param that Quantity
   * @return N
   */
  def divide(that: AN): N = value / that.to(unit)
  def /(that: AN): N = divide(that)

  /**
   * Returns the remainder of a division by a number
   * @param that Quantity
   * @return Quantity
   */
  def remainder(that: N): AN = unit(value % that)
  def %(that: N): AN = remainder(that)

  /**
   * Returns the remainder of a division by a like quantity
   * @param that Quantity
   * @return N
   */
  def remainder(that: AN): N = value % that.to(unit)
  def %(that: AN): N = remainder(that)

  /**
   * Returns a Pair that includes the result of divideToInteger and remainder
   * @param that Number
   * @return (Quantity, Quantity)
   */
  def divideAndRemainder(that: N): (A, A) = value /% that match {
    case (q, r) ⇒ (unit(q).asInstanceOf[A], unit(r).asInstanceOf[A])
  }
  def /%(that: N) = divideAndRemainder(that)

  /**
   * Returns a Pair that includes the result of divideToInteger and remainder
   * @param that Quantity
   * @return (N, Quantity)
   */
  def divideAndRemainder(that: AN): (N, A) = value /% that.to(unit) match {
    case (q, r) ⇒ (q, unit(r).asInstanceOf[A])
  }
  def /%(that: AN) = divideAndRemainder(that)

  /**
   * Returns the negative value of this Quantity
   * @return Quantity
   */
  def negate: A = unit(-value).asInstanceOf[A]
  def unary_- : A = negate

  /**
   * Returns the absolute value of this Quantity
   * @return Quantity
   */
  def abs: A = unit(sqNum.abs(value)).asInstanceOf[A]

  /**
   * Override of equals method
   *
   * @param that must be of matching value and unit
   * @return
   */
  override def equals(that: Any) = that match {
    case x: Quantity[_, _] if x.dimension == dimension => value == x.asInstanceOf[Quantity[A, _]].to(unit)
    case _ ⇒ false
  }

  /**
   * Override of hashCode
   *
   * @return
   */
  override def hashCode() = toString.hashCode()

  //  /**
  //   * Returns boolean result of approximate equality comparison
  //   * @param that Quantity
  //   * @param tolerance Quantity
  //   * @return
  //   */
  //  def approx(that: AN)(implicit tolerance: AN) = that within this.plusOrMinus(tolerance)
  //  /** approx */
  //  def =~(that: AN)(implicit tolerance: AN) = approx(that)
  //  /** approx */
  //  def ≈(that: AN)(implicit tolerance: AN) = approx(that)
  //  /** approx */
  //  def ~=(that: AN)(implicit tolerance: AN) = approx(that)

  /**
   * Implements Ordered.compare
   * @param that Quantity
   * @return Int
   */
  def compare(that: AN): Int = sqNum.compare(value, that.to(unit))

  /**
   * Returns the max of this and that Quantity
   * @param that Quantity
   * @return Quantity
   */
  def max(that: AN): AN = if (value >= that.to(unit)) this else that

  /**
   * Returns the min of this and that Quantity
   * @param that Quantity
   * @return Quantity
   */
  def min(that: AN): AN = if (value <= that.to(unit)) this else that

  //  /**
  //   * Returns a QuantityRange representing the range for this value +- that
  //   * @param that Quantity
  //   * @return QuantityRange
  //   */
  //  def plusOrMinus(that: AN): QuantityRange[AN, N] = QuantityRange[AN, N](this - that, this + that)
  //  def +-(that: AN) = plusOrMinus(that)
  //
  //  /**
  //   * Returns a QuantityRange that goes from this to that
  //   * @param that Quantity
  //   * @return QuantityRange
  //   */
  //  def to(that: AN): QuantityRange[AN, N] = QuantityRange(this / sqNum.one, that)
  //
  //  /**
  //   * Returns true if this value is within (contains) the range
  //   * @param range QuantityRange
  //   * @return Boolean
  //   */
  //  def within(range: QuantityRange[AN, N]) = range.contains(self)
  //
  //  /**
  //   * Returns true if this value is not within (contains) the range
  //   * @param range QuantityRange
  //   * @return Boolean
  //   */
  //  def notWithin(range: QuantityRange[AN, N]) = !range.contains(self)

  /**
   * Returns a N representing the quantity in terms of the supplied unit
   * {{{
   *   val d = Feet(3)
   *   (d to Inches) should be(36)
   * }}}
   * @param uom UnitOfMeasure[A]
   * @return N
   */
  def to(uom: UnitOfMeasure[A]): N = uom match {
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
  def toString(uom: UnitOfMeasure[A]): String = s"${crossFormat(sqNum.toDouble(to(uom)))} ${uom.symbol}"

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
  def toTuple: (N, String) = (value, unit.symbol)

  /**
   * Returns a pair representing the numeric value and the uom's symbol
   * @param uom UnitOfMeasure[A]
   * @return
   */
  def toTuple(uom: UnitOfMeasure[A]): (N, String) = (to(uom), uom.symbol)

  /**
   * Applies a function to the underlying value of the Quantity, returning a new Quantity in the same unit
   * @param f N => N function
   * @return
   */
  def map(f: N ⇒ N) = unit(f(value))

}
