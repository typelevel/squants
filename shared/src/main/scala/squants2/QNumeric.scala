package squants2

import scala.language.implicitConversions
import scala.math.BigDecimal.RoundingMode
import scala.math.BigDecimal.RoundingMode.RoundingMode
import scala.util.Try

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
  def fromDouble(a: Double): A
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

    def *[B](that: Quantity[B, _])(implicit f: B => A): Quantity[A, _] = that.asNum[A] * a
    // TODO: this*SVector; this*Price; this/Time; this per Time /
  }

  implicit object QDouble extends QNumeric[Double] {
    lazy val zero: Double = 0d
    lazy val one: Double = 1d
    override def plus[B](a: Double, b: B)(implicit f: B => Double): Double = a + f(b)
    override def minus[B](a: Double, b: B)(implicit f: B => Double): Double = a - f(b)
    override def times[B](a: Double, b: B)(implicit f: B => Double): Double = a * f(b)
    override def divide[B](a: Double, b: B)(implicit f: B => Double): Double = a / f(b)
    override def mod[B](a: Double, b: B)(implicit f: B => Double): Double = a % f(b)
    override def negate(a: Double): Double = -a
    override def abs(a: Double): Double = math.abs(a)
    override def ceil(a: Double): Double = math.ceil(a)
    override def floor(a: Double): Double = math.floor(a)
    override def rint(a: Double): Double = math.rint(a)
    override def rounded(a: Double, scale: Int, mode: RoundingMode): Double = BigDecimal(a).setScale(scale, mode).toDouble
    override def toInt(a: Double): Int = a.toInt
    override def toLong(a: Double): Long = a.toLong
    override def toFloat(a: Double): Float = a.toFloat
    override def toDouble(a: Double): Double = a
    override def fromDouble(a: Double): Double = a
    override def fromString(str: String): Option[Double] = Try(BigDecimal(str).toDouble).toOption
    override def compare[B](a: Double, b: B)(implicit f: B => Double): Int = a.compareTo(f(b))
  }

  implicit object QBigDecimal extends QNumeric[BigDecimal] {
    lazy val zero: BigDecimal = BigDecimal(0)
    lazy val one: BigDecimal = BigDecimal(1)
    override def plus[B](a: BigDecimal, b: B)(implicit f: B => BigDecimal): BigDecimal = a + f(b)
    override def minus[B](a: BigDecimal, b: B)(implicit f: B => BigDecimal): BigDecimal = a - f(b)
    override def times[B](a: BigDecimal, b: B)(implicit f: B => BigDecimal): BigDecimal = a * f(b)
    override def divide[B](a: BigDecimal, b: B)(implicit f: B => BigDecimal): BigDecimal = a / f(b)
    override def mod[B](a: BigDecimal, b: B)(implicit f: B => BigDecimal): BigDecimal = a % f(b)
    override def negate(a: BigDecimal): BigDecimal = -a
    override def abs(a: BigDecimal): BigDecimal = a.abs
    override def ceil(a: BigDecimal): BigDecimal = rounded(a, 0, RoundingMode.UP)
    override def floor(a: BigDecimal): BigDecimal = rounded(a, 0, RoundingMode.DOWN)
    override def rint(a: BigDecimal): BigDecimal = rounded(a, 0, RoundingMode.HALF_EVEN)
    override def rounded(a: BigDecimal, scale: Int, mode: RoundingMode): BigDecimal = a.setScale(scale, mode)
    override def toInt(a: BigDecimal): Int = a.toInt
    override def toLong(a: BigDecimal): Long = a.toLong
    override def toFloat(a: BigDecimal): Float = a.toFloat
    override def toDouble(a: BigDecimal): Double = a.toDouble
    override def fromDouble(a: Double): BigDecimal = BigDecimal(a)
    override def fromString(str: String): Option[BigDecimal] = Try(BigDecimal(str)).toOption
    override def compare[B](a: BigDecimal, b: B)(implicit f: B => BigDecimal): Int = a.compareTo(f(b))
  }

  implicit object QInt extends QNumeric[Int] {
    lazy val zero: Int = 0
    lazy val one: Int = 1
    override def plus[B](a: Int, b: B)(implicit f: B => Int): Int = a + f(b)
    override def minus[B](a: Int, b: B)(implicit f: B => Int): Int = a - f(b)
    override def times[B](a: Int, b: B)(implicit f: B => Int): Int = a * f(b)
    override def divide[B](a: Int, b: B)(implicit f: B => Int): Int = a / f(b)
    override def mod[B](a: Int, b: B)(implicit f: B => Int): Int = a % f(b)
    override def negate(a: Int): Int = -a
    override def abs(a: Int): Int = math.abs(a)
    override def ceil(a: Int): Int = a
    override def floor(a: Int): Int = a
    override def rint(a: Int): Int = a
    override def rounded(a: Int, scale: Int, mode: RoundingMode): Int = a
    override def toInt(a: Int): Int = a
    override def toLong(a: Int): Long = a.toLong
    override def toFloat(a: Int): Float = a.toFloat
    override def toDouble(a: Int): Double = a.toDouble
    override def fromDouble(a: Double): Int = a.toInt
    override def fromString(str: String): Option[Int] = Try(BigDecimal(str).toInt).toOption
    override def compare[B](a: Int, b: B)(implicit f: B => Int): Int = a.compareTo(f(b))
  }

  implicit object QLong extends QNumeric[Long] {
    lazy val zero: Long = 0L
    lazy val one: Long = 1L
    override def plus[B](a: Long, b: B)(implicit f: B => Long): Long = a + f(b)
    override def minus[B](a: Long, b: B)(implicit f: B => Long): Long = a - f(b)
    override def times[B](a: Long, b: B)(implicit f: B => Long): Long = a * f(b)
    override def divide[B](a: Long, b: B)(implicit f: B => Long): Long = a / f(b)
    override def mod[B](a: Long, b: B)(implicit f: B => Long): Long = a % f(b)
    override def negate(a: Long): Long = -a
    override def abs(a: Long): Long = math.abs(a)
    override def ceil(a: Long): Long = a
    override def floor(a: Long): Long = a
    override def rint(a: Long): Long = a
    override def rounded(a: Long, scale: Int, mode: RoundingMode): Long = a
    override def toInt(a: Long): Int = a.toInt
    override def toLong(a: Long): Long = a
    override def toFloat(a: Long): Float = a.toFloat
    override def toDouble(a: Long): Double = a.toDouble
    override def fromDouble(a: Double): Long = a.toLong
    override def fromString(str: String): Option[Long] = Try(BigDecimal(str).toLong).toOption
    override def compare[B](a: Long, b: B)(implicit f: B => Long): Int = a.compareTo(f(b))
  }

  implicit object QFloat extends QNumeric[Float] {
    lazy val zero: Float = 0f
    lazy val one: Float = 1f
    override def plus[B](a: Float, b: B)(implicit f: B => Float): Float = a + f(b)
    override def minus[B](a: Float, b: B)(implicit f: B => Float): Float = a - f(b)
    override def times[B](a: Float, b: B)(implicit f: B => Float): Float = a * f(b)
    override def divide[B](a: Float, b: B)(implicit f: B => Float): Float = a / f(b)
    override def mod[B](a: Float, b: B)(implicit f: B => Float): Float = a % f(b)
    override def negate(a: Float): Float = -a
    override def abs(a: Float): Float = math.abs(a)
    override def ceil(a: Float): Float = math.ceil(a).toFloat
    override def floor(a: Float): Float = math.floor(a).toFloat
    override def rint(a: Float): Float = math.rint(a).toFloat
    override def rounded(a: Float, scale: Int, mode: RoundingMode): Float = BigDecimal(a).setScale(scale, mode).toFloat
    override def toInt(a: Float): Int = a.toInt
    override def toLong(a: Float): Long = a.toLong
    override def toFloat(a: Float): Float = a
    override def toDouble(a: Float): Double = a.toDouble
    override def fromDouble(a: Double): Float = a.toFloat
    override def fromString(str: String): Option[Float] = Try(BigDecimal(str).toFloat).toOption
    override def compare[B](a: Float, b: B)(implicit f: B => Float): Int = a.compareTo(f(b))
  }

}
