package squants2

import scala.annotation.tailrec
import scala.math.Numeric.Implicits.infixNumericOps
import scala.math.Ordering.Implicits.infixOrderingOps

/**
 * Represents a Range starting at one Quantity value and going up to another
 *
 * @author  garyKeorkunian
 * @since   0.1
 * @param lower Quantity representing the lower bound of the range
 * @param upper Quantity representing the upper bound of the range
 * @tparam A the Quantity Type
 */
case class QuantityRange[A, D <: Dimension](lower: Quantity[A, D], upper: Quantity[A, D])(implicit num: Numeric[A], c: Converter[A]) {
  if (lower >= upper) {
    throw new IllegalArgumentException("QuantityRange upper bound must be strictly greater than to the lower bound")
  }

  /**
   * Create a Seq of `multiple` ranges equal in size to the original with sequential range values
   * If `multiple` contains a fractional component, the last item in the resulting range will be equal to that
   * fraction of the original
   *
   * @param multiple Number of ranges to create
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def times[B](multiple: B)(implicit f: B => A): QuantitySeries[A, D] = {
    val multipleA = f(multiple)
    val remainder = multipleA % num.one
    val count = (multipleA - remainder) / num.one
    val ranges = (0 until count.toInt)
      .map(n => QuantityRange(lower + (toQuantity * num.fromInt(n)), upper + (toQuantity * num.fromInt(n))))
    if (remainder > num.zero)
      ranges :+ QuantityRange(lower + (toQuantity * count), lower + (toQuantity * num.plus(count, remainder)))
    else ranges
  }
  /** times */
  def *[B](multiple: B)(implicit f: B => A): QuantitySeries[A, D] = times(multiple)

  /**
   * Divides the range into a Series of ranges each with a range of size `that`
   * The Seq will begin at `from` and go till `to`.  If the range is not evenly divisible
   * by `that`, the last item in the list will contain the remainder
   *
   * QuantityRange(Each(1), Each(4)) / Each(1) =>
   *   Vector(QuantityRange(1.0 ea,2.0 ea), QuantityRange(2.0 ea,3.0 ea), QuantityRange(3.0 ea,4.0 ea))
   *
   * @param that Quantity
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def divide[B](that: Quantity[B, D])(implicit f: B => A): QuantitySeries[A, D] = {
    val thatA = that.asNum[A]
    @tailrec
    def accumulate(acc: QuantitySeries[A, D], start: Quantity[A, D]): QuantitySeries[A, D] = {
      if (start >= upper) acc
      else accumulate(acc :+ (start to (start + thatA).min(upper)), start + thatA)
    }
    accumulate(IndexedSeq.empty.asInstanceOf[QuantitySeries[A, D]], lower)
  }
  /** divide */
  def /[B](that: Quantity[B, D])(implicit f: B => A): QuantitySeries[A, D] = divide(that)

  /**
   * Divides the range into a Seq of `divisor` ranges
   * The Seq will begin at `from` and go till `to`.
   * If `that` is an integer value, the range will evenly divided at all points.
   * If `that` has a fractional component, the first n-1 ranges will be evenly divided by the `that`
   * and the last range in the list will contain the remainder.
   *
   * QuantityRange(Each(1), Each(4)) / 3 =>
   *   Vector(QuantityRange(1.0 ea,2.0 ea), QuantityRange(2.0 ea,3.0 ea), QuantityRange(3.0 ea,4.0 ea))
   *
   * @param that Double
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def divide[B](that: B)(implicit f: B => A): QuantitySeries[A, D] = divide(this.toQuantity / that)
  /** divide */
  def /[B](that: B)(implicit f: B => A): QuantitySeries[A, D] = divide(that)

  /**
   * Divides the range into a Seq of ranges of `size` each and applies a f to each element
   *
   * @param size Quantity representing the size for each QuantityRange in the Seq
   * @param op the side affecting operation
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def foreach[B, U](size: Quantity[B, D])(op: QuantityRange[A, D] => U)(implicit f: B => A): Unit = /(size).foreach(op)

  /**
   * Divides the range into a Seq of `divisor` ranges and applies a f to each element
   *
   * @param divisor Quantity representing the size for each QuantityRange in the Seq
   * @param op the side affecting operation
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def foreach[B, U](divisor: B)(op: QuantityRange[A, D] => U)(implicit f: B => A): Unit = /(divisor).foreach(op)

  /**
   * Divides the range into a Seq of ranges of `size` each and applies a map operation to each
   *
   * @param size Quantity representing the size for each QuantityRange in the Seq
   * @param op the transformation operation
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @tparam C the result type of the map operation
   * @return
   */
  def map[B, C](size: Quantity[B, D])(op: QuantityRange[A, D] => C)(implicit f: B => A): Seq[C] = /(size).map(op)

  /**
   * Divides the range into a Seq of `divisor` ranges and applies a map operation to each
   *
   * @param divisor Quantity representing the size for each QuantityRange in the Seq
   * @param op the transformation operation
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @tparam C the result type of the map operation
   * @return
   */
  def map[B, C](divisor: B)(op: QuantityRange[A, D] => C)(implicit f: B => A): Seq[C] = map(toQuantity / divisor)(op)

  /**
   * Divides the range into a Seq of ranges of `size` each and applies a foldLeft operation
   *
   * @param size Quantity representing the size for each QuantityRange in the Seq
   * @param z the start value
   * @param op the binary operator
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @tparam C the result type of the binary operator
   * @return
   */
  def foldLeft[B, C](size: Quantity[B, D], z: C)(op: (C, QuantityRange[A, D]) => C)(implicit f: B => A): C = /(size).foldLeft[C](z)(op)
  /** foldLeft */
  def /:[B](size: Quantity[B, D], z: B)(op: (B, QuantityRange[A, D]) => B)(implicit f: B => A): B = foldLeft(size, z)(op)

  /**
   * Divides the range into a Seq of ranges of `size` each and applies a foldLeft operation
   *
   * @param divisor The number of ranges to split the range into
   * @param z the start value
   * @param op the binary operator
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @tparam C the result type of the binary operator
   * @return
   */
  def foldLeft[B, C](divisor: B, z: C)(op: (C, QuantityRange[A, D]) => C)(implicit f: B => A): C = /(divisor).foldLeft[C](z)(op)
  /** foldLeft */
  def /:[B, C](divisor: B, z: C)(op: (C, QuantityRange[A, D]) => C)(implicit f: B => A): C = foldLeft(divisor, z)(op)

  /**
   * Divides the range into a Seq of ranges of `size` each and applies a foldRight operation
   *
   * @param size Quantity representing the size for each QuantityRange in the Seq
   * @param z the start value
   * @param op the binary operator
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @tparam C the result type of the binary operator
   * @return
   */
  def foldRight[B, C](size: Quantity[B, D], z: C)(op: (QuantityRange[A, D], C) => C)(implicit f: B => A): C = /(size).foldRight[C](z)(op)
  /** foldRight */
  def :\[B, C](size: Quantity[B, D], z: C)(op: (QuantityRange[A, D], C) => C)(implicit f: B => A): C = foldRight(size, z)(op)

  /**
   * Divides the range into a Seq of ranges of `size` each and applies a foldRight operation
   *
   * @param divisor The number of ranges to split the range into
   * @param z the start value
   * @param op the binary operator
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @tparam C the result type of the binary operator
   * @return
   */
  def foldRight[B, C](divisor: B, z: C)(op: (QuantityRange[A, D], C) => C)(implicit f: B => A): C = /(divisor).foldRight[C](z)(op)
  /** foldRight */
  def :\[B, C](divisor: B, z: C)(op: (QuantityRange[A, D], C) => C)(implicit f: B => A): C = foldRight(divisor, z)(op)

  /**
   * Increments the range's from and to values by an amount equal to the Quantity value of the range
   * @return
   */
  lazy val inc: QuantityRange[A, D] = QuantityRange(lower + toQuantity, upper + toQuantity)
  /** inc */
  def ++(): QuantityRange[A, D] = inc

  /**
   * Increments the range's from and to values by an amount equal to the value of `that`
   * @param that Quantity
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def inc[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] =
    QuantityRange(this.lower + that, this.upper + that)
  /** int */
  def ++[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] = inc(that)

  /**
   * Decrements the range's from and to value by an amount equal to the Quantity value of the range
   * @return
   */
  lazy val dec: QuantityRange[A, D] = QuantityRange(lower - toQuantity, upper - toQuantity)
  /** dec */
  def --(): QuantityRange[A, D] = dec

  /**
   * Decrements the range's from and to values by an amount equal to the value of `that`
   * @param that Quantity
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def dec[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] =
    QuantityRange(this.lower - that, this.upper - that)
  /** dec */
  def --[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] = dec(that)

  /**
   * Increments the `to` value by an amount equal to the value of `that`
   * @param that Quantity
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def incTo[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] =
    QuantityRange(this.lower, this.upper + that)
  /** incTo */
  def =+[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] = incTo(that)

  def decTo[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] =
    QuantityRange(this.lower, this.upper - that)
  /** decTo */
  def =-[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] = decTo(that)

  /**
   * Increments the `from` value by an amount equal to the value of `that`
   * @param that Quantity
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def incFrom[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] =
    QuantityRange(this.lower + that, this.upper)
  /** incFrom */
  def +=[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] = incFrom(that)

  /**
   * Decrements the `from` value by an amount equal to the value of `that`
   * @param that Quantity
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def decFrom[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] =
    QuantityRange(this.lower - that, this.upper)
  /** decFrom */
  def -=[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] = decFrom(that)

  /**
   * Decrements the `from` value and increments the `to` by an amount equal to the value of `that`
   * @param that Quantity
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def decFromIncTo[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] =
    QuantityRange(this.lower - that, this.upper + that)
  /** decFromIncTo */
  def -+[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] = decFromIncTo(that)

  /**
   * Increments the `from` value and decrements the `to` by an amount equal to the value of `that`
   * @param that Quantity
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def incFromDecTo[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] =
    QuantityRange(this.lower + that, this.upper - that)
  /** incFromDecTo */
  def +-[B](that: Quantity[B, D])(implicit f: B => A): QuantityRange[A, D] = incFromDecTo(that)

  /**
   * Returns true if the quantity is contained within this range, otherwise false.
   * This check is *exclusive* of the range's upper limit.
   * @param q Quantity
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def contains[B](q: Quantity[B, D])(implicit f: B => A): Boolean =
    (q.asNum[A] >= lower) && (q.asNum[A] < upper)

  /**
   * Return true if `that` range is completely contained with `this` range, otherwise false
   * @param that Quantity
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def contains[B](that: QuantityRange[B, D])(implicit f: B => A): Boolean = {
    val thatA = that.asNum[A]
    thatA.lower >= this.lower &&
      thatA.lower < this.upper &&
      thatA.upper >= this.lower &&
      thatA.upper < this.upper
  }

  /**
   * Returns true if `that` range contains any part that is in `this` range, otherwise false
   * @param range QuantityRange[A, D]
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def partiallyContains[B](range: QuantityRange[B, D])(implicit f: B => A): Boolean =
    range.lower.asNum[A] < upper && range.upper.asNum[A] > lower

  /**
   * Returns true if `that` quantity is included within `this` range.
   * This check is *inclusive* of the range's upper limit.
   * @param q Quantity
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def includes[B](q: Quantity[B, D])(implicit f: B => A): Boolean = {
    val qA = q.asNum[A]
    qA >= lower && qA <= upper
  }

  /**
   * Returns true if `that` range is completely included in `this` range, otherwise false
   * @param that QuantityRange[A, D]
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def includes[B](that: QuantityRange[B, D])(implicit f: B => A): Boolean = {
    val thatA = that.asNum[A]
    thatA.lower >= this.lower &&
      thatA.lower <= this.upper &&
      thatA.upper >= this.lower &&
      thatA.upper <= this.upper
  }

  /**
   * Returns true if `that` range includes any part that is in `this` range, otherwise false
   * @param range QuantityRange[A, D]
   * @param f an implicit transformation of B to A
   * @tparam B the numeric type of the parameter
   * @return
   */
  def partiallyIncludes[B](range: QuantityRange[B, D])(implicit f: B => A): Boolean =
    range.asNum[A].lower <= upper && range.asNum[A].upper >= lower

  /**
   * Creates a like QuantityRange using the B as the Numeric type
   * @param f A function to transform A to B
   * @tparam B The Numeric type for new QuantityRange
   * @return
   */
  def asNum[B: Numeric : Converter](implicit f: A => B): QuantityRange[B, D] = QuantityRange(lower.asNum[B], upper.asNum[B])

  /**
   * Returns a quantity that is equal to the difference between the `from` and `to`
   * @return
   */
  lazy val toQuantity: upper.type = upper - lower

  /**
   * Returns this Range's boundary values as a Seq[A] of the two
   * @return
   */
  lazy val toSeq: Seq[Quantity[A, D]] = Seq(lower, upper)

  /**
   * Return this Range's boundary values as List[A] or the two
   * @return
   */
  lazy val toList: List[Quantity[A, D]] = List(lower, upper)
}
