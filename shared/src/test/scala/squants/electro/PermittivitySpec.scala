package squants.electro

import org.scalatest.{FlatSpec, Matchers}
import squants.QuantityParseException
import squants.space.Meters

/**
  * @author Nicolas Vinuesa
  * @since 1.4
  *
  */
class PermittivitySpec extends FlatSpec with Matchers {

  behavior of "Permittivity and its Units of Measure"

  it should "create values using UOM factories" in {
    FaradsMeters(1).toFaradsMeters should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Permittivity("10.22 F/m").get should be(FaradsMeters(10.22))
    Permittivity("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Permittivity", "10.22 zz"))
    Permittivity("zz F/m").failed.get should be(QuantityParseException("Unable to parse Permittivity", "zz F/m"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = FaradsMeters(10)
    x.toFaradsMeters should be(10.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    FaradsMeters(1).toString(FaradsMeters) should be("1.0 F/m")
  }

  it should "return Capacitance when multiplied by Length" in {
    FaradsMeters(1) * Meters(1) should be(Farads(1))
  }

  behavior of "PermittivityConversions"

  it should "provide aliases for single unit values" in {
    import PermittivityConversions._

    faradMeter should be(FaradsMeters(1))
  }

  it should "provide implicit conversion from Double" in {
    import PermittivityConversions._

    val d = 10.22
    d.faradsMeters should be(FaradsMeters(d))
  }

  it should "provide Numeric support" in {
    import PermittivityConversions.PermittivityNumeric

    val rs = List(FaradsMeters(100), FaradsMeters(10))
    rs.sum should be(FaradsMeters(110))
  }
}
