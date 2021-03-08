/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.experimental

import scala.util.{ Failure, Success, Try }

/**
 * Represents a Dimension or Quantity Type
 *
 * This trait should be mixed into the Companion Objects of specific Quantity Types.
 *
 * @tparam A Quantity Type
 */
trait Dimension[A <: Quantity[A, _]] {
  /**
   * The name
   * @return
   */
  def name: String

  /**
   * Set of available units
   * @return
   */
  def units: Set[UnitOfMeasure[A]]

  /**
   * The unit with a conversions factor of 1.
   * The conversionFactor for other units should be set relative to this unit.
   * @return
   */
  def primaryUnit: UnitOfMeasure[A] with PrimaryUnit

  /**
   * The International System of Units (SI) Base Unit
   * @return
   */
  def siUnit: UnitOfMeasure[A] with SiUnit

  /**
   * Maps a string representation of a unit symbol into the matching UnitOfMeasure object
   * @param symbol String
   * @return
   */
  def symbolToUnit(symbol: String): Option[UnitOfMeasure[A]] = units.find(u ⇒ u.symbol == symbol)

  /**
   * Tries to map a string or tuple value to Quantity of this Dimension
   * @param value the source string (ie, "10 kW") or tuple (ie, (10, "kW"))
   * @return Try[A]
   */
  protected def parse(value: Any) = value match {
    case s: String              ⇒ parseString(s)
    case (v: BigDecimal, u: String) ⇒ parseTuple(v, u)
    case (v: Double, u: String) ⇒ parseTuple(v, u)
    case (v: Float, u: String) ⇒ parseTuple(v, u)
    case (v: Long, u: String) ⇒ parseTuple(v, u)
    case (v: Int, u: String) ⇒ parseTuple(v, u)
  }

  private def parseString(s: String): Try[Quantity[A, BigDecimal]] = {
    s match {
      case QuantityString(value, symbol) ⇒ Success(symbolToUnit(symbol).get(BigDecimal(value)))
      case _                             ⇒ Failure(QuantityParseException(s"Unable to parse $name", s))
    }
  }
  private lazy val QuantityString = ("^([-+]?[0-9]*\\.?[0-9]+(?:[eE][-+]?[0-9]+)?) *(" + units.map { (u: UnitOfMeasure[A]) => u.symbol }.reduceLeft(_ + "|" + _) + ")$").r
  private def parseTuple[N : SquantsNumeric](value: N, symbol: String): Try[Quantity[A, N]] = {
    symbolToUnit(symbol) match {
      case Some(unit) ⇒ Success(unit(value))
      case None       ⇒ Failure(QuantityParseException(s"Unable to identify $name unit $symbol", (value, symbol).toString()))
    }
  }
}

case class QuantityParseException(message: String, expression: String) extends Exception

/**
 * SI Base Quantity
 */
trait BaseDimension { self: Dimension[_] ⇒
  /**
   * SI Base Unit for this Quantity
   * @return
   */
  def siUnit: SiBaseUnit

  /**
   * SI Dimension Symbol
   * @return
   */
  def dimensionSymbol: String
}
