/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.market

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.space.{ Yards, Meters }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class PriceSpec extends FlatSpec with Matchers {

  behavior of "Price and its Units of Measure"

  it should "create Price objects using the primary constructor" in {
    val p = Price(Money(100, "USD"), Meters(1))
    p.money should be(Money(100, "USD"))
    p.quantity should be(Meters(1))
  }

  it should "properly add two like prices" in {
    val p1 = Price(Money(5, "USD"), Meters(1))
    val p2 = Price(Money(8, "USD"), Meters(1))
    val p3 = Price(Money(13, "USD"), Meters(1))
    p3 should be(p1 + p2)
  }

  it should "properly subtract two like prices" in {
    val p1 = Price(Money(15, "USD"), Meters(1))
    val p2 = Price(Money(8, "USD"), Meters(1))
    val p3 = Price(Money(7, "USD"), Meters(1))
    p3 should be(p1 - p2)
  }

  it should "properly multiply by a Double" in {
    val p1 = Price(Money(9, "USD"), Meters(1))
    val p2 = Price(Money(3, "USD"), Meters(1))
    p1 should be(p2 * 3)
  }

  it should "properly multiply by a BigDecimal" in {
    val p1 = Price(Money(9, "USD"), Meters(1))
    val p2 = Price(Money(3, "USD"), Meters(1))
    p1 should be(p2 * BigDecimal(3))
  }

  it should "properly divide by a Double" in {
    val p1 = Price(Money(9, "USD"), Meters(1))
    val p2 = Price(Money(3, "USD"), Meters(1))
    p2 should be(p1 / 3)
  }

  it should "properly divide by a BigDecimal" in {
    val p1 = Price(Money(9, "USD"), Meters(1))
    val p2 = Price(Money(3, "USD"), Meters(1))
    p2 should be(p1 / BigDecimal(3))
  }

  it should "properly divide by a like Price" in {
    val p1 = Price(Money(9, "USD"), Meters(1))
    val p2 = Price(Money(3, "USD"), Meters(1))
    p1 / p2 should be(BigDecimal(3))
    (p1 / p2).toDouble should be(3)
  }

  it should "return Money when multiplied by Quantity" in {
    val p = Price(Money(10, "USD"), Meters(1))
    p * Meters(10) should be(Money(100, "USD"))
  }

  it should "return Quantity when multiplied by Money" in {
    val p = Price(Money(10, "USD"), Meters(1))
    p * Money(40, "USD") should be(Meters(4))
  }

  it should "return properly formatted strings" in {
    val p = Price(Money(10.22, "USD"), Meters(1))
    p.toString should be(p.money.toString + "/" + p.quantity.toString)
  }

  it should "return a properly formatted string in terms of the given unit" in {
    val p = Price(Money(10.22, "USD"), Meters(1))
    p.toString(Yards) should be(p.money.toString + "/" + p.quantity.toString(Yards))
  }

  it should "convert a Price to a different currency with a valid MoneyContext" in {
    implicit val moneyContext = MoneyContext(USD, defaultCurrencySet, Seq(USD(1) toThe JPY(100)))
    val p = Price(USD(10), Meters(1))
    p in JPY should be(Price(USD(10) in JPY, Meters(1)))
  }

  it should "return a properly formatted string converted to different currency and/or unit with a valid MoneyContext" in {
    implicit val moneyContext = MoneyContext(USD, defaultCurrencySet, Seq(USD(1) toThe JPY(100)))
    val p = Price(USD(10), Meters(1))
    p toString (JPY, Yards) should be(p.money.in(JPY).toString + "/" + p.quantity.toString(Yards))
  }
}
