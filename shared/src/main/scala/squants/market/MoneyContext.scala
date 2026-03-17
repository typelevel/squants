/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.market

/**
 * MoneyContext
 *
 * Provides a context for Money specific operations.
 *
 * When provided as an implicit parameter, the defaultCurrency will be used by the
 * Money factory when no other currency is provided.
 *
 * Provides for cross-currency conversions.
 *
 * Will act as an implicit parameter to cross currency operations to allow for
 * easy conversions
 *
 * @author  garyKeorkunian
 * @since   0.1
 * @param defaultCurrency Currency used when none is supplied to the Money factory
 * @param rates Collection of Exchange Rates used for currency conversions
 */
case class MoneyContext(
    defaultCurrency: Currency,
    currencies: Set[Currency],
    rates: Seq[CurrencyExchangeRate],
    allowIndirectConversions: Boolean = true) {

  lazy val currencyMap = currencies.map { (c: Currency) => c.code -> c }.toMap

  /**
    * Custom implementation using SortedSets to ensure consistent output
    * @return String representation of this instance
    */
  override def toString: String = string
  private lazy val string = {
    val cSet = currencies.map(_.toString).toSeq.sorted.mkString(",")
    val rSet = rates.map(_.toString).sorted.mkString(",")
    s"MoneyContext(DefaultCurrency(${defaultCurrency.code}),Currencies($cSet),ExchangeRates($rSet),AllowIndirectConversions($allowIndirectConversions))"
  }

  /**
   * Returns an Option on an exchange rate if a direct rate exists, otherwise None
   *
   * @param curA Currency A
   * @param curB Currency B
   * @return
   */
  def directRateFor(curA: Currency, curB: Currency): Option[CurrencyExchangeRate] = {
    rates.find(r =>
      r.base.currency == curA && r.counter.currency == curB ||
        r.base.currency == curB && r.counter.currency == curA)
  }

  /**
   * Return an Option on an exchange rate.  If a direct rate exists an Option on that will be returned.
   * Otherwise, if a cross rate can be determined (1 hop limit), it will be created and returned in an Option.
   * Otherwise, None will be returned
   *
   * @param curA Currency A
   * @param curB Currency B
   * @return
   */
  def indirectRateFor(curA: Currency, curB: Currency): Option[CurrencyExchangeRate] = {

    // TODO Improve this to attempt to use defaultCurrency first

    directRateFor(curA, curB) match {
      case Some(rate) => Some(rate)
      case _ =>
        val ratesWithCurA = rates.filter(r => r.base.currency == curA || r.counter.currency == curA)
        val ratesWithCurB = rates.filter(r => r.base.currency == curB || r.counter.currency == curB)

        val curs = for {
          cur <- currencies
          if ratesWithCurA.map(_.base.currency).contains(cur) || ratesWithCurA.map(_.counter.currency).contains(cur)
          if ratesWithCurB.map(_.base.currency).contains(cur) || ratesWithCurB.map(_.counter.currency).contains(cur)
        } yield cur

        curs.headOption match {
          case Some(cur) => Some(CurrencyExchangeRate(convert(cur(1), curA), convert(cur(1), curB)))
          case None      => None
        }
    }
  }

  /**
   * Converts a Money value to the specified currency.
   *
   * The conversion first attempts to use an existing exchange rate for the two currencies in question.
   * If no direct exchange works, a cross rate (limited to 1 hop) will be calculated and used.
   * If no cross rate can be calculated a NoSuchElementException is thrown
   *
   * @param money  Money to be converted
   * @param currency Currency to be converted to
   * @return
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  def convert(money: Money, currency: Currency): Money = {

    if (money.currency == currency) money
    else directRateFor(money.currency, currency) match {
      case Some(rate) => rate.convert(money)
      case _ if allowIndirectConversions => indirectRateFor(money.currency, currency) match {
        case Some(crossRate) => crossRate.convert(money)
        case None            => throw new NoSuchExchangeRateException(s"Rate for currency pair (${money.currency} / $currency)")
      }
      case _ => throw new NoSuchExchangeRateException(s"Rate for currency pair (${money.currency} / $currency)")
    }
  }

  /**
   * Adds two money values that may or may not be in the same currency.
   *
   * The result will be in the same currency as the first parameter.
   *
   * @param moneyA Money A
   * @param moneyB Money B
   * @return
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  def add(moneyA: Money, moneyB: Money): Money = moneyA.currency(moneyA.amount + convert(moneyB, moneyA.currency).amount)

  /**
   * Subtracts two money values that may or may not be in the same currency
   *
   * The result will be in the same currency as the first parameter.
   *
   * @param moneyA Money A
   * @param moneyB Money B
   * @return
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  def subtract(moneyA: Money, moneyB: Money): Money = moneyA.currency(moneyA.amount - convert(moneyB, moneyA.currency).amount)

  /**
   * Divides two money value that may or may not be in the same currency after converting the second to the first
   *
   * @param moneyA Money A
   * @param moneyB Money B
   * @return
   */
  def divide(moneyA: Money, moneyB: Money): BigDecimal = moneyA.amount / convert(moneyB, moneyA.currency).amount

  /**
   * Performs a standard compare on two money values that may or may not be in the same currency
   * @param moneyA Money A
   * @param moneyB Money B
   * @return
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  def compare(moneyA: Money, moneyB: Money): Int =
    if (moneyA.amount > convert(moneyB, moneyA.currency).amount) 1
    else if (moneyA.amount < convert(moneyB, moneyA.currency).amount) -1
    else 0

  /**
    * Create a copy of this context with additional currencies added to the existing set
    * @param additionalCurrencies Set[Currency]
    * @return
    */
  def withAdditionalCurrencies(additionalCurrencies: Set[Currency]) = copy (currencies = currencies ++ additionalCurrencies)

  /**
    * Create a copy of this context with a new list of rates
    * @param rates List[CurrencyExchangeRate]
    * @return
    */
  def withExchangeRates(rates: List[CurrencyExchangeRate]) = copy(rates = rates)
}
