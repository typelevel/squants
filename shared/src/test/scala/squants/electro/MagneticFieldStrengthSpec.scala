package squants.electro

import squants.QuantityParseException
import squants.space.Meters
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
  * @author Nicolas Vinuesa
  * @since 1.4
  *
  */
class MagneticFieldStrengthSpec extends AnyFlatSpec with Matchers {

  behavior of "MagneticFieldStrength and its Units of Measure"

  it should "create values using UOM factories" in {
    FaradsPerMeter(1).toFaradsMeters should be(1)
  }

  it should "create values from properly formatted Strings" in {
    MagneticFieldStrength("10.22 A/m").get should be(AmperesPerMeter(10.22))
    MagneticFieldStrength("10.22 zz").failed.get should be(QuantityParseException("Unable to parse MagneticFieldStrength", "10.22 zz"))
    MagneticFieldStrength("zz A/m").failed.get should be(QuantityParseException("Unable to parse MagneticFieldStrength", "zz A/m"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = AmperesPerMeter(10)
    x.toAmperesPerMeter should be(10.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    AmperesPerMeter(1).toString(AmperesPerMeter) should be("1.0 A/m")
  }

  it should "return ElectricCurrent when multiplied by Length" in {
    AmperesPerMeter(1) * Meters(1) should be(Amperes(1))
  }

  behavior of "MagneticFieldStrengthConversions"

  it should "provide aliases for single unit values" in {
    import MagneticFieldStrengthConversions._

    amperePerMeter should be(AmperesPerMeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import MagneticFieldStrengthConversions._

    val d = 10.22
    d.amperesPerMeter should be(AmperesPerMeter(d))
  }

  it should "provide Numeric support" in {
    import MagneticFieldStrengthConversions.MagneticFieldStrengthNumeric

    val rs = List(AmperesPerMeter(100), AmperesPerMeter(10))
    rs.sum should be(AmperesPerMeter(110))
  }
}
