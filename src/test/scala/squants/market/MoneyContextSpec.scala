/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.market

import org.scalatest.{ Matchers, FlatSpec }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MoneyContextSpec extends FlatSpec with Matchers {

  val defCur = USD
  val rates = List(
    CurrencyExchangeRate(EUR(1), USD(1.25)),
    CurrencyExchangeRate(USD(1), JPY(100)),
    CurrencyExchangeRate(GBP(1), USD(1.6686)),
    CurrencyExchangeRate(USD(1), CAD(1.1126)),
    CurrencyExchangeRate(USD(1), HKD(7.7565)),
    CurrencyExchangeRate(USD(1), CNY(6.0915)),
    CurrencyExchangeRate(AUD(1), USD(0.8961)),
    CurrencyExchangeRate(XAU(1), USD(1200.00)),
    CurrencyExchangeRate(XAG(1), USD(20.00)),
    CurrencyExchangeRate(CHF(1), NOK(40)))

  val moneyContext = MoneyContext(defCur, defaultCurrencySet, rates)

  behavior of "MoneyContext"

  it should "initialize with appropriate values" in {
    assert(moneyContext.defaultCurrency == defCur)
    assert(moneyContext.rates == rates)
  }

  it should "properly create Money using the default currency with an implicit MoneyContext in scope" in {
    implicit val moneyContext = MoneyContext(defCur, defaultCurrencySet, rates)
    assert(Money(10.22) == defCur(10.22))
  }

  it should "return Some(rate) for a given currency pair for which there is a rate" in {
    assert(moneyContext.directRateFor(USD, JPY) == Some(CurrencyExchangeRate(USD(1), JPY(100))))
    assert(moneyContext.directRateFor(JPY, USD) == Some(CurrencyExchangeRate(USD(1), JPY(100))))
  }

  it should "return None for a given currency pair for which there is no rate" in {
    assert(moneyContext.directRateFor(CAD, AUD) == None)
    assert(moneyContext.directRateFor(EUR, JPY) == None)
  }

  it should "return Some(rate) for a given currency pair for which there is a direct rate" in {
    assert(moneyContext.indirectRateFor(USD, JPY) == Some(CurrencyExchangeRate(USD(1), JPY(100))))
    assert(moneyContext.indirectRateFor(JPY, USD) == Some(CurrencyExchangeRate(USD(1), JPY(100))))
  }

  it should "return Some(rate) for a given currency pair for which there is no direct, but an indirect rate" in {
    assert(moneyContext.indirectRateFor(XAG, XAU).get.rate == CurrencyExchangeRate(XAG(60), XAU(1)).rate)
    assert(moneyContext.indirectRateFor(EUR, JPY).get.rate == CurrencyExchangeRate(EUR(0.80), JPY(100)).rate)
  }

  it should "return None for a given currency pair for which there is no direct or indirect rate" in {
    assert(moneyContext.indirectRateFor(CHF, USD) == None)
    assert(moneyContext.indirectRateFor(NOK, XAG) == None)
  }

  it should "properly convert Money values between currencies for which there is a direct exchange rate" in {
    assert(moneyContext.convert(USD(10), JPY) == JPY(1000.00))
    assert(moneyContext.convert(JPY(200), USD) == USD(2))
    assert(moneyContext.convert(XAG(30), USD) == USD(600))
  }

  it should "properly convert Money values between currencies for which there is only an indirect exchange rate" in {
    assert(moneyContext.convert(XAU(1), XAG) == XAG(60))
    assert(moneyContext.convert(JPY(100), EUR) == EUR(0.8))
  }

  it should "properly return the same Money when converting to the same currency" in {
    assert(moneyContext.convert(USD(12.28), USD) == USD(12.28))
    assert(moneyContext.convert(XAG(12.28), XAG) == XAG(12.28))
  }

  it should "properly return the same Money when converting to the same currency with an empty context" in {
    val context = MoneyContext(defCur, defaultCurrencySet, Nil)
    assert(context.convert(USD(12.28), USD) == USD(12.28))
    assert(context.convert(XAG(12.28), XAG) == XAG(12.28))
  }

  it should "throw a NoExchangeRateException when converting between Currencies for which no indirect rate can be determined" in {
    intercept[NoSuchExchangeRateException] {
      moneyContext.convert(CHF(100), USD)
    }
    intercept[NoSuchExchangeRateException] {
      moneyContext.convert(NOK(100), XAG)
    }
  }

  it should "throw a NoExchangeRateException when converting where an indirect rate exists but is not allowed by the context" in {
    val context = MoneyContext(defCur, defaultCurrencySet, rates, allowIndirectConversions = false)
    intercept[NoSuchExchangeRateException] {
      assert(context.convert(XAU(1), XAG) == XAG(60))
    }
    intercept[NoSuchExchangeRateException] {
      assert(context.convert(JPY(100), EUR) == EUR(0.8))
    }
  }

  it should "return Money in the first currency when adding two Moneys in different currencies" in {
    assert(moneyContext.add(USD(1), JPY(100)) == USD(2))
  }

  it should "return Money in the first currency when subtracting two Moneys in different currencies" in {
    assert(moneyContext.subtract(USD(2), JPY(100)) == USD(1))
  }

  it should "return BigDecimal when dividing two Moneys in different currencies" in {
    assert(moneyContext.divide(USD(2), JPY(100)) == 2)
  }

  it should "return Int based on a standard comparison of two Moneys in different currencies" in {
    assert(moneyContext.compare(USD(2), JPY(100)) == 1)
    assert(moneyContext.compare(USD(1), JPY(200)) == -1)
    assert(moneyContext.compare(USD(1), JPY(100)) == 0)
  }

  it should "return a copy with a new set of rates" in {
    val newRates = List(
      CurrencyExchangeRate(EUR(1), USD(1.25)),
      CurrencyExchangeRate(USD(1), JPY(100)),
      CurrencyExchangeRate(GBP(1), USD(1.6686)),
      CurrencyExchangeRate(USD(1), CAD(1.1126)))
    val newContext = moneyContext.withExchangeRates(newRates)
    assert(newContext.defaultCurrency == moneyContext.defaultCurrency)
    assert(newContext.currencies == moneyContext.currencies)
    assert(newContext.rates == newRates)
  }
}
