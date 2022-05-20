package squants2

/**
 * TODO - the new model now requires this by implemented for each QNumeric type for every Dimension
 * TODO - this needs to be simplified in order to provide these for Dimensions
 * TODO - the alternative is to leave this to users that actually need a Numeric for a specific Dimension and QNumeric
 *
 * Base class for creating objects to manage quantities as Numeric.
 *
 * One limitation is the `times` operation which is not supported by every quantity type
 *
 * @tparam A Quantity type
 */
abstract class AbstractQuantityNumeric[A, D <: Dimension](val unit: UnitOfMeasure[D] with PrimaryUnit)(implicit qNum: QNumeric[A]) extends Numeric[Quantity[A, D]] {
  override def plus(x: Quantity[A, D], y: Quantity[A, D]): Quantity[A, D] = x + y
  override def minus(x: Quantity[A, D], y: Quantity[A, D]): Quantity[A, D] = x - y

  /**
   * `times` is not a supported Numeric operation for Quantities.
   * It is not possible to multiply a dimensional quantity by a like quantity and get another like quantity.
   * Applying this class in a way that uses this method will result in an UnsupportedOperationException being thrown.
   *
   * @param x Quantity[A, D]
   * @param y Quantity[A, D]
   * @return
   * @throws scala.UnsupportedOperationException for most types
   */
  override def times(x: Quantity[A, D], y: Quantity[A, D]): Quantity[A, D] = throw new UnsupportedOperationException(s"Numeric.times not supported for ${unit.dimension.name}")
  override def negate(x: Quantity[A, D]): Quantity[A, D] = -x
  override def fromInt(x: Int): Quantity[A, D] = unit(qNum.fromDouble(x))
  override def toInt(x: Quantity[A, D]): Int = qNum.toInt(x.to(unit))
  override def toLong(x: Quantity[A, D]): Long = qNum.toLong(x.to(unit))
  override def toFloat(x: Quantity[A, D]): Float = qNum.toFloat(x.to(unit))
  override def toDouble(x: Quantity[A, D]): Double = qNum.toDouble(x.to(unit))
  override def compare(x: Quantity[A, D], y: Quantity[A, D]): Int = x.compare(y)
  override def parseString(str: String): Option[Quantity[A, D]] = qNum.fromString(str).map(a => unit(a))
}