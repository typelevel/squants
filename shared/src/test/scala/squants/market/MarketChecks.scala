/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.market

import org.scalacheck.Properties
import squants._
import org.scalacheck.Prop._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
object MarketChecks extends Properties("Market") with QuantityChecks {

  property("Money + Money = Money") = forAll(posNum, posNum) { (a: TestData, b: TestData) =>
    USD(a + b) == USD(a) + USD(b)
  }

  property("Money - Money = Money") = forAll(posNum, posNum) { (a: TestData, b: TestData) =>
    USD(a - b) == USD(a) - USD(b)
  }

  property("Money * Double = Money") = forAll(posNum, posNum) { (a: TestData, b: TestData) =>
    USD(a * b) == USD(a) * b.toDouble
  }

  property("Money / Double = Money") = forAll(posNum, posNum) { (a: TestData, b: TestData) =>
    USD(BigDecimal(a) / b) == USD(a) / b.toDouble
  }

  property("Money = ExchangeRate * Money") = forAll(posNum, posNum) { (a: TestData, b: TestData) =>
    USD(BigDecimal(b) / a) == CurrencyExchangeRate(USD(1), JPY(a)) * JPY(b) &&
      USD(BigDecimal(b) / a) == JPY(b) * CurrencyExchangeRate(USD(1), JPY(a))
  }

  property("(variations of) Money * Double + Money * Double = (Money * Double) * 2") = forAll(posNum, posNum) { (a: TestData, b: TestData) =>
    val m = USD(a.toDouble)
    val x = b.toDouble

    x * m + x * m == ((m * x) * 2) &&
      m * x + x * m == ((m * x) * 2) &&
      m * x + m * x == ((m * x) * 2) &&
      x * m + m * x == ((m * x) * 2) &&
      x * m + x * m == ((x * m) * 2) &&
      m * x + x * m == ((x * m) * 2) &&
      m * x + m * x == ((x * m) * 2) &&
      x * m + m * x == ((x * m) * 2)
  }

  property("Money / Double + Money / Double = (Money / Double) * 2") = forAll(posNum, posNum) { (a: TestData, b: TestData) =>
    implicit val tolUSD: Money = USD(1e-30)
    val m = if (a > 0) USD(a.toDouble) else USD(1)
    val x = if (b > 0) b.toDouble else 1d

    (m / x + m / x) ~= ((m / x) * 2)
  }
}
