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
import squants.mass.Kilograms
import squants.QuantityStringParseException

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MoneySpec extends FlatSpec with Matchers {

  behavior of "Money and its Units of Measure"

  it should "create values using factories that take Currency" in {
    Money(BigDecimal(10), USD) should be(Money(10, USD))
    Money(10, USD) should be(Money(10, USD))
  }

  it should "create values using factories that take Currency Code (String)" in {
    Money(BigDecimal(10), "USD") should be(Money(10, USD))
    Money(10, "USD") should be(Money(10, USD))
  }

  it should "create values using Currency (UOM) factories" in {
    USD(BigDecimal(10)) should be(Money(10, USD))
    USD(10) should be(Money(10, USD))
  }

  it should "create values using 'no currency' factories with an implicit MoneyContext in scope" in {
    implicit val moneyContext = MoneyContext(USD, defaultCurrencySet, Nil)
    Money(BigDecimal(10)) should be(USD(10))
    Money(10) should be(USD(10))
  }

  it should "create values from formatted strings" in {
    Money("500 USD").get should be(USD(500))
    Money("500USD").get should be(USD(500))
    Money("5.50USD").get should be(USD(5.5))
    Money("500 EUR").get should be(EUR(500))
    Money("10000.0 JPY").get should be(JPY(10000))
    Money("23.45 CAD").get should be(CAD(23.45))
    Money("23.45 ZZZ").failed.get should be(QuantityStringParseException("Unable to parse Money", "23.45 ZZZ"))
  }

  it should "return proper result when comparing like currencies" in {
    USD(10) should be(USD(10))
    USD(10) == USD(10) should be(right = true)
    USD(10) != USD(9.99) should be(right = true)
    USD(10) == USD(9.99) should be(right = false)
    USD(10) != USD(10) should be(right = false)

    USD(10) > USD(9.99) should be(right = true)
    USD(10) >= USD(9.99) should be(right = true)
    USD(10) >= USD(10) should be(right = true)

    USD(9.99) < USD(10) should be(right = true)
    USD(9.99) <= USD(10) should be(right = true)
    USD(10) <= USD(10) should be(right = true)
  }

  it should "return proper result when comparing dislike currencies with no MoneyContext in scope" in {
    USD(10) == JPY(10) should be(right = false)
    USD(10) != JPY(10) should be(right = true)
  }

  it should "return proper result when comparing dislike currencies with a MoneyContext in scope" in {
    implicit val moneyContext = defaultMoneyContext.withExchangeRates(List(USD(1) -> JPY(100)))

    USD(10) moneyEquals JPY(1000) should be(right = true)
    USD(10) ==# JPY(1000) should be(right = true)
    USD(10) moneyEquals JPY(2000) should be(right = false)
    USD(10) ==# JPY(2000) should be(right = false)

    USD(10) moneyNotEquals JPY(1000) should be(right = false)
    USD(10) !=# JPY(1000) should be(right = false)
    USD(10) moneyNotEquals JPY(2000) should be(right = true)
    USD(10) !=# JPY(2000) should be(right = true)

    USD(1) moneyCompare JPY(100) should be(0)
    USD(1) moneyCompare JPY(1000) should be(-1)
    USD(1) moneyCompare JPY(10) should be(1)

    USD(1) ># JPY(99) should be(right = true)
    USD(1) ># JPY(100) should be(right = false)
    USD(1) ># JPY(101) should be(right = false)

    USD(1) >=# JPY(99) should be(right = true)
    USD(1) >=# JPY(100) should be(right = true)
    USD(1) >=# JPY(101) should be(right = false)

    USD(1) <# JPY(99) should be(right = false)
    USD(1) <# JPY(100) should be(right = false)
    USD(1) <# JPY(101) should be(right = true)

    USD(1) <=# JPY(99) should be(right = false)
    USD(1) <=# JPY(100) should be(right = true)
    USD(1) <=# JPY(101) should be(right = true)
  }

  it should "compare a non-null Quantity to a null and return a proper result" in {
    val x = USD(2.1)
    x == null should be(right = false)
    null == x should be(right = false)
    x != null should be(right = true)
    null != x should be(right = true)
  }

  it should "compare a null Quantity to null and return a proper result" in {
    val x: Money = null
    x == null should be(right = true)
    null == x should be(right = true)
    x != null should be(right = false)
    null != x should be(right = false)
  }

  it should "compare a null Quantity to a non-null Quantity" in {
    val x = null
    val y = USD(2.1)
    x == y should be(right = false)
    y == x should be(right = false)
  }

  it should "not equal an equivalent value of a different type" in {
    val x = USD(2.1)
    val y = Kilograms(2.1)
    x.equals(y) should be(right = false)
    x == y should be(right = false)
    x != y should be(right = true)
  }

  it should "return proper result on max/min operation with an implicit MoneyContext in scope" in {
    val r1 = CurrencyExchangeRate(USD(1), JPY(100))
    val r2 = CurrencyExchangeRate(USD(1), EUR(.75))
    implicit val moneyContext = MoneyContext(USD, defaultCurrencySet, List(r1, r2))
    val x = USD(100)
    val y = JPY(100)
    x.moneyMax(y) should be(x)
    y.moneyMin(x) should be(y)
  }

  it should "return proper result when adding like currencies with no MoneyContext in scope" in {
    USD(1) + USD(2) should be(USD(3))
    USD(1).plus(USD(2)) should be(USD(3))
  }

  it should "return proper result when subtracting like currencies with no MoneyContext in scope" in {
    USD(5) - USD(2) should be(USD(3))
    USD(5).minus(USD(2)) should be(USD(3))
  }

  it should "return proper result when dividing like currencies with no MoneyContext in scope" in {
    USD(10) / USD(2) should be(5)
    USD(10).divide(USD(2)) should be(5)
  }

  it should "throw a NoSuchExchangeRateException when adding cross currency values with only the defaultMoneyContext in scope" in {
    intercept[NoSuchExchangeRateException] {
      USD(1) + JPY(2) should be(CAD(3))
    }
  }

  it should "throw an UnsupportedOperationException when adding cross currency using the default Quantity.plus method" in {
    intercept[UnsupportedOperationException] {
      USD(1).plus(JPY(2)) should be(CAD(3))
    }
  }

  it should "throw a NoSuchExchangeRateException when subtracting cross currency values with only the defaultMoneyContext in scope" in {
    intercept[NoSuchExchangeRateException] {
      USD(1) - JPY(2) should be(CAD(3))
    }
  }

  it should "throw an UnsupportedOperationException when subtracting cross currency using the default Quantity.minus method" in {
    intercept[UnsupportedOperationException] {
      USD(1).minus(JPY(2)) should be(CAD(3))
    }
  }

  it should "throw a NoSuchExchangeRateException when dividing cross currency values with only the defaultMoneyContext in scope" in {
    intercept[NoSuchExchangeRateException] {
      USD(10) / JPY(2) should be(5)
    }
  }

  it should "throw an UnsupportedOperationException when dividing cross currency using the default Quantity.minus method" in {
    intercept[UnsupportedOperationException] {
      USD(10).divide(JPY(2)) should be(5)
    }
  }

  it should "return proper results when multiplying by Double" in {
    USD(10) * 2 should be(USD(20))
    JPY(23.50) * 3 should be(JPY(70.50))
  }

  it should "return proper results when multiplying by BigDecimal" in {
    USD(10) * BigDecimal(2) should be(USD(20))
    JPY(23.50) * BigDecimal(3) should be(JPY(70.50))
  }

  it should "return the equal value in the other currency when multiplied by a CurrencyExchangeRate" in {
    val rate = CurrencyExchangeRate(USD(1), JPY(100))
    USD(10) * rate should be(JPY(1000))
  }

  it should "return proper results when dividing by Double" in {
    USD(10) / 2 should be(USD(5))
    JPY(75) / 3 should be(JPY(25))
  }

  it should "return proper results when dividing by BigDecimal" in {
    USD(10) / BigDecimal(2) should be(USD(5))
    JPY(75) / BigDecimal(3) should be(JPY(25))
  }

  it should "return proper results when remaindering by Double" in {
    USD(10) % 4 should be(USD(2))
    JPY(75) % 3 should be(JPY(0))
  }

  it should "return proper results when remaindering by BigDecimal" in {
    USD(10) % BigDecimal(4) should be(USD(2))
    JPY(75) % BigDecimal(3) should be(JPY(0))
  }

  it should "return proper results when dividing and remaindering by Double" in {
    USD(10) /% 3 should be((USD(3), USD(1)))
    JPY(75) /% 3 should be((JPY(25), JPY(0)))
  }

  it should "return proper results when dividing and remaindering by BigDecimal" in {
    USD(10) /% BigDecimal(3) should be((USD(3), USD(1)))
    JPY(75) /% BigDecimal(3) should be((JPY(25), JPY(0)))
  }

  it should "return proper result when dividing by a Money of like Currency" in {
    USD(10) / USD(2) should be(5)
  }

  it should "return proper result when dividing by a Money of another Currency" in {
    val r1 = CurrencyExchangeRate(USD(1), JPY(100))
    val r2 = CurrencyExchangeRate(USD(1), EUR(.75))
    implicit val moneyContext = MoneyContext(USD, defaultCurrencySet, List(r1, r2))

    USD(10) / JPY(200) should be(5)
    EUR(7.5) / USD(10) should be(1)
  }

  it should "return proper results when negating a value" in {
    -USD(10) should be(USD(-10))
    USD(10).negate should be(USD(-10))
  }

  it should "return proper results when getting the absolute value" in {
    USD(10).abs should be(USD(10))
    USD(-10).abs should be(USD(10))
    (-USD(10)).abs should be(USD(10))
    (-USD(-10)).abs should be(USD(10))
  }

  it should "add cross currencies with a MoneyContext and applicable rates in scope" in {
    val r1 = CurrencyExchangeRate(USD(1), JPY(100))
    val r2 = CurrencyExchangeRate(USD(1), EUR(.50))
    implicit val moneyContext = MoneyContext(USD, defaultCurrencySet, List(r1, r2))

    USD(10) + JPY(500) should be(USD(15))
    EUR(10) + USD(10) should be(EUR(15))
    EUR(7.5) + JPY(500) should be(EUR(10)) // Uses indirect rate via USD

    USD(10) - JPY(500) should be(USD(5))
    EUR(10) - USD(10) should be(EUR(5))
    EUR(7.5) - JPY(500) should be(EUR(5)) // Uses indirect rate via USD
  }

  it should "return an Exchange Rate on toThe (->) a different currency" in {
    JPY(100) toThe USD(1) should be(CurrencyExchangeRate(USD(1), JPY(100)))
    USD(1) toThe JPY(100) should be(CurrencyExchangeRate(JPY(100), USD(1)))
    JPY(100) -> USD(1) should be(CurrencyExchangeRate(USD(1), JPY(100)))
    USD(1) -> JPY(100) should be(CurrencyExchangeRate(JPY(100), USD(1)))
  }

  it should "return an Exchange Rate on Currency./" in {
    USD / CAD(1.05) should be(CurrencyExchangeRate(USD(1), CAD(1.05)))
    USD / JPY(100) should be(CurrencyExchangeRate(USD(1), JPY(100)))
  }

  it should "throw an IllegalArgumentException on toThe (->) applied to the same currency" in {
    intercept[IllegalArgumentException] {
      USD(100).toThe(USD(3)) == CurrencyExchangeRate(USD(100), USD(3))
    }
    intercept[IllegalArgumentException] {
      USD(100) -> USD(3) == CurrencyExchangeRate(USD(100), USD(3))
    }
  }

  it should "throw an IllegalArgumentException on Currency./ applied to the same currency" in {
    intercept[IllegalArgumentException] {
      USD / USD(100) == CurrencyExchangeRate(USD(1), USD(100))
    }
  }

  it should "convert currencies based on the MoneyContext" in {
    val r1 = CurrencyExchangeRate(USD(1), JPY(100))
    val r2 = CurrencyExchangeRate(USD(1), EUR(.75))
    implicit val moneyContext = MoneyContext(USD, defaultCurrencySet, List(r1, r2))

    (USD(1.5) in JPY) should be(JPY(150))
    (USD(1) in EUR) should be(EUR(0.75))
    (JPY(100) in USD) should be(USD(1))
    (EUR(75) in USD) should be(USD(100))
    (EUR(75) in JPY) should be(JPY(10000)) // Uses indirect rate via USD
    (JPY(100) in EUR) should be(EUR(0.75)) // Uses indirect rate via USD

    (USD(1.5) to JPY).toDouble should be(150)
    (USD(1) to EUR).toDouble should be(0.75)
    (JPY(100) to USD).toDouble should be(1)
    (EUR(75) to USD).toDouble should be(100)
    (EUR(75) to JPY).toDouble should be(10000) // Uses indirect rate via USD
    (JPY(100) to EUR).toDouble should be(0.75) // Uses indirect rate via USD
  }

  it should "not compile cross currency conversions with no implicit MoneyContext in scope" in {
    //  (CAD(1.5) in USD) should be(USD(1))
    //  (CAD(1.5) to USD).toDouble should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    USD(10).toString should be("10.00 USD")
    USD(10).toFormattedString should be("$10.00")
  }

  it should "return properly formatted strings in different currency with an implicit MoneyContext in scope" in {
    implicit val moneyContext = MoneyContext(USD, defaultCurrencySet, Seq(USD(1) toThe JPY(100)))
    USD(10).toString(JPY) should be("1000.00 JPY")
    USD(10).toFormattedString(JPY) should be("¥1000.00")
  }

  it should "return a properly sorted list of Moneys" in {
    implicit val fxContext = MoneyContext(USD, defaultCurrencySet, Nil)
    val unorderedList = List(Money(10), Money(100), Money(1))
    val orderedList = unorderedList.sorted
    orderedList.head should be(Money(1))
    orderedList.tail.head should be(Money(10))
    orderedList.tail.tail.head should be(Money(100))
  }

  behavior of "MoneyConversions"

  it should "provide aliases for single unit values" in {
    import MoneyConversions._
    dollar should be(USD(1))
    euro should be(EUR(1))
    yen should be(JPY(1))
  }

  it should "provide implicit conversion from Double" in {
    import MoneyConversions._

    implicit val moneyContext = MoneyContext(USD, defaultCurrencySet, Nil)
    val d = 10d

    d.money should be(Money(d, USD))
    d.XAU should be(Money(d, squants.market.XAU))
    d.USD should be(Money(d, squants.market.USD))
    d.dollars should be(Money(d, squants.market.USD))
    d.cents should be(Money(d / 100d, squants.market.USD))
    d.EUR should be(Money(d, squants.market.EUR))
    d.euros should be(Money(d, squants.market.EUR))
    d.JPY should be(Money(d, squants.market.JPY))
    d.yen should be(Money(d, squants.market.JPY))
    d.GBP should be(Money(d, squants.market.GBP))
    d.poundSterling should be(Money(d, squants.market.GBP))
    d.CHF should be(Money(d, squants.market.CHF))
    d.swissFrancs should be(Money(d, squants.market.CHF))
    d.AUD should be(Money(d, squants.market.AUD))
    d.CAD should be(Money(d, squants.market.CAD))
    d.SEK should be(Money(d, squants.market.SEK))
    d.NOK should be(Money(d, squants.market.NOK))
    d.NZD should be(Money(d, squants.market.NZD))
    d.BTC should be(Money(d, squants.market.BTC))
    d.bitCoin should be(BTC(d))
  }

  it should "provide Numeric support within a MoneyContext with no Exchange Rates" in {
    import MoneyConversions._

    implicit val moneyContext = defaultMoneyContext
    implicit val moneyNum = new MoneyNumeric()

    val ms = List(USD(100), USD(10), USD(1))
    ms.sum should be(USD(111))

    intercept[NoSuchExchangeRateException] {
      val ms2 = List(USD(100), CAD(100), JPY(100))
      ms2.sum should be(USD(111))
    }
  }

  it should "provide Numeric support within a MoneyContext with applicable Exchange Rates" in {
    import MoneyConversions._

    implicit val moneyContext = defaultMoneyContext.withExchangeRates(List(USD(1) -> CAD(10), USD(1) -> JPY(100)))
    implicit val moneyNum = new MoneyNumeric()

    val ms = List(USD(100), USD(10), USD(1))
    ms.sum should be(USD(111))

    val ms2 = List(USD(100), CAD(100), JPY(100))
    ms2.sum should be(USD(111))
  }
}
