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
    Each(1).toEach should be(1)
    Dozen(1).toDozen should be(1)
    Score(1).toScore should be(1)
    Gross(1).toGross should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Gross(1)
    x.toEach should be(144)
    x.toDozen should be(12)
    x.toScore should be(144d / 20)
    x.toGross should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Each(1).toString(Each) should be("1.0 ea")
    Dozen(1).toString(Dozen) should be("1.0 dz")
    Score(1).toString(Score) should be("1.0 score")
    Gross(1).toString(Gross) should be("1.0 gr")
  }

  it should "return another Dimensionless  when multiplied by a Dimensionless" in {
    Each(2) * Dozen(1) should be(Dozen(2))
  }

  it should "return a Frequency when divided by Time" in {
    Each(60) / Seconds(1) should be(Hertz(60))
  }

  it should " return a Time when divided by Frequency" in {
    Each(60) / Hertz(60) should be(Seconds(1))
  }

  behavior of "CountsConversions"

  it should "provide aliases for single unit values" in {
    import DimensionlessConversions._

    each should be(Each(1))
    dozen should be(Dozen(1))
    score should be(Score(1))
    gross should be(Gross(1))
    hundred should be(Each(100))
    thousand should be(Each(1000))
    million should be(Each(1000000))
  }

  it should "provide implicit conversion from Double" in {
    import DimensionlessConversions._

    val coefficient = 10d
    coefficient.each should be(Each(coefficient))
    coefficient.ea should be(Each(coefficient))
    coefficient.dozen should be(Dozen(coefficient))
    coefficient.dz should be(Dozen(coefficient))
    coefficient.score should be(Score(coefficient))
    coefficient.gross should be(Gross(coefficient))
    coefficient.gr should be(Gross(coefficient))
    coefficient.hundred should be(Each(coefficient * 100))
    coefficient.thousand should be(Each(coefficient * 1000))
    coefficient.million should be(Each(coefficient * 1000000D))
  }

  it should "provide Numeric support" in {
    import DimensionlessConversions.DimensionlessNumeric

    // The `times` operation is allowed for Dimensionless quantities
    DimensionlessNumeric.times(Each(10), Dozen(3)) should be(Dozen(30))
  }
}
