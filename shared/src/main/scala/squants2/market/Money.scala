/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants2.market

import squants2._

import java.util.Objects
import scala.language.implicitConversions
import scala.util.{ Success, Try }
import scala.math.Ordering.Implicits.infixOrderingOps
import scala.math.Numeric.Implicits.infixNumericOps

/**
 * Represents a quantity of Money[A].
 *
 * Money[A] is similar to other quantities in that it represents an amount of something - purchasing power - and
 * it is measured in units - currencies.
 *
 * The main difference is that the conversion rate between currencies can not be certain at compile.
 * (In fact it may not always be so easy to know them at runtime as well.)
 *
 * To address this diversion from the way most other quantities work, Money[A] overrides several of the standard methods
 * and operators to ensure one of two rules is followed:
 *
 *  1) this and that are in the same currency, or
 *  2) there is in an implicit MoneyContext[A] in scope (which may or may not have the applicable exchange rate)
 *
 * Methods and operations applied to Money[A]s of different currencies may throw a NoSuchExchangeRateException if the
 * implicit MoneyContext[A] does not contain the Rate(s) necessary to perform the conversion.
 *
 * The defaultMoneyContext includes USD as the default currency, a list of ~20 other currencies and NO exchange rates
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value the amount of Money[A]
 * @param currency the currency in which the Money[A] is denominated
 */
final case class Money[A: Numeric] private[squants2] (value: A, currency: Currency)
    extends Quantity[A, Money] {


  def unit: UnitOfMeasure[Money] = currency

  /**
   * Returns a string formatted with the original precision amount and the currency code
   *
   * eg USD(123.456) => "123.456 USD"
   *
   * @return String
   */
    // TODO fix formatting
   override def toString: String = value.toString + " " + currency.code

  /**
    * Converts the amount to the given currency and returns a string formatted with the original precision and the currency code
    *
    * @param c Currency
    * @param context MoneyContext[A] required for conversion
    * @return
    */
  def toString(c: Currency)(implicit context: MoneyContext[A]): String = in(c).toString

  /**
   * Returns a string formatted with the amount, rounded based on the Currency rules, and the currency symbol
   *
   * eg USD(12.4563) => "\$123.46"
   *
   * @return String
   */
//TODO  def toFormattedString: String = currency.symbol + amount.setScale(currency.formatDecimals, BigDecimal.RoundingMode.HALF_EVEN).toString

//TODO  def toFormattedString(c: Currency)(implicit context: MoneyContext[A]): String = in(c).toFormattedString

  /**
   * Adds this Money[A] to that Money[A] converted to this.currency via context
   *
   * @param that Money[A]
   * @param context MoneyContext[A] required for cross currency operations
   * @return Money[A]
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  def moneyPlus(that: Money[A])(implicit context: MoneyContext[A]): Money[A] = context.add(this, that)
  /** moneyPlus **/
  def +(that: Money[A])(implicit context: MoneyContext[A]): Money[A] = context.add(this, that)

  /**
   * Overrides Quantity.plus to only work on like currencies.
   * Cross currency additions should use Money[A]Plus
   *
   * @param that Money[A]
   * @return Money[A]
   * @throws scala.UnsupportedOperationException when attempted on cross currencies
   */
  override def plus[B](that: Money[B])(implicit f: B => A): Money[A] = that.currency match {
    case this.currency ⇒ new Money[A](num.plus(this.value, that.value), currency)
    case _             ⇒ throw new UnsupportedOperationException("plus not supported for cross-currency comparison - use Money[A]Plus")
  }

  /**
   * Subtracts that Money[A] from this Money[A] converted to this.currency via context
   *
   * @param that    Money[A]
   * @param context MoneyContext[A] required for cross currency operations
   * @return Money[A]
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  def moneyMinus(that: Money[A])(implicit context: MoneyContext[A]): Money[A] = context.subtract(this, that)
  /** Money[A]Minus **/
  def -(that: Money[A])(implicit context: MoneyContext[A]): Money[A] = context.subtract(this, that)

  /**
   * Override Quantity.minus to only work on like currencies
   * Cross currency subtractions should use Money[A]Minus
   *
   * @param that Money[A]
   * @return Money[A]
   * @throws scala.UnsupportedOperationException when attempted on cross currencies
   */
  override def minus[B](that: Money[B])(implicit f: B => A): Money[A] = that.currency match {
    case this.currency ⇒ new Money[A](this.value - that.value, currency)
    case _             ⇒ throw new UnsupportedOperationException("minus not supported for cross-currency comparison - use Money[A]Minus")
  }

//  /**
//   * Multiplies this Money[A] by that [[squants.market.CurrencyExchangeRate]] and returns the equal value in the other currency.
//   *
//   * Delegates to CurrencyExchangeRate * Money[A]
//   *
//   * @param that BigDecimal
//   * @return
//   */
//TODO  def *(that: CurrencyExchangeRate): Money[A] = that * this

  /**
   * Divides this Money by that Money and returns the ratio between the converted amounts
   *
   * @param that Money[A]
   * @param context MoneyContext[A]
   * @return
   */
  def moneyDivide(that: Money[A])(implicit context: MoneyContext[A]): A = context.divide(this, that)
  def /(that: Money[A])(implicit context: MoneyContext[A]): A = moneyDivide(that)

  /**
   * Divide this Money by another (non-Money) Quantity and return a Price
   * @param that Quantity
   * @tparam A Quantity Type
   * @return Price[A]
   */
  def /[B, Q[N] <: Quantity[N, Q]](that: Q[B]): Price[A, B, Q] = Price(this, that)



  /**
   * Divide this Money by a Price and return Quantity
   * @param that Price
   * @tparam A Quantity Type
   * @return A
   */
  def /[B, Q[N] <: Quantity[N, Q]](that: Price[A, B, Q])(implicit f: B => A): Q[A] = that.quantity.asNum[A] * (this divide that.money)

  /**
   * Override for Quantity.divide to only work on Money[A]s of like Currency
   * Cross currency subtractions should use Money[A]Minus
   *
   * @param that Money[A]
   * @return A
   */
  override def divide[B](that: Money[B])(implicit f: B => A): A = that.currency match {
    case this.currency ⇒ this.value / that.value
    case _             ⇒ throw new UnsupportedOperationException("divide not supported for cross-currency comparison - use Money[A]Divide")
  }

  /**
   * Override for Quantity.max to only work on Money[A]s of like Currency
   * @param that Money[A]
   * @return Int
   */
  override def max[B](that: Money[B])(implicit f: B => A): Money[A] = (that, that.currency) match {
    case (_: Money[A], this.currency) ⇒ super.max(that)
    case _                            ⇒ throw new UnsupportedOperationException("max not supported for cross-currency comparison - use moneyMax")
  }

  /**
   * Override for Quantity.max to only work on Money[A]s of like Currency
   * @param that Quantity
   * @return Int
   */
  override def min[B](that: Money[B])(implicit f: B => A): Money[A] = (that, that.currency) match {
    case (_: Money[A], this.currency) ⇒ super.min(that)
    case _                            ⇒ throw new UnsupportedOperationException("min not supported for cross-currency comparison - use moneyMin")
  }

  /**
   * Override for Quantity.equal to only match Money[A]s of like Currency
   * @param that Money[A] must be of matching value and unit
   * @return
   */
  override def equals(that: Any): Boolean = that match {
    case m: Money[A] ⇒ value == m.value && currency == m.currency
    case _        ⇒ false
  }

  /**
   * Override for Quantity.hashCode because Money[A] doesn't contain a primary unit
   * @return
   */
  override def hashCode(): Int = Objects.hash(value, currency)

  /**
   * Override for Quantity.compare to only work on Money[A]s of like Currency
   * @param that Money[A]
   * @return Int
   */
  override def compare(that: Money[A]): Int = that.currency match {
    case this.currency ⇒ if (this.value > that.value) 1 else if (this.value < that.value) -1 else 0
    case _             ⇒ throw new UnsupportedOperationException("Comparison between Moneys of dislike Currency is not supported")
  }

  /**
   * Supports max operation on Money[A]s of dislike Currency
   * @param that Money
   * @param moneyContext MoneyContext
   * @return
   */
  def moneyMax(that: Money[A])(implicit moneyContext: MoneyContext[A]): Money[A] = moneyContext.compare(this, that) match {
    case -1 ⇒ that
    case _  ⇒ this
  }

  /**
   * Supports min operation on Moneys of dislike Currency
   * @param that Money
   * @param moneyContext MoneyContext
   * @return
   */
  def moneyMin(that: Money[A])(implicit moneyContext: MoneyContext[A]): Money[A] = moneyContext.compare(this, that) match {
    case 1 ⇒ that
    case _ ⇒ this
  }

  /**
   * Supports equality comparisons on Moneys of dislike Currency
   * @param that Money
   * @param moneyContext MoneyContext
   * @return
   */
  def moneyEquals(that: Money[A])(implicit moneyContext: MoneyContext[A]): Boolean = moneyCompare(that) == 0

  /**
   * Supports non-equality comparisons on Moneys of dislike Currency
   * @param that Money
   * @param moneyContext MoneyContext
   * @return
   */
  def moneyNotEquals(that: Money[A])(implicit moneyContext: MoneyContext[A]): Boolean = moneyCompare(that) != 0

  /**
   * Supports compare operation on Moneys of dislike Currency
   * @param that Money
   * @param moneyContext MoneyContext
   * @return
   */
  def moneyCompare(that: Money[A])(implicit moneyContext: MoneyContext[A]): Int = moneyContext.compare(this, that)

  def ==#(that: Money[A])(implicit moneyContext: MoneyContext[A]): Boolean = moneyCompare(that) == 0
  def !=#(that: Money[A])(implicit moneyContext: MoneyContext[A]): Boolean = moneyCompare(that) != 0
  def >#(that: Money[A])(implicit moneyContext: MoneyContext[A]): Boolean = moneyCompare(that) > 0
  def >=#(that: Money[A])(implicit moneyContext: MoneyContext[A]): Boolean = moneyCompare(that) >= 0
  def <#(that: Money[A])(implicit moneyContext: MoneyContext[A]): Boolean = moneyCompare(that) < 0
  def <=#(that: Money[A])(implicit moneyContext: MoneyContext[A]): Boolean = moneyCompare(that) <= 0

  /**
   * Combines with that Money to create an [[squants.market.CurrencyExchangeRate]]
   *
   * Exchange Rates on the same currency are not supported
   *
   * val rate: CurrencyExchangeRate = JPY(100) toThe USD(1)
   *
   * @param that Money
   * @return
   * @throws scala.IllegalArgumentException if the that.currency matches this.currency
   */
  def toThe(that: Money[A]): CurrencyExchangeRate[A] = that.currency match {
    case this.currency ⇒ throw new IllegalArgumentException("Can not create Exchange Rate on matching currencies")
    case _             ⇒ CurrencyExchangeRate(that, this)
  }

  /**
   * toThe
   */
  def -> : Money[A] => CurrencyExchangeRate[A] = toThe

  /**
   * Convert this Money to a number representing the value in the supplied currency unit
   *
   * @param unit Currency
   * @param context MoneyContext required for cross currency operations
   * @return Double
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  def to(unit: Currency)(implicit context: MoneyContext[A]): A = context.convert(this, unit).value

  /**
   * Reboxes this Money value in a Money in the given Currency
   *
   * @param unit Currency
   * @param context MoneyContext required for cross currency operations
   * @return Money[A]
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  def in(unit: Currency)(implicit context: MoneyContext[A]): Money[A] = context.convert(this, unit)
}

/**
 * Factory singleton for Money[A]
 */
object  Money extends Dimension[Money] ("Money") {
  override def units: Set[UnitOfMeasure[Money]] =
    throw new UnsupportedOperationException("Money.units is not supported.  Use MarketContext to get Currencies instead.")

  override def primaryUnit: UnitOfMeasure[Money] with PrimaryUnit[Money] =
    throw new UnsupportedOperationException("Currency.primaryUnit is not supported.  Consider using MarketContext.defaultCurrency instead.")

  override def siUnit: UnitOfMeasure[Money] with SiUnit[Money] =
    throw new UnsupportedOperationException("Currency.siUnit is not supported.  Consider using MarketContext.defaultCurrency instead.")
}

/**
 * Represents a Currency, which is the Unit of Measure for Money[A]
 *
 * @param code Currency code
 * @param name Currency name
 * @param symbol Currency symbol
 * @param formatDecimals Number of decimals in standard formatting
 */
abstract class Currency(val code: String, val name: String, val symbol: String, val formatDecimals: Int) extends UnitOfMeasure[Money] {
  override def dimension: Dimension[Money] = Money
  override protected [squants2] def conversionFactor: ConversionFactor =
    throw new UnsupportedOperationException("Currency.conversionFactor is not supported.  Use MarketContext and CurrencyExchangeRates for conversions.")
  
  def apply[A: Numeric](n: A): Money[A] = Money(n, this)

  def /[A](that: Money[A])(implicit num: Numeric[A]): CurrencyExchangeRate[A] = that toThe Money(num.one, this)
  override def toString: String = code

  def canEqual(other: Any): Boolean = other.isInstanceOf[Currency]

  override def equals(other: Any): Boolean = other match {
    case that: Currency =>
      (that canEqual this) &&
        code == that.code &&
        name == that.name &&
        symbol == that.symbol &&
        formatDecimals == that.formatDecimals
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(code, name, symbol, formatDecimals)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

object Currency {
  def apply(currency: String)(implicit fxContext: MoneyContext[_]): Try[Currency] = {
    fxContext.currencyMap.get(currency)
    .fold(Try[Currency](throw NoSuchCurrencyException(currency, fxContext)))(Success(_))
  }
}

case object USD extends Currency("USD", "US Dollar", "$", 2)
case object ARS extends Currency("ARS", "Argentinean Peso", "$", 2)
case object AUD extends Currency("AUD", "Australian Dollar", "$", 2)
case object BRL extends Currency("BRL", "Brazilian Real", "R$", 2)
case object CAD extends Currency("CAD", "Canadian Dollar", "$", 2)
case object CHF extends Currency("CHF", "Swiss Franc", "CHF", 2)
case object CLP extends Currency("CLP", "Chilean Peso", "$", 2)
case object CNY extends Currency("CNY", "Chinese Yuan Renminbi", "¥", 2)
case object CZK extends Currency("CZK", "Czech Republic Koruny", "Kč", 2)
case object DKK extends Currency("DKK", "Danish Kroner", "kr", 2)
case object EUR extends Currency("EUR", "Euro", "€", 2)
case object GBP extends Currency("GBP", "British Pound", "£", 2)
case object HKD extends Currency("HKD", "Hong Kong Dollar", "$", 2)
case object INR extends Currency("INR", "Indian Rupee", "₹", 2)
case object JPY extends Currency("JPY", "Japanese Yen", "¥", 0)
case object KRW extends Currency("KRW", "South Korean Won", "₩", 0)
case object MXN extends Currency("MXN", "Mexican Peso", "$", 2)
case object MYR extends Currency("MYR", "Malaysian Ringgit", "RM", 2)
case object NOK extends Currency("NOK", "Norwegian Krone", "kr", 2)
case object NZD extends Currency("NZD", "New Zealand Dollar", "$", 2)
case object RUB extends Currency("RUB", "Russian Ruble", "\u20BD", 2)
case object SEK extends Currency("SEK", "Swedish Kroner", "kr", 2)
case object XAG extends Currency("XAG", "Silver", "oz", 4)
case object XAU extends Currency("XAU", "Gold", "oz", 4)
case object BTC extends Currency("BTC", "Bitcoin", "\u20BF", 15)
case object ETH extends Currency("ETH", "Ether", "\u039E", 15)
case object LTC extends Currency("LTC", "Litecoin", "\u0141", 15)
case object ZAR extends Currency("ZAR", "South African Rand", "R", 2)
case object NAD extends Currency("NAD", "Namibian Dollar", "N$", 2)
case object TRY extends Currency("TRY", "Turkish lira", "₺", 2)

/**
 * Support for Money[A] DSL
 */
object MoneyConversions {
  lazy val dollar: Money[Int] = Money(1, USD)
  lazy val euro: Money[Int] = Money(1, EUR)
  lazy val yen: Money[Int] = Money(1, JPY)

  implicit def fromLong(l: Long): MoneyConversions[BigDecimal] = new MoneyConversions(BigDecimal(l))
  implicit def fromDouble(d: Double): MoneyConversions[BigDecimal] = new MoneyConversions(BigDecimal(d))

  implicit class MoneyConversions[A](a: A)(implicit num: Numeric[A]) {
    def money(implicit context: MoneyContext[A]): Money[A] = Money(a, context.defaultCurrency)
    def XAU: Money[A] = market.XAU(a)
    def XAG: Money[A] = market.XAG(a)
    def USD: Money[A] = market.USD(a)
    def dollars: Money[A] = USD
    def cents: Money[A] = market.USD(a / num.fromInt(100))
    def EUR: Money[A] = market.EUR(a)
    def euros: Money[A] = EUR
    def JPY: Money[A] = market.JPY(a)
    def yen: Money[A] = JPY
    def GBP: Money[A] = market.GBP(a)
    def poundSterling: Money[A] = GBP
    def CHF: Money[A] = market.CHF(a)
    def swissFrancs: Money[A] = CHF
    def AUD: Money[A] = market.AUD(a)
    def CAD: Money[A] = market.CAD(a)
    def SEK: Money[A] = market.SEK(a)
    def HKD: Money[A] = market.HKD(a)
    def NOK: Money[A] = market.NOK(a)
    def NZD: Money[A] = market.NZD(a)
    def BTC: Money[A] = market.BTC(a)
    def bitcoin: Money[A] = BTC
    def ETH: Money[A] = market.ETH(a)
    def ether: Money[A] = ETH
    def LTC: Money[A] = market.LTC(a)
    def litecoin: Money[A] = LTC
    def ZAR: Money[A] = market.ZAR(a)
    def NAD: Money[A] = market.NAD(a)
    def TRY: Money[A] = market.TRY(a)
  }

//  class MoneyNumeric[A: Numeric]()(implicit mc: MoneyContext[A]) extends Numeric[Money[A]] {
//    def plus(x: Money[A], y: Money[A]) = x + y
//    def minus(x: Money[A], y: Money[A]) = x - y
//    def times(x: Money[A], y: Money[A]) = throw new UnsupportedOperationException("Numeric.times not supported for Quantities")
//    def negate(x: Money[A]) = -x
//    def fromInt(x: Int) = mc.defaultCurrency(x)
//    def toInt(x: Money[A]) = x.value.toInt
//    def toLong(x: Money[A]) = x.value.toLong
//    def toFloat(x: Money[A]) = x.value.toFloat
//    def toDouble(x: Money[A]) = x.value
//    def compare(x: Money[A], y: Money[A]) = if (x.value > y.value) 1 else if (x.value < y.value) -1 else 0
//    def parseString(str: String): Option[Money[A]] = Money[A](str).toOption
//
//    /**
//      * Custom implementation using SortedSets to ensure consistent output
//      * @return String representation of this instance
//      */
//    override def toString: String = s"MoneyNumeric($mc)"
//  }
}
