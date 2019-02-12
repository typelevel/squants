/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.experimental.json

import org.json4s.{Formats, Serializer}
import squants.market.{Money, MoneyContext}
import org.json4s.JsonAST._
import org.json4s.JsonAST.JString
import org.json4s.reflect.TypeInfo
import org.json4s.JsonAST.JDecimal

/**
 * Provides JSON serialization and deserialization for Money type
 */
class MoneySerializer(fxContext: MoneyContext) extends Serializer[Money] {
  import MoneySerializer._

  def deserialize(implicit format: Formats) = {
    case (TypeInfo(money, _), json) if Clazz.isAssignableFrom(money) ⇒ json match {
      case JObject(List(
        JField("amount", JDecimal(_)),
        JField("currency", JString(_)))) ⇒
        val m = json.extract[MoneyData]
        Money(m.amount, m.currency)(fxContext).get
      case JObject(List(
        JField("amount", JInt(_)),
        JField("currency", JString(_)))) ⇒
        val m = json.extract[MoneyData]
        Money(m.amount, m.currency)(fxContext).get
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

object MoneySerializer {
  private val Clazz = classOf[Money]
  private case class MoneyData(amount: BigDecimal, currency: String)
}
