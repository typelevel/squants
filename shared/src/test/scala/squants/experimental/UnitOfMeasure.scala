/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.experimental

/**
 * A Unit of Measure is used to define the scale of a quantity measurement
 *
 * Each Quantity Dimension must include at least one Unit of Measure, and one and only Primary.
 * Other units of measure are defined with conversionFactors relative to the Primary.
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @tparam A The type of Quantity being measured
 */
trait UnitOfMeasure[A <: Quantity[A, _]] extends Serializable {
  /**
   * Factory method for creating instances of a Quantity in this UnitOfMeasure
   * @param n N - the Quantity's value in terms of this UnitOfMeasure
   * @return
   */
  def apply[N: SquantsNumeric](n: N): Quantity[A, N]

  /**
   * Extractor method for getting the Numeric value of a Quantity in this UnitOfMeasure
   * @param q A - The Quantity being matched
   * @return
   */
  def unapply(q: A) = Some(q.to(this))

  /**
   * Symbol used when representing Quantities in this UnitOfMeasure
   * @return
   */
  def symbol: String

  /**
   * Defines a signature for converting a quantity from this UOM to the Primary UOM
   * @return
   */
  protected def converterFrom[N]: (N, SquantsNumeric[N]) ⇒ N

  /**
   * Defines a signature for converting a quantity to this UOM from the Primary UOM
   * @return
   */
  protected def converterTo[N]: (N, SquantsNumeric[N]) ⇒ N

  /**
   * Applies the converterTo method to a value
   * @param n N value in terms of teh ValueUnit
   * @tparam N Type
   * @return
   */
  final def convertTo[N](n: N)(implicit sqNum: SquantsNumeric[N]): N = converterTo(n, sqNum)

  /**
   * Applies the converterFrom method to a value
   *
   * @param n N value in terms of this Unit
   * @tparam N Type
   * @return
   */
  final def convertFrom[N](n: N)(implicit sqNum: SquantsNumeric[N]): N = converterFrom(n, sqNum)
}

/**
 * A Unit of Measure that require a simple multiplier for converting to and from the underlying value's unit
 */
trait UnitConverter { uom: UnitOfMeasure[_] ⇒

  /**
   * Defines a multiplier value relative to the Quantity's [[squants.PrimaryUnit]]
   *
   * @return
   */
  protected def conversionFactor: Double

  /**
   * Implements the converterTo method as a simple quotient of the value and the multiplier
   * @return
   */
  protected def converterTo[N]: (N, SquantsNumeric[N]) ⇒ N = (n, sqNum) ⇒ sqNum.divide(n, sqNum.fromDouble(conversionFactor))

  /**
   * Implements the converterFrom method as a simple product of the value and the multiplier
   * @return
   */
  protected def converterFrom[N]: (N, SquantsNumeric[N]) ⇒ N = (n, sqNum) ⇒ sqNum.times(n, sqNum.fromDouble(conversionFactor))

}

/**
 * Identifies the Unit of Measure with a conversionFactor of 1.0.
 *
 * It is used as the intermediary unit during conversions
 *
 * Each Quantity should have one and only one ValueUnit
 */
trait PrimaryUnit extends UnitConverter { uom: UnitOfMeasure[_] ⇒

  /**
   * Implements the converterTo method to just return the underlying value
   * @return
   */
  override final def converterTo[N]: (N, SquantsNumeric[N]) ⇒ N = (n, _) ⇒ n

  /**
   * Implements the converterFrom method to just return the underlying value
   * @return
   */
  override final def converterFrom[N]: (N, SquantsNumeric[N]) ⇒ N = (n, _) ⇒ n

  /**
   * Value unit multiplier is always equal to 1
   */
  final val conversionFactor = 1d
}

/**
 * A market trait identifying SI Units
 */
trait SiUnit

/**
 * A marker trait identifying SI Base Units
 */
trait SiBaseUnit extends SiUnit
