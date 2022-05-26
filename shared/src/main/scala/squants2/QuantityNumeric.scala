package squants2

/**
 * Class for creating objects to manage quantities as Numeric.
 *
 * A constructor is supplied in the base Dimension class
 *
 * object Dimension {
 *    /* ... */
 *    def numeric[A: Numeric]: QuantityNumeric[A, this.type] = QuantityNumeric(this)
 *    /* ... */
 * }
 *
 * User code can create these as needed:
 *
 * val ds = Seq(12.28.each, Each(12.28), Each(-10.22), 1.1.each)
 * val sum = ds.sum(Dimensionless.numeric[Double])
 *
 * One limitation is the `times` operation (A * A => A), which is not a valid operation for Quantities
 *
 * @param dimension Dimension
 * @param num Numeric type of underlying Quantity value
 * @tparam A Quantity type
 * @tparam Q Dimension type
 */

class QuantityNumeric[A, Q[N] <: Quantity[N, Q]](dimension: Dimension[Q])(implicit num: Numeric[A]) extends Numeric[Quantity[A, Q]] {
  private val unit: UnitOfMeasure[Q]  = dimension.primaryUnit.asInstanceOf[UnitOfMeasure[Q]]

  override def plus(x: Quantity[A, Q], y: Quantity[A, Q]): Quantity[A, Q] = x + y
  override def minus(x: Quantity[A, Q], y: Quantity[A, Q]): Quantity[A, Q] = x - y

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
  override def times(x: Quantity[A, Q], y: Quantity[A, Q]): Quantity[A, Q] = throw new UnsupportedOperationException(s"Numeric.times not supported for ${dimension.name}")
  override def negate(x: Quantity[A, Q]): Quantity[A, Q] = -x

  override def compare(x: Quantity[A, Q], y: Quantity[A, Q]): Int = x.compare(y)

  // All Numeric constructors and extractors are based on the dimension's primary Unit
  override def fromInt(x: Int): Quantity[A, Q] = unit(num.fromInt(x))
  override def toInt(x: Quantity[A, Q]): Int = num.toInt(x.to(unit))
  override def toLong(x: Quantity[A, Q]): Long = num.toLong(x.to(unit))
  override def toFloat(x: Quantity[A, Q]): Float = num.toFloat(x.to(unit))
  override def toDouble(x: Quantity[A, Q]): Double = num.toDouble(x.to(unit))

  override def parseString(str: String): Option[Quantity[A, Q]] = unit.dimension.parseString[A](str).toOption
}
