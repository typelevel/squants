/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2018, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.radio

import org.scalatest.{FlatSpec, Matchers}
import squants.QuantityParseException
import squants.space.{ SquareMeters, SquareCentimeters }
import squants.time.Seconds

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class AreaTimeSpec extends FlatSpec with Matchers {

  behavior of "AreaTime and its Units of Measure"

  it should "create values using UOM factories" in {
    SquareMeterSeconds(1).toSquareMeterSeconds should be(1)
    SquareCentimeterSeconds(1).toSquareCentimeterSeconds should be(1)
  }

  it should "create values from properly formatted Strings" in {
    AreaTime("10.22 m²‧s").get should be(SquareMeterSeconds(10.22))
    AreaTime("1.74 cm²‧s").get should be(SquareCentimeterSeconds(1.74))
    AreaTime("10.22 zz").failed.get should be(
      QuantityParseException("Unable to parse AreaTime", "10.22 zz"))
    AreaTime("zz W/m²").failed.get should be(
      QuantityParseException("Unable to parse AreaTime", "zz W/m²"))
  }

  it should "properly convert to all supported Units of Measure" in {

    val x = SquareMeterSeconds(1)
    x.toSquareMeterSeconds should be(1)
    x.toSquareCentimeterSeconds should be(10000.0)

    val y = SquareCentimeterSeconds(1)
    y.toSquareCentimeterSeconds should be(1)
    y.toSquareMeterSeconds should be(1.0 / 10000.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    SquareMeterSeconds(1).toString should be("1.0 m²‧s")
    SquareCentimeterSeconds(1).toString should be("1.0 cm²‧s")
  }

  it should "return Area when divided by Time" in {
    SquareMeterSeconds(1) / Seconds(1) should be(SquareMeters(1))
    SquareCentimeterSeconds(1) / Seconds(1) should be(SquareCentimeters(1))
  }

  it should "return Time when divided by Area" in {
    SquareMeterSeconds(1) / Seconds(1) should be(SquareMeters(1))
    SquareCentimeterSeconds(1) / SquareCentimeters(1) should be(Seconds(1))
  }

  behavior of "AreaTimeConversions"

  it should "provide aliases for single unit values" in {
    import AreaTimeConversions._

    squareMeterSeconds should be(SquareMeterSeconds(1))
    squareCentimeterSeconds should be(SquareCentimeterSeconds(1))
  }

  it should "provide implicit conversion from Double" in {
    import AreaTimeConversions._

    val d = 10.22d
    d.squareMeterSeconds should be(SquareMeterSeconds(d))
    d.squareCentimeterSeconds should be(SquareCentimeterSeconds(d))
  }

  it should "provide Numeric support" in {
    import AreaTimeConversions.AreaTimeNumeric

    val irrs = List(SquareCentimeterSeconds(10), SquareCentimeterSeconds(100))
    irrs.sum should be(SquareMeterSeconds(0.011))

    val smsIrrs =
      List(SquareMeterSeconds(10), SquareMeterSeconds(100))
    smsIrrs.sum should be(SquareMeterSeconds(110))
  }
}
