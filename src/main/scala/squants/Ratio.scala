/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

/**
 * Defines an interface and partial implementation for types that represent a ratio between any two quantities
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @tparam A Quantity A
 * @tparam B Quantity B
 */
trait Ratio[A <: Quantity[A], B <: Quantity[B]] {
  def base: A
  def counter: B
  def convertToBase(q: B): A = base * (q / counter)
  def convertToCounter(q: A): B = counter * (q / base)
}

trait LikeRatio[A <: Quantity[A]] extends Ratio[A, A] {
  def ratio = base / counter
  def inverseRatio = counter / base
}
