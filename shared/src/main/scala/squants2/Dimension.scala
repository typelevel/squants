package squants2

import scala.util.{ Failure, Success, Try }

/**
 * Represents a Dimension or Quantity Type
 *
 * This trait should be mixed into the Companion Objects of specific Quantity Types.
 *
 * @param name The name of the Dimension
 */
abstract class Dimension(val name: String) {
  type D = this.type
  type Q[A] = Quantity[A, D]

  /**
   * Set of available units
   * @return
   */
  def units: Set[UnitOfMeasure[D]]

  /**
   * The unit with a conversions factor of 1.
   * The conversionFactor for other units should be set relative to this unit.
   * @return
   */
  def primaryUnit: UnitOfMeasure[D] with PrimaryUnit

  /**
   * The International System of Units (SI) Base Unit
   * @return
   */
  def siUnit: UnitOfMeasure[D] with SiUnit

  /**
   * Maps a string representation of a unit symbol into the matching UnitOfMeasure object
   * @param symbol String
   * @return
   */
  def symbolToUnit(symbol: String): Option[UnitOfMeasure[D]] = units.find(u => u.symbol == symbol)

  /**
   * Tries to map a string or tuple value to Quantity of this Dimension
   * @param value the source string (ie, "10 kW") or tuple (ie, (10, "kW"))
   * @return a Try containing the `Quantity` of a `QuantityParseException`
   */
  protected def parse(value: Any): Try[Q[_]] = value match {
    case s: String              => parseString(s)
    case (v: Byte, u: String)   => parseTuple((v.toInt, u))
    case (v: Short, u: String)  => parseTuple((v.toInt, u))
    case (v: Int, u: String)    => parseTuple((v, u))
    case (v: Long, u: String)   => parseTuple((v, u))
    case (v: Float, u: String)  => parseTuple((v, u))
    case (v: Double, u: String) => parseTuple((v, u))
    case _                      => Failure(QuantityParseException(s"Unable to parse $name", value.toString))
  }

  def parseString(s: String): Try[Quantity[BigDecimal, D]] = {
    s match {
      case QuantityString(value, symbol) => Success(symbolToUnit(symbol).get(BigDecimal(value)))
      case _                             => Failure(QuantityParseException(s"Unable to parse $name", s))
    }
  }

  private lazy val QuantityString = ("^([-+]?[0-9]*\\.?[0-9]+(?:[eE][-+]?[0-9]+)?) *(" + units.map { (u: UnitOfMeasure[D]) => u.symbol }.reduceLeft(_ + "|" + _) + ")$").r

  def parseTuple[A: QNumeric](t: (A, String))(implicit num: Numeric[A]): Try[Quantity[_, D]] = {
    val value = t._1
    val symbol = t._2
    symbolToUnit(symbol) match {
      case Some(unit) => Success(unit(value))
      case None       => Failure(QuantityParseException(s"Unable to identify $name unit $symbol", s"(${num.toDouble(value)},$symbol)"))
    }
  }

  implicit val dimensionImplicit: Dimension = this

  override def equals(that: Any): Boolean = that match {
    case dimension: Dimension => dimension.getClass.getName == this.getClass.getName
    case _                    => false
  }

  override def hashCode(): Int = getClass.getName.hashCode
}

/**
 * SI Base Quantity
 * @param name The name of the Dimension
 * @param dimensionSymbol The SI dimension symbol
 */
abstract class BaseDimension(name: String, val dimensionSymbol: String) extends Dimension(name) {
  override def siUnit: UnitOfMeasure[D] with SiBaseUnit
}

