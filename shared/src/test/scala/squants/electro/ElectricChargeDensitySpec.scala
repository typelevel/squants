package squants.electro

import squants.QuantityParseException
import squants.space.{CubicMeters, Meters, SquareMeters}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
  *
  * @author Nicolas Vinuesa
  * @since 1.4
  *
  */
class ElectricChargeDensitySpec extends AnyFlatSpec with Matchers {

  behavior of "ElectricChargeDensity and its Units of Measure"

  it should "create values using UOM factories" in {
    CoulombsPerCubicMeter(1).toCoulombsCubicMeters should be(1)
  }

  it should "create values from properly formatted Strings" in {
    ElectricChargeDensity("10.22 C/m³").get should be(CoulombsPerCubicMeter(10.22))
    ElectricChargeDensity("10.22 zz").failed.get should be(QuantityParseException("Unable to parse ElectricChargeDensity", "10.22 zz"))
    ElectricChargeDensity("zz C/m³").failed.get should be(QuantityParseException("Unable to parse ElectricChargeDensity", "zz C/m³"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = CoulombsPerCubicMeter(10)
    x.toCoulombsCubicMeters should be(10.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    CoulombsPerCubicMeter(1).toString(CoulombsPerCubicMeter) should be("1.0 C/m³")
  }

  it should "return ElectricCharge when multiplied by Volume" in {
    CoulombsPerCubicMeter(1) * CubicMeters(1) should be(Coulombs(1))
  }

  it should "return LinearElectricChargeDensity when multiplied by Area" in {
    CoulombsPerCubicMeter(1) * SquareMeters(1) should be(CoulombsPerMeter(1))
  }

  it should "return AreaElectricChargeDensity when multiplied by Length" in {
    CoulombsPerCubicMeter(1) * Meters(1) should be(CoulombsPerSquareMeter(1))
  }

  behavior of "ElectricChargeDensityConversions"

  it should "provide aliases for single unit values" in {
    import ElectricChargeDensityConversions._

    coulombPerCubicMeter should be(CoulombsPerCubicMeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import ElectricChargeDensityConversions._

    val d = 10.22
    d.coulombsPerCubicMeter should be(CoulombsPerCubicMeter(d))
  }

  it should "provide Numeric support" in {
    import ElectricChargeDensityConversions.ElectricChargeDensityNumeric

    val rs = List(CoulombsPerCubicMeter(100), CoulombsPerCubicMeter(10))
    rs.sum should be(CoulombsPerCubicMeter(110))
  }
}
