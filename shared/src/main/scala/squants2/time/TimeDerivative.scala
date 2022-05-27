/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants2.time

import squants2._

/**
 * Represents a rate of change over time of the integral quantity
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @tparam Q The type of quantity changing
 */
trait TimeDerivative[A, Q[N] <: Quantity[N, Q]] {
  protected[squants2] def timeIntegrated: Q[A]
  protected[squants2] def derivativeTime: Time[A]
  protected implicit def num: Numeric[A]
  /**
   * Returns the amount of change in the integral that will happen over the given Time
   *
   * @param that Time
   * @return
   */
  def *(that: Time[A]): Q[A] = timeIntegrated * (that / this.derivativeTime)

  /**
   * Returns the portion of quantity change per unit of Time
   *
   * @param that The amount of Quantity
   * @return
   */
  def /(that: Q[A]): Frequency[A] = (timeIntegrated / that) / derivativeTime
}

/**
 * Represents a Quantity type used as the integral of a time derivative
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @tparam Q The Quantity type for the TimeDerivative for which this is the base
 */
trait TimeIntegral[A, Q[N] <: Quantity[N, Q]] {
  protected[squants2] def timeDerived: Q[A]
  protected[squants2] def integralTime: Time[A]
  protected implicit def num: Numeric[A]

  /**
   * Returns the Time Derivative which represents a change of the underlying quantity equal to this
   * quantity over the given time.
   *
   * @param that Time
   * @return
   */
  def /(that: Time[A]): Q[A] = this.timeDerived * (this.integralTime / that)
  def per(that: Time[A]): Q[A] = /(that)

  /**
   * Returns the amount time required to achieve the given change in the Derivative
   *
   * @param that Derivative
   * @return
   */
  def /(that: Q[A] with TimeDerivative[A, Q]): Time[A] = that.derivativeTime * (this.timeDerived / that)

  /**
    * Returns the Time Derivative of this Quantity based on the Frequency this Quantity occurs
    *
    * @param that Frequency - the rate at which this Quantity occurs
    * @return
    */
  def *(that: Frequency[A]): Q[A] = /(this.integralTime) * (that * integralTime).toEach

}


//trait SecondTimeDerivative[A, Q[N] <: Quantity[N, Q] with SecondTimeIntegral[N, Q[_]]] { // self: TimeDerivative[A, _] ⇒
//  protected[squants2] def time: Time[A]
//  def *(that: TimeSquared[A]): Q[A]
//}
//
//
//trait SecondTimeIntegral[A, Q[N] <: Quantity[N, Q] with SecondTimeDerivative[N, Q[_]]] { // self: TimeIntegral[A, _] ⇒
//  def /(that: Q[A]): TimeSquared[A]
//  def /(that: TimeSquared[A]): Q[A]
//  def per(that: TimeSquared[A]): Q[A] = /(that)
//}
