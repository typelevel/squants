/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import squants.QuantityParseException
import squants.space.{Acres, Hectares, SquareMeters}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.2.3
 *
 */
class AreaDensitySpec extends AnyFlatSpec with Matchers {

  behavior of "AreaDensity and its Units of Measure"

  it should "create values using UOM factories" in {
    KilogramsPerSquareMeter(1).toKilogramsPerSquareMeter should be(1)
    KilogramsPerHectare(1).toKilogramsPerHectare should be(1)
    PoundsPerAcre(1).toPoundsPerAcre should be(1)
    GramsPerSquareCentimeter(1).toGramsPerSquareCentimeter should be(1)
  }

  it should "create values from properly formatted Strings" in {
    AreaDensity("10.22 kg/m²").get should be(KilogramsPerSquareMeter(10.22))
    AreaDensity("10.45 zz").failed.get should be(QuantityParseException("Unable to parse AreaDensity", "10.45 zz"))
    AreaDensity("zz kg/m²").failed.get should be(QuantityParseException("Unable to parse AreaDensity", "zz kg/m²"))
    AreaDensity("10.33 kg/hectare").get should be(KilogramsPerHectare(10.33))
    AreaDensity("10.33 lb/acre").get should be(PoundsPerAcre(10.33))
    AreaDensity("10.19 g/cm²").get should be(GramsPerSquareCentimeter(10.19))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = KilogramsPerSquareMeter(1)
    x.toKilogramsPerSquareMeter should be(1)
    x.toKilogramsPerHectare should be(1e4)
    x.toPoundsPerAcre should be(8921.79 +- 0.01)
    x.toGramsPerSquareCentimeter should be(0.1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    KilogramsPerSquareMeter(1).toString should be("1.0 kg/m²")
    KilogramsPerHectare(1).toString should be("1.0 kg/hectare")
    PoundsPerAcre(1).toString should be("1.0 lb/acre")
    GramsPerSquareCentimeter(1).toString should be("1.0 g/cm²")
  }

  it should "return Mass when multiplied by Volume" in {
    KilogramsPerSquareMeter(1) * SquareMeters(1) should be(Kilograms(1))
    KilogramsPerHectare(1) * Hectares(1) should be(Kilograms(1))
    PoundsPerAcre(1) * Acres(1) should be(Pounds(1))
    GramsPerSquareCentimeter(1000) * SquareMeters(1e-4) should be(Kilograms(1))
  }

  behavior of "AreaDensityConversion"

  it should "provide aliases for single unit values" in {
    import AreaDensityConversions._

    kilogramPerSquareMeter should be(KilogramsPerSquareMeter(1))
    kilogramPerHectare should be(KilogramsPerHectare(1))
    poundsPerAcre should be(PoundsPerAcre(1))
    gramPerSquareCentimeter should be(GramsPerSquareCentimeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import AreaDensityConversions._

    val d = 10.22d
    d.kilogramsPerSquareMeter should be(KilogramsPerSquareMeter(d))
    d.kilogramsPerHectare should be(KilogramsPerHectare(d))
    d.gramsPerSquareCentimeter should be(GramsPerSquareCentimeter(d))
  }

  it should "provide Numeric support" in {
    import AreaDensityConversions.AreaDensityNumeric

    val as = List(KilogramsPerSquareMeter(100), KilogramsPerSquareMeter(10))
    as.sum should be(KilogramsPerSquareMeter(110))
  }
}
