/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.experimental.json

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
        JField("amount", JDecimal(_)),
        JField("currency", JString(_)))) ⇒
        val m = json.extract[MoneyData]
        Money(m.amount, m.currency)
      case JObject(List(
        JField("amount", JInt(_)),
        JField("currency", JString(_)))) ⇒
        val m = json.extract[MoneyData]
        Money(m.amount, m.currency)
    }
  }

  def serialize(implicit format: Formats) = {
    case money: Money ⇒
      JObject(
        List(
          "amount" -> JDecimal(money.amount),
          "currency" -> JString(money.unit.code)))
  }
}
