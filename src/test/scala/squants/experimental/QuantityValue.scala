/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.experimental

/**
 * EXPERIMENTAL
 *
 * This is essentially a proof-of-concept for replacing the underlying Double
 */
trait QuantityValue[T] {
  def value: T
  def plus(that: QuantityValue[T]): QuantityValue[T]
  def + = plus _
  def minus(that: QuantityValue[T]): QuantityValue[T]
  def - = minus _
  def times(that: QuantityValue[T]): QuantityValue[T]
  def * = times _
  def divide(that: QuantityValue[T]): QuantityValue[T]
  def / = divide _
  def negate(): QuantityValue[T]
  def unary_- = negate _
  def compare(that: QuantityValue[T]): Int
  def toInt: Int
  def toLong: Long
  def toFloat: Float
  def toDouble: Double
}

case class QVDouble(value: Double) extends QuantityValue[Double] {
  def plus(that: QuantityValue[Double]) = QVDouble(value + that.value)
  def minus(that: QuantityValue[Double]) = QVDouble(value - that.value)
  def times(that: QuantityValue[Double]) = QVDouble(value * value)
  def divide(that: QuantityValue[Double]) = QVDouble(value / value)
  def negate() = QVDouble(-value)
  def compare(that: QuantityValue[Double]) = if (value > that.value) 1 else if (value < that.value) -1 else 0
  def toInt = value.toInt
  def toLong = value.toLong
  def toFloat = value.toFloat
  def toDouble = value
}
