/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.json

import org.json4s.{ Formats, Serializer }
import squants.market.Money
import org.json4s.JsonAST._
import org.json4s.JsonAST.JString
import org.json4s.reflect.TypeInfo
import org.json4s.JsonAST.JDecimal

/**
 * Provides JSON serialization and deserialization for Money type
 */
class MoneySerializer extends Serializer[Money] {

  private val Clazz = classOf[Money]
  case class MoneyData(amount: BigDecimal, currency: String)

  def deserialize(implicit format: Formats) = {
    case (TypeInfo(money, _), json) if Clazz.isAssignableFrom(money) ⇒ json match {
      case JObject(List(
        JField("amount", JDecimal(amount)),
        JField("currency", JString(currency)))) ⇒
        val m = json.extract[MoneyData]
        Money(m.amount, m.currency)
      case JObject(List(
        JField("amount", JInt(amount)),
        JField("currency", JString(currency)))) ⇒
        val m = json.extract[MoneyData]
        Money(m.amount, m.currency)
    }
  }

  def serialize(implicit format: Formats) = {
    case money: Money ⇒
      JObject(
        List(
          "amount" -> JDecimal(money.amount),
          "currency" -> JString(money.valueUnit.code)))
  }
}

