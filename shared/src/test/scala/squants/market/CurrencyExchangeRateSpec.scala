/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.market

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class CurrencyExchangeRateSpec extends AnyFlatSpec with Matchers {

  behavior of "CurrencyExchangeRate"

  it should "create CurrencyExchangeRates using the default factory method" in {
    val rate = CurrencyExchangeRate(USD(1), JPY(100))
    rate.base should be(USD(1))
    rate.counter should be(JPY(100))
  }

  it should "properly return a Currency Exchange Rate" in {
    val rate = CurrencyExchangeRate(USD(1), JPY(100))
    rate.rate should be(100)
  }

  it should "properly return a converted Money value" in {
    val rate1 = CurrencyExchangeRate(USD(1), JPY(100))
    val rate2 = CurrencyExchangeRate(USD(1), EUR(75))

    // using the convert method directly
    rate1.convert(JPY(100)) should be(USD(1))
    rate1.convert(USD(1)) should be(JPY(100))
    rate2.convert(EUR(75)) should be(USD(1))
    rate2.convert(USD(1)) should be(EUR(75))

    // using the * operator
    rate1 * JPY(100) should be(USD(1))
    rate1 * USD(1) should be(JPY(100))
    rate2 * EUR(75) should be(USD(1))
    rate2 * USD(1) should be(EUR(75))

    // using the methods inherited from Ratio
    rate1.convertToBase(JPY(100)) should be(USD(1))
    rate1.convertToCounter(USD(1)) should be(JPY(100))
    rate2.convertToBase(EUR(75)) should be(USD(1))
    rate2.convertToCounter(USD(1)) should be(EUR(75))
  }

  it should "properly return a string formatted as an FX quote" in {
    val rate = CurrencyExchangeRate(USD(1), JPY(100))
    rate.toString should be("USD/JPY 100.0")
  }
}
