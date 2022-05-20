package squants2

import squants2.QNumeric.*

/**
 * A Unit of Measure is used to define the scale of a quantity measurement
 *
 * Each Quantity Dimension must include at least one Unit of Measure, and one and only one Primary.
 * Other units of measure are defined with conversionFactors relative to the Primary.
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @tparam D The Dimension being measured
 */
trait UnitOfMeasure[D <: Dimension] extends Serializable {

  def dimension: D

  /**
   * Factory method for creating instances of a Quantity in this UnitOfMeasure
   * @param a the value of the quantity in this unit
   * @tparam A the QNumeric type for `a`
   * @return
   */
  def apply[A: QNumeric](a: A): Quantity[A, D]

  /**
   * Extractor method for getting the QNumeric value of a Quantity in this UnitOfMeasure
   * @param q a Quantity of in this Dimension
   * @tparam A the QNumeric type
   * @return
   */
  def unapply[A: QNumeric](q: Quantity[A, D]): Option[A] = Some(q.to(this))

  /**
   * Symbol used when representing Quantities in this UnitOfMeasure
   * @return
   */
  def symbol: String

  /**
   * The number of these units that equals 1 of the PrimaryUnit
   *
   * @return
   */
  protected def conversionFactor: Double

  /**
   * Converts a Quantity to this UnitOfMeasure
   *
   * May be overridden by Units that require exceptional conversion (e.g., Temperature, Money)
   *
   * @param quantity the Quantity being converted
   * @param uom the Unit to which the Quantity is being converted
   * @param qNum QNumeric for the Quantity
   * @tparam A QNumeric type
   * @return
   */
  def convertTo[A](quantity: Quantity[A, D], uom: UnitOfMeasure[D])(implicit qNum: QNumeric[A]): Quantity[A, D] = {
    if (uom eq this) quantity else {
      uom(quantity.value * qNum.fromDouble(conversionFactor) / qNum.fromDouble(uom.conversionFactor))
    }
  }
}

trait PrimaryUnit { self: UnitOfMeasure[_] => }
trait SiUnit { self: UnitOfMeasure[_] => }
trait SiBaseUnit extends SiUnit { self: UnitOfMeasure[_] => }
