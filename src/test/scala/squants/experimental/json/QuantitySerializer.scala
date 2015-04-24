/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.experimental.json

import squants.{ Dimension, UnitOfMeasure, Quantity }
import org.json4s.{ Formats, Serializer }
import org.json4s.JsonAST._
import squants.energy._
import squants.time._
import org.json4s.reflect.TypeInfo
import org.json4s.JsonAST.JString
import org.json4s.JsonAST.JInt
import org.json4s.JsonAST.JDecimal
import squants.mass.Mass

/**
 * Provides JSON serialization and deserialization for Quantity types
 * @tparam A - The Quantity Type
 */
abstract class QuantitySerializer[A <: Quantity[A]] //(unitOfMeasure: UnitOfMeasure[A])
    extends Serializer[A] {

  protected def Clazz: Class[_]
  protected def dimension: Dimension[A]
  protected def symbolToUnit: String ⇒ Option[UnitOfMeasure[A]] = dimension.units.map {
    u ⇒
      u.symbol -> u
  }.toMap.get

  val c = Clazz
  def deserialize(implicit format: Formats) = {
    case (TypeInfo(q, _), json) if Clazz.isAssignableFrom(q) ⇒ json match {
      case JObject(List(
        JField("value", JDecimal(value)),
        JField("unit", JString(unit)))) ⇒
        symbolToUnit(unit) match {
          case Some(u) ⇒ u(value)
          case None    ⇒ throw new Exception(s"Could not find matching unit for symbol $unit")
        }
      case JObject(List(
        JField("value", JInt(value)),
        JField("unit", JString(unit)))) ⇒
        symbolToUnit(unit) match {
          case Some(u) ⇒ u(value)
          case None    ⇒ throw new Exception(s"Could not find matching unit for symbol $unit")
        }
    }
  }

  def serializeQuantity(q: Quantity[A]) =
    JObject(
      List(
        "value" -> JDecimal(q.value),
        "unit" -> JString(q.unit.symbol)))
}

/*
 * Serializers for specific Quantity Types
 */
class PowerSerializer extends QuantitySerializer[Power] {
  protected val Clazz = classOf[Power]
  protected val dimension = Power
  def serialize(implicit format: Formats) = {
    case p: Power ⇒ serializeQuantity(p)
  }
}

class TimeSerializer extends QuantitySerializer[Time] {
  protected val Clazz = classOf[Time]
  protected val dimension = Time
  def serialize(implicit format: Formats) = {
    case t: Time ⇒ serializeQuantity(t)
  }
}

class MassSerializer extends QuantitySerializer[Mass] {
  protected val Clazz = classOf[Mass]
  protected val dimension = Mass
  def serialize(implicit format: Formats) = {
    case m: Mass ⇒ serializeQuantity(m)
  }
}
