/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

/**
 * A Unit of Measure is used to define the scale of a quantity measurement
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @tparam A The type of Quantity being measured
 */
trait UnitOfMeasure[A <: Quantity[A]] extends Serializable {
  /**
   * Factory method for creating instances of a Quantity in this UnitOfMeasure
   * @param d Double - the Quantity's value in terms of this UnitOfMeasure
   * @return
   */
  def apply(d: Double): A

  /**
   * Symbol used when representing Quantities in this UnitOfMeasure
   * @return
   */
  def symbol: String
}

/**
 * Provides for defining and calling a converter method that converts a Quantities value from it's [[squants.ValueUnit]]
 * to an alternative Unit of Measure
 */
trait UnitConverter { uom: UnitOfMeasure[_] ⇒
  /**
   * Defines a signature for converting a quantity from this UOM to the Value UOM
   * @return
   */
  protected def converterFrom: Double ⇒ Double

  /**
   * Defines a signature for converting a quantity to this UOM from the Value UOM
   * @return
   */
  protected def converterTo: Double ⇒ Double

  /**
   * Applies the converterTo method to a value
   * @param value Double
   * @return
   */
  final def convertTo(value: Double) = converterTo(value)

  /**
   * Applies the converterFrom method to a value
   *
   * @param value Double
   * @return
   */
  final def convertFrom(value: Double) = converterFrom(value)
}

/**
 * A Unit of Measure that require a simple multiplier for converting to and from the underlying value's unit
 */
trait UnitMultiplier extends UnitConverter { uom: UnitOfMeasure[_] ⇒

  /**
   * Defines a multiplier value relative to the Quantity's [[squants.ValueUnit]]
   *
   * @return
   */
  def multiplier: Double

  /**
   * Implements the converterTo method as a simple quotient of the value and the multiplier
   * @return
   */
  def converterTo = value ⇒ value / multiplier

  /**
   * Implements the converterFrom method as a simple product of the value and the multiplier
   * @return
   */
  def converterFrom = value ⇒ value * multiplier
}

/**
 * Identifies the Unit of Measure used for storing the quantity's underlying value
 *
 * Each Quantity should have one and only one ValueUnit
 */
trait ValueUnit extends UnitMultiplier { uom: UnitOfMeasure[_] ⇒

  /**
   * Implements the converterTo method to just return the underlying value
   * @return
   */
  override final def converterTo: Double ⇒ Double = value ⇒ value

  /**
   * Implements the converterFrom method to just return the underlying value
   * @return
   */
  override final def converterFrom: Double ⇒ Double = value ⇒ value

  /**
   * Value unit multiplier is always equal to 1
   */
  final val multiplier = 1d
}

/**
 * A unit of measure used for Base (SI) Quantities
 * @tparam QuantityType The type of Quantity being measured
 */
trait BaseQuantityUnit[QuantityType <: Quantity[QuantityType] with BaseQuantity]
    extends UnitOfMeasure[QuantityType] { uom: UnitOfMeasure[_] ⇒

  /**
   * The SI symbol for base physical quantities
   *
   * @return the SI symbol
   */
  def dimensionSymbol: String
}

/**
 * A marker trait identifying SI Base Units
 */
trait BaseUnit { uom: BaseQuantityUnit[_] ⇒ }
