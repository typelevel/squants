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

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ActivitySpec extends FlatSpec with Matchers {

  behavior of "Activity and its Units of Measure"

  it should "create values using UOM factories" in {
    Curies(1).toCuries should be(1)
    Rutherfords(1).toRutherfords should be(1)
    Becquerels(1).toBecquerels should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Activity("10.22 Ci").get should be(Curies(10.22))
    Activity("5 Bq").get should be(Becquerels(5))
    Activity("7 Rd").get should be(Rutherfords(7))
    Activity("10.22 zz").failed.get should be(
      QuantityParseException("Unable to parse Activity", "10.22 zz"))
    Activity("zz W/m²").failed.get should be(
      QuantityParseException("Unable to parse Activity", "zz W/m²"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Curies(1)
    x.toCuries should be(1)
    x.toRutherfords should be(37000)
    x.toBecquerels should be(3700.0 * 10000000.0)

    val y = Becquerels(1)
    y.toBecquerels should be(1)
    y.toCuries should be(1.0 / 37000000000.0)
    y.toRutherfords should be(1.0 / 1000000.0)

    val z = Rutherfords(1)
    z.toRutherfords should be(1)
    z.toBecquerels should be(1000000)
    z.toCuries should be(1.0 / 37000.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Becquerels(1).toString should be("1.0 Bq")
    Curies(1).toString should be("1.0 Ci")
    Rutherfords(1).toString should be("1.0 Rd")
  }

  it should "return ParticleFlux when divided by AreaTime" in {
    Becquerels(1) / SquareMeterSeconds(1) should be(BecquerelsPerSquareMeterSecond(1))
    Curies(1) / SquareMeterSeconds(37000) should be(BecquerelsPerSquareMeterSecond(1000000))
    Rutherfords(1) / SquareMeterSeconds(1) should be(BecquerelsPerSquareMeterSecond(1000000))
  }

  behavior of "ActivityConversions"

  it should "provide aliases for single unit values" in {
    import ActivityConversions._

    becquerel should be(Becquerels(1))
    curie should be(Curies(1))
    rutherford should be(Rutherfords(1))
  }

  it should "provide implicit conversion from Double" in {
    import ActivityConversions._

    val d = 10.22d
    d.becquerels should be(Becquerels(d))
    d.curies should be(Curies(d))
    d.rutherfords should be(Rutherfords(d))
  }

  it should "provide Numeric support" in {
    import ActivityConversions.ActivityNumeric

    val irrs = List(Becquerels(10), Becquerels(100))
    irrs.sum should be(Becquerels(110))

    val irrsCurie = List(Curies(10), Curies(100))
    irrsCurie.sum should be(Curies(110))

    val irrsRd = List(Rutherfords(10), Rutherfords(100))
    irrsRd.sum should be(Rutherfords(110))
  }
}
