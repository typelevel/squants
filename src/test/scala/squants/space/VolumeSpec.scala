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

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class VolumeSpec extends FlatSpec with Matchers {

  behavior of "Volume and its Units of Measure"

  it should "create values using UOM factories" in {

    assert(CubicMeters(1).toCubicMeters == 1)
    assert(Litres(1).toLitres == 1)
    assert(Milliliters(1).toMillilitres == 1)

    assert(CubicMiles(1).toCubicMiles == 1)
    assert(CubicYards(1).toCubicYards == 1)
    assert(CubicFeet(1).toCubicFeet == 1)
    assert(CubicInches(1).toCubicInches == 1)

    assert(UsGallons(1).toUsGallons == 1)
    assert(UsQuarts(1).toUsQuarts == 1)
    assert(UsPints(1).toUsPints == 1)
    assert(UsCups(1).toUsCups == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = CubicMeters(1)
    assert(x.toCubicMeters == 1)

    assert(x.toLitres == 1000)
    assert(x.toMillilitres == 1000000)

    assert(x.toCubicMiles == 1 / math.pow(UsMiles.multiplier, 3))
    assert(x.toCubicYards == 1 / BigDecimal(Yards.multiplier).pow(3).toDouble)
    assert(x.toCubicFeet == 1 / BigDecimal(Feet.multiplier).pow(3).toDouble)
    assert(x.toCubicInches == 1 / math.pow(Inches.multiplier, 3))

    val litresPerUsGallon = 3.785411784
    assert(x.toUsGallons == 1000d / litresPerUsGallon)
    assert(x.toUsQuarts == 4000d / litresPerUsGallon)
    assert(x.toUsPints == 8000d / litresPerUsGallon)
    assert(x.toUsCups == 16000d / litresPerUsGallon)
    assert(x.toFluidOunces == 128000d / litresPerUsGallon)
    assert(x.toTablespoons == (128000d / litresPerUsGallon) * 2d)
    assert(x.toTeaspoons == (128000d / litresPerUsGallon) * 6d)

    val litresPerUsDryGallon = 4.4048837
    assert(x.toUsDryGallons == 1000d / litresPerUsDryGallon)
    assert(x.toUsDryQuarts == 4000d / litresPerUsDryGallon)
    assert(x.toUsDryPints == 8000d / litresPerUsDryGallon)
    assert(x.toUsDryCups == 16000d / litresPerUsDryGallon)

    val litresPerImperialGallon = 4.54609
    assert(x.toImperialGallons == 1000d / litresPerImperialGallon)
    assert(x.toImperialQuarts == 4000d / litresPerImperialGallon)
    assert(x.toImperialPints == 8000d / litresPerImperialGallon)
    assert(x.toImperialCups == 16000d / litresPerImperialGallon)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(CubicMeters(1).toString(CubicMeters) == "1.0 m³")
    assert(Litres(1).toString(Litres) == "1.0 L")
    assert(Milliliters(1).toString(Milliliters) == "1.0 ml")

    assert(CubicMiles(1).toString(CubicMiles) == "1.0 mi³")
    assert(CubicYards(1).toString(CubicYards) == "1.0 yd³")
    assert(CubicFeet(1).toString(CubicFeet) == "1.0 ft³")
    assert(CubicInches(1).toString(CubicInches) == "1.0 in³")

    assert(UsGallons(1).toString(UsGallons) == "1.0 gal")
    assert(UsQuarts(1).toString(UsQuarts) == "1.0 qt")
    assert(UsPints(1).toString(UsPints) == "1.0 pt")
    assert(UsCups(1).toString(UsCups) == "1.0 c")
  }

  it should "return Mass when multiplied by Density" in {
    assert(CubicMeters(1) * KilogramsPerCubicMeter(10) == Kilograms(10))
  }

  it should "return Energy when multiplied by EnergyDensity" in {
    assert(CubicMeters(1) * JoulesPerCubicMeter(10) == Joules(10))
  }

  it should "return Length when divided by Area" in {
    assert(CubicMeters(1) / SquareMeters(1) == Meters(1))
  }

  it should "return Area when divided by Length" in {
    assert(CubicMeters(1) / Meters(1) == SquareMeters(1))
  }

  it should "return VolumeFlowRate when divided by Time" in {
    assert(CubicMeters(1) / Seconds(1) == CubicMetersPerSecond(1))
  }

  it should "return Time when divided by VolumeFlowRate" in {
    assert(CubicMeters(1) / CubicMetersPerSecond(1) == Seconds(1))
  }

  behavior of "VolumeConversions"

  it should "provide aliases for single unit values" in {
    import VolumeConversions._

    assert(cubicMeter == CubicMeters(1))
    assert(litre == Litres(1))
    assert(millilitre == Milliliters(1))

    assert(cubicMile == CubicMiles(1))
    assert(cubicYard == CubicYards(1))
    assert(cubicFoot == CubicFeet(1))
    assert(cubicInch == CubicInches(1))

    assert(gallon == UsGallons(1))
    assert(quart == UsQuarts(1))
    assert(pint == UsPints(1))
    assert(cup == UsCups(1))
    assert(fluidOunce == FluidOunces(1))
    assert(tablespoon == Tablespoons(1))
    assert(teaspoon == Teaspoons(1))
  }

  it should "provide implicit conversion from Double" in {
    import VolumeConversions._

    val d = 10d

    assert(d.cubicMeters == CubicMeters(d))
    assert(d.cubicMetres == CubicMeters(d))
    assert(d.litres == Litres(d))
    assert(d.millilitres == Milliliters(d))

    assert(d.cubicMiles == CubicMiles(d))
    assert(d.cubicYards == CubicYards(d))
    assert(d.cubicFeet == CubicFeet(d))
    assert(d.cubicInches == CubicInches(d))

    assert(d.gallons == UsGallons(d))
    assert(d.quarts == UsQuarts(d))
    assert(d.pints == UsPints(d))
    assert(d.cups == UsCups(d))
    assert(d.fluidOunces == FluidOunces(d))
    assert(d.tablespoons == Tablespoons(d))
    assert(d.teaspoons == Teaspoons(d))
  }
}
