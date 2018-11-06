package squants.electro

import org.scalatest.{FlatSpec, Matchers}
import squants.QuantityParseException
import squants.space.Meters

/**
  *
  * @author Nicolas Vinuesa
  * @since 1.4
  *
  */
class LinearElectricChargeDensitySpec extends FlatSpec with Matchers {

  behavior of "LinearElectricChargeDensity and its Units of Measure"

  it should "create values using UOM factories" in {
    CoulombsPerMeter(1).toCoulombsMeters should be(1)
  }

  it should "create values from properly formatted Strings" in {
    LinearElectricChargeDensity("10.22 C/m").get should be(CoulombsPerMeter(10.22))
    LinearElectricChargeDensity("10.22 zz").failed.get should be(QuantityParseException("Unable to parse LinearElectricChargeDensity", "10.22 zz"))
    LinearElectricChargeDensity("zz C/m").failed.get should be(QuantityParseException("Unable to parse LinearElectricChargeDensity", "zz C/m"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = CoulombsPerMeter(10)
    x.toCoulombsMeters should be(10.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    CoulombsPerMeter(1).toString(CoulombsPerMeter) should be("1.0 C/m")
  }

  it should "return ElectricCharge when multiplied by Length" in {
    CoulombsPerMeter(1) * Meters(1) should be(Coulombs(1))
  }

  it should "return AreaElectricChargeDensity when divided by Length" in {
    CoulombsPerMeter(1) / Meters(1) should be(CoulombsPerSquareMeter(1))
  }

  behavior of "LinearElectricChargeDensityConversions"

  it should "provide aliases for single unit values" in {
    import LinearElectricChargeDensityConversions._

    coulombPerMeter should be(CoulombsPerMeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import LinearElectricChargeDensityConversions._

    val d = 10.22
    d.coulombsPerMeter should be(CoulombsPerMeter(d))
  }

  it should "provide Numeric support" in {
    import LinearElectricChargeDensityConversions.LinearElectricChargeDensityNumeric

    val rs = List(CoulombsPerMeter(100), CoulombsPerMeter(10))
    rs.sum should be(CoulombsPerMeter(110))
  }
}
