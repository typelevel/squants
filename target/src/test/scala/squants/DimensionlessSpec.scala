/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.time.Hertz

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class DimensionlessSpec extends FlatSpec with Matchers {

  behavior of "Count and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(Each(1).toEach == 1)
    assert(Dozen(1).toDozen == 1)
    assert(Score(1).toScore == 1)
    assert(Gross(1).toGross == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Gross(1)
    assert(x.toEach == 144)
    assert(x.toDozen == 12)
    assert(x.toScore == 144d / 20)
    assert(x.toGross == 1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Each(1).toString(Each) == "1.0 ea")
    assert(Dozen(1).toString(Dozen) == "1.0 dz")
    assert(Score(1).toString(Score) == "1.0 score")
    assert(Gross(1).toString(Gross) == "1.0 gr")
  }

  it should "return a Frequency when divided by Time" in {
    assert(Each(60) / Seconds(1) == Hertz(60))
  }

  it should " return a Time when divided by Frequency" in {
    assert(Each(60) / Hertz(60) == Seconds(1))
  }

  behavior of "CountsConversions"

  it should "provide aliases for single unit values" in {
    import DimensionlessConversions._

    assert(each == Each(1))
    assert(dozen == Dozen(1))
    assert(score == Score(1))
    assert(gross == Gross(1))
    assert(hundred == Each(100))
    assert(thousand == Each(1000))
    assert(million == Each(1000000))
  }

  it should "provide implicit conversion from Double" in {
    import DimensionlessConversions._

    val coefficient = 10d
    assert(coefficient.each == Each(coefficient))
    assert(coefficient.ea == Each(coefficient))
    assert(coefficient.dozen == Dozen(coefficient))
    assert(coefficient.dz == Dozen(coefficient))
    assert(coefficient.score == Score(coefficient))
    assert(coefficient.gross == Gross(coefficient))
    assert(coefficient.gr == Gross(coefficient))
    assert(coefficient.hundred == Each(coefficient * 100))
    assert(coefficient.thousand == Each(coefficient * 1000))
    assert(coefficient.million == Each(coefficient * 1000000D))
  }
}
