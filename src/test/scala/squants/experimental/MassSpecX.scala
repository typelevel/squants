/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.experimental

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.MetricSystem

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MassSpecX extends FlatSpec with Matchers {

  behavior of "Mass and its Units of Measure"

  it should "create values using UOM factories" in {
    Micrograms(10.22).toMicrograms should be(10.22)
    Milligrams(10.22).toMilligrams should be(10.22)
    Grams(10.22).toGrams should be(10.22)
    Kilograms(10.22).toKilograms should be(10.22)
    Tonnes(10.22).toTonnes should be(10.22)
    Pounds(10.22).toPounds should be(10.22)
    Ounces(10.22).toOunces should be(10.22)
  }

  it should "create values from properly formatted Strings" in {
    MassX("10.22 mcg").get should be(Micrograms(10.22))
    MassX("10.22 mg").get should be(Milligrams(10.22))
    MassX("10.22 g").get should be(Grams(10.22))
    MassX("10.22 kg").get should be(Kilograms(10.22))
    MassX("10.22 t").get should be(Tonnes(10.22))
    MassX("10.22 lb").get should be(Pounds(10.22))
    MassX("10.22 oz").get should be(Ounces(10.22))
    MassX("10.45 zz").failed.get should be(QuantityStringParseException("Unable to parse Mass", "10.45 zz"))
    MassX("zz g").failed.get should be(QuantityStringParseException("Unable to parse Mass", "zz g"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Grams(1)
    x.toMicrograms should be(1 / MetricSystem.Micro)
    x.toMilligrams should be(1 / MetricSystem.Milli)
    x.toGrams should be(1)
    x.toKilograms should be(1 / MetricSystem.Kilo)
    x.toTonnes should be(1 / MetricSystem.Mega)
    x.toPounds should be(1 / Pounds.conversionFactor)
    x.toOunces should be(1 / Ounces.conversionFactor)

    Grams(1000) should be(Kilograms(1))
    Kilograms(0.45359237) should be(Pounds(1))
    Ounces(16) should be(Pounds(1))
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Micrograms(1).toString(Micrograms) should be("1.0 mcg")
    Milligrams(1).toString(Milligrams) should be("1.0 mg")
    Grams(1).toString(Grams) should be("1.0 g")
    Kilograms(1).toString(Kilograms) should be("1.0 kg")
    Tonnes(1).toString(Tonnes) should be("1.0 t")
    Pounds(1).toString(Pounds) should be("1.0 lb")
    Ounces(1).toString(Ounces) should be("1.0 oz")
  }

  behavior of "MassConversions"

  it should "provide aliases for single unit values" in {
    import MassConversionsX._

    microgram should be(Micrograms(1))
    milligram should be(Milligrams(1))
    gram should be(Grams(1))
    kilogram should be(Kilograms(1))
    tonne should be(Tonnes(1))
    pound should be(Pounds(1))
    ounce should be(Ounces(1))
  }

  it should "provide implicit conversion from Double" in {
    import MassConversionsX._

    val d = 10.22
    d.mcg should be(Micrograms(d))
    d.mg should be(Milligrams(d))
    d.milligrams should be(Milligrams(d))
    d.g should be(Grams(d))
    d.grams should be(Grams(d))
    d.kg should be(Kilograms(d))
    d.kilograms should be(Kilograms(d))
    d.tonnes should be(Tonnes(d))
    d.pounds should be(Pounds(d))
    d.ounces should be(Ounces(d))
  }

  it should "provide implicit conversions from arbitrary Numeric types" in {
    import MassConversionsX._

    val i = 10
    i.grams should be(Grams(i))

    val bd = BigDecimal(10.22)
    bd.grams should be(Grams(bd))
  }

  it should "provide implicit conversions from String" in {
    import MassConversionsX._

    "10.45 mcg".toMass.get should be(Micrograms(10.45))
    "10.45 mg".toMass.get should be(Milligrams(10.45))
    "10.45 g".toMass.get should be(Grams(10.45))
    "10.45 kg".toMass.get should be(Kilograms(10.45))
    "10.45 t".toMass.get should be(Tonnes(10.45))
    "10.45 lb".toMass.get should be(Pounds(10.45))
    "10.45 oz".toMass.get should be(Ounces(10.45))
    "10.45 zz".toMass.failed.get should be(QuantityStringParseException("Unable to parse Mass", "10.45 zz"))
    "zz oz".toMass.failed.get should be(QuantityStringParseException("Unable to parse Mass", "zz oz"))
  }

  it should "provide Numeric support" in {
    import MassConversionsX._

    val ms = List(Grams(1000), Kilograms(10))
    ms.sum should be(Kilograms(11))
  }
}
