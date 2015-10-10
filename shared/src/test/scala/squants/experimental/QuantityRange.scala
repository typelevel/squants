///*                                                                      *\
//** Squants                                                              **
//**                                                                      **
//** Scala Quantities and Units of Measure Library and DSL                **
//** (c) 2013-2015, Gary Keorkunian                                       **
//**                                                                      **
//\*                                                                      */
//
//package squants.experimental
//
//import scala.annotation.tailrec
//
///**
// * Represents a Range starting at one Quantity value and going up to another
// *
// * @author  garyKeorkunian
// * @since   0.1
// *
// * @param lower Quantity representing the lower bound of the range
// * @param upper Quantity representing the upper bound of the range
// * @tparam A the Quantity Type
// */
//case class QuantityRange[A <: Quantity[A, _], N](lower: A, upper: A)(implicit sqNum: SquantsNumeric[N]) {
//  if (lower >= upper) throw new IllegalArgumentException("QuantityRange upper bound must be greater than or equal to the lower bound")
//
//  import sqNum.mkSquantsNumericOps
//
//  /**
//   * Create a Seq of `multiple` ranges equal in size to the original with sequential range values
//   * If `multiple` contains a fractional component, the last item in the resulting range will be equal to that
//   * fraction of the original
//   *
//   * @param multiple Number of ranges to create
//   * @return
//   */
//  def times(multiple: N): QuantitySeries[A] = {
//    val remainder = multiple % sqNum.one
//    val count = ((multiple - remainder) / sqNum.one).toInt
//    val ranges = (0 until count).map(n ⇒ QuantityRange(lower + (toQuantity * n), upper + (toQuantity * n)))
//    if (remainder > sqNum.zero) ranges :+ QuantityRange(lower + (toQuantity * count), lower + (toQuantity * (count + remainder)))
//    else ranges
//  }
//  /** times */
//  def *(multiple: N) = times(multiple)
//
//  /**
//   * Divides the range into a Series of ranges each with a range of size `that`
//   * The Seq will begin at `from` and go till `to`.  If the range is not evenly divisible
//   * by `that`, the last item in the list will contain the remainder
//   *
//   * QuantityRange(Each(1), Each(4)) / Each(1) =>
//   *   Vector(QuantityRange(1.0 ea,2.0 ea), QuantityRange(2.0 ea,3.0 ea), QuantityRange(3.0 ea,4.0 ea))
//   *
//   * @param that Quantity
//   * @return
//   */
//  def divide(that: A): QuantitySeries[A] = {
//    @tailrec
//    def accumulate(acc: QuantitySeries[A], start: A): QuantitySeries[A] = {
//      if (start >= upper) acc
//      else accumulate(acc :+ (start to (start + that).min(upper)), start + that)
//    }
//    accumulate(IndexedSeq.empty.asInstanceOf[QuantitySeries[A]], lower)
//  }
//  /** divide */
//  def /(that: A) = divide(that)
//
//  /**
//   * Divides the range into a Seq of `divisor` ranges
//   * The Seq will begin at `from` and go till `to`.
//   * If `that` is an integer value, the range will evenly divided at all points.
//   * If `that` has a fractional component, the first n-1 ranges will be evenly divided by the `that`
//   * and the last range in the list will contain the remainder.
//   *
//   * QuantityRange(Each(1), Each(4)) / 3 =>
//   *   Vector(QuantityRange(1.0 ea,2.0 ea), QuantityRange(2.0 ea,3.0 ea), QuantityRange(3.0 ea,4.0 ea))
//   *
//   * @param that Double
//   * @return
//   */
//  def divide(that: N): QuantitySeries[A] = divide(this.toQuantity / that)
//  /** divide */
//  def /(divisor: N) = divide(divisor)
//
//  /**
//   * Divides the range into a Seq of ranges of `size` each and applies a f to each element
//   *
//   * @param size Quantity representing the size for each QuantityRange in the Seq
//   * @param op the side affecting operation
//   * @return
//   */
//  def foreach[U](size: A)(op: QuantityRange[A, N] ⇒ U) = /(size).foreach(op)
//
//  /**
//   * Divides the range into a Seq of `divisor` ranges and applies a f to each element
//   *
//   * @param divisor Quantity representing the size for each QuantityRange in the Seq
//   * @param op the side affecting operation
//   * @return
//   */
//  def foreach[U](divisor: Double)(op: QuantityRange[A, N] ⇒ U) = /(divisor).foreach(op)
//
//  /**
//   * Divides the range into a Seq of ranges of `size` each and applies a map operation to each
//   *
//   * @param size Quantity representing the size for each QuantityRange in the Seq
//   * @param op the transformation operation
//   * @tparam B the result type of the map operation
//   * @return
//   */
//  def map[B](size: A)(op: QuantityRange[A, N] ⇒ B): Seq[B] = /(size).map(op)
//
//  /**
//   * Divides the range into a Seq of `divisor` ranges and applies a map operation to each
//   *
//   * @param divisor Quantity representing the size for each QuantityRange in the Seq
//   * @param op the transformation operation
//   * @tparam B the result type of the map operation
//   * @return
//   */
//  def map[B](divisor: Double)(op: QuantityRange[A, N] ⇒ B): Seq[B] = map(toQuantity / divisor)(op)
//
//  /**
//   * Divides the range into a Seq of ranges of `size` each and applies a foldLeft operation
//   *
//   * @param size Quantity representing the size for each QuantityRange in the Seq
//   * @param z the start value
//   * @param op the binary operator
//   * @tparam B the result type of the binary operator
//   * @return
//   */
//  def foldLeft[B](size: A, z: B)(op: (B, QuantityRange[A, N]) ⇒ B): B = /(size).foldLeft[B](z)(op)
//  /** foldLeft */
//  def /:[B](size: A, z: B)(op: (B, QuantityRange[A, N]) ⇒ B) = foldLeft(size, z)(op)
//
//  /**
//   * Divides the range into a Seq of ranges of `size` each and applies a foldLeft operation
//   *
//   * @param divisor The number of ranges to split the range into
//   * @param z the start value
//   * @param op the binary operator
//   * @tparam B the result type of the binary operator
//   * @return
//   */
//  def foldLeft[B](divisor: Double, z: B)(op: (B, QuantityRange[A, N]) ⇒ B): B = /(divisor).foldLeft[B](z)(op)
//  /** foldLeft */
//  def /:[B](divisor: Double, z: B)(op: (B, QuantityRange[A, N]) ⇒ B) = foldLeft(divisor, z)(op)
//
//  /**
//   * Divides the range into a Seq of ranges of `size` each and applies a foldRight operation
//   *
//   * @param size Quantity representing the size for each QuantityRange in the Seq
//   * @param z the start value
//   * @param op the binary operator
//   * @tparam B the result type of the binary operator
//   * @return
//   */
//  def foldRight[B](size: A, z: B)(op: (QuantityRange[A, N], B) ⇒ B): B = /(size).foldRight[B](z)(op)
//  /** foldRight */
//  def :\[B](size: A, z: B)(op: (QuantityRange[A, N], B) ⇒ B) = foldRight(size, z)(op)
//
//  /**
//   * Divides the range into a Seq of ranges of `size` each and applies a foldRight operation
//   *
//   * @param divisor The number of ranges to split the range into
//   * @param z the start value
//   * @param op the binary operator
//   * @tparam B the result type of the binary operator
//   * @return
//   */
//  def foldRight[B](divisor: Double, z: B)(op: (QuantityRange[A, N], B) ⇒ B): B = /(divisor).foldRight[B](z)(op)
//  /** foldRight */
//  def :\[B](divisor: Double, z: B)(op: (QuantityRange[A, N], B) ⇒ B) = foldRight(divisor, z)(op)
//
//  /**
//   * Increments the range's from and to values by an amount equal to the Quantity value of the range
//   * @return
//   */
//  lazy val inc = QuantityRange(lower + toQuantity, upper + toQuantity)
//  /** inc */
//  def ++() = inc
//
//  /**
//   * Increments the range's from and to values by an amount equal to the value of `that`
//   * @param that Quantity
//   * @return
//   */
//  def inc(that: A) = QuantityRange(lower + that, upper + that)
//  /** int */
//  def ++(that: A) = inc(that)
//
//  /**
//   * Decrements the range's from and to value by an amount equal to the Quantity value of the range
//   * @return
//   */
//  lazy val dec = QuantityRange(lower - toQuantity, upper - toQuantity)
//  /** dec */
//  def --() = dec
//
//  /**
//   * Decrements the range's from and to values by an amount equal to the value of `that`
//   * @param that Quantity
//   * @return
//   */
//  def dec(that: A) = QuantityRange(lower - that, upper - that)
//  /** dec */
//  def --(that: A) = dec(that)
//
//  /**
//   * Increments the `to` value by an amount equal to the value of `that`
//   * @param that Quantity
//   * @return
//   */
//  def incTo(that: A) = QuantityRange(lower, upper + that)
//  /** incTo */
//  def =+(that: A) = incTo(that)
//
//  def decTo(that: A) = QuantityRange(lower, upper - that)
//  /** decTo */
//  def =-(that: A) = decTo(that)
//
//  /**
//   * Increments the `from` value by an amount equal to the value of `that`
//   * @param that Quantity
//   * @return
//   */
//  def incFrom(that: A) = QuantityRange(lower + that, upper)
//  /** incFrom */
//  def +=(that: A) = incFrom(that)
//
//  /**
//   * Decrements the `from` value by an amount equal to the value of `that`
//   * @param that Quantity
//   * @return
//   */
//  def decFrom(that: A) = QuantityRange(lower - that, upper)
//  /** decFrom */
//  def -=(that: A) = decFrom(that)
//
//  /**
//   * Decrements the `from` value and increments the `to` by an amount equal to the value of `that`
//   * @param that Quantity
//   * @return
//   */
//  def decFromIncTo(that: A) = QuantityRange(lower - that, upper + that)
//  /** decFromIncTo */
//  def -+(that: A) = decFromIncTo(that)
//
//  /**
//   * Increments the `from` value and decrements the `to` by an amount equal to the value of `that`
//   * @param that Quantity
//   * @return
//   */
//  def incFromDecTo(that: A) = QuantityRange(lower + that, upper - that)
//  /** incFromDecTo */
//  def +-(that: A) = incFromDecTo(that)
//
//  /**
//   * Returns true if the quantity is contained within this range, otherwise false
//   * @param q Quantity
//   * @return
//   */
//  def contains(q: A) = q >= lower && q < upper
//
//  /**
//   * Return true if `that` range is completely contained with `this` range, otherwise false
//   * @param that Quantity
//   * @return
//   */
//  def contains(that: QuantityRange[A, N]) =
//    that.lower >= lower &&
//      that.lower < upper &&
//      that.upper >= lower &&
//      that.upper < upper
//
//  /**
//   * Returns true if `that` range contains any part that is in `this` range, otherwise false
//   * @param range QuantityRange[A]
//   * @return
//   */
//  def partiallyContains(range: QuantityRange[A, N]) = range.lower < upper && range.upper > lower
//
//  /**
//   * Returns true if `that` quantity is included within `this` range
//   * @param q Quantity
//   * @return
//   */
//  def includes(q: A) = q >= lower && q <= upper
//
//  /**
//   * Returns true if `that` range is completely included in `this` range, otherwise false
//   * @param that QuantityRange[A]
//   * @return
//   */
//  def includes(that: QuantityRange[A, N]) =
//    that.lower >= lower &&
//      that.lower <= upper &&
//      that.upper >= lower &&
//      that.upper <= upper
//
//  /**
//   * Returns true if `that` range includes any part that is in `this` range, otherwise false
//   * @param range QuantityRange[A]
//   * @return
//   */
//  def partiallyIncludes(range: QuantityRange[A, N]) = range.lower <= upper && range.upper >= lower
//
//  /**
//   * Returns a quantity that is equal to the difference between the `from` and `to`
//   * @return
//   */
//  lazy val toQuantity = upper - lower
//
//  /**
//   * Returns this Range's boundary values as a Seq[A] of the two
//   * @return
//   */
//  lazy val toSeq: Seq[A] = Seq(lower, upper)
//
//  /**
//   * Return this Range's boundary values as List[A] or the two
//   * @return
//   */
//  lazy val toList: List[A] = List(lower, upper)
//}
