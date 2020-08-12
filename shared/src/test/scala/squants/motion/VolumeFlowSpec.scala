/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants.space.CubicMeters
import squants.time.Seconds
import squants.{ QuantityParseException, CustomMatchers }
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class VolumeFlowSpec extends AnyFlatSpec with Matchers with CustomMatchers {

  behavior of "VolumeFlow and its Units of Measure"

  it should "create values using UOM factories" in {
    CubicMetersPerSecond(1).toCubicMetersPerSecond should be(1)
    LitresPerDay(1).toLitresPerDay should be(1)
    LitresPerHour(1).toLitresPerHour should be(1)
    LitresPerMinute(1).toLitresPerMinute should be(1)
    LitresPerSecond(1).toLitresPerSecond should be(1)
    NanolitresPerDay(1).toNanolitresPerDay should be(1)
    NanolitresPerHour(1).toNanolitresPerHour should be(1)
    NanolitresPerMinute(1).toNanolitresPerMinute should be(1)
    NanolitresPerSecond(1).toNanolitresPerSecond should be(1)
    MicrolitresPerDay(1).toMicrolitresPerDay should be(1)
    MicrolitresPerHour(1).toMicrolitresPerHour should be(1)
    MicrolitresPerMinute(1).toMicrolitresPerMinute should be(1)
    MicrolitresPerSecond(1).toMicrolitresPerSecond should be(1)
    MillilitresPerDay(1).toMillilitresPerDay should be(1)
    MillilitresPerHour(1).toMillilitresPerHour should be(1)
    MillilitresPerMinute(1).toMillilitresPerMinute should be(1)
    MillilitresPerSecond(1).toMillilitresPerSecond should be(1)
    CubicFeetPerHour(1).toCubicFeetPerHour should be(1)
    GallonsPerDay(1).toGallonsPerDay should be(1)
    GallonsPerHour(1).toGallonsPerHour should be(1)
    GallonsPerMinute(1).toGallonsPerMinute should be(1)
    GallonsPerSecond(1).toGallonsPerSecond should be(1)
  }

  it should "create values from properly formatted Strings" in {
    VolumeFlow("10.22 m³/s").get should be(CubicMetersPerSecond(10.22))
    VolumeFlow("10.22 L/s").get should be(LitresPerSecond(10.22))
    VolumeFlow("10.22 L/min").get should be(LitresPerMinute(10.22))
    VolumeFlow("10.22 L/h").get should be(LitresPerHour(10.22))
    VolumeFlow("10.22 L/d").get should be(LitresPerDay(10.22))
    VolumeFlow("10.22 nl/s").get should be(NanolitresPerSecond(10.22))
    VolumeFlow("10.22 nl/min").get should be(NanolitresPerMinute(10.22))
    VolumeFlow("10.22 nl/h").get should be(NanolitresPerHour(10.22))
    VolumeFlow("10.22 nl/d").get should be(NanolitresPerDay(10.22))
    VolumeFlow("10.22 µl/s").get should be(MicrolitresPerSecond(10.22))
    VolumeFlow("10.22 µl/min").get should be(MicrolitresPerMinute(10.22))
    VolumeFlow("10.22 µl/h").get should be(MicrolitresPerHour(10.22))
    VolumeFlow("10.22 µl/d").get should be(MicrolitresPerDay(10.22))
    VolumeFlow("10.22 ml/s").get should be(MillilitresPerSecond(10.22))
    VolumeFlow("10.22 ml/min").get should be(MillilitresPerMinute(10.22))
    VolumeFlow("10.22 ml/h").get should be(MillilitresPerHour(10.22))
    VolumeFlow("10.22 ml/d").get should be(MillilitresPerDay(10.22))
    VolumeFlow("10.22 ft³/hr").get should be(CubicFeetPerHour(10.22))
    VolumeFlow("10.22 GPD").get should be(GallonsPerDay(10.22))
    VolumeFlow("10.22 GPH").get should be(GallonsPerHour(10.22))
    VolumeFlow("10.22 GPM").get should be(GallonsPerMinute(10.22))
    VolumeFlow("10.22 GPS").get should be(GallonsPerSecond(10.22))
    VolumeFlow("10.22 zz").failed.get should be(QuantityParseException("Unable to parse VolumeFlow", "10.22 zz"))
    VolumeFlow("zz m³/s").failed.get should be(QuantityParseException("Unable to parse VolumeFlow", "zz m³/s"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = CubicMetersPerSecond(10.22)
    x.toCubicMetersPerSecond should be(10.22)
    x.toLitresPerDay should be(CubicMeters(10.22).toLitres / Seconds(1).toDays +- 1E-7)
    x.toLitresPerHour should be(CubicMeters(10.22).toLitres / Seconds(1).toHours +- 1E-7)
    x.toLitresPerMinute should be(CubicMeters(10.22).toLitres / Seconds(1).toMinutes)
    x.toLitresPerSecond should be(CubicMeters(10.22).toLitres / Seconds(1).toSeconds)
    x.toNanolitresPerDay should be(CubicMeters(10.22).toNanolitres / Seconds(1).toDays +- 100)
    x.toNanolitresPerHour should be(CubicMeters(10.22).toNanolitres / Seconds(1).toHours)
    x.toNanolitresPerMinute should be(CubicMeters(10.22).toNanolitres / Seconds(1).toMinutes)
    x.toNanolitresPerSecond should be(CubicMeters(10.22).toNanolitres / Seconds(1).toSeconds)
    x.toMicrolitresPerDay should be(CubicMeters(10.22).toMicrolitres / Seconds(1).toDays)
    x.toMicrolitresPerHour should be(CubicMeters(10.22).toMicrolitres / Seconds(1).toHours)
    x.toMicrolitresPerMinute should be(CubicMeters(10.22).toMicrolitres / Seconds(1).toMinutes)
    x.toMicrolitresPerSecond should be(CubicMeters(10.22).toMicrolitres / Seconds(1).toSeconds)
    x.toMillilitresPerDay should be(CubicMeters(10.22).toMillilitres / Seconds(1).toDays +- 1E-4)
    x.toMillilitresPerHour should be(CubicMeters(10.22).toMillilitres / Seconds(1).toHours)
    x.toMillilitresPerMinute should be(CubicMeters(10.22).toMillilitres / Seconds(1).toMinutes +- 1E-7)
    x.toMillilitresPerSecond should be(CubicMeters(10.22).toMillilitres / Seconds(1).toSeconds)
    x.toCubicFeetPerHour should be(CubicMeters(10.22).toCubicFeet / Seconds(1).toHours +- 0.00000001)
    x.toGallonsPerDay should be(CubicMeters(10.22).toUsGallons / Seconds(1).toDays +- 0.00000001)
    x.toGallonsPerHour should be(CubicMeters(10.22).toUsGallons / Seconds(1).toHours)
    x.toGallonsPerMinute should be(CubicMeters(10.22).toUsGallons / Seconds(1).toMinutes +- 0.0000000001)
    x.toGallonsPerSecond should be(CubicMeters(10.22).toUsGallons / Seconds(1).toSeconds)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    CubicMetersPerSecond(1).toString(CubicMetersPerSecond) should be("1.0 m³/s")
    LitresPerDay(1).toString(LitresPerDay) should be("1.0 L/d")
    LitresPerHour(1).toString(LitresPerHour) should be("1.0 L/h")
    LitresPerMinute(1).toString(LitresPerMinute) should be("1.0 L/min")
    LitresPerSecond(1).toString(LitresPerSecond) should be("1.0 L/s")
    NanolitresPerDay(1).toString(NanolitresPerDay) should be("1.0 nl/d")
    NanolitresPerHour(1).toString(NanolitresPerHour) should be("1.0 nl/h")
    NanolitresPerMinute(1).toString(NanolitresPerMinute) should be("1.0 nl/min")
    NanolitresPerSecond(1).toString(NanolitresPerSecond) should be("1.0 nl/s")
    MicrolitresPerDay(1).toString(MicrolitresPerDay) should be("1.0 µl/d")
    MicrolitresPerHour(1).toString(MicrolitresPerHour) should be("1.0 µl/h")
    MicrolitresPerMinute(1).toString(MicrolitresPerMinute) should be("1.0 µl/min")
    MicrolitresPerSecond(1).toString(MicrolitresPerSecond) should be("1.0 µl/s")
    MillilitresPerDay(1).toString(MillilitresPerDay) should be("1.0 ml/d")
    MillilitresPerHour(1).toString(MillilitresPerHour) should be("1.0 ml/h")
    MillilitresPerMinute(1).toString(MillilitresPerMinute) should be("1.0 ml/min")
    MillilitresPerSecond(1).toString(MillilitresPerSecond) should be("1.0 ml/s")
    CubicFeetPerHour(1).toString(CubicFeetPerHour) should be("1.0 ft³/hr")
    GallonsPerDay(1).toString(GallonsPerDay) should be("1.0 GPD")
    GallonsPerHour(1).toString(GallonsPerHour) should be("1.0 GPH")
    GallonsPerMinute(1).toString(GallonsPerMinute) should be("1.0 GPM")
    GallonsPerSecond(1).toString(GallonsPerSecond) should be("1.0 GPS")
  }

  it should "return Volume when multiplied by Time" in {
    CubicMetersPerSecond(1) * Seconds(1) should be(CubicMeters(1))
  }

  behavior of "VolumeFlowConversions"

  it should "provide aliases for single unit values" in {
    import VolumeFlowConversions._

    cubicMeterPerSecond should be(CubicMetersPerSecond(1))
    litresPerDay should be(LitresPerDay(1))
    litersPerDay should be(LitresPerDay(1))
    litresPerHour should be(LitresPerHour(1))
    litersPerHour should be(LitresPerHour(1))
    litresPerMinute should be(LitresPerMinute(1))
    litersPerMinute should be(LitresPerMinute(1))
    litresPerSecond should be(LitresPerSecond(1))
    litersPerSecond should be(LitresPerSecond(1))
    nanolitresPerDay should be(NanolitresPerDay(1))
    nanolitersPerDay should be(NanolitresPerDay(1))
    nanolitresPerHour should be(NanolitresPerHour(1))
    nanolitersPerHour should be(NanolitresPerHour(1))
    nanolitresPerMinute should be(NanolitresPerMinute(1))
    nanolitersPerMinute should be(NanolitresPerMinute(1))
    nanolitresPerSecond should be(NanolitresPerSecond(1))
    nanolitersPerSecond should be(NanolitresPerSecond(1))
    microlitresPerDay should be(MicrolitresPerDay(1))
    microlitersPerDay should be(MicrolitresPerDay(1))
    microlitresPerHour should be(MicrolitresPerHour(1))
    microlitersPerHour should be(MicrolitresPerHour(1))
    microlitresPerMinute should be(MicrolitresPerMinute(1))
    microlitersPerMinute should be(MicrolitresPerMinute(1))
    microlitresPerSecond should be(MicrolitresPerSecond(1))
    microlitersPerSecond should be(MicrolitresPerSecond(1))
    millilitresPerDay should be(MillilitresPerDay(1))
    millilitersPerDay should be(MillilitresPerDay(1))
    millilitresPerHour should be(MillilitresPerHour(1))
    millilitersPerHour should be(MillilitresPerHour(1))
    millilitresPerMinute should be(MillilitresPerMinute(1))
    millilitersPerMinute should be(MillilitresPerMinute(1))
    millilitresPerSecond should be(MillilitresPerSecond(1))
    millilitersPerSecond should be(MillilitresPerSecond(1))
    cubicFeetPerHour should be(CubicFeetPerHour(1))
    gallonPerDay should be(GallonsPerDay(1))
    gallonPerHour should be(GallonsPerHour(1))
    gallonPerMinute should be(GallonsPerMinute(1))
    gallonPerSecond should be(GallonsPerSecond(1))
  }

  it should "provide implicit conversion from Double" in {
    import VolumeFlowConversions._

    val d = 10.22d
    d.cubicMetersPerSecond should be(CubicMetersPerSecond(d))
    d.litresPerDay should be(LitresPerDay(d))
    d.litresPerHour should be(LitresPerHour(d))
    d.litresPerMinute should be(LitresPerMinute(d))
    d.litresPerSecond should be(LitresPerSecond(d))
    d.nanolitresPerDay should be(NanolitresPerDay(d))
    d.nanolitresPerHour should be(NanolitresPerHour(d))
    d.nanolitresPerMinute should be(NanolitresPerMinute(d))
    d.nanolitresPerSecond should be(NanolitresPerSecond(d))
    d.microlitresPerDay should be(MicrolitresPerDay(d))
    d.microlitresPerHour should be(MicrolitresPerHour(d))
    d.microlitresPerMinute should be(MicrolitresPerMinute(d))
    d.microlitresPerSecond should be(MicrolitresPerSecond(d))
    d.millilitresPerDay should be(MillilitresPerDay(d))
    d.millilitresPerHour should be(MillilitresPerHour(d))
    d.millilitresPerMinute should be(MillilitresPerMinute(d))
    d.millilitresPerSecond should be(MillilitresPerSecond(d))
    d.cubicFeetPerHour should be(CubicFeetPerHour(d))
    d.gallonsPerDay should be(GallonsPerDay(d))
    d.gallonsPerHour should be(GallonsPerHour(d))
    d.gallonsPerMinute should be(GallonsPerMinute(d))
    d.gallonsPerSecond should be(GallonsPerSecond(d))
  }

  it should "provide Numeric support" in {
    import VolumeFlowConversions.VolumeFlowNumeric
    implicit val tolerance = GallonsPerDay(0.0000000000001)
    val vfrs = List(GallonsPerDay(24), GallonsPerHour(1))
    vfrs.sum should beApproximately(GallonsPerDay(48))
  }
}
