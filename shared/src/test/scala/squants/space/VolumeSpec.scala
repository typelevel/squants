/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import squants.QuantityParseException
import squants.energy.{ Joules, JoulesPerCubicMeter }
import squants.mass.{ Kilograms, KilogramsPerCubicMeter }
import squants.motion.CubicMetersPerSecond
import squants.time.Seconds
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class VolumeSpec extends AnyFlatSpec with Matchers {

  behavior of "Volume and its Units of Measure"

  it should "create values using UOM factories" in {

    CubicMeters(1).toCubicMeters should be(1)
    Litres(1).toLitres should be(1)
    Nanolitres(1).toNanolitres should be(1)
    Microlitres(1).toMicrolitres should be(1)
    Millilitres(1).toMillilitres should be(1)
    Centilitres(1).toCentilitres should be(1)
    Decilitres(1).toDecilitres should be(1)
    Hectolitres(1).toHectolitres should be(1)

    CubicUsMiles(1).toCubicMiles should be(1)
    CubicYards(1).toCubicYards should be(1)
    CubicFeet(1).toCubicFeet should be(1)
    CubicInches(1).toCubicInches should be(1)

    UsGallons(1).toUsGallons should be(1)
    UsQuarts(1).toUsQuarts should be(1)
    UsPints(1).toUsPints should be(1)
    UsCups(1).toUsCups should be(1)

    FluidOunces(1).toFluidOunces should be(1)
    Tablespoons(1).toTablespoons should be(1)
    Teaspoons(1).toTeaspoons should be(1)

    AcreFeet(1).toAcreFeet should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Volume("10.22 m³").get should be(CubicMeters(10.22))
    Volume("10.22 L").get should be(Litres(10.22))
    Volume("10.22 nl").get should be(Nanolitres(10.22))
    Volume("10.22 µl").get should be(Microlitres(10.22))
    Volume("10.22 ml").get should be(Millilitres(10.22))
    Volume("10.22 cl").get should be(Centilitres(10.22))
    Volume("10.22 dl").get should be(Decilitres(10.22))
    Volume("10.22 hl").get should be(Hectolitres(10.22))
    Volume("10.22 mi³").get should be(CubicUsMiles(10.22))
    Volume("10.22 yd³").get should be(CubicYards(10.22))
    Volume("10.22 ft³").get should be(CubicFeet(10.22))
    Volume("10.22 in³").get should be(CubicInches(10.22))
    Volume("10.22 gal").get should be(UsGallons(10.22))
    Volume("10.22 qt").get should be(UsQuarts(10.22))
    Volume("10.22 pt").get should be(UsPints(10.22))
    Volume("10.22 c").get should be(UsCups(10.22))
    Volume("10.22 oz").get should be(FluidOunces(10.22))
    Volume("10.22 tbsp").get should be(Tablespoons(10.22))
    Volume("10.22 tsp").get should be(Teaspoons(10.22))
    Volume("10.22 acft").get should be(AcreFeet(10.22))
    Volume("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Volume", "10.22 zz"))
    Volume("ZZ L").failed.get should be(QuantityParseException("Unable to parse Volume", "ZZ L"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = CubicMeters(1)
    x.toCubicMeters should be(1)

    x.toLitres should be(1000)
    x.toNanolitres - 1000000000000.0d < 1 should be(right = true) // Some issues with conversion precision
    x.toMicrolitres - 1000000000.0d < 1 should be(right = true) // Some issues with conversion precision
    x.toMillilitres - 1000000.0d < 1 should be(right = true) // Some issues with conversion precision
    x.toCentilitres - 100000 < 1 should be(right = true) // Some issues with conversion precision
    x.toDecilitres should be(10000)
    x.toHectolitres should be(10)

    x.toCubicMiles should be(1 / math.pow(UsMiles.conversionFactor, 3))
    x.toCubicYards should be(1 / BigDecimal(Yards.conversionFactor).pow(3).toDouble)
    x.toCubicFeet should be(1 / BigDecimal(Feet.conversionFactor).pow(3).toDouble)
    x.toCubicInches should be(1 / math.pow(Inches.conversionFactor, 3))

    val litresPerUsGallon = 3.785411784
    x.toUsGallons should be(1000d / litresPerUsGallon)
    x.toUsQuarts should be(4000d / litresPerUsGallon)
    x.toUsPints should be(8000d / litresPerUsGallon)
    x.toUsCups should be(16000d / litresPerUsGallon)
    x.toFluidOunces should be(128000d / litresPerUsGallon)
    x.toTablespoons should be((128000d / litresPerUsGallon) * 2d)
    x.toTeaspoons should be((128000d / litresPerUsGallon) * 6d)

    val litresPerUsDryGallon = 4.4048837
    x.toUsDryGallons should be(1000d / litresPerUsDryGallon)
    x.toUsDryQuarts should be(4000d / litresPerUsDryGallon)
    x.toUsDryPints should be(8000d / litresPerUsDryGallon)
    x.toUsDryCups should be(16000d / litresPerUsDryGallon)

    val litresPerImperialGallon = 4.54609
    x.toImperialGallons should be(1000d / litresPerImperialGallon)
    x.toImperialQuarts should be(4000d / litresPerImperialGallon)
    x.toImperialPints should be(8000d / litresPerImperialGallon)
    x.toImperialCups should be(16000d / litresPerImperialGallon)

    x.toAcreFeet should be(8.107083295302069E-4)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    CubicMeters(1).toString(CubicMeters) should be("1.0 m³")
    Litres(1).toString(Litres) should be("1.0 L")
    Nanolitres(1).toString(Nanolitres) should be("1.0 nl")
    Microlitres(1).toString(Microlitres) should be("1.0 µl")
    Millilitres(1).toString(Millilitres) should be("1.0 ml")
    Centilitres(1).toString(Centilitres) should be("1.0 cl")
    Decilitres(1).toString(Decilitres) should be("1.0 dl")
    Hectolitres(1).toString(Hectolitres) should be("1.0 hl")

    CubicUsMiles(1).toString(CubicUsMiles) should be("1.0 mi³")
    CubicYards(1).toString(CubicYards) should be("1.0 yd³")
    CubicFeet(1).toString(CubicFeet) should be("1.0 ft³")
    CubicInches(1).toString(CubicInches) should be("1.0 in³")

    UsGallons(1).toString(UsGallons) should be("1.0 gal")
    UsQuarts(1).toString(UsQuarts) should be("1.0 qt")
    UsPints(1).toString(UsPints) should be("1.0 pt")
    UsCups(1).toString(UsCups) should be("1.0 c")

    AcreFeet(1).toString(AcreFeet) should be("1.0 acft")
  }

  it should "return Mass when multiplied by Density" in {
    CubicMeters(1) * KilogramsPerCubicMeter(10) should be(Kilograms(10))
  }

  it should "return Energy when multiplied by EnergyDensity" in {
    CubicMeters(1) * JoulesPerCubicMeter(10) should be(Joules(10))
  }

  it should "return Length when divided by Area" in {
    CubicMeters(1) / SquareMeters(1) should be(Meters(1))
  }

  it should "return Area when divided by Length" in {
    CubicMeters(1) / Meters(1) should be(SquareMeters(1))
  }

  it should "return VolumeFlowRate when divided by Time" in {
    CubicMeters(1) / Seconds(1) should be(CubicMetersPerSecond(1))
  }

  it should "return Time when divided by VolumeFlowRate" in {
    CubicMeters(1) / CubicMetersPerSecond(1) should be(Seconds(1))
  }

  it should "return Length when cube rooted" in {
    CubicMeters(27).cubeRoot should be(Meters(3))
  }

  behavior of "VolumeConversions"

  it should "provide aliases for single unit values" in {
    import VolumeConversions._

    cubicMeter should be(CubicMeters(1))
    litre should be(Litres(1))
    nanoliter should be(Nanolitres(1))
    nanolitre should be(Nanolitres(1))
    microliter should be(Microlitres(1))
    microlitre should be(Microlitres(1))
    milliliter should be(Millilitres(1))
    millilitre should be(Millilitres(1))
    centiliter should be(Centilitres(1))
    centilitre should be(Centilitres(1))
    deciliter should be(Decilitres(1))
    decilitre should be(Decilitres(1))
    hectoliter should be(Hectolitres(1))
    hectolitre should be(Hectolitres(1))

    cubicMile should be(CubicUsMiles(1))
    cubicYard should be(CubicYards(1))
    cubicFoot should be(CubicFeet(1))
    cubicInch should be(CubicInches(1))

    gallon should be(UsGallons(1))
    quart should be(UsQuarts(1))
    pint should be(UsPints(1))
    cup should be(UsCups(1))
    fluidOunce should be(FluidOunces(1))
    tablespoon should be(Tablespoons(1))
    teaspoon should be(Teaspoons(1))

    acreFoot should be(AcreFeet(1))
  }

  it should "provide implicit conversion from Double" in {
    import VolumeConversions._

    val d = 10d

    d.cubicMeters should be(CubicMeters(d))
    d.cubicMetres should be(CubicMeters(d))
    d.litres should be(Litres(d))
    d.nanoliters should be(Nanolitres(d))
    d.nanolitres should be(Nanolitres(d))
    d.microliters should be(Microlitres(d))
    d.microlitres should be(Microlitres(d))
    d.milliliters should be(Millilitres(d))
    d.millilitres should be(Millilitres(d))
    d.centiliters should be(Centilitres(d))
    d.centilitres should be(Centilitres(d))
    d.deciliters should be(Decilitres(d))
    d.decilitres should be(Decilitres(d))
    d.hectoliters should be(Hectolitres(d))
    d.hectolitres should be(Hectolitres(d))

    d.cubicMiles should be(CubicUsMiles(d))
    d.cubicYards should be(CubicYards(d))
    d.cubicFeet should be(CubicFeet(d))
    d.cubicInches should be(CubicInches(d))

    d.gallons should be(UsGallons(d))
    d.quarts should be(UsQuarts(d))
    d.pints should be(UsPints(d))
    d.cups should be(UsCups(d))
    d.fluidOunces should be(FluidOunces(d))
    d.tablespoons should be(Tablespoons(d))
    d.teaspoons should be(Teaspoons(d))

    d.acreFeet should be(AcreFeet(d))
  }

  it should "provide Numeric support" in {
    import VolumeConversions.VolumeNumeric

    val vs = List(CubicMeters(100), CubicMeters(1))
    vs.sum should be(CubicMeters(101))
  }
}
