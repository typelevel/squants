/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.market

import org.scalatest.{ FlatSpec, Matchers }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class CurrencyExchangeRateSpec extends FlatSpec with Matchers {

  behavior of "CurrencyExchangeRate"

  it should "create CurrencyExchangeRates using the default factory method" in {
    val rate = CurrencyExchangeRate(USD(1), JPY(100))
    assert(rate.base == USD(1))
    assert(rate.counter == JPY(100))
  }

  it should "properly return a Currency Exchange Rate" in {
    val rate = CurrencyExchangeRate(USD(1), JPY(100))
    assert(rate.rate == 100)
  }

  it should "properly return a converted Money value" in {
    val rate1 = CurrencyExchangeRate(USD(1), JPY(100))
    val rate2 = CurrencyExchangeRate(USD(1), EUR(75))

    // using the convert method directly
    assert(rate1.convert(JPY(100)) == USD(1))
    assert(rate1.convert(USD(1)) == JPY(100))
    assert(rate2.convert(EUR(75)) == USD(1))
    assert(rate2.convert(USD(1)) == EUR(75))

    // using the * operator
    assert(rate1 * JPY(100) == USD(1))
    assert(rate1 * USD(1) == JPY(100))
    assert(rate2 * EUR(75) == USD(1))
    assert(rate2 * USD(1) == EUR(75))

    // using the methods inherited from Ratio
    assert(rate1.convertToBase(JPY(100)) == USD(1))
    assert(rate1.convertToCounter(USD(1)) == JPY(100))
    assert(rate2.convertToBase(EUR(75)) == USD(1))
    assert(rate2.convertToCounter(USD(1)) == EUR(75))
  }

  it should "properly return a string formatted as an FX quote" in {
    val rate = CurrencyExchangeRate(USD(1), JPY(100))
    assert(rate.toString == "USD/JPY 100.0")
  }
}
