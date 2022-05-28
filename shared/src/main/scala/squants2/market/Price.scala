/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants2.market

import squants2._

/**
 * Represents a price
 *
 * A price is an [[squants.Ratio]] between a quantity of [[squants.market.Money]]
 * and some other [[squants.Quantity]]
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param money Money
 * @param quantity Quantity
 * @tparam Q Quantity Type
 */
case class Price[A: Numeric, B, Q[N] <: Quantity[N, Q]](money: Money[A], quantity: Q[B])
  extends Ratio[A, B, Money, Q] with Serializable {

  def base: Money[A] = money
  def counter: Q[B] = quantity

  // TODO Add verification that money amounts are the same OR convert
  def plus(that: Price[A, B, Q]): Price[A, B, Q] = Price(this.money plus that.money, quantity)
  def +(that: Price[A, B, Q]): Price[A, B, Q] = plus(that)

  def minus(that: Price[A, B, Q]): Price[A, B, Q] = Price(this.money minus that.money, quantity)
  def -(that: Price[A, B, Q]): Price[A, B, Q] = minus(that)

  def times(that: A): Price[A, B, Q] = Price(this.money * that, quantity)
  def *(that: A): Price[A, B, Q] = Price(this.money * that, quantity)

  def divide(that: A): Price[A, B, Q] = Price(this.money / that, quantity)
  def /(that: A): Price[A, B, Q] = divide(that)

  def divide(that: Price[A, B, Q]): A = this.money.value / that.money.value
  def /(that: Price[A, B, Q]): A = divide(that)

//todo  def in(currency: Currency)(implicit moneyContext: MoneyContext[A]): Money[A] =
//    (money in currency) / quantity

  /**
   * Returns the Cost (Money) for a quantity `that` of A
   * @param that Quantity
   * @return
   */
  def *(that: Q[B])(implicit f: B => A): Money[A] = convertToBase(that)

//  /**
//   * Returns the Quantity that will cost that)
//   * @param that Money
//   * @return
//   */
//  @deprecated("Use `money / price` instead", "0.6.3")
  def *(that: Money[A])(implicit f: B => A): Q[A] = that / this

  override def toString: String = money.toString + "/" + quantity.toString

  def toString(unit: UnitOfMeasure[Q]): String = money.toString + "/" + quantity.toString(unit)

  def toString(currency: Currency, unit: UnitOfMeasure[Q])(implicit moneyContext: MoneyContext[A]): String =
    (money in currency).toString + "/" + quantity.toString(unit)
}

