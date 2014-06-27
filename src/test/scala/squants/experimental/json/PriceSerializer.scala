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
import squants.market.Money
import org.json4s.JsonAST._
import squants.energy._
import squants.time.{ Hours, Time }
import squants.market.Price
import org.json4s.JsonAST.JString
import org.json4s.reflect.TypeInfo
import org.json4s.JsonAST.JInt
import org.json4s.JsonAST.JDecimal
import squants.mass.{ Kilograms, Mass }

/**
 * Provides JSON serialization and deserialization for Price type
 * @tparam A The type of quantity of being priced
 */
trait PriceSerializerT[A <: Quantity[A]] extends Serializer[Price[A]] {

  protected def Clazz = classOf[Price[A]]
  def QuantityValidator: String ⇒ Boolean
  def StringToQuantity: String ⇒ A

  /**
   * Implementation
   * @param format Formats
   * @return
   */
  def deserialize(implicit format: Formats) = {
    // Using jsonContainsValidQuantity to verify if this deserializer partial is the proper one
    // is suspect, as some units of different quantity types may use the same symbol.
    // TODO - Come up with better way to verify that this is the Serializer for a given Price[A]
    case (TypeInfo(price, _), json) if Clazz.isAssignableFrom(price) && jsonContainsValidQuantity(json, QuantityValidator) ⇒
      deserializePrice[A](json, StringToQuantity)
  }

  /**
   * Helper function for deserialization of a Price of any Quantity type
   * @param json JValue to be deserialized
   * @param stringToQuantity Function used to convert a string to a Quantity
   * @tparam B Quantity Type
   * @return
   */
  def deserializePrice[B <: Quantity[B]](json: JValue, stringToQuantity: String ⇒ B): Price[B] = json match {
    case JObject(List(
      JField("amount", JDecimal(amount)),
      JField("currency", JString(currency)),
      JField("per", JString(per)))) ⇒
      Price(Money(amount, currency), stringToQuantity(per))
    case JObject(List(
      JField("amount", JInt(amount)),
      JField("currency", JString(currency)),
      JField("per", JString(per)))) ⇒
      Price(Money(amount.toDouble, currency), stringToQuantity(per))
  }

  /**
   * Helper function for serializing a Price of any Quantity Type
   * @param price Price
   * @param unit Unit used for serializing the Quantity part of the price
   * @tparam B Quantity Type
   * @return
   */
  def serializePrice[B <: Quantity[B]](price: Price[B], unit: UnitOfMeasure[B]) = {
    JObject(
      List(
        "amount" -> JDecimal(price.money.value),
        "currency" -> JString(price.money.valueUnit.code),
        "per" -> JString(price.quantity.toString(unit))))
  }

  /**
   * Helper function for validating if the json contains a price that can be deserialized
   * into a price of this type, by determing if the "per" value can be converted using
   * the validator
   * @param json JValue being validated
   * @param quantityValidator Function for validating the "per" value
   * @return
   */
  def jsonContainsValidQuantity(json: JValue, quantityValidator: String ⇒ Boolean): Boolean = {
    json match {
      case JObject(List(
        JField("amount", _),
        JField("currency", _),
        JField("per", JString(per)))) ⇒ quantityValidator(per)
    }
  }
}

/*
 * Serializers for Prices of specific Quantity types
 */
class EnergyPriceSerializer(unitOfMeasure: UnitOfMeasure[Energy])
    extends PriceSerializerT[Energy] {
  def StringToQuantity = s ⇒ Energy(s).get
  def QuantityValidator = s ⇒ Energy(s).isSuccess
  def serialize(implicit format: Formats) = {
    case p @ Price(_, MegawattHours(_)) ⇒ serializePrice(p.asInstanceOf[Price[Energy]], unitOfMeasure)
  }
}

class MassPriceSerializer(unitOfMeasure: UnitOfMeasure[Mass])
    extends PriceSerializerT[Mass] {
  def StringToQuantity = s ⇒ Mass(s).get
  def QuantityValidator = s ⇒ Mass(s).isSuccess
  def serialize(implicit format: Formats) = {
    case p @ Price(_, Kilograms(_)) ⇒ serializePrice(p.asInstanceOf[Price[Mass]], unitOfMeasure)
  }
}

class TimePriceSerializer(unitOfMeasure: UnitOfMeasure[Time])
    extends PriceSerializerT[Time] {
  def StringToQuantity = s ⇒ Time(s).get
  def QuantityValidator = s ⇒ Time(s).isSuccess
  def serialize(implicit format: Formats) = {
    case p @ Price(_, Hours(_)) ⇒ serializePrice(p.asInstanceOf[Price[Time]], unitOfMeasure)
  }
}
