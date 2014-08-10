/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.experimental

import scala.language.implicitConversions

trait SquantsNumeric[T] extends Ordering[T] {

  def plus(a: T, b: T): T
  def minus(a: T, b: T): T
  def times(a: T, b: T): T
  def divide(a: T, b: T): T
  def negate(a: T): T

  class Ops(lhs: T) {
    def +(rhs: T) = plus(lhs, rhs)
    def -(rhs: T) = minus(lhs, rhs)
    def *(rhs: T) = times(lhs, rhs)
    def /(rhs: T) = divide(lhs, rhs)
    def unary_-() = negate(lhs)
  }
  implicit def mkSquantsNumericOps(lhs: T): Ops = new Ops(lhs)
}

object SquantsNumeric {

  trait DoubleIsSquantsNumeric extends SquantsNumeric[Double] {
    def plus(x: Double, y: Double): Double = x + y
    def minus(x: Double, y: Double): Double = x - y
    def times(x: Double, y: Double): Double = x * y
    def divide(x: Double, y: Double): Double = x / y
    def negate(x: Double): Double = -x
  }

  trait BigDecimalIsSquantsNumeric extends SquantsNumeric[BigDecimal] {
    def plus(x: BigDecimal, y: BigDecimal): BigDecimal = x + y
    def minus(x: BigDecimal, y: BigDecimal): BigDecimal = x - y
    def times(x: BigDecimal, y: BigDecimal): BigDecimal = x * y
    def divide(x: BigDecimal, y: BigDecimal): BigDecimal = x / y
    def negate(x: BigDecimal): BigDecimal = -x
  }

  implicit object DoubleIsSquantsNumeric extends DoubleIsSquantsNumeric with Ordering.DoubleOrdering

  implicit object BigDecimalIsSquantsNumeric extends BigDecimalIsSquantsNumeric with Ordering.BigDecimalOrdering
}
