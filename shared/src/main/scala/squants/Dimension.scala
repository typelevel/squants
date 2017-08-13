/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import scala.util.{ Failure, Success, Try }

/**
 * Represents a Dimension or Quantity Type
 *
 * This trait should be mixed into the Companion Objects of specific Quantity Types.
 *
 * @tparam A Quantity Type
 */
trait Dimension[A <: Quantity[A]] {
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
  protected def parse(value: Any): Try[A] = value match {
    case s: String              =>
      if (Platform.name == "native") {
        // inefficient but correct parsing
        units.flatMap { uom =>
          if (s.endsWith(uom.symbol)) {
            val remaining = s.dropRight(uom.symbol.length).trim
            val doubleRegex = "\\d+(.\\d+)?".r

            remaining match {
              case doubleRegex(_) =>
                Some(uom(remaining.toDouble))
              case _ =>
                None
            }
          } else {
            None
          }
        }.headOption.map { v =>
          Success(v)
        }.getOrElse {
          Failure(QuantityParseException(s"Unable to parse $name", s))
        }
      } else {
        parseString(s)
      }
    case (v: Byte, u: String)   => parseTuple(v.toDouble, u)
    case (v: Short, u: String)  => parseTuple(v.toDouble, u)
    case (v: Int, u: String)    => parseTuple(v.toDouble, u)
    case (v: Long, u: String)   => parseTuple(v.toDouble, u)
    case (v: Float, u: String)  => parseTuple(v.toDouble, u)
    case (v: Double, u: String) => parseTuple(v, u)
  }


  private def parseString(s: String): Try[A] = {
    s match {
      case QuantityString(value, symbol) ⇒ Success(symbolToUnit(symbol).get(BigDecimal(value)))
      case _                             ⇒ Failure(QuantityParseException(s"Unable to parse $name", s))
    }
  }

  private lazy val QuantityString =
    ("^([-+]?[0-9]*\\.?[0-9]+(?:[eE][-+]?[0-9]+)?) *(" + units.map { u: UnitOfMeasure[A] ⇒ u.symbol }.reduceLeft(_ + "|" + _) + ")$").r

  private def parseTuple(value: Double, symbol: String): Try[A] = {
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
