/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import org.scalatest.{ Matchers, FlatSpec }
import squants.motion.CubicMetersPerSecond
import squants.time.Seconds
import squants.mass.{ Kilograms, KilogramsPerCubicMeter }
import squants.energy.{ Joules, JoulesPerCubicMeter }
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class VolumeSpec extends FlatSpec with Matchers {

  behavior of "Volume and its Units of Measure"

  it should "create values using UOM factories" in {

    CubicMeters(1).toCubicMeters should be(1)
    Litres(1).toLitres should be(1)
    Milliliters(1).toMillilitres should be(1)

    CubicMiles(1).toCubicMiles should be(1)
    CubicYards(1).toCubicYards should be(1)
    CubicFeet(1).toCubicFeet should be(1)
    CubicInches(1).toCubicInches should be(1)

    UsGallons(1).toUsGallons should be(1)
    UsQuarts(1).toUsQuarts should be(1)
    UsPints(1).toUsPints should be(1)
    UsCups(1).toUsCups should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = CubicMeters(1)
    x.toCubicMeters should be(1)

    x.toLitres should be(1000)
    x.toMillilitres should be(1000000)

    x.toCubicMiles should be(1 / math.pow(UsMiles.multiplier, 3))
    x.toCubicYards should be(1 / BigDecimal(Yards.multiplier).pow(3).toDouble)
    x.toCubicFeet should be(1 / BigDecimal(Feet.multiplier).pow(3).toDouble)
    x.toCubicInches should be(1 / math.pow(Inches.multiplier, 3))

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
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    CubicMeters(1).toString(CubicMeters) should be("1.0 m³")
    Litres(1).toString(Litres) should be("1.0 L")
    Milliliters(1).toString(Milliliters) should be("1.0 ml")

    CubicMiles(1).toString(CubicMiles) should be("1.0 mi³")
    CubicYards(1).toString(CubicYards) should be("1.0 yd³")
    CubicFeet(1).toString(CubicFeet) should be("1.0 ft³")
    CubicInches(1).toString(CubicInches) should be("1.0 in³")

    UsGallons(1).toString(UsGallons) should be("1.0 gal")
    UsQuarts(1).toString(UsQuarts) should be("1.0 qt")
    UsPints(1).toString(UsPints) should be("1.0 pt")
    UsCups(1).toString(UsCups) should be("1.0 c")
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

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = CubicMeters(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Volume](ser)
    x should be(des)
  }

  behavior of "VolumeConversions"

  it should "provide aliases for single unit values" in {
    import VolumeConversions._

    cubicMeter should be(CubicMeters(1))
    litre should be(Litres(1))
    millilitre should be(Milliliters(1))

    cubicMile should be(CubicMiles(1))
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
  }

  it should "provide implicit conversion from Double" in {
    import VolumeConversions._

    val d = 10d

    d.cubicMeters should be(CubicMeters(d))
    d.cubicMetres should be(CubicMeters(d))
    d.litres should be(Litres(d))
    d.millilitres should be(Milliliters(d))

    d.cubicMiles should be(CubicMiles(d))
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
  }

  it should "provide Numeric support" in {
    import VolumeConversions.VolumeNumeric

    val vs = List(CubicMeters(100), CubicMeters(1))
    vs.sum should be(CubicMeters(101))
  }
}
