/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import org.scalatest.{ Matchers, FlatSpec }
import squants.MetricSystem
import squants.motion.{ Pascals, Newtons }
import squants.photo.{ Candelas, CandelasPerSquareMeter, Lumens, Lux }
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class AreaSpec extends FlatSpec with Matchers {

  behavior of "Area and its Units of Measure"

  it should "create values using UOM factories" in {

    assert(SquareMeters(1).toSquareMeters == 1)
    assert(SquareCentimeters(1).toSquareCentimeters == 1)
    assert(SquareKilometers(1).toSquareKilometers == 1)
    assert(SquareUsMiles(1).toSquareUsMiles == 1)
    assert(SquareYards(1).toSquareYards == 1)
    assert(SquareFeet(1).toSquareFeet == 1)
    assert(Hectares(1).toHectares == 1)
    assert(Acres(1).toAcres == 1)
    assert(Barnes(1).toBarnes == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = SquareMeters(1)
    assert(x.toSquareMeters == 1)
    assert(x.toSquareCentimeters == 1 / (MetricSystem.Centi * MetricSystem.Centi))
    assert(x.toSquareKilometers == 1 / (MetricSystem.Kilo * MetricSystem.Kilo))
    assert(x.toSquareUsMiles == 1 / SquareUsMiles.multiplier) // TODO - Using the UOM multiplier is cheating
    assert(x.toSquareYards == 1 / SquareYards.multiplier) // TODO - If the multiplier is wrong, the test still passes
    assert(x.toSquareFeet == 1 / SquareFeet.multiplier)
    assert(x.toHectares == 1 / Hectares.multiplier)
    assert(x.toAcres == 1 / Acres.multiplier)
    assert(x.toBarnes == 1 / Barnes.multiplier)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(SquareMeters(1).toString(SquareMeters) == "1.0 m²")
    assert(SquareCentimeters(1).toString(SquareCentimeters) == "1.0 cm²")
    assert(SquareKilometers(1).toString(SquareKilometers) == "1.0 km²")
    assert(SquareUsMiles(1).toString(SquareUsMiles) == "1.0 mi²")
    assert(SquareYards(1).toString(SquareYards) == "1.0 yd²")
    assert(SquareFeet(1).toString(SquareFeet) == "1.0 ft²")
    assert(Hectares(1).toString(Hectares) == "1.0 ha")
    assert(Acres(1).toString(Acres) == "1.0 acre")
    assert(Barnes(1).toString(Barnes) == "1.0 b")
  }

  it should "return Volume when multiplied by Length" in {
    assert(SquareMeters(1) * Meters(1) == CubicMeters(1))
  }

  it should "return Force when multiplied by Pressure" in {
    assert(SquareMeters(1) * Pascals(1) == Newtons(1))
  }

  it should "return Lumens when multiplied by Illuminance" in {
    assert(SquareMeters(1) * Lux(1) == Lumens(1))
  }

  it should "return Candelas when multiplied by Luminance" in {
    assert(SquareMeters(1) * CandelasPerSquareMeter(1) == Candelas(1))
  }

  it should "return Length when divided by Length" in {
    assert(SquareMeters(1) / Meters(1) == Meters(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = SquareMeters(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Area](ser)
    assert(x == des)
  }

  behavior of "AreaConversion"

  it should "provide aliases for single unit values" in {
    import AreaConversions._

    assert(squareMeter == SquareMeters(1))
    assert(squareCentimeter == SquareCentimeters(1))
    assert(squareKilometer == SquareKilometers(1))
    assert(squareMile == SquareUsMiles(1))
    assert(squareYard == SquareYards(1))
    assert(squareFoot == SquareFeet(1))
    assert(squareInch == SquareInches(1))
    assert(hectare == Hectares(1))
    assert(acre == Acres(1))
    assert(barne == Barnes(1))
  }

  it should "provide implicit conversion from Double" in {
    import AreaConversions._

    val d = 10d
    assert(d.squareMeters == SquareMeters(d))
    assert(d.squareCentimeters == SquareCentimeters(d))
    assert(d.squareKilometers == SquareKilometers(d))
    assert(d.squareMiles == SquareUsMiles(d))
    assert(d.squareYards == SquareYards(d))
    assert(d.squareFeet == SquareFeet(d))
    assert(d.squareInches == SquareInches(d))
    assert(d.hectares == Hectares(d))
    assert(d.acres == Acres(d))
    assert(d.barnes == Barnes(d))
  }
}
