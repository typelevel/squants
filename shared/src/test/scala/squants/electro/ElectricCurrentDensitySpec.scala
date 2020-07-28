package squants.electro

import squants.QuantityParseException
import squants.space.{Meters, SquareMeters}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
  * @author Nicolas Vinuesa
  * @since 1.4
  *
  */
class ElectricCurrentDensitySpec extends AnyFlatSpec with Matchers {

  behavior of "ElectricCurrentDensity and its Units of Measure"

  it should "create values using UOM factories" in {
    AmperesPerSquareMeter(1).toAmperesPerSquareMeter should be(1)
  }

  it should "create values from properly formatted Strings" in {
    ElectricCurrentDensity("10.22 A/m²").get should be(AmperesPerSquareMeter(10.22))
    ElectricCurrentDensity("10.22 zz").failed.get should be(QuantityParseException("Unable to parse ElectricCurrentDensity", "10.22 zz"))
    ElectricCurrentDensity("zz A/m²").failed.get should be(QuantityParseException("Unable to parse ElectricCurrentDensity", "zz A/m²"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = AmperesPerSquareMeter(10)
    x.toAmperesPerSquareMeter should be(10.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    AmperesPerSquareMeter(1).toString(AmperesPerSquareMeter) should be("1.0 A/m²")
  }

  it should "return ElectricCurrent when multiplied by Area" in {
    AmperesPerSquareMeter(1) * SquareMeters(1) should be(Amperes(1))
  }

  it should "return MagneticFieldStrength when multiplied by Length" in {
    AmperesPerSquareMeter(1) * Meters(1) should be(AmperesPerMeter(1))
  }

  behavior of "ElectricCurrentDensityConversions"

  it should "provide aliases for single unit values" in {
    import ElectricCurrentDensityConversions._

    amperePerSquareMeter should be(AmperesPerSquareMeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import ElectricCurrentDensityConversions._

    val d = 10.22
    d.amperesPerSquareMeter should be(AmperesPerSquareMeter(d))
  }

  it should "provide Numeric support" in {
    import ElectricCurrentDensityConversions.ElectricCurrentDensityNumeric

    val rs = List(AmperesPerSquareMeter(100), AmperesPerSquareMeter(10))
    rs.sum should be(AmperesPerSquareMeter(110))
  }
}
