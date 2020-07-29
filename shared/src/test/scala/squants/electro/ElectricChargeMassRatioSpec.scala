package squants.electro

import squants.QuantityParseException
import squants.mass.Kilograms
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
  *
  * @author Nicolas Vinuesa
  * @since 1.4
  *
  */
class ElectricChargeMassRatioSpec extends AnyFlatSpec with Matchers {

  behavior of "ElectricChargeMassRatio and its Units of Measure"

  it should "create values using UOM factories" in {
    CoulombsPerKilogram(1).toCoulombsKilograms should be(1)
  }

  it should "create values from properly formatted Strings" in {
    ElectricChargeMassRatio("10.22 C/kg").get should be(CoulombsPerKilogram(10.22))
    ElectricChargeMassRatio("10.22 zz").failed.get should be(QuantityParseException("Unable to parse ElectricChargeMassRatio", "10.22 zz"))
    ElectricChargeMassRatio("zz C/kg").failed.get should be(QuantityParseException("Unable to parse ElectricChargeMassRatio", "zz C/kg"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = CoulombsPerKilogram(10)
    x.toCoulombsKilograms should be(10.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    CoulombsPerKilogram(1).toString(CoulombsPerKilogram) should be("1.0 C/kg")
  }

  it should "return ElectricCharge when multiplied by Mass" in {
    CoulombsPerKilogram(1) * Kilograms(1) should be(Coulombs(1))
  }

  behavior of "ElectricChargeMassRatioConversions"

  it should "provide aliases for single unit values" in {
    import ElectricChargeMassRatioConversions._

    coulombPerKilogram should be(CoulombsPerKilogram(1))
  }

  it should "provide implicit conversion from Double" in {
    import ElectricChargeMassRatioConversions._

    val d = 10.22
    d.coulombsPerKilogram should be(CoulombsPerKilogram(d))
  }

  it should "provide Numeric support" in {
    import ElectricChargeMassRatioConversions.ElectricChargeMassRatioNumeric

    val rs = List(CoulombsPerKilogram(100), CoulombsPerKilogram(10))
    rs.sum should be(CoulombsPerKilogram(110))
  }
}
