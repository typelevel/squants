package squants.electro

import squants.QuantityParseException
import squants.space.{Meters, SquareMeters}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
  *
  * @author Nicolas Vinuesa
  * @since 1.4
  *
  */
class AreaElectricChargeDensitySpec extends AnyFlatSpec with Matchers {

  behavior of "AreaElectricChargeDensity and its Units of Measure"

  it should "create values using UOM factories" in {
    CoulombsPerSquareMeter(1).toCoulombsSquareMeters should be(1)
  }

  it should "create values from properly formatted Strings" in {
    AreaElectricChargeDensity("10.22 C/m²").get should be(CoulombsPerSquareMeter(10.22))
    AreaElectricChargeDensity("10.22 zz").failed.get should be(QuantityParseException("Unable to parse AreaElectricChargeDensity", "10.22 zz"))
    AreaElectricChargeDensity("zz C/m²").failed.get should be(QuantityParseException("Unable to parse AreaElectricChargeDensity", "zz C/m²"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = CoulombsPerSquareMeter(10)
    x.toCoulombsSquareMeters should be(10.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    CoulombsPerSquareMeter(1).toString(CoulombsPerSquareMeter) should be("1.0 C/m²")
  }

  it should "return ElectricCharge when multiplied by Area" in {
    CoulombsPerSquareMeter(1) * SquareMeters(1) should be(Coulombs(1))
  }

  it should "return LinearElectricChargeDensity when multiplied by Length" in {
    CoulombsPerSquareMeter(1) * Meters(1) should be(CoulombsPerMeter(1))
  }

  behavior of "AreaElectricChargeDensityConversions"

  it should "provide aliases for single unit values" in {
    import AreaElectricChargeDensityConversions._

    coulombPerSquareMeter should be(CoulombsPerSquareMeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import AreaElectricChargeDensityConversions._

    val d = 10.22
    d.coulombsPerSquareMeter should be(CoulombsPerSquareMeter(d))
  }

  it should "provide Numeric support" in {
    import AreaElectricChargeDensityConversions.AreaElectricChargeDensityNumeric

    val rs = List(CoulombsPerSquareMeter(100), CoulombsPerSquareMeter(10))
    rs.sum should be(CoulombsPerSquareMeter(110))
  }
}
