/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.market

import squants.Ratio
import squants.Platform.crossFormat

/**
 * Represent the rate of exchange between two currencies
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param base the base or "Fixed" currency (usually a value of 1 currency unit, but not required)
 * @param counter the counter or "Variable" currency
 */
case class CurrencyExchangeRate(base: Money, counter: Money) extends Ratio[Money, Money] {
  require(base.currency != counter.currency, "Can not create Exchange Rate on matching currencies")

  /**
   * @return Double the rate = counter / base
   */
  def rate: Double = (counter.amount / base.amount).toDouble

  /**
   * Converts the given money into the other currency of this exchange rate
   *
   * @param money Money
   * @return
   */
  def convert(money: Money) = money.currency match {
    case base.currency    => convertToCounter(money)
    case counter.currency => convertToBase(money)
    case _                => throw new IllegalArgumentException("The currency of money must match the currency of base or counter")
  }

  /** convert  */
  def *(money: Money) = convert(money)


  /**
    * Override methods from Ratio to ensure BigDecimal precision math is applied
    *
    * @param m Money
    * @return
    */
  override def convertToBase(m: Money): Money = base * (m / counter)
  override def convertToCounter(m: Money): Money = counter * (m / base)

  /**
   * Returns the rate formatted in as standard FX Quote"
   * @return
   */
  override def toString = s"${base.currency.code}/${counter.currency.code} ${crossFormat(rate)}"
}
