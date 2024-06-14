/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2017, Transcriptic, Inc.                                         **
**                                                                      **
\*                                                                      */

package squants.motion

import org.scalatest.{ FlatSpec, Matchers }
import squants.space.CubicMeters
import squants.time.Seconds
import squants.{ CustomMatchers, QuantityParseException }

class VolumeAccelerationSpec extends FlatSpec with Matchers with CustomMatchers {
  behavior of "VolumeAcceleration and its Units of Measure"

  it should "create values using UOM factories" in {
    CubicMetersPerSecondSquared(1).toCubicMetersPerSecondSquared should be(1)
    LitresPerSecondSquared(1).toLitresPerSecondSquared should be(1)
    MillilitresPerSecondSquared(1).toMillilitresPerSecondSquared should be(1)
    MicrolitresPerSecondSquared(1).toMicrolitresPerSecondSquared should be(1)
  }

  it should "create values from properly formatted Strings" in {
    VolumeAcceleration("12.34 m³/s²").get should be(CubicMetersPerSecondSquared(12.34))
    VolumeAcceleration("12.34 L/s²").get should be(LitresPerSecondSquared(12.34))
    VolumeAcceleration("12.34 mL/s²").get should be(MillilitresPerSecondSquared(12.34))
    VolumeAcceleration("12.34 µL/s²").get should be(MicrolitresPerSecondSquared(12.34))
    VolumeAcceleration("12.34 zz").failed.get should be(QuantityParseException("Unable to parse VolumeAcceleration", "12.34 zz"))
    VolumeAcceleration("zz m³/s²").failed.get should be(QuantityParseException("Unable to parse VolumeAcceleration", "zz m³/s²"))
  }

  it should "properly convert to all supported units of Measure" in {
    val x = CubicMetersPerSecondSquared(12.34)
    x.toCubicMetersPerSecondSquared should be(12.34)
    x.toLitresPerSecondSquared should be(12340.0 +- 0.00000001)
    x.toMillilitresPerSecondSquared should be(12340000.0 +- 0.00000001)
    x.toMicrolitresPerSecondSquared should be(12340000000.0 +- 0.00000001)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    CubicMetersPerSecondSquared(1).toString(CubicMetersPerSecondSquared) should be("1.0 m³/s²")
    LitresPerSecondSquared(1).toString(LitresPerSecondSquared) should be("1.0 L/s²")
    MillilitresPerSecondSquared(1).toString(MillilitresPerSecondSquared) should be("1.0 mL/s²")
    MicrolitresPerSecondSquared(1).toString(MicrolitresPerSecondSquared) should be("1.0 µL/s²")
  }

  it should "return VolumeFlow when multiplied by Time" in {
    CubicMetersPerSecondSquared(2) * Seconds(3) should be(CubicMetersPerSecond(6))
  }

  it should "return Volume when multiplied by TimeSquared" in {
    CubicMetersPerSecondSquared(2) * Seconds(2).squared should be(CubicMeters(8))
  }

  behavior of "VolumeAccelerationConversions"

  it should "provide aliases for single unit values" in {
    import VolumeAccelerationConversions._

    cubicMetersPerSecondSquared should be(CubicMetersPerSecondSquared(1))
    litresPerSecondSquared should be(LitresPerSecondSquared(1))
    millilitresPerSecondSquared should be(MillilitresPerSecondSquared(1))
    microlitresPerSecondSquared should be(MicrolitresPerSecondSquared(1))
  }

  it should "provide implicit conversion from Double" in {
    import VolumeAccelerationConversions._

    val d = 12.34d
    d.cubicMetersPerSecondSquared should be(CubicMetersPerSecondSquared(d))
    d.litresPerSecondSquared should be(LitresPerSecondSquared(d))
    d.millilitresPerSecondSquared should be(MillilitresPerSecondSquared(d))
    d.microlitresPerSecondSquared should be(MicrolitresPerSecondSquared(d))
  }

  it should "provide Numeric support" in {
    import VolumeAccelerationConversions.VolumeAccelerationNumeric
    implicit val tolerance = CubicMetersPerSecondSquared(0.00000001)
    val vaList = List(CubicMetersPerSecondSquared(1), LitresPerSecondSquared(2))
    vaList.sum should beApproximately(LitresPerSecondSquared(1002))
  }
}
