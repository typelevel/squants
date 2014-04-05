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

    SquareMeters(1).toSquareMeters should be(1)
    SquareCentimeters(1).toSquareCentimeters should be(1)
    SquareKilometers(1).toSquareKilometers should be(1)
    SquareUsMiles(1).toSquareUsMiles should be(1)
    SquareYards(1).toSquareYards should be(1)
    SquareFeet(1).toSquareFeet should be(1)
    Hectares(1).toHectares should be(1)
    Acres(1).toAcres should be(1)
    Barnes(1).toBarnes should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = SquareMeters(1)
    x.toSquareMeters should be(1)
    x.toSquareCentimeters should be(1 / (MetricSystem.Centi * MetricSystem.Centi))
    x.toSquareKilometers should be(1 / (MetricSystem.Kilo * MetricSystem.Kilo))
    x.toSquareUsMiles should be(1 / SquareUsMiles.multiplier) // TODO - Using the UOM multiplier is cheating
    x.toSquareYards should be(1 / SquareYards.multiplier) // TODO - If the multiplier is wrong, the test still passes
    x.toSquareFeet should be(1 / SquareFeet.multiplier)
    x.toHectares should be(1 / Hectares.multiplier)
    x.toAcres should be(1 / Acres.multiplier)
    x.toBarnes should be(1 / Barnes.multiplier)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    SquareMeters(1).toString(SquareMeters) should be("1.0 m²")
    SquareCentimeters(1).toString(SquareCentimeters) should be("1.0 cm²")
    SquareKilometers(1).toString(SquareKilometers) should be("1.0 km²")
    SquareUsMiles(1).toString(SquareUsMiles) should be("1.0 mi²")
    SquareYards(1).toString(SquareYards) should be("1.0 yd²")
    SquareFeet(1).toString(SquareFeet) should be("1.0 ft²")
    Hectares(1).toString(Hectares) should be("1.0 ha")
    Acres(1).toString(Acres) should be("1.0 acre")
    Barnes(1).toString(Barnes) should be("1.0 b")
  }

  it should "return Volume when multiplied by Length" in {
    SquareMeters(1) * Meters(1) should be(CubicMeters(1))
  }

  it should "return Force when multiplied by Pressure" in {
    SquareMeters(1) * Pascals(1) should be(Newtons(1))
  }

  it should "return Lumens when multiplied by Illuminance" in {
    SquareMeters(1) * Lux(1) should be(Lumens(1))
  }

  it should "return Candelas when multiplied by Luminance" in {
    SquareMeters(1) * CandelasPerSquareMeter(1) should be(Candelas(1))
  }

  it should "return Length when divided by Length" in {
    SquareMeters(1) / Meters(1) should be(Meters(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = SquareMeters(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Area](ser)
    x should be(des)
  }

  behavior of "AreaConversion"

  it should "provide aliases for single unit values" in {
    import AreaConversions._

    squareMeter should be(SquareMeters(1))
    squareCentimeter should be(SquareCentimeters(1))
    squareKilometer should be(SquareKilometers(1))
    squareMile should be(SquareUsMiles(1))
    squareYard should be(SquareYards(1))
    squareFoot should be(SquareFeet(1))
    squareInch should be(SquareInches(1))
    hectare should be(Hectares(1))
    acre should be(Acres(1))
    barne should be(Barnes(1))
  }

  it should "provide implicit conversion from Double" in {
    import AreaConversions._

    val d = 10d
    d.squareMeters should be(SquareMeters(d))
    d.squareCentimeters should be(SquareCentimeters(d))
    d.squareKilometers should be(SquareKilometers(d))
    d.squareMiles should be(SquareUsMiles(d))
    d.squareYards should be(SquareYards(d))
    d.squareFeet should be(SquareFeet(d))
    d.squareInches should be(SquareInches(d))
    d.hectares should be(Hectares(d))
    d.acres should be(Acres(d))
    d.barnes should be(Barnes(d))
  }
}
