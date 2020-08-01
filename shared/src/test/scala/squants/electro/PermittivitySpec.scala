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
class PermittivitySpec extends AnyFlatSpec with Matchers {

  behavior of "Permittivity and its Units of Measure"

  it should "create values using UOM factories" in {
    FaradsPerMeter(1).toFaradsMeters should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Permittivity("10.22 F/m").get should be(FaradsPerMeter(10.22))
    Permittivity("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Permittivity", "10.22 zz"))
    Permittivity("zz F/m").failed.get should be(QuantityParseException("Unable to parse Permittivity", "zz F/m"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = FaradsPerMeter(10)
    x.toFaradsMeters should be(10.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    FaradsPerMeter(1).toString(FaradsPerMeter) should be("1.0 F/m")
  }

  it should "return Capacitance when multiplied by Length" in {
    FaradsPerMeter(1) * Meters(1) should be(Farads(1))
  }

  behavior of "PermittivityConversions"

  it should "provide aliases for single unit values" in {
    import PermittivityConversions._

    faradPerMeter should be(FaradsPerMeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import PermittivityConversions._

    val d = 10.22
    d.faradsPerMeter should be(FaradsPerMeter(d))
  }

  it should "provide Numeric support" in {
    import PermittivityConversions.PermittivityNumeric

    val rs = List(FaradsPerMeter(100), FaradsPerMeter(10))
    rs.sum should be(FaradsPerMeter(110))
  }
}
