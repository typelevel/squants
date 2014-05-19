/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.json

import squants.{ UnitOfMeasure, Quantity }
import org.json4s.{ Formats, Serializer }
import org.json4s.JsonAST._
import squants.energy._
import squants.time._
import org.json4s.reflect.TypeInfo
import org.json4s.JsonAST.JString
import org.json4s.JsonAST.JInt
import org.json4s.JsonAST.JDecimal

/**
 * Provides JSON serialization and deserialization for Squants Quantity types
 * @param unitOfMeasure - Unit used in JSON
 * @tparam T - The Quantity Type
 */
abstract class QuantitySerializer[T <: Quantity[T]](unitOfMeasure: UnitOfMeasure[T])
    extends Serializer[T] {

  protected def Clazz: Class[_]
  protected def symbolToUnit: String ⇒ UnitOfMeasure[T]

  val c = Clazz
  def deserialize(implicit format: Formats) = {
    case (TypeInfo(q, _), json) if Clazz.isAssignableFrom(q) ⇒ json match {
      case JObject(List(
        JField("value", JDecimal(value)),
        JField("unit", JString(unit)))) ⇒
        symbolToUnit(unit)(value.toDouble)
      case JObject(List(
        JField("value", JInt(value)),
        JField("unit", JString(unit)))) ⇒
        symbolToUnit(unit)(value.toDouble)
    }
  }

  def serializeQuantity(q: Quantity[T]) =
    JObject(
      List(
        "value" -> JDecimal(q.to(unitOfMeasure)),
        "unit" -> JString(unitOfMeasure.symbol)))
}

/*
 * Serializers for specific Quantity Types
 */
class PowerSerializer(unitOfMeasure: UnitOfMeasure[Power])
    extends QuantitySerializer[Power](unitOfMeasure) {
  protected val Clazz = classOf[Power]
  protected val symbolToUnit = Map(
    Kilowatts.symbol -> Kilowatts,
    Megawatts.symbol -> Megawatts,
    Gigawatts.symbol -> Gigawatts)
  def serialize(implicit format: Formats) = {
    case p: Power ⇒ serializeQuantity(p)
  }
}

class TimeSerializer(unitOfMeasure: UnitOfMeasure[Time])
    extends QuantitySerializer[Time](unitOfMeasure) {
  protected val Clazz = classOf[Time]
  protected val symbolToUnit = Map(
    Milliseconds.symbol -> Milliseconds,
    Seconds.symbol -> Seconds,
    Minutes.symbol -> Minutes,
    Hours.symbol -> Hours)
  def serialize(implicit format: Formats) = {
    case t: Time ⇒ serializeQuantity(t)
  }
}
