/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class RatioSpec extends AnyFlatSpec with Matchers {

  behavior of "Ratio"

  case class TestRatio(base: Mass, counter: Length) extends Ratio[Mass, Length]

  it should "create a ratio with the correct base and counter values" in {
    val ratio = TestRatio(Kilograms(10), Meters(5))
    ratio.base should be(Kilograms(10))
    ratio.counter should be(Meters(5))
  }

  it should "convert a value of the base type to a value of the counter type" in {
    val ratio = TestRatio(Kilograms(10), Meters(5))
    ratio.convertToCounter(Kilograms(25)) should be(Meters(12.5))
  }

  it should "convert a value of the counter type to a value of the base type" in {
    val ratio = TestRatio(Kilograms(10), Meters(5))
    ratio.convertToBase(Meters(25)) should be(Kilograms(50))
  }

  behavior of "LikeRatio"

  case class TestLikeRatio(base: Mass, counter: Mass) extends LikeRatio[Mass]

  it should "create a ratio with the correct base, counter and ratio values" in {
    val ratio = TestLikeRatio(Kilograms(10), Kilograms(5))
    ratio.base should be(Kilograms(10))
    ratio.counter should be(Kilograms(5))
    ratio.ratio should be(2)
    ratio.inverseRatio should be(.5)
  }

  it should "convert a value of the base type to a value of the counter type" in {
    val ratio = TestLikeRatio(Kilograms(10), Kilograms(5))
    ratio.convertToCounter(Kilograms(25)) should be(Kilograms(12.5))
  }

  it should "convert a value of the counter type to a value of the base type" in {
    val ratio = TestLikeRatio(Kilograms(10), Kilograms(5))
    ratio.convertToBase(Kilograms(25)) should be(Kilograms(50))
  }
}
