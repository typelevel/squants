/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.time

import squants.Quantity

/**
 * Represents a rate of change over time of the integral quantity
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @tparam A The type of quantity changing
 */
trait TimeDerivative[A <: Quantity[A] with TimeIntegral[_]] { self: Quantity[_] ⇒
  def change: A
  def time: Time

  /**
   * Returns the amount of change in the integral that will happen over the given Time
   *
   * @param that Time
   * @return
   */
  def *(that: Time): A = change * (that / time)
}

/**
 * Represents a Quantity type used as the integral of a time derivative
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @tparam A The Quantity type for the TimeDerivative for which this is the base
 */
trait TimeIntegral[A <: Quantity[A] with TimeDerivative[_]] { self: Quantity[_] ⇒

  /**
   * Returns the Time Derivative which represents a change of the underlying quantity equal to this
   * quantity over the given time.
   *
   * @param that Time
   * @return
   */
  def /(that: Time): A

  /**
   * Returns the amount time required to achieve the given change in the Derivative
   *
   * @param that Derivative
   * @return
   */
  def /(that: A): Time
}
