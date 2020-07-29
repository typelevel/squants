package squants.motion

import squants.{CustomMatchers, QuantityParseException}
import squants.mass.{KilogramsMetersSquared, PoundsSquareFeet}
import squants.space.{Feet, Meters}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
  *
  * @author paxelord
  * @since 1.2
  */
class TorqueSpec extends AnyFlatSpec with Matchers with CustomMatchers {

  behavior of "Torque and its Units of Measure"
  
  it should "create values using UOM factories" in {
    NewtonMeters(1).toNewtonMeters should be(1)
    PoundsSquareFeet(1).toPoundsSquareFeet should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Torque("10.22 N‧m").get should be(NewtonMeters(10.22))
    Torque("10.22 lb‧ft").get should be(PoundFeet(10.22))
    Torque("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Torque", "10.22 zz"))
    Torque("zz N‧m").failed.get should be(QuantityParseException("Unable to parse Torque", "zz N‧m"))
  }

  it should "properly convert to all supported Units of Measure" in {
    implicit val tolerance = 1e-10
    NewtonMeters(1).toPoundFeet should beApproximately(Newtons(1).toPoundForce * Meters(1).toFeet)
    PoundFeet(1).toNewtonMeters should beApproximately(PoundForce(1).toNewtons * Feet(1).toMeters)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    NewtonMeters(1).toString(NewtonMeters) should be("1.0 N‧m")
    PoundFeet(1).toString(PoundFeet) should be("1.0 lb‧ft")
  }

  it should "return AngularAcceleration when divided by MomentOfInertia" in {
    NewtonMeters(1) / KilogramsMetersSquared(1) should be(RadiansPerSecondSquared(1))
  }

  behavior of "TorqueConversions"

  it should "provide aliases for single unit values" in {
    import TorqueConversions._

    newtonMeters should be(NewtonMeters(1))
    poundFeet should be(PoundFeet(1))
  }

  it should "provide implicit conversion from Double" in {
    import TorqueConversions._

    val d = 10d
    d.newtonMeters should be(NewtonMeters(d))
    d.poundFeet should be(PoundFeet(d))
  }

  it should "provide Numeric support" in {
    import TorqueConversions.TorqueNumeric

    val torqueList = List(NewtonMeters(100), NewtonMeters(10))
    torqueList.sum should be(NewtonMeters(110))
  }
}