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
import squants.energy.WattHours
import squants.time.Hours

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ParticleFluxSpec extends FlatSpec with Matchers {

  behavior of "ParticleFlux and its Units of Measure"

  it should "create values using UOM factories" in {
    BecquerelsPerSquareMeterSecond(1).toBecquerelsPerSquareMeterSecond should be(1)
    BecquerelsPerSquareCentimeterSecond(1).toBecquerelsPerSquareCentimeterSecond should be(1)
  }

  it should "create values from properly formatted Strings" in {
    ParticleFlux("10.22 Bq/m²‧s").get should be(BecquerelsPerSquareMeterSecond(10.22))
    ParticleFlux("1.74 Bq/cm²‧s").get should be(BecquerelsPerSquareCentimeterSecond(1.74))
    ParticleFlux("10.22 zz").failed.get should be(
      QuantityParseException("Unable to parse ParticleFlux", "10.22 zz"))
    ParticleFlux("zz W/m²").failed.get should be(
      QuantityParseException("Unable to parse ParticleFlux", "zz W/m²"))
  }

  it should "properly convert to all supported Units of Measure" in {

    val x = BecquerelsPerSquareMeterSecond(1)
    x.toBecquerelsPerSquareMeterSecond should be(1)
    x.toBecquerelsPerSquareCentimeterSecond should be(0.0001)

    val y = BecquerelsPerSquareCentimeterSecond(1)
    y.toBecquerelsPerSquareCentimeterSecond should be(1)
    y.toBecquerelsPerSquareMeterSecond should be(10000.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    BecquerelsPerSquareMeterSecond(1).toString should be("1.0 Bq/m²‧s")
    BecquerelsPerSquareCentimeterSecond(1).toString should be("1.0 Bq/cm²‧s")
  }

  it should "return Activity when multiplied by AreaTime" in {
    BecquerelsPerSquareMeterSecond(1) * SquareMeterSeconds(1) should be(Becquerels(1))
    BecquerelsPerSquareCentimeterSecond(1) * SquareCentimeterSeconds(1) should be(Becquerels(1))
  }

  it should "return Irradiance when multiplied by Energy" in {
    BecquerelsPerSquareMeterSecond(1) * WattHours(1) should be(WattsPerSquareMeter(Hours(1).toSeconds))
  }

  behavior of "ParticleFluxConversions"

  it should "provide aliases for single unit values" in {
    import ParticleFluxConversions._

    becquerelPerSquareMeterSecond should be(BecquerelsPerSquareMeterSecond(1))
    becquerelPerSquareCentimeterSecond should be(BecquerelsPerSquareCentimeterSecond(1))
  }

  it should "provide implicit conversion from Double" in {
    import ParticleFluxConversions._

    val d = 10.22d
    d.becquerelsPerSquareMeterSecond should be(BecquerelsPerSquareMeterSecond(d))
    d.becquerelsPerSquareCentimeterSecond should be(BecquerelsPerSquareCentimeterSecond(d))
  }

  it should "provide Numeric support" in {
    import ParticleFluxConversions.ParticleFluxNumeric

    val irrs = List(
      BecquerelsPerSquareCentimeterSecond(10),
      BecquerelsPerSquareCentimeterSecond(100))
    irrs.sum should be(BecquerelsPerSquareCentimeterSecond(110))

    val smsIrrs = List(
      BecquerelsPerSquareMeterSecond(10), BecquerelsPerSquareMeterSecond(100))
    smsIrrs.sum should be(BecquerelsPerSquareMeterSecond(110))
  }
}
