/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.MetricSystem
import squants.motion._
import squants.time.Seconds
import squants.space.CubicMeters
import org.json4s.{ DefaultFormats, ShortTypeHints }
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MassSpec extends FlatSpec with Matchers {

  behavior of "Mass and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(Micrograms(1).toMicrograms == 1)
    assert(Milligrams(1).toMilligrams == 1)
    assert(Grams(1).toGrams == 1)
    assert(Kilograms(1).toKilograms == 1)
    assert(Tonnes(1).toTonnes == 1)
    assert(Pounds(1).toPounds == 1)
    assert(Ounces(1).toOunces == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Grams(1)
    assert(x.toMicrograms == 1 / MetricSystem.Micro)
    assert(x.toMilligrams == 1 / MetricSystem.Milli)
    assert(x.toGrams == 1)
    assert(x.toKilograms == 1 / MetricSystem.Kilo)
    assert(x.toTonnes == 1 / MetricSystem.Mega)
    assert(x.toPounds == 1 / Pounds.multiplier)
    assert(x.toOunces == 1 / Ounces.multiplier)

    assert(Grams(1000) == Kilograms(1))
    assert(Kilograms(0.45359237) == Pounds(1))
    assert(Ounces(16) == Pounds(1))
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Micrograms(1).toString(Micrograms) == "1.0 mcg")
    assert(Milligrams(1).toString(Milligrams) == "1.0 mg")
    assert(Grams(1).toString(Grams) == "1.0 g")
    assert(Kilograms(1).toString(Kilograms) == "1.0 kg")
    assert(Tonnes(1).toString(Tonnes) == "1.0 t")
    assert(Pounds(1).toString(Pounds) == "1.0 lb")
    assert(Ounces(1).toString(Ounces) == "1.0 oz")
  }

  it should "return Momentum when multiplied by Velocity" in {
    assert(Kilograms(1) * MetersPerSecond(1) == NewtonSeconds(1))
  }

  it should "return Force when multiplied by Acceleration" in {
    assert(Kilograms(1) * MetersPerSecondSquared(1) == Newtons(1))
  }

  it should "return Volume when divided by Density" in {
    assert(Kilograms(1) / KilogramsPerCubicMeter(1) == CubicMeters(1))
  }

  it should "return Time when divided by MassFlowRate" in {
    assert(Kilograms(1) / KilogramsPerSecond(1) == Seconds(1))
  }

  it should "return MassFlowRate when divided by Time" in {
    assert(Kilograms(1) / Seconds(1) == KilogramsPerSecond(1))
  }

  it should "return Density when divided by Volume" in {
    assert(Kilograms(1) / CubicMeters(1) == KilogramsPerCubicMeter(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Kilograms(10)
    val ser = Serialization.write(x)
    val des = Serialization.read[Mass](ser)
    assert(x == des)
  }

  behavior of "MassConversions"

  it should "provide aliases for single unit values" in {
    import MassConversions._

    assert(microgram == Micrograms(1))
    assert(milligram == Milligrams(1))
    assert(gram == Grams(1))
    assert(kilogram == Kilograms(1))
    assert(tonne == Tonnes(1))
    assert(pound == Pounds(1))
    assert(ounce == Ounces(1))
  }

  it should "provide implicit conversion from Double" in {
    import MassConversions._

    val d = 10d
    assert(d.mcg == Micrograms(d))
    assert(d.mg == Milligrams(d))
    assert(d.milligrams == Milligrams(d))
    assert(d.g == Grams(d))
    assert(d.grams == Grams(d))
    assert(d.kg == Kilograms(d))
    assert(d.kilograms == Kilograms(d))
    assert(d.tonnes == Tonnes(d))
    assert(d.pounds == Pounds(d))
    assert(d.ounces == Ounces(d))
  }

  it should "provide implicit conversions from String" in {
    import MassConversions._

    assert("10.45 mcg".toMass.get == Micrograms(10.45))
    assert("10.45 mg".toMass.get == Milligrams(10.45))
    assert("10.45 g".toMass.get == Grams(10.45))
    assert("10.45 kg".toMass.get == Kilograms(10.45))
    assert("10.45 t".toMass.get == Tonnes(10.45))
    assert("10.45 tonnes".toMass.get == Tonnes(10.45))
    assert("10.45 lb".toMass.get == Pounds(10.45))
    assert("10.45 oz".toMass.get == Ounces(10.45))
  }
}
