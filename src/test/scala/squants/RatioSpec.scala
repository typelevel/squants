/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import org.scalatest.{ Matchers, FlatSpec }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class RatioSpec extends FlatSpec with Matchers {

  behavior of "Ratio"

  case class TestRatio(base: Mass, counter: Length) extends Ratio[Mass, Length]

  it should "create a ratio with the correct base and counter values" in {
    val ratio = TestRatio(Kilograms(10), Meters(5))
    assert(ratio.base == Kilograms(10))
    assert(ratio.counter == Meters(5))
  }

  it should "convert a value of the base type to a value of the counter type" in {
    val ratio = TestRatio(Kilograms(10), Meters(5))
    assert(ratio.convertToCounter(Kilograms(25)) == Meters(12.5))
  }

  it should "convert a value of the counter type to a value of the base type" in {
    val ratio = TestRatio(Kilograms(10), Meters(5))
    assert(ratio.convertToBase(Meters(25)) == Kilograms(50))
  }

  behavior of "LikeRatio"

  case class TestLikeRatio(base: Mass, counter: Mass) extends LikeRatio[Mass]

  it should "create a ratio with the correct base, counter and ratio values" in {
    val ratio = TestLikeRatio(Kilograms(10), Kilograms(5))
    assert(ratio.base == Kilograms(10))
    assert(ratio.counter == Kilograms(5))
    assert(ratio.ratio == 2)
    assert(ratio.inverseRatio == .5)
  }

  it should "convert a value of the base type to a value of the counter type" in {
    val ratio = TestLikeRatio(Kilograms(10), Kilograms(5))
    assert(ratio.convertToCounter(Kilograms(25)) == Kilograms(12.5))
  }

  it should "convert a value of the counter type to a value of the base type" in {
    val ratio = TestLikeRatio(Kilograms(10), Kilograms(5))
    assert(ratio.convertToBase(Kilograms(25)) == Kilograms(50))
  }
}
