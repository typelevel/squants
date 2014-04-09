/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.market

import squants._

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
 * @tparam A Quantity Type
 */
case class Price[A <: Quantity[A]](money: Money, quantity: A) extends Ratio[Money, A] with Serializable {
  def base = money
  def counter = quantity

  // TODO Add verification that money amounts are the same OR convert
  def plus(that: Price[A]): Price[A] = Price(money + that.money, quantity)
  def +(that: Price[A]): Price[A] = plus(that)
  def minus(that: Price[A]): Price[A] = Price(money - that.money, quantity)
  def -(that: Price[A]): Price[A] = minus(that)

  def times(that: Double): Price[A] = Price(money * that, quantity)
  def *(that: Double): Price[A] = Price(money * that, quantity)
  def times(that: BigDecimal): Price[A] = Price(money * that, quantity)
  def *(that: BigDecimal): Price[A] = Price(money * that, quantity)

  def divide(that: Double): Price[A] = Price(money / that, quantity)
  def /(that: Double): Price[A] = divide(that)
  def divide(that: BigDecimal): Price[A] = Price(money / that, quantity)
  def /(that: BigDecimal): Price[A] = divide(that)
  def divide(that: Price[A]): BigDecimal = money.amount / that.money.amount
  def /(that: Price[A]): BigDecimal = divide(that)

  /**
   * Returns the Cost (Money) for a quantity `that` of A
   * @param that Quantity
   * @return
   */
  def *(that: A): Money = convertToBase(that)

  /**
   * Returns the Quantity that will cost that)
   * @param that Money
   * @return
   */
  def *(that: Money): A = convertToCounter(that)

  override def toString = money.toString + "/" + quantity.toString
}

