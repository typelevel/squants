package squants2

import scala.util.{ Failure, Success, Try }

/**
 * Represents a Dimension or Quantity Type
 *
 * This trait should be mixed into the Companion Objects of specific Quantity Types.
 *
 * @param name The name of the Dimension
 */
abstract class Dimension[Q[_] <: Quantity[_, Q]](val name: String) {

  /**
   * Set of available units
   * @return
   */
  def units: Set[UnitOfMeasure[Q]]

  /**
   * The unit with a conversions factor of 1.
   * The conversionFactor for other units should be set relative to this unit.
   * @return
   */
  def primaryUnit: UnitOfMeasure[Q] with PrimaryUnit[Q]

  /**
   * The International System of Units (SI) Base Unit
   * @return
   */
  def siUnit: UnitOfMeasure[Q] with SiUnit[Q]

  /**
   * Maps a string representation of a unit symbol into the matching UnitOfMeasure object
   * @param symbol String
   * @return
   */
  def symbolToUnit(symbol: String): Option[UnitOfMeasure[Q]] = units.find(u => u.symbol == symbol)

  /**
   * General factory for creating Quantities of this dimension
   * @param a the quantity's value
   * @param uom the quantity's unit of measure
   * @tparam A the value type
   * @return
   */
  def apply[A: Numeric](a: A, uom: UnitOfMeasure[Q]): Quantity[A, Q] = uom(a)

  /**
   * Tries to map a string or tuple value to Quantity of this Dimension
   * @param value the source string (ie, "10 kW") or tuple (ie, (10, "kW"))
   * @return a Try containing the `Quantity` of a `QuantityParseException`
   */
  protected def parse[A: Numeric](value: Any): Try[Quantity[_, Q]] = value match {
    case s: String              => parseString[A](s)
    case (v: Byte, u: String)   => parseTuple((v.toInt, u))
    case (v: Short, u: String)  => parseTuple((v.toInt, u))
    case (v: Int, u: String)    => parseTuple((v, u))
    case (v: Long, u: String)   => parseTuple((v, u))
    case (v: Float, u: String)  => parseTuple((v, u))
    case (v: Double, u: String) => parseTuple((v, u))
    case _                      => Failure(QuantityParseException(s"Unable to parse $name", value.toString))
  }

  def parseString[A](s: String)(implicit num: Numeric[A]): Try[Quantity[A, Q]] = {
    s match {
      case QuantityString(valStr, symbol) => Success(symbolToUnit(symbol).get(num.parseString(valStr).get))
      case _                              => Failure(QuantityParseException(s"Unable to parse $name", s))
    }
  }

  private lazy val QuantityString = ("^([-+]?[0-9]*\\.?[0-9]+(?:[eE][-+]?[0-9]+)?) *(" + units.map { (u: UnitOfMeasure[Q]) => u.symbol }.reduceLeft(_ + "|" + _) + ")$").r

  def parseTuple[A](t: (A, String))(implicit num: Numeric[A]): Try[Quantity[A, Q]] = {
    val value = t._1
    val symbol = t._2
    symbolToUnit(symbol) match {
      case Some(unit) => Success(unit(value))
      case None       => Failure(QuantityParseException(s"Unable to identify $name unit $symbol", s"(${num.toDouble(value)},$symbol)"))
    }
  }

  implicit val dimensionImplicit: Dimension[Q] = this

  /**
   * Creates a QuantityNumeric for Quantities of this Dimension that can be used in operations that require Numerics.
   *
   * See `QuantityNumeric` for more details
   *
   * @tparam A - The Numeric used for the underlying Quantity value
   * @return
   */
  def numeric[A: Numeric]: QuantityNumeric[A, Q] = new QuantityNumeric[A, Q](this)

//  override def equals(that: Any): Boolean = {
//    that match {
//      case dimension: Dimension[_] => if (dimension.name == this.name) true
//      else false
//      case _                    => false
//    }
//  }

  override def hashCode(): Int = getClass.getName.hashCode
}

/**
 * SI Base Quantity
 * @param name The name of the Dimension
 * @param dimensionSymbol The SI dimension symbol
 */
abstract class BaseDimension[Q[_] <: Quantity[_, Q]](name: String, val dimensionSymbol: String) extends Dimension[Q](name) {
  override def siUnit: UnitOfMeasure[Q] with SiBaseUnit[Q]
}
