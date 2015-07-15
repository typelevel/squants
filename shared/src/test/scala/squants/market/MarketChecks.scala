/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.market

import org.scalacheck.Properties
import squants.QuantityChecks
import org.scalacheck.Prop._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
object MarketChecks extends Properties("Market") with QuantityChecks {

  property("Money + Money = Money") = forAll(posNum, posNum) { (a: TestData, b: TestData) ⇒
    USD(a + b) == USD(a.toDouble) + USD(b.toDouble)
  }

  property("Money - Money = Money") = forAll(posNum, posNum) { (a: TestData, b: TestData) ⇒
    USD(a - b) == USD(a.toDouble) - USD(b.toDouble)
  }

  property("Money * Double = Money") = forAll(posNum, posNum) { (a: TestData, b: TestData) ⇒
    USD(a * b) == USD(a.toDouble) * b
  }

  property("Money / Double = Money") = forAll(posNum, posNum) { (a: TestData, b: TestData) ⇒
    USD(a.toDouble / b) == USD(a.toDouble) / b
  }

  property("Money = ExchangeRate * Money") = forAll(posNum, posNum) { (a: TestData, b: TestData) ⇒
    USD(b.toDouble / a.toDouble) == CurrencyExchangeRate(USD(1), JPY(a.toDouble)) * JPY(b.toDouble) &&
      USD(b.toDouble / a.toDouble) == JPY(b.toDouble) * CurrencyExchangeRate(USD(1), JPY(a.toDouble))
  }
}
