/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.market

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MoneySpec extends FlatSpec with Matchers {

  behavior of "Money and its Units of Measure"

  it should "create values using factories that take Currency" in {
    assert(Money(BigDecimal(10), USD) == Money(10, USD))
    assert(Money(10, USD) == Money(10, USD))
  }

  it should "create values using factories that take Currency Code (String)" in {
    assert(Money(BigDecimal(10), "USD") == Money(10, USD))
    assert(Money(10, "USD") == Money(10, USD))
  }

  it should "create values using Currency (UOM) factories" in {
    assert(USD(BigDecimal(10)) == Money(10, USD))
    assert(USD(10) == Money(10, USD))
  }

  it should "create values using 'no currency' factories with an implicit MoneyContext in scope" in {
    implicit val moneyContext = MoneyContext(USD, defaultCurrencySet, Nil)
    assert(Money(BigDecimal(10)) == USD(10))
    assert(Money(10) == USD(10))
  }

  it should "create values from formatted strings" in {
    assert(Money("500 USD").right.get == USD(500))
    assert(Money("5.50USD").right.get == USD(5.5))
    assert(Money("500 EUR").right.get == EUR(500))
    assert(Money("10000.0 JPY").right.get == JPY(10000))
    assert(Money("23.45 CAD").right.get == CAD(23.45))
    assert(Money("23.45 ZZZ").left.get == "Unable to parse 23.45 ZZZ as Money")
  }

  it should "return proper result when comparing like currencies" in {
    assert(USD(10) == USD(10))
    assert(USD(10) != USD(9.99))

    assert(USD(10) > USD(9.99))
    assert(USD(10) >= USD(9.99))
    assert(USD(10) >= USD(10))

    assert(USD(9.99) < USD(10))
    assert(USD(9.99) <= USD(10))
    assert(USD(10) <= USD(10))
  }

  it should "return proper result when comparing dislike currencies with a MoneyContext in scope" is pending

  it should "return proper result when adding like currencies with no MoneyContext in scope" in {
    assert(USD(1) + USD(2) == USD(3))
    assert(USD(1).plus(USD(2)) == USD(3))
  }

  it should "return proper result when subtracting like currencies with no MoneyContext in scope" in {
    assert(USD(5) - USD(2) == USD(3))
    assert(USD(5).minus(USD(2)) == USD(3))
  }

  it should "return proper result when dividing like currencies with no MoneyContext in scope" in {
    assert(USD(10) / USD(2) == 5)
    assert(USD(10).divide(USD(2)) == 5)
  }

  it should "throw a NoSuchExchangeRateException when adding cross currency values with only the defaultMoneyContext in scope" in {
    intercept[NoSuchExchangeRateException] {
      assert(USD(1) + JPY(2) == CAD(3))
    }
  }

  it should "throw an UnsupportedOperationException when adding cross currency using the default Quantity.plus method" in {
    intercept[UnsupportedOperationException] {
      assert(USD(1).plus(JPY(2)) == CAD(3))
    }
  }

  it should "throw a NoSuchExchangeRateException when subtracting cross currency values with only the defaultMoneyContext in scope" in {
    intercept[NoSuchExchangeRateException] {
      assert(USD(1) - JPY(2) == CAD(3))
    }
  }

  it should "throw an UnsupportedOperationException when subtracting cross currency using the default Quantity.minus method" in {
    intercept[UnsupportedOperationException] {
      assert(USD(1).minus(JPY(2)) == CAD(3))
    }
  }

  it should "throw a NoSuchExchangeRateException when dividing cross currency values with only the defaultMoneyContext in scope" in {
    intercept[NoSuchExchangeRateException] {
      assert(USD(10) / JPY(2) == 5)
    }
  }

  it should "throw an UnsupportedOperationException when dividing cross currency using the default Quantity.minus method" in {
    intercept[UnsupportedOperationException] {
      assert(USD(10).divide(JPY(2)) == 5)
    }
  }

  it should "return proper results when multiplying by Double" in {
    assert(USD(10) * 2 == USD(20))
    assert(JPY(23.50) * 3 == JPY(70.50))
  }

  it should "return proper results when multiplying by BigDecimal" in {
    assert(USD(10) * BigDecimal(2) == USD(20))
    assert(JPY(23.50) * BigDecimal(3) == JPY(70.50))
  }

  it should "return the equal value in the other currency when multiplied by a CurrencyExchangeRate" in {
    val rate = CurrencyExchangeRate(USD(1), JPY(100))
    assert(USD(10) * rate == JPY(1000))
  }

  it should "return proper results when dividing by Double" in {
    assert(USD(10) / 2 == USD(5))
    assert(JPY(75) / 3 == JPY(25))
  }

  it should "return proper results when dividing by BigDecimal" in {
    assert(USD(10) / BigDecimal(2) == USD(5))
    assert(JPY(75) / BigDecimal(3) == JPY(25))
  }

  it should "return proper results when remaindering by Double" in {
    assert(USD(10) % 4 == USD(2))
    assert(JPY(75) % 3 == JPY(0))
  }

  it should "return proper results when remaindering by BigDecimal" in {
    assert(USD(10) % BigDecimal(4) == USD(2))
    assert(JPY(75) % BigDecimal(3) == JPY(0))
  }

  it should "return proper results when dividing and remaindering by Double" in {
    assert(USD(10) /% 3 == (USD(3), USD(1)))
    assert(JPY(75) /% 3 == (JPY(25), JPY(0)))
  }

  it should "return proper results when dividing and remaindering by BigDecimal" in {
    assert(USD(10) /% BigDecimal(3) == (USD(3), USD(1)))
    assert(JPY(75) /% BigDecimal(3) == (JPY(25), JPY(0)))
  }

  it should "return proper result when dividing by a Money value" in {
    val r1 = CurrencyExchangeRate(USD(1), JPY(100))
    val r2 = CurrencyExchangeRate(USD(1), EUR(.75))
    implicit val moneyContext = MoneyContext(USD, defaultCurrencySet, List(r1, r2))

    assert(USD(10) / USD(2) == 5)
  }

  it should "return proper results when negating a value" in {
    assert(-USD(10) == USD(-10))
    assert(USD(10).negate == USD(-10))
  }

  it should "return proper results when getting the absolute value" in {
    assert(USD(10).abs == USD(10))
    assert(USD(-10).abs == USD(10))
    assert((-USD(10)).abs == USD(10))
    assert((-USD(-10)).abs == USD(10))
  }

  it should "add cross currencies with a MoneyContext and applicable rates in scope" in {
    val r1 = CurrencyExchangeRate(USD(1), JPY(100))
    val r2 = CurrencyExchangeRate(USD(1), EUR(.50))
    implicit val moneyContext = MoneyContext(USD, defaultCurrencySet, List(r1, r2))

    assert(USD(10) + JPY(500) == USD(15))
    assert(EUR(10) + USD(10) == EUR(15))
    assert(EUR(7.5) + JPY(500) == EUR(10)) // Uses indirect rate via USD

    assert(USD(10) - JPY(500) == USD(5))
    assert(EUR(10) - USD(10) == EUR(5))
    assert(EUR(7.5) - JPY(500) == EUR(5)) // Uses indirect rate via USD
  }

  it should "return an Exchange Rate on toThe a different currency" in {
    assert(JPY(100).toThe(USD(1)) == CurrencyExchangeRate(USD(1), JPY(100)))
    assert(USD(1).toThe(JPY(100)) == CurrencyExchangeRate(JPY(100), USD(1)))
  }

  it should "throw an IllegalArgumentException on toThe a same currency" in {
    intercept[IllegalArgumentException] {
      USD(100).toThe(USD(3)) == CurrencyExchangeRate(USD(100), USD(3))
    }
  }

  it should "convert currencies based on the MoneyContext" in {
    val r1 = CurrencyExchangeRate(USD(1), JPY(100))
    val r2 = CurrencyExchangeRate(USD(1), EUR(.75))
    implicit val moneyContext = MoneyContext(USD, defaultCurrencySet, List(r1, r2))

    assert((USD(1.5) in JPY) == JPY(150))
    assert((USD(1) in EUR) == EUR(0.75))
    assert((JPY(100) in USD) == USD(1))
    assert((EUR(75) in USD) == USD(100))
    assert((EUR(75) in JPY) == JPY(10000)) // Uses indirect rate via USD
    assert((JPY(100) in EUR) == EUR(0.75)) // Uses indirect rate via USD

    assert((USD(1.5) to JPY).toDouble == 150)
    assert((USD(1) to EUR).toDouble == 0.75)
    assert((JPY(100) to USD).toDouble == 1)
    assert((EUR(75) to USD).toDouble == 100)
    assert((EUR(75) to JPY).toDouble == 10000) // Uses indirect rate via USD
    assert((JPY(100) to EUR).toDouble == 0.75) // Uses indirect rate via USD
  }

  it should "not compile cross currency conversions with no implicit MoneyContext in scope" in {
    //  assert((CAD(1.5) in USD) == USD(1))
    //  assert((CAD(1.5) to USD).toDouble == 1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(USD(10).toString == "10.00 USD")
    assert(USD(10).toFormattedString == "$10.00")
  }

  it should "return a properly sorted list of Moneys" in {
    implicit val fxContext = MoneyContext(USD, defaultCurrencySet, Nil)
    val unorderedList = List(Money(10), Money(100), Money(1))
    val orderedList = unorderedList.sorted
    assert(orderedList.head == Money(1))
    assert(orderedList.tail.head == Money(10))
    assert(orderedList.tail.tail.head == Money(100))
  }

  it should "serialize to and de-serialize Currency from Json" in {
    implicit val formats = DefaultFormats
    val ser = Serialization.write(USD)
    val des = Serialization.read[Currency](ser)
    assert(des == USD)
  }

  it should "serialize to and de-serialize Money from Json" in {
    implicit val formats = DefaultFormats.withBigDecimal
    val x = USD(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Money](ser)
    assert(x == des)
  }

  behavior of "MoneyConversions"

  it should "provide aliases for single unit values" in {
    import MoneyConversions._
    assert(dollar == USD(1))
    assert(euro == EUR(1))
    assert(yen == JPY(1))
  }

  it should "provide implicit conversion from Double" in {
    import MoneyConversions._

    implicit val moneyContext = MoneyContext(USD, defaultCurrencySet, Nil)
    val d = 10d

    assert(d.money == Money(d, USD))
    assert(d.XAU == Money(d, squants.market.XAU))
    assert(d.USD == Money(d, squants.market.USD))
    assert(d.dollars == Money(d, squants.market.USD))
    assert(d.cents == Money(d / 100d, squants.market.USD))
    assert(d.EUR == Money(d, squants.market.EUR))
    assert(d.euros == Money(d, squants.market.EUR))
    assert(d.JPY == Money(d, squants.market.JPY))
    assert(d.yen == Money(d, squants.market.JPY))
    assert(d.GBP == Money(d, squants.market.GBP))
    assert(d.poundSterling == Money(d, squants.market.GBP))
    assert(d.CHF == Money(d, squants.market.CHF))
    assert(d.swissFrancs == Money(d, squants.market.CHF))
    assert(d.AUD == Money(d, squants.market.AUD))
    assert(d.CAD == Money(d, squants.market.CAD))
    assert(d.SEK == Money(d, squants.market.SEK))
    assert(d.NOK == Money(d, squants.market.NOK))
    assert(d.NZD == Money(d, squants.market.NZD))
    assert(d.BTC == Money(d, squants.market.BTC))
    assert(d.bitCoin == BTC(d))
  }
}
