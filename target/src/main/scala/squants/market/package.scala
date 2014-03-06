/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

/**
 * Squants Market API
 *
 * Market Types are similar but not quite the same as other quantities in the library.
 *
 * The primary type, [[squants.market.Money]], is derived from Quantity, and its Units of Measure are Currencies.
 * However, because the conversion multipliers between units can not be predefined, many of the behaviors have been
 * overridden and augmented to realize correct behavior.
 *
 * [[squants.market.Price]]s represent a Ratio between Money and some other Quantity.  Prices can be created from
 * ratios of Money and a Quantity
 * {{{
 *   val money: Money = USD(10)
 *   val length: Length = Meters(1)
 *   val price: Price[Length] = money / length
 * }}}
 * Multiplying a Price * Quantity will yield Money amount that represents the cost of the quantity
 * {{{
 *   val cost: Money = price * Meters(3.8)
 * }}}
 * and multiplying Price * Money will yield the corresponding Quantity amount
 * {{{
 *   val budget: Money = USD(250)
 *   val quote: Length = price * budget
 * }}}
 *
 * [[squants.market.CurrencyExchangeRate]]s represent conversion rates between currencies.
 * Use them to explicitly convert Money values in one currency to values in another.
 *
 * [[squants.market.MoneyContext]] provide the implicit context necessary to perform cross-currency operations
 * on Money values with conversions automatically applied.
 *
 * Some binary math operations will work on Moneys of like Currency with no MoneyContext in scope.
 * Attempts to perform these operations on Moneys of dissimilar currencies will throw an exception at runtime.
 *
 * Other operations, including direct conversions to other currencies, require a MoneyContext and will not compile without it.
 * However, there is no compile time check to determine if the correct exchange rates will be available at runtime.
 * Operation requiring conversion without the required rates available will throw a NoSuchExchangeRateException at runtime.
 *
 * The defaultMoneyContext uses the USD as the default and provides a list of ~20 common currencies, and NO exchange rates.
 * If your application requires something different you should initialize your own implicit MoneyContext
 *
 * @author  garyKeorkunian
 * @since   0.1
 */
package object market {

  /* Supported Currencies */
  lazy val USD = Currency("USD", "US Dollar", "$", 2)
  lazy val ARS = Currency("ARS", "Argentinean Peso", "$", 2)
  lazy val AUD = Currency("AUD", "Australian Dollar", "$", 2)
  lazy val BRL = Currency("BRL", "Brazilian Real", "R$", 2)
  lazy val CAD = Currency("CAD", "Canadian Dollar", "$", 2)
  lazy val CHF = Currency("CHF", "Swiss Franc", "CHF", 2)
  lazy val CLP = Currency("CLP", "Chilean Peso", "¥", 2)
  lazy val CNY = Currency("CNY", "Chinese Yuan Renmimbi", "¥", 2)
  lazy val CZK = Currency("CZK", "Czech Republic Koruny", "Kč", 2)
  lazy val DKK = Currency("DKK", "Danish Kroner", "kr", 2)
  lazy val EUR = Currency("EUR", "Euro", "€", 2)
  lazy val GBP = Currency("GBP", "British Pound", "£", 2)
  lazy val HKD = Currency("HKD", "Hong Kong Dollar", "$", 2)
  lazy val INR = Currency("INR", "Indian Rupee", "Rp", 2)
  lazy val JPY = Currency("JPY", "Japanese Yen", "¥", 2)
  lazy val KRW = Currency("KRW", "South Korean Won", "kr", 2)
  lazy val MXN = Currency("MXN", "Mexican Peso", "$", 2)
  lazy val NOK = Currency("NOK", "Norwegian Krone", "kr", 2)
  lazy val NZD = Currency("NZD", "New Zealand Dollar", "$", 2)
  lazy val RUB = Currency("RUB", "Russian Ruble", "руб", 2)
  lazy val SEK = Currency("SEK", "Swedish Kroner", "kr", 2)
  lazy val XAG = Currency("XAG", "Silver", "oz", 4)
  lazy val XAU = Currency("XAU", "Gold", "oz", 4)
  lazy val BTC = Currency("BTC", "BitCoin", "B", 15)

  lazy val defaultCurrencyMap: Map[String, Currency] =
    List(USD, ARS, AUD, BRL, CAD, CHF, CLP, CNY, CZK, DKK, EUR,
      GBP, HKD, INR, JPY, KRW, MXN, NOK, NZD, RUB, SEK, XAG, XAU, BTC)
      .map(c ⇒ (c.code, c)).toMap

  lazy val defaultCurrencySet = defaultCurrencyMap.map(_._2).toSet
  lazy val defaultMoneyContext = MoneyContext(USD, defaultCurrencySet, Nil)

  class NoSuchExchangeRateException(val s: String) extends Exception
}
