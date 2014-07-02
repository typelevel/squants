/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import org.scalatest.matchers.{ MatchResult, Matcher }

trait CustomMatchers {

  class QuantityWithinRangeMatcher[A <: Quantity[A]](range: QuantityRange[A]) extends Matcher[A] {
    def apply(left: A) = {
      implicit val r = range
      MatchResult(
        r.contains(left),
        s"$left was not within $range",
        s"$left was within $range")
    }
  }

  def beWithin[A <: Quantity[A]](range: QuantityRange[A]) =
    new QuantityWithinRangeMatcher(range)

  class QuantityApproximatelyEqualTo[A <: Quantity[A]](expectedValue: A)(implicit tolerance: A)
      extends Matcher[A] {
    def apply(left: A) = {
      MatchResult(
        left approx expectedValue,
        s"$left was not approximately equal to $expectedValue ($tolerance)",
        s"$left was approximately equal to $expectedValue ($tolerance)")
    }
  }

  def beApproximately[A <: Quantity[A]](expectedValue: A)(implicit tolerance: A) =
    new QuantityApproximatelyEqualTo(expectedValue)(tolerance)

  class DoubleApproximatelyEqualTo(expectedValue: Double)(implicit tolerance: Double)
      extends Matcher[Double] {
    def apply(left: Double) = {
      MatchResult(
        (expectedValue - tolerance) <= left & left <= (expectedValue + tolerance),
        s"$left was not approximately equal to $expectedValue ($tolerance)",
        s"$left was approximately equal to $expectedValue ($tolerance)")
    }
  }

  def beApproximately(expectedValue: Double)(implicit tolerance: Double) =
    new DoubleApproximatelyEqualTo(expectedValue)
}
