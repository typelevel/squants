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
class ElectricFieldStrengthSpec extends AnyFlatSpec with Matchers {

  behavior of "ElectricFieldStrength and its Units of Measure"

  it should "create values using UOM factories" in {
    VoltsPerMeter(1).toVoltsPerMeter should be(1)
  }

  it should "create values from properly formatted Strings" in {
    ElectricFieldStrength("10.22 V/m").get should be(VoltsPerMeter(10.22))
    ElectricFieldStrength("10.22 zz").failed.get should be(QuantityParseException("Unable to parse ElectricFieldStrength", "10.22 zz"))
    ElectricFieldStrength("zz V/m").failed.get should be(QuantityParseException("Unable to parse ElectricFieldStrength", "zz V/m"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = VoltsPerMeter(10)
    x.toVoltsPerMeter should be(10.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    VoltsPerMeter(1).toString(VoltsPerMeter) should be("1.0 V/m")
  }

  it should "return Electric Potential when multiplied by Length" in {
    VoltsPerMeter(1) * Meters(1) should be(Volts(1))
  }

  behavior of "ElectricFieldStrengthConversions"

  it should "provide aliases for single unit values" in {
    import ElectricFieldStrengthConversions._

    voltPerMeter should be(VoltsPerMeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import ElectricFieldStrengthConversions._

    val d = 10.22
    d.voltsPerMeter should be(VoltsPerMeter(d))
  }

  it should "provide Numeric support" in {
    import ElectricFieldStrengthConversions.ElectricFieldStrengthNumeric

    val rs = List(VoltsPerMeter(100), VoltsPerMeter(10))
    rs.sum should be(VoltsPerMeter(110))
  }
}
