package squants.mass

import org.scalatest.{FlatSpec, Matchers}
import squants.CustomMatchers
import squants.motion.{NewtonMeters, RadiansPerSecondSquared}
import squants.space.{Feet, Meters}

/**
  *
  * @author paxelord
  * @since 1.3
  **/
class MomentOfInertiaSpec extends FlatSpec with Matchers with CustomMatchers {

  behavior of "MomentOfInertia and its Units of Measure"

  val unitValueSi = KilogramsMetersSquared(1)

  it should "create values using UOM factories" in {
    KilogramsMetersSquared(10.22).toKilogramsMetersSquared should be(10.22)
    PoundsSquareFeet(10.22).toPoundsSquareFeet should be(10.22)
  }

  it should "create values from properly formatted Strings" in {
    MomentOfInertia("10.22 kg‧m²").get should be(KilogramsMetersSquared(10.22))
    MomentOfInertia("10.22 lb‧ft²").get should be(PoundsSquareFeet(10.22))
  }

  it should "properly convert to all supported Units of Measure" in {
    implicit val tolerance = 1e-10
    val a = KilogramsMetersSquared(1)

    val kilogramsToPounds = 1D / Pounds.conversionFactor
    val metersToFeet = 1D / Feet.conversionFactor
    a.toPoundsSquareFeet should beApproximately(kilogramsToPounds * metersToFeet * metersToFeet)

    val b = PoundsSquareFeet(1)
    val poundsToKilograms = Pounds.conversionFactor
    val feetToMeters = Feet.conversionFactor
    b.toKilogramsMetersSquared should beApproximately(poundsToKilograms * feetToMeters * feetToMeters)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    PoundsSquareFeet(1).toString(PoundsSquareFeet) should be("1.0 lb‧ft²")
    unitValueSi.toString(KilogramsMetersSquared) should be("1.0 kg‧m²")
  }

  it should "return Torque when multiplied by AngularAcceleration" in {
    unitValueSi * RadiansPerSecondSquared(1) should be(NewtonMeters(1))
  }

  it should "return mass when atCenter is called" in {
    unitValueSi atCenter Meters(1) should be(Kilograms(1))
  }

  it should "provide aliases for single unit values" in {
    import MomentOfInertiaConversions._

    poundSquareFeet should be(PoundsSquareFeet(1))
    kilogramMetersSquared should be(KilogramsMetersSquared(1))
  }

  it should "provide implicit conversion from Double" in {
    import MomentOfInertiaConversions._

    val d = 10.22
    d.kilogramMetersSquared should be(KilogramsMetersSquared(d))
    d.poundSquareFeet should be(PoundsSquareFeet(d))
  }

  it should "provide Numeric support" in {
    import MomentOfInertiaConversions._

    val momentOfInertiaList = List(KilogramsMetersSquared(100), KilogramsMetersSquared(10))
    momentOfInertiaList.sum should be(KilogramsMetersSquared(110))
  }
}
