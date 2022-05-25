package squants2

/**
 * A Unit of Measure is used to define the scale of a quantity measurement
 *
 * Each Quantity Dimension must include at least one Unit of Measure, and one and only one Primary.
 * Other units of measure are defined with conversionFactors relative to the Primary.
 *
 * @author  garyKeorkunian
 * @since   0.1
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
  def apply[A: Numeric](a: A): Quantity[A, D]

  /**
   * Extractor method for getting the QNumeric value of a Quantity in this UnitOfMeasure
   * @param q a Quantity of in this Dimension
   * @tparam A the QNumeric type
   * @return
   */
  def unapply[A: Numeric](q: Quantity[A, D]): Option[A] = Some(q.toNum(this))

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
  protected [squants2] def conversionFactor: ConversionFactor

  /**
   * Converts a Quantity to this UnitOfMeasure
   *
   * May be overridden by Units that require exceptional conversion (e.g., Temperature, Money)
   *
   * @param quantity the Quantity being converted
   * @param uom the Unit to which the Quantity is being converted
   * @param num QNumeric for the Quantity
   * @tparam A QNumeric type
   * @return
   */
  def convertTo[A](quantity: Quantity[A, D], uom: UnitOfMeasure[D])(implicit num: Numeric[A]): Quantity[A, D] = {

    if (uom eq this) quantity else {
      val thisFactor = num.parseString(conversionFactor.toString).get
      val thatFactor = num.parseString(uom.conversionFactor.toString).get

      val newValue = num match {
        case fnum: Fractional[A] =>
          fnum.times(quantity.value, fnum.div(thisFactor, thatFactor))
        case inum: Integral[A] =>
          inum.times(quantity.value, inum.quot(thisFactor, thatFactor))
        case _ => throw new UnsupportedOperationException("Unknown numeric type")
      }
      uom(newValue)
    }
  }
}

trait PrimaryUnit { self: UnitOfMeasure[_] => }
trait SiUnit { self: UnitOfMeasure[_] => }
trait SiBaseUnit extends SiUnit { self: UnitOfMeasure[_] => }
