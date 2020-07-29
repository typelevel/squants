/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.experimental

import scala.language.implicitConversions

trait SquantsNumeric[T] extends Ordering[T] {

  def plus(a: T, b: T): T
  def minus(a: T, b: T): T
  def times(a: T, b: T): T
  def divide(a: T, b: T): T
  def remainder(a: T, b: T): T
  def divideAndRemainder(a: T, b: T): (T, T)
  def negate(a: T): T
  def abs(a: T): T

  def zero: T
  def one: T

  def fromDouble(a: Double): T
  def toInt(a: T): Int
  def toLong(a: T): Long
  def toFloat(a: T): Float
  def toDouble(a: T): Double

  class Ops(lhs: T) {
    def +(rhs: T) = plus(lhs, rhs)
    def -(rhs: T) = minus(lhs, rhs)
    def *(rhs: T) = times(lhs, rhs)
    def /(rhs: T) = divide(lhs, rhs)
    def %(rhs: T) = remainder(lhs, rhs)
    def /%(rhs: T) = divideAndRemainder(lhs, rhs)
    def unary_-() = negate(lhs)
    def >(rhs: T) = gt(lhs, rhs)
    def >=(rhs: T) = gteq(lhs, rhs)
    def <(rhs: T) = lt(lhs, rhs)
    def <=(rhs: T) = lteq(lhs, rhs)
  }
  implicit def mkSquantsNumericOps(lhs: T): Ops = new Ops(lhs)
}

object SquantsNumeric extends ScalaVersionSpecificNumericInstances {

  trait IntIsSquantsNumeric extends SquantsNumeric[Int] {
    def plus(x: Int, y: Int): Int = x + y
    def minus(x: Int, y: Int): Int = x - y
    def times(x: Int, y: Int): Int = x * y
    def divide(x: Int, y: Int): Int = x / y
    def remainder(x: Int, y: Int): Int = x % y
    def divideAndRemainder(x: Int, y: Int) = x /% y
    def negate(x: Int): Int = -x
    def abs(x: Int): Int = math.abs(x)
    def zero = 0
    def one = 1
    def fromDouble(x: Double): Int = x.toInt
    def toInt(x: Int): Int = x
    def toLong(x: Int): Long = x.toLong
    def toFloat(x: Int): Float = x.toFloat
    def toDouble(x: Int): Double = x.toDouble
  }

  trait LongIsSquantsNumeric extends SquantsNumeric[Long] {
    def plus(x: Long, y: Long): Long = x + y
    def minus(x: Long, y: Long): Long = x - y
    def times(x: Long, y: Long): Long = x * y
    def divide(x: Long, y: Long): Long = x / y
    def remainder(x: Long, y: Long): Long = x % y
    def divideAndRemainder(x: Long, y: Long) = x /% y
    def negate(x: Long): Long = -x
    def abs(x: Long): Long = math.abs(x)
    def zero = 0L
    def one = 1L
    def fromDouble(x: Double): Long = x.toLong
    def toInt(x: Long): Int = x.toInt
    def toLong(x: Long): Long = x
    def toFloat(x: Long): Float = x.toFloat
    def toDouble(x: Long): Double = x.toDouble
  }

  trait FloatIsSquantsNumeric extends SquantsNumeric[Float] {
    def plus(x: Float, y: Float): Float = x + y
    def minus(x: Float, y: Float): Float = x - y
    def times(x: Float, y: Float): Float = x * y
    def divide(x: Float, y: Float): Float = x / y
    def remainder(x: Float, y: Float): Float = x % y
    def divideAndRemainder(x: Float, y: Float) = x /% y
    def negate(x: Float): Float = -x
    def abs(x: Float): Float = math.abs(x)
    def zero = 0F
    def one = 1F
    def toDouble(x: Float): Double = x.toDouble
    def toInt(x: Float): Int = x.toInt
    def toLong(x: Float): Long = x.toLong
    def toFloat(x: Float): Float = x
    def fromDouble(x: Double): Float = x.toFloat
  }

  trait DoubleIsSquantsNumeric extends SquantsNumeric[Double] {
    def plus(x: Double, y: Double): Double = x + y
    def minus(x: Double, y: Double): Double = x - y
    def times(x: Double, y: Double): Double = x * y
    def divide(x: Double, y: Double): Double = x / y
    def remainder(x: Double, y: Double): Double = x % y
    def divideAndRemainder(x: Double, y: Double) = x /% y
    def negate(x: Double): Double = -x
    def abs(x: Double): Double = math.abs(x)
    def zero = 0D
    def one = 1D
    def fromDouble(x: Double): Double = x
    def toInt(x: Double): Int = x.toInt
    def toLong(x: Double): Long = x.toLong
    def toFloat(x: Double): Float = x.toFloat
    def toDouble(x: Double): Double = x
  }

  trait BigDecimalIsSquantsNumeric extends SquantsNumeric[BigDecimal] {
    def plus(x: BigDecimal, y: BigDecimal): BigDecimal = x + y
    def minus(x: BigDecimal, y: BigDecimal): BigDecimal = x - y
    def times(x: BigDecimal, y: BigDecimal): BigDecimal = x * y
    def divide(x: BigDecimal, y: BigDecimal): BigDecimal = x / y
    def remainder(x: BigDecimal, y: BigDecimal): BigDecimal = x % y
    def divideAndRemainder(x: BigDecimal, y: BigDecimal): (BigDecimal, BigDecimal) = x /% y
    def negate(x: BigDecimal): BigDecimal = -x
    def abs(x: BigDecimal): BigDecimal = x.abs
    def zero = BigDecimal(0)
    def one = BigDecimal(1)
    def fromDouble(x: Double): BigDecimal = BigDecimal(x)
    def toInt(x: BigDecimal): Int = x.toInt
    def toLong(x: BigDecimal): Long = x.toLong
    def toFloat(x: BigDecimal): Float = x.toFloat
    def toDouble(x: BigDecimal): Double = x.toDouble
  }

  implicit object IntIsSquantsNumeric extends IntIsSquantsNumeric with Ordering.IntOrdering
  implicit object LongIsSquantsNumeric extends LongIsSquantsNumeric with Ordering.LongOrdering
  implicit object BigDecimalIsSquantsNumeric extends BigDecimalIsSquantsNumeric with Ordering.BigDecimalOrdering
}
