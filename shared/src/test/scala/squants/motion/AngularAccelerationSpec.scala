package squants.motion

import squants.QuantityParseException
import squants.space.{Meters, Radians}
import squants.time.Seconds
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
  *
  * @author paxelord
  * @since 1.3
  */
class AngularAccelerationSpec extends AnyFlatSpec with Matchers{

  behavior of "AngularAcceleration and its Units of Measure"

  it should "create values using UOM factories" in {
    RadiansPerSecondSquared(1).toRadiansPerSecondSquared should be(1)
    DegreesPerSecondSquared(1).toDegreesPerSecondSquared should be(1)
    GradiansPerSecondSquared(1).toGradsPerSecondSquared should be(1)
    TurnsPerSecondSquared(1).toTurnsPerSecondSquared should be(1)
  }

  it should "create values from properly formatted Strings" in {
    AngularAcceleration("10.22 rad/s²").get should be(RadiansPerSecondSquared(10.22))
    AngularAcceleration("10.22 °/s²").get should be(DegreesPerSecondSquared(10.22))
    AngularAcceleration("10.22 grad/s²").get should be(GradiansPerSecondSquared(10.22))
    AngularAcceleration("10.22 turns/s²").get should be(TurnsPerSecondSquared(10.22))
    AngularAcceleration("10.22 zz").failed.get should be(QuantityParseException("Unable to parse AngularAcceleration", "10.22 zz"))
    AngularAcceleration("zz rad/s²").failed.get should be(QuantityParseException("Unable to parse AngularAcceleration", "zz rad/s²"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = RadiansPerSecondSquared(1)
    x.toRadiansPerSecondSquared should be(1)
    x.toDegreesPerSecondSquared should be(Radians(1).toDegrees)
    x.toGradsPerSecondSquared should be(Radians(1).toGradians)
    x.toTurnsPerSecondSquared should be(Radians(1).toTurns)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    RadiansPerSecondSquared(1).toString(RadiansPerSecondSquared) should be("1.0 rad/s²")
    DegreesPerSecondSquared(1).toString(DegreesPerSecondSquared) should be("1.0 °/s²")
    GradiansPerSecondSquared(1).toString(GradiansPerSecondSquared) should be("1.0 grad/s²")
    TurnsPerSecondSquared(1).toString(TurnsPerSecondSquared) should be("1.0 turns/s²")
  }

  it should "return AnglularVelocity when multiplied by Time" in {
    RadiansPerSecondSquared(1) * Seconds(1) should be(RadiansPerSecond(1))
  }

  it should "return Acceleration of particle onRadius of AngularAcceleration" in {
    RadiansPerSecondSquared(1) onRadius Meters(1) should be(MetersPerSecondSquared(1))
  }

  behavior of "AngularAccelerationConversions"

  it should "provide aliases for single unit values" in {
    import AngularAccelerationConversions._

    radianPerSecondSquared should be(RadiansPerSecondSquared(1))
    degreePerSecondSquared should be(DegreesPerSecondSquared(1))
    gradPerSecondSquared should be(GradiansPerSecondSquared(1))
    turnPerSecondSquared should be(TurnsPerSecondSquared(1))
  }

  it should "provide implicit conversion from Double" in {
    import AngularAccelerationConversions._

    val d = 10.22d
    d.radiansPerSecondSquared should be(RadiansPerSecondSquared(d))
    d.degreesPerSecondSquared should be(DegreesPerSecondSquared(d))
    d.gradsPerSecondSquared should be(GradiansPerSecondSquared(d))
    d.turnsPerSecondSquared should be(TurnsPerSecondSquared(d))
  }

  it should "provide Numeric support" in {
    import AngularAccelerationConversions.AngularAccelerationNumeric

    val aas = List(RadiansPerSecondSquared(100), RadiansPerSecondSquared(10))
    aas.sum should be(RadiansPerSecondSquared(110))
  }
}
