/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

/**
 * Base class for creating objects to manage quantities as Numeric.
 *
 * One limitation is the `times` operation which is not supported by every quantity type
 *
 * @tparam A Quantity type
 */
abstract class AbstractQuantityNumeric[A <: Quantity[A]](val unit: UnitOfMeasure[A] with PrimaryUnit) extends Numeric[A] {
  def plus(x: A, y: A) = x + y
  def minus(x: A, y: A) = x - y

  /**
   * `times` is not a supported Numeric operation for Quantities.
   * It is not possible to multiply a dimensional quantity by a like quantity and get another like quantity.
   * Applying this class in a way that uses this method will result in an UnsupportedOperationException being thrown.
   *
   * @param x Quantity[A]
   * @param y Quantity[A]
   * @return
   * @throws scala.UnsupportedOperationException for most types
   */
  def times(x: A, y: A): A = throw new UnsupportedOperationException("Numeric.times not supported for Quantities")
  def negate(x: A) = -x
  def fromInt(x: Int) = unit(x)
  def toInt(x: A) = x.to(unit).toInt
  def toLong(x: A) = x.to(unit).toLong
  def toFloat(x: A) = x.to(unit).toFloat
  def toDouble(x: A) = x.to(unit)
  def compare(x: A, y: A) = if (x.to(unit) > y.to(unit)) 1 else if (x.to(unit) < y.to(unit)) -1 else 0
  // As there's no direct access to the Dimension (which has parseString) from a UnitOfMeasure,
  // we create a dummy instance here and access its dimension member
  def parseString(str: String): Option[A] = unit(0).dimension.parseString(str).toOption
}
