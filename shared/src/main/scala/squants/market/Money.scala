/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.market

import squants._

import scala.util.{Failure, Success, Try}
import scala.language.implicitConversions
import scala.math.BigDecimal.RoundingMode
import scala.math.BigDecimal.RoundingMode.RoundingMode

/**
 * Represents a quantity of Money.
 *
 * Money is similar to other quantities in that it represents an amount of something - purchasing power - and
 * it is measured in units - currencies.
 *
 * The main difference is that the conversion rate between currencies can not be certain at compile.
 * (In fact it may not always be so easy to know them at runtime as well.)
 *
 * To address this diversion from the way most other quantities work, Money overrides several of the standard methods
 * and operators to ensure one of two rules is followed:
 *
 *  1) this and that are in the same currency, or
 *  2) there is in an implicit MoneyContext in scope (which may or may not have the applicable exchange rate)
 *
 * Methods and operations applied to moneys of different currencies may throw a NoSuchExchangeRateException if the
 * implicit MoneyContext does not contain the Rate(s) necessary to perform the conversion.
 *
 * The defaultMoneyContext includes USD as the default currency, a list of ~20 other currencies and NO exchange rates
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param amount the amount of money
 * @param currency the currency in which the money is denominated
 */
final class Money private (val amount: BigDecimal)(val currency: Currency)
    extends Quantity[Money] {

  def dimension = Money

  def unit = currency
  def value = amount.toDouble

  /**
   * Returns a string formatted with the original precision amount and the currency code
   *
   * eg USD(123.456) => "123.456 USD"
   *
   * @return String
   */
  override def toString: String = amount.underlying.stripTrailingZeros.toString + " " + currency.code

  /**
    * Converts the amount to the given currency and returns a string formatted with the original precision and the currency code
    *
    * @param c Currency
    * @param context MoneyContext required for conversion
    * @return
    */
  def toString(c: Currency)(implicit context: MoneyContext): String = in(c).toString

  /**
   * Returns a string formatted with the amount, rounded based on the Currency rules, and the currency symbol
   *
   * eg USD(12.4563) => "\$123.46"
   *
   * @return String
   */
  def toFormattedString: String = currency.symbol + amount.setScale(currency.formatDecimals, BigDecimal.RoundingMode.HALF_EVEN).toString

  def toFormattedString(c: Currency)(implicit context: MoneyContext): String = in(c).toFormattedString

  /**
   * Adds this Money to that Money converted to this.currency via context
   *
   * @param that Money
   * @param context MoneyContext required for cross currency operations
   * @return Money
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  def moneyPlus(that: Money)(implicit context: MoneyContext = defaultMoneyContext) = context.add(this, that)
  /** moneyPlus **/
  def +(that: Money)(implicit context: MoneyContext = defaultMoneyContext) = context.add(this, that)

  /**
   * Overrides Quantity.plus to only work on like currencies.
   * Cross currency additions should use moneyPlus
   *
   * @param that Money
   * @return Money
   * @throws scala.UnsupportedOperationException when attempted on cross currencies
   */
  override def plus(that: Money): Money = that.currency match {
    case this.currency ⇒ new Money(this.amount + that.amount)(currency)
    case _             ⇒ throw new UnsupportedOperationException("plus not supported for cross-currency comparison - use moneyPlus")
  }

  /**
   * Subtracts that Money from this Money converted to this.currency via context
   *
   * @param that Money
   * @param context MoneyContext required for cross currency operations
   * @return Money
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  def moneyMinus(that: Money)(implicit context: MoneyContext = defaultMoneyContext) = context.subtract(this, that)
  /** moneyMinus **/
  def -(that: Money)(implicit context: MoneyContext = defaultMoneyContext) = context.subtract(this, that)

  /**
   * Override Quantity.minus to only work on like currencies
   * Cross currency subtractions should use moneyMinus
   *
   * @param that Money
   * @return Money
   * @throws scala.UnsupportedOperationException when attempted on cross currencies
   */
  override def minus(that: Money): Money = that.currency match {
    case this.currency ⇒ new Money(this.amount - that.amount)(currency)
    case _             ⇒ throw new UnsupportedOperationException("minus not supported for cross-currency comparison - use moneyMinus")
  }

  /**
   * Multiplies this money by that BigDecimal and returns a new Money
   *
   * @param that BigDecimal
   * @return Money
   */
  def times(that: BigDecimal): Money = new Money(amount * that)(currency)
  def *(that: BigDecimal): Money = times(that)

  /**
    * Overrides Quantity.times to ensure BigDecimal math is performed
    *
    * @param that Double
    * @return Quantity
    */
  override def times(that: Double): Money = new Money(amount * that)(currency)
  override def *(that: Double): Money = times(that)

  /**
   * Multiplies this money by that [[squants.market.CurrencyExchangeRate]] and returns the equal value in the other currency.
   *
   * Delegates to CurrencyExchangeRate * Money
   *
   * @param that BigDecimal
   * @return
   */
  def *(that: CurrencyExchangeRate): Money = that * this

  /**
   * Divides this money by that BigDecimal and returns a new Money
   *
   * @param that BigDecimal
   * @return Money
   */
  def divide(that: BigDecimal): Money = new Money(amount / that)(currency)
  def /(that: BigDecimal): Money = divide(that)

  /**
    * Overrides Quantity.divide to ensure BigDecimal math is performed
    *
    * @param that Double
    * @return Quantity
    */
  override def divide(that: Double): Money = new Money(amount / that)(currency)
  override def /(that: Double): Money = divide(that)

  /**
   * Integer divides this money by that BigDecimal and returns the remainder
   * @param that BigDecimal
   * @return Money
   */
  def %(that: BigDecimal): Money = new Money(amount % that)(currency)

  /**
   * Integer divides this money by that BigDecimal and returns the quotient and the remainder
   * @param that BigDecimal
   * @return (Money, Money)
   */
  def /%(that: BigDecimal): (Money, Money) = amount /% that match {
    case (q, r) ⇒ (new Money(q)(currency), new Money(r)(currency))
  }

  /**
   * Divides this money by that money and returns the ratio between the converted amounts
   *
   * @param that Money
   * @param context MoneyContext
   * @return
   */
  def moneyDivide(that: Money)(implicit context: MoneyContext): BigDecimal = context.divide(this, that)
  def /(that: Money)(implicit context: MoneyContext = defaultMoneyContext): BigDecimal = moneyDivide(that)

  /**
   * Divide this money by another (non-money) Quantity and return a Price
   * @param that Quantity
   * @tparam A Quantity Type
   * @return Price[A]
   */
  def /[A <: Quantity[A]](that: A): Price[A] = Price(this, that)

  /**
   * Divide this money by a Price and return Quantity
   * @param that Price
   * @tparam A Quantity Type
   * @return A
   */
  def /[A <: Quantity[A]](that: Price[A]): A = that.quantity * (this / that.money).toDouble

  /**
   * Override for Quantity.divide to only work on Moneys of like Currency
   * Cross currency subtractions should use moneyMinus
   *
   * @param that Money
   * @return Double
   */
  override def divide(that: Money): Double = that.currency match {
    case this.currency ⇒ (this.amount / that.amount).toDouble
    case _             ⇒ throw new UnsupportedOperationException("divide not supported for cross-currency comparison - use moneyDivide")
  }

  /**
   * Override for Quantity.max to only work on Moneys of like Currency
   * @param that Money
   * @return Int
   */
  override def max(that: Money): Money = (that, that.currency) match {
    case (m: Money, this.currency) ⇒ new Money(amount.max(m.amount))(currency)
    case _                         ⇒ throw new UnsupportedOperationException("max not supported for cross-currency comparison - use moneyMax")
  }

  /**
   * Override for Quantity.max to only work on Moneys of like Currency
   * @param that Quantity
   * @return Int
   */
  override def min(that: Money): Money = (that, that.currency) match {
    case (m: Money, this.currency) ⇒ new Money(amount.min(m.amount))(currency)
    case _                         ⇒ throw new UnsupportedOperationException("min not supported for cross-currency comparison - use moneyMin")
  }

  /**
   * Override for Quantity.equal to only match Moneys of like Currency
   * @param that Money must be of matching value and unit
   * @return
   */
  override def equals(that: Any): Boolean = that match {
    case m: Money ⇒ amount == m.amount && currency == m.currency
    case _        ⇒ false
  }

  /**
   * Override for Quantity.compare to only work on Moneys of like Currency
   * @param that Money
   * @return Int
   */
  override def compare(that: Money): Int = that.currency match {
    case this.currency ⇒ if (this.amount > that.amount) 1 else if (this.amount < that.amount) -1 else 0
    case _             ⇒ throw new UnsupportedOperationException("Comparison between Moneys of dislike Currency is not supported")
  }

  /**
   * Supports max operation on Moneys of dislike Currency
   * @param that Money
   * @param moneyContext MoneyContext
   * @return
   */
  def moneyMax(that: Money)(implicit moneyContext: MoneyContext) = moneyContext.compare(this, that) match {
    case -1 ⇒ that
    case _  ⇒ this
  }

  /**
   * Supports min operation on Moneys of dislike Currency
   * @param that Money
   * @param moneyContext MoneyContext
   * @return
   */
  def moneyMin(that: Money)(implicit moneyContext: MoneyContext) = moneyContext.compare(this, that) match {
    case 1 ⇒ that
    case _ ⇒ this
  }

  /**
   * Supports equality comparisons on Moneys of dislike Currency
   * @param that Money
   * @param moneyContext MoneyContext
   * @return
   */
  def moneyEquals(that: Money)(implicit moneyContext: MoneyContext) = moneyCompare(that) == 0

  /**
   * Supports non-equality comparisons on Moneys of dislike Currency
   * @param that Money
   * @param moneyContext MoneyContext
   * @return
   */
  def moneyNotEquals(that: Money)(implicit moneyContext: MoneyContext) = moneyCompare(that) != 0

  /**
   * Supports compare operation on Moneys of dislike Currency
   * @param that Money
   * @param moneyContext MoneyContext
   * @return
   */
  def moneyCompare(that: Money)(implicit moneyContext: MoneyContext) = moneyContext.compare(this, that)

  def ==#(that: Money)(implicit moneyContext: MoneyContext) = moneyCompare(that) == 0
  def !=#(that: Money)(implicit moneyContext: MoneyContext) = moneyCompare(that) != 0
  def >#(that: Money)(implicit moneyContext: MoneyContext) = moneyCompare(that) > 0
  def >=#(that: Money)(implicit moneyContext: MoneyContext) = moneyCompare(that) >= 0
  def <#(that: Money)(implicit moneyContext: MoneyContext) = moneyCompare(that) < 0
  def <=#(that: Money)(implicit moneyContext: MoneyContext) = moneyCompare(that) <= 0

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
  def toThe(that: Money) = that.currency match {
    case this.currency ⇒ throw new IllegalArgumentException("Can not create Exchange Rate on matching currencies")
    case _             ⇒ CurrencyExchangeRate(that, this)
  }
  /**
   * toThe
   */
  def -> = toThe _

  /**
   * Convert this Money to a Double representing the currency unit
   *
   * @param unit Currency
   * @param context MoneyContext required for cross currency operations
   * @return Double
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  def to(unit: Currency)(implicit context: MoneyContext) = context.convert(this, unit).amount

  /**
   * Reboxes this Money value in a Money in the given Currency
   *
   * @param unit Currency
   * @param context MoneyContext required for cross currency operations
   * @return Money
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  def in(unit: Currency)(implicit context: MoneyContext) = context.convert(this, unit)

  /**
    * Returns a Money rounded using scale and mode.
    *
    * @param scale Int - scale of the Money to be returned
    * @param mode RoundingMode - defaults to HALF_EVEN
    * @return Quantity
    */
  override def rounded(scale: Int, mode: RoundingMode = RoundingMode.HALF_EVEN) = currency(amount.setScale(scale, mode))

  /**
    * Applies a function to the underlying amount of the Money, returning a Money in the same Currency
    *
    * @param f BigDecimal => BigDecimal function
    * @return Money
    */
  def mapAmount(f: BigDecimal => BigDecimal) = currency(f(amount))
}

/**
 * Factory singleton for Money
 */
object Money extends Dimension[Money] {
  def apply(value: Double)(implicit fxContext: MoneyContext) = new Money(BigDecimal(value))(fxContext.defaultCurrency)
  def apply(value: BigDecimal)(implicit fxContext: MoneyContext) = new Money(value)(fxContext.defaultCurrency)

  def apply(value: BigDecimal, currency: Currency) = new Money(value)(currency)
  def apply(value: BigDecimal, currency: String) = new Money(value)(defaultCurrencyMap(currency))

  def apply[A](n: A, currency: Currency)(implicit num: Numeric[A]) = new Money(BigDecimal(num.toDouble(n)))(currency)
  def apply[A](n: A, currency: String)(implicit num: Numeric[A]) = new Money(BigDecimal(num.toDouble(n)))(defaultCurrencyMap(currency))

  def apply(s: String): Try[Money] = {
    lazy val regex = ("([-+]?[0-9]*\\.?[0-9]+) *(" + defaultCurrencySet.map(_.code).reduceLeft(_ + "|" + _) + ")").r
    s match {
      case regex(value, currency) ⇒ Success(Money(value.toDouble, defaultCurrencyMap(currency)))
      case _                      ⇒ Failure(QuantityParseException("Unable to parse Money", s))
    }
  }
  def name = "Money"

  def primaryUnit = ??? // Should not be used with Money - drawn from MoneyContext instead
  def siUnit = ??? // Should not be used with Money - drawn from MoneyContext instead
  def units = ??? // Should not be used with Money - drawn from MoneyContext instead
}

/**
 * Represents a Currency, which is the Unit of Measure for Money
 *
 * @param code Currency code
 * @param name Currency name
 * @param symbol Currency symbol
 * @param formatDecimals Number of decimals in standard formatting
 */
abstract class Currency(val code: String, val name: String, val symbol: String, val formatDecimals: Int) extends UnitOfMeasure[Money] {
  def apply(d: BigDecimal): Money = Money(d, this)
  def apply[A](n: A)(implicit num: Numeric[A]) = Money(BigDecimal(num.toDouble(n)), this)
  protected def converterFrom: Double ⇒ Double = ???
  protected def converterTo: Double ⇒ Double = ???
  def /(that: Money): CurrencyExchangeRate = that toThe Money(1, this)
  override def toString: String = code
}

object USD extends Currency("USD", "US Dollar", "$", 2)
object ARS extends Currency("ARS", "Argentinean Peso", "$", 2)
object AUD extends Currency("AUD", "Australian Dollar", "$", 2)
object BRL extends Currency("BRL", "Brazilian Real", "R$", 2)
object CAD extends Currency("CAD", "Canadian Dollar", "$", 2)
object CHF extends Currency("CHF", "Swiss Franc", "CHF", 2)
object CLP extends Currency("CLP", "Chilean Peso", "¥", 2)
object CNY extends Currency("CNY", "Chinese Yuan Renmimbi", "¥", 2)
object CZK extends Currency("CZK", "Czech Republic Koruny", "Kč", 2)
object DKK extends Currency("DKK", "Danish Kroner", "kr", 2)
object EUR extends Currency("EUR", "Euro", "€", 2)
object GBP extends Currency("GBP", "British Pound", "£", 2)
object HKD extends Currency("HKD", "Hong Kong Dollar", "$", 2)
object INR extends Currency("INR", "Indian Rupee", "₹", 2)
object JPY extends Currency("JPY", "Japanese Yen", "¥", 0)
object KRW extends Currency("KRW", "South Korean Won", "kr", 0)
object MXN extends Currency("MXN", "Mexican Peso", "$", 2)
object MYR extends Currency("MYR", "Malaysian Ringgit", "RM", 2)
object NOK extends Currency("NOK", "Norwegian Krone", "kr", 2)
object NZD extends Currency("NZD", "New Zealand Dollar", "$", 2)
object RUB extends Currency("RUB", "Russian Ruble", "руб", 2)
object SEK extends Currency("SEK", "Swedish Kroner", "kr", 2)
object XAG extends Currency("XAG", "Silver", "oz", 4)
object XAU extends Currency("XAU", "Gold", "oz", 4)
object BTC extends Currency("BTC", "BitCoin", "B", 15)
object ETH extends Currency("ETH", "Ether", "\u039e", 15)
object LTC extends Currency("LTC", "Litecoin", "\u0141", 15)

/**
 * Support for Money DSL
 */
object MoneyConversions {
  lazy val dollar = Money(1, USD)
  lazy val euro = Money(1, EUR)
  lazy val yen = Money(1, JPY)

  implicit def fromLong(l: Long) = new MoneyConversions(BigDecimal(l))
  implicit def fromDouble(d: Double) = new MoneyConversions(BigDecimal(d))

  implicit class MoneyConversions[A](n: A)(implicit num: Numeric[A]) {
    def money(implicit context: MoneyContext) = Money(n, context.defaultCurrency)
    def XAU = Money(n, squants.market.XAU)
    def XAG = Money(n, squants.market.XAG)
    def USD = Money(n, squants.market.USD)
    def dollars = USD
    def cents = Money(num.toDouble(n) / 100d, squants.market.USD)
    def EUR = Money(n, squants.market.EUR)
    def euros = EUR
    def JPY = Money(n, squants.market.JPY)
    def yen = JPY
    def GBP = Money(n, squants.market.GBP)
    def poundSterling = GBP
    def CHF = Money(n, squants.market.CHF)
    def swissFrancs = CHF
    def AUD = Money(n, squants.market.AUD)
    def CAD = Money(n, squants.market.CAD)
    def SEK = Money(n, squants.market.SEK)
    def HKD = Money(n, squants.market.HKD)
    def NOK = Money(n, squants.market.NOK)
    def NZD = Money(n, squants.market.NZD)
    def BTC = Money(n, squants.market.BTC)
    def bitCoin = BTC
    def ETH = Money(n, squants.market.ETH)
    def ether = ETH
    def LTC = Money(n, squants.market.LTC)
    def liteCoin = LTC
  }

  class MoneyNumeric()(implicit mc: MoneyContext) extends Numeric[Money] {
    def plus(x: Money, y: Money) = x + y
    def minus(x: Money, y: Money) = x - y
    def times(x: Money, y: Money) = throw new UnsupportedOperationException("Numeric.times not supported for Quantities")
    def negate(x: Money) = -x
    def fromInt(x: Int) = mc.defaultCurrency(x)
    def toInt(x: Money) = x.value.toInt
    def toLong(x: Money) = x.value.toLong
    def toFloat(x: Money) = x.value.toFloat
    def toDouble(x: Money) = x.value
    def compare(x: Money, y: Money) = if (x.value > y.value) 1 else if (x.value < y.value) -1 else 0

    /**
      * Custom implementation using SortedSets to ensure consistent output
      * @return String representation of this instance
      */
    override def toString: String = s"MoneyNumeric($mc)"
  }
}
