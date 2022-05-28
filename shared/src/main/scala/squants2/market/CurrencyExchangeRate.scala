/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants2.market

import squants2.Ratio
import squants2.QuantityValueExtensions

import scala.math.Numeric.Implicits.infixNumericOps


/**
 * Represent the rate of exchange between two currencies
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param base the base or "Fixed" currency (usually a value of 1 currency unit, but not required)
 * @param counter the counter or "Variable" currency
 */
case class CurrencyExchangeRate[A: Numeric](base: Money[A], counter: Money[A]) extends Ratio[A, A, Money, Money] {
  require(base.currency != counter.currency, "Can not create Exchange Rate on matching currencies")

  /**
   * @return Double the rate = counter / base
   */
  def rate: A = counter.value / base.value

  /**
   * Converts the given money into the other currency of this exchange rate
   *
   * @param money Money
   * @return
   */
  def convert(money: Money[A]): Money[A] = money.currency match {
    case base.currency    ⇒ convertToCounter(money)
    case counter.currency ⇒ convertToBase(money)
    case _                ⇒ throw new IllegalArgumentException("The currency of money must match the currency of base or counter")
  }

  /** convert  */
  def *(money: Money[A]): Money[A] = convert(money)

// TODO Validate these updates
  override def convertToBase(q: Money[A])(implicit f: A => A): Money[A] = base.currency(q.value * rate)
  override def convertToCounter(q: Money[A])(implicit f: A => A): Money[A] = counter.currency(q.value / rate)

  /**
   * Returns the rate formatted in as standard FX Quote"
   * @return
   */
//todo  override def toString = s"${base.currency.code}/${counter.currency.code} ${crossFormat(rate)}"
}
