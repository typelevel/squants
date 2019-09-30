/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.experimental.json

import squants.Quantity
import org.json4s.{Formats, Serializer}
import squants.market.{Money, MoneyContext, Price}
import org.json4s.JsonAST._
import squants.energy._
import squants.time.Time
import org.json4s.JsonAST.JString
import org.json4s.reflect.TypeInfo
import org.json4s.JsonAST.JInt
import org.json4s.JsonAST.JDecimal
import squants.mass.Mass
import scala.util.Try

/**
 * Provides JSON serialization and deserialization for Price type
 * @tparam A The type of quantity of being priced
 */
sealed trait PriceSerializerT[A <: Quantity[A]] extends Serializer[Price[A]] {

  implicit def fxContext: MoneyContext

  protected def Clazz = classOf[Price[A]]
  def parseQuantity: String ⇒ Try[A]
  private def QuantityValidator(s: String) = parseQuantity(s).isSuccess
  private def StringToQuantity(s: String) = parseQuantity(s).get

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
      Price(Money(amount, currency).get, stringToQuantity(per))
    case JObject(List(
      JField("amount", JInt(amount)),
      JField("currency", JString(currency)),
      JField("per", JString(per)))) ⇒
      Price(Money(amount.toDouble, currency).get, stringToQuantity(per))
  }

  def serialize(implicit format: Formats) = {
    case p: Price[_] ⇒ serializePrice(p)
  }

  /**
   * Helper function for serializing a Price of any Quantity Type
   * @param price Price
   * @tparam B Quantity Type
   * @return
   */
  def serializePrice[B <: Quantity[B]](price: Price[B]) = {
    JObject(
      List(
        "amount" -> JDecimal(price.money.value),
        "currency" -> JString(price.money.unit.code),
        "per" -> JString(price.quantity.toString)))
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
class EnergyPriceSerializer(ctx: MoneyContext) extends PriceSerializerT[Energy] {
  implicit lazy val fxContext = ctx
  def parseQuantity = s ⇒ Energy(s)
}

class MassPriceSerializer(ctx: MoneyContext) extends PriceSerializerT[Mass] {
  implicit lazy val fxContext = ctx
  def parseQuantity = s ⇒ Mass(s)
}

class TimePriceSerializer(ctx: MoneyContext) extends PriceSerializerT[Time] {
  implicit lazy val fxContext = ctx
  def parseQuantity = s ⇒ Time(s)
}
