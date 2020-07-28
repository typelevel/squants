/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import squants.time.Hertz
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class DimensionlessSpec extends AnyFlatSpec with Matchers {

  behavior of "Dimensionless and its Units of Measure"

  it should "create values using UOM factories" in {
    Percent(1).toPercent should be(1)
    Each(1).toEach should be(1)
    Dozen(1).toDozen should be(1)
    Score(1).toScore should be(1)
    Gross(1).toGross should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Dimensionless("10.22 %").get should be(Percent(10.22))
    Dimensionless("10.22 ea").get should be(Each(10.22))
    Dimensionless("10.22 dz").get should be(Dozen(10.22))
    Dimensionless("10.22 score").get should be(Score(10.22))
    Dimensionless("10.22 gr").get should be(Gross(10.22))
    Dimensionless("10.45 zz").failed.get should be(QuantityParseException("Unable to parse Dimensionless", "10.45 zz"))
    Dimensionless("zz ea").failed.get should be(QuantityParseException("Unable to parse Dimensionless", "zz ea"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Gross(1)
    x.toPercent should be(14400)
    x.toEach should be(144)
    x.toDozen should be(12)
    x.toScore should be(144d / 20)
    x.toGross should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Percent(1).toString(Percent) should be("1.0 %")
    Each(1).toString(Each) should be("1.0 ea")
    Dozen(1).toString(Dozen) should be("1.0 dz")
    Score(1).toString(Score) should be("1.0 score")
    Gross(1).toString(Gross) should be("1.0 gr")
  }

  it should "return another Dimensionless when multiplied by a Dimensionless" in {
    Each(2) * Dozen(1) should be(Dozen(2))
    Dozen(5) * Percent(10) should be(Each(6))
  }

  it should "return another Dimensionless when added to a Double" in {
    Each(10) + 10.22 should be(Each(20.22))
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

    percent should be(Percent(1))
    each should be(Each(1))
    dozen should be(Dozen(1))
    score should be(Score(1))
    gross should be(Gross(1))
    hundred should be(Each(1e2))
    thousand should be(Each(1e3))
    million should be(Each(1e6))
  }

  it should "provide implicit conversion from Double" in {
    import DimensionlessConversions._

    val coefficient = 10d
    coefficient.percent should be(Percent(coefficient))
    coefficient.each should be(Each(coefficient))
    coefficient.ea should be(Each(coefficient))
    coefficient.dozen should be(Dozen(coefficient))
    coefficient.dz should be(Dozen(coefficient))
    coefficient.score should be(Score(coefficient))
    coefficient.gross should be(Gross(coefficient))
    coefficient.gr should be(Gross(coefficient))
    coefficient.hundred should be(Each(coefficient * 1e2))
    coefficient.thousand should be(Each(coefficient * 1e3))
    coefficient.million should be(Each(coefficient * 1e6))
  }

  it should "provide an implicit conversion to Double" in {
    import DimensionlessConversions._

    10 + 5.each should be(15d)
    100 - 1.dozen should be(88d)
    100 * 15.percent should be(15)
    12000 / 1.dozen should be(1000d)
  }

  it should "provide Numeric support" in {
    import DimensionlessConversions.DimensionlessNumeric

    // The `times` operation is allowed for Dimensionless quantities
    DimensionlessNumeric.times(Each(10), Dozen(3)) should be(Dozen(30))
  }
}
