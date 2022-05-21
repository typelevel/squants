package squants2

import scala.annotation.tailrec
import scala.language.implicitConversions
import scala.math.BigDecimal.RoundingMode
import scala.math.BigDecimal.RoundingMode.RoundingMode

trait QNumeric[A] {
  def zero: A
  def one: A
  def plus[B](a: A, b: B)(implicit f: B => A): A
  def minus[B](a: A, b: B)(implicit f: B => A): A
  def times[B](a: A, b: B)(implicit f: B => A): A
  def divide[B](a: A, b: B)(implicit f: B => A): A
  def mod[B](a: A, b: B)(implicit f: B => A): A
  def negate(a: A): A
  def abs(a: A): A
  def ceil(a: A): A
  def floor(a: A): A
  def rint(a: A): A
  def sqrt(a: A): A
  def sin(a: A): A
  def cos(a: A): A
  def tan(a: A): A
  def asin(a: A): A
  def acos(a: A): A
  def atan(a: A): A
  def rounded(a: A, scale: Int, mode: RoundingMode = RoundingMode.HALF_EVEN): A

  def compare[B](a: A, b: B)(implicit f: B => A): Int
  def lt[B](a: A, b: B)(implicit f: B => A): Boolean = compare(a, b) < 0
  def lteq[B](a: A, b: B)(implicit f: B => A): Boolean = compare(a, b) <= 0
  def gt[B](a: A, b: B)(implicit f: B => A): Boolean = compare(a, b) > 0
  def gteq[B](a: A, b: B)(implicit f: B => A): Boolean = compare(a, b) >= 0
  def equiv[B](a: A, b: B)(implicit f: B => A): Boolean = compare(a, b) == 0

  def toInt(a: A): Int
  def toLong(a: A): Long
  def toFloat(a: A): Float
  def toDouble(a: A): Double

  def fromInt(n: Int): A
  def fromDouble(d: Double): A
  def fromString(str: String): Option[A]
}

object QNumeric {

  implicit class QNumericOps[A](a: A)(implicit qNum: QNumeric[A]) {
    def +[B](that: B)(implicit f: B => A): A = qNum.plus(a, that)
    def -[B](that: B)(implicit f: B => A): A = qNum.minus(a, that)
    def *[B](that: B)(implicit f: B => A): A = qNum.times(a, that)
    def /[B](that: B)(implicit f: B => A): A = qNum.divide(a, that)
    def %[B](that: B)(implicit f: B => A): A = qNum.mod(a, that)
    def unary_- : A = qNum.negate(a)
    def toInt: Int = qNum.toInt(a)

    def <[B](that: B)(implicit f: B => A): Boolean = qNum.lt(a, that)
    def <=[B](that: B)(implicit f: B => A): Boolean = qNum.lteq(a, that)
    def >[B](that: B)(implicit f: B => A): Boolean = qNum.gt(a, that)
    def >=[B](that: B)(implicit f: B => A): Boolean = qNum.gteq(a, that)
    def equiv[B](that: B)(implicit f: B => A): Boolean = qNum.equiv(a, that)


//    override def compare(that: A): Int = qNum.compare(a, that)

    def *[B](that: Quantity[B, _])(implicit f: B => A): that.Q[A] = that.asNum[A] * a
    // TODO: this*SVector; this*Price; this/Time; this per Time /
  }

  /**
   * QNumeric based on a Numeric
   *
   * @param num Numeric
   * @tparam A the Numeric type
   */
  class NumericIsQNumeric[A](implicit num: Numeric[A]) extends QNumeric[A] {
    override lazy val zero: A = num.zero
    override lazy val one: A = num.one
    override def plus[B](a: A, b: B)(implicit f: B => A): A = num.plus(a, f(b))
    override def minus[B](a: A, b: B)(implicit f: B => A): A = num.minus(a, f(b))
    override def times[B](a: A, b: B)(implicit f: B => A): A = num.times(a, f(b))

    override def divide[B](a: A, b: B)(implicit f: B => A): A = num match {
      case fnum: Fractional[A] => fnum.div(a, f(b))
      case inum: Integral[A] => inum.quot(a, f(b))
    }

    override def mod[B](a: A, b: B)(implicit f: B => A): A = num match {
      case _: Fractional[A] =>
        @tailrec
        def reduce(x: A, y: A): A = { // TODO: validate this
          if(num.equiv(x, y)) num.zero
          else if(num.lt(x, y)) y
          else reduce(x, num.minus(x, y))
        }
        reduce(a, f(b))
      case inum: Integral[A] => inum.rem(a, f(b))
    }

    override def negate(a: A): A = num.negate(a)
    override def abs(a: A): A = num.abs(a)

    override def compare[B](a: A, b: B)(implicit f: B => A): Int = num.compare(a, f(b))

    override def toInt(a: A): Int = num.toInt(a)
    override def toLong(a: A): Long = num.toLong(a)
    override def toFloat(a: A): Float = num.toFloat(a)
    override def toDouble(a: A): Double = num.toDouble(a)

    override def fromInt(n: Int): A = num.fromInt(n)
    override def fromString(str: String): Option[A] = num.parseString(str)

    // TODO: Not the best solution for these, but can be overridden by specific QNumeric implementations
    override def fromDouble(d: Double): A = fromString(d.toString).get
    override def ceil(a: A): A = fromDouble(math.ceil(toDouble(a)))
    override def floor(a: A): A = fromDouble(math.floor(toDouble(a)))
    override def rint(a: A): A = fromDouble(math.rint(toDouble(a)))
    override def sqrt(a: A): A = fromDouble(math.sqrt(toDouble(a)))
    override def sin(a: A): A = fromDouble(math.sin(toDouble(a)))
    override def cos(a: A): A = fromDouble(math.cos(toDouble(a)))
    override def tan(a: A): A = fromDouble(math.tan(toDouble(a)))
    override def asin(a: A): A = fromDouble(math.asin(toDouble(a)))
    override def acos(a: A): A = fromDouble(math.acos(toDouble(a)))
    override def atan(a: A): A = fromDouble(math.atan(toDouble(a)))
    override def rounded(a: A, scale: Int, mode: RoundingMode): A = num match {
      case _: Fractional[A]          => fromDouble(BigDecimal(toDouble(a)).setScale(scale, mode).toDouble)
      case _: Integral[A]            => a
    }
  }

  /**
   * Implicit conversion from any in scope Numeric to a QNumeric
   *
   * @tparam A Numeric type
   * @return
   */
  implicit def numericToQNumeric[A: Numeric]: NumericIsQNumeric[A] = new NumericIsQNumeric[A]

  /**
   * Explicit QNumeric for BigDecimal
   */
  implicit object QBigDecimal extends NumericIsQNumeric[BigDecimal]{
    override def rounded(a: BigDecimal, scale: Int, mode: RoundingMode): BigDecimal = a.setScale(scale, mode)
  }

}
