/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.thermal

import org.scalatest.{ FlatSpec, Matchers, TryValues }
import org.scalatest.prop.TableDrivenPropertyChecks
import squants.energy.Joules
import squants.mass.Kilograms
import squants.QuantityParseException

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class TemperatureSpec extends FlatSpec
  with Matchers with TableDrivenPropertyChecks with TryValues {

  behavior of "Temperature and its Units of Measure"

  they should "create values using UOM factories" in {
    Kelvin(1).toKelvinScale should be(1)
    Fahrenheit(1).toFahrenheitScale should be(1)
    Celsius(1).toCelsiusScale should be(1)
    Rankine(1).toScale(Rankine) should be(1)

    Kelvin(1).toKelvinDegrees should be(1)
    Fahrenheit(1).toFahrenheitDegrees should be(1)
    Celsius(1).toCelsiusDegrees should be(1)
    Rankine(1).toDegrees(Rankine) should be(1)
  }

  they should "create values from properly formatted Strings" in {
    Temperature("10.22°F").get should be(Fahrenheit(10.22))
    Temperature("10.22°K").get should be(Kelvin(10.22))
    Temperature("10.22°C").get should be(Celsius(10.22))
    Temperature("10.22°R").get should be(Rankine(10.22))
    Temperature("10.22 F").get should be(Fahrenheit(10.22))
    Temperature("10.22 K").get should be(Kelvin(10.22))
    Temperature("10.22 C").get should be(Celsius(10.22))
    Temperature("10.22 R").get should be(Rankine(10.22))
    Temperature("7.06E123°F").get should be(Fahrenheit(7.06e123))
    Temperature("7.06E123 F").get should be(Fahrenheit(7.06e123))
    Temperature("7.06E123°K").get should be(Kelvin(7.06e123))
    Temperature("7.06E123 K").get should be(Kelvin(7.06e123))
    Temperature("7.06E123°C").get should be(Celsius(7.06e123))
    Temperature("7.06E123 C").get should be(Celsius(7.06e123))
    Temperature("7.06E123°R").get should be(Rankine(7.06e123))
    Temperature("7.06E123 R").get should be(Rankine(7.06e123))
    Temperature("10.22 Z").failed.get should be(QuantityParseException("Unable to parse Temperature", "10.22 Z"))
    Temperature("ZZ F").failed.get should be(QuantityParseException("Unable to parse Temperature", "ZZ F"))
  }

  they should "be flexible in parsing strings with regard to degree symbol and whitespace" in {
    Temperature("10.22 f").get should be (Fahrenheit(10.22))
    Temperature("10.22 °f").get should be (Fahrenheit(10.22))
    Temperature("10.22  °f").get should be (Fahrenheit(10.22))
    Temperature("10.22 ° f").get should be (Fahrenheit(10.22))
    Temperature("10.22 °   f").get should be (Fahrenheit(10.22))
    Temperature("10.22  °   f").get should be (Fahrenheit(10.22))
    Temperature("10.22°f").get should be (Fahrenheit(10.22))

    Temperature("10.22°°f").failed.get should be (QuantityParseException("Unable to parse Temperature", "10.22°°f"))
  }

  they should "refuse to parse strings that only have a unit - issue #261" in {

    val symbols = Table("°K", "°F", "°C", "°R", "K", "F", "C", "R")

    forAll(symbols) { symbol =>
      Temperature(symbol).failure.exception shouldBe a [QuantityParseException]
    }
  }

  they should "properly convert to all supported Units of Measure (Scale)" in {
    val x = Kelvin(0)
    x.toKelvinScale should be(0)
    x.toFahrenheitScale should be(-459.67)
    x.toCelsiusScale should be(-273.15)
    x.toScale(Rankine) should be(0)
    x.to(Kelvin) should be(0)
    x.to(Fahrenheit) should be(-459.67)
    x.to(Celsius) should be(-273.15)
    x.to(Rankine) should be(0)

    val y = Fahrenheit(32)
    y.toKelvinScale should be(273.15)
    y.toFahrenheitScale should be(32)
    y.toCelsiusScale should be(0)
    y.toScale(Rankine) should be(491.67)
    y.to(Kelvin) should be(273.15)
    y.to(Fahrenheit) should be(32)
    y.to(Celsius) should be(0)
    y.to(Rankine) should be(491.67)

    val z = Celsius(100)
    z.toKelvinScale should be(373.15)
    z.toFahrenheitScale should be(212)
    z.toCelsiusScale should be(100)
    z.toScale(Rankine) should be(671.67)
    z.to(Kelvin) should be(373.15)
    z.to(Fahrenheit) should be(212)
    z.to(Celsius) should be(100)
    z.to(Rankine) should be(671.67)
  }

  they should "properly convert to all supported Units of Measure (Degrees)" in {
    val x = Kelvin(5)
    x.toKelvinDegrees should be(5)
    x.toFahrenheitDegrees should be(9)
    x.toCelsiusDegrees should be(5)
    x.toDegrees(Rankine) should be(9)

    val y = Fahrenheit(9)
    y.toKelvinDegrees should be(5)
    y.toFahrenheitDegrees should be(9)
    y.toCelsiusDegrees should be(5)

    y.toDegrees(Kelvin) should be(5)
    y.toDegrees(Fahrenheit) should be(9)
    y.toDegrees(Celsius) should be(5)
    y.toDegrees(Rankine) should be(9)
  }

  they should "properly rebox Kelvin to all supported Units of Measure (Scale)" in {
    val x = Kelvin(0)
    x.inFahrenheit should be(Fahrenheit(-459.67))
    x.inCelsius should be(Celsius(-273.15))
    x.in(Fahrenheit) should be(Fahrenheit(-459.67))
    x.in(Celsius) should be(Celsius(-273.15))
    x.in(Rankine) should be(Rankine(0))
  }

  they should "properly rebox Fahrenheit to all supported Units of Measure (Scale)" in {
    val x = Fahrenheit(0)
    (x.inKelvin - Kelvin((5d / 9) * 459.67)).value < 0.000000000001 should be(right = true)
    x.inCelsius should be(Celsius((5d / 9) * -32))
    (x.in(Kelvin) - Kelvin((5d / 9) * 459.67)).value < 0.000000000001 should be(right = true)
    x.in(Celsius) should be(Celsius((5d / 9) * -32))
    x.in(Rankine) should be(Rankine(459.67))
  }

  they should "properly rebox Celsius to all supported Units of Measure (Scale)" in {
    val x = Celsius(0)
    x.inKelvin should be(Kelvin(273.15))
    x.inFahrenheit should be(Fahrenheit(32))
    x.in(Kelvin) should be(Kelvin(273.15))
    x.in(Fahrenheit) should be(Fahrenheit(32))
    x.in(Rankine).value should be(491.67 +- 0.000000000001)
  }

  they should "properly plus Temperatures in like scale (Scale + Degrees)" in {
    Kelvin(10) + Kelvin(20) should be(Kelvin(30))
    Fahrenheit(10) + Fahrenheit(20) should be(Fahrenheit(30))
    Celsius(10) + Celsius(20) should be(Celsius(30))
    Rankine(10) + Rankine(20) should be(Rankine(30))
  }

  they should "properly minus Temperatures in like scale (Scale + Degrees)" in {
    Kelvin(30) - Kelvin(20) should be(Kelvin(10))
    Fahrenheit(30) - Fahrenheit(20) should be(Fahrenheit(10))
    Celsius(30) - Celsius(20) should be(Celsius(10))
    Rankine(30) - Rankine(20) should be(Rankine(10))
  }

  they should "properly plus Temperatures in different scales (Scale + Degrees)" in {
    Kelvin(10) + Celsius(20) should be(Kelvin(30))
    Kelvin(10) + Fahrenheit(18) should be(Kelvin(20))
    Kelvin(10) + Rankine(18) should be(Kelvin(20))

    Fahrenheit(10) + Kelvin(20) should be(Fahrenheit(46))
    Fahrenheit(10) + Celsius(20) should be(Fahrenheit(46))
    Fahrenheit(10) + Rankine(20) should be(Fahrenheit(30))

    Celsius(10) + Kelvin(20) should be(Celsius(30))
    Celsius(10) + Fahrenheit(36) should be(Celsius(30))
    Celsius(10) + Rankine(36) should be(Celsius(30))
  }

  they should "properly minus Temperatures in different scales (Scale + Degrees)" in {
    Kelvin(50) - Celsius(20) should be(Kelvin(30))
    Kelvin(30) - Fahrenheit(18) should be(Kelvin(20))
    Kelvin(30) - Rankine(18) should be(Kelvin(20))

    Fahrenheit(100) - Kelvin(20) should be(Fahrenheit(64))
    Fahrenheit(100) - Celsius(20) should be(Fahrenheit(64))
    Fahrenheit(100) - Rankine(20) should be(Fahrenheit(80))

    Celsius(50) - Kelvin(20) should be(Celsius(30))
    Celsius(50) - Fahrenheit(36) should be(Celsius(30))
    Celsius(50) - Rankine(36) should be(Celsius(30))
  }

  they should "properly times Double (Degrees)" in {
    Kelvin(10) * 5 should be(Kelvin(50))
    Fahrenheit(10) * 5 should be(Fahrenheit(50))
    Celsius(10) * 5 should be(Celsius(50))
    Rankine(10) * 5 should be(Rankine(50))
  }

  they should "properly divide Double (Degrees)" in {
    Kelvin(10) / 5 should be(Kelvin(2))
    Fahrenheit(10) / 5 should be(Fahrenheit(2))
    Celsius(10) / 5 should be(Celsius(2))
    Rankine(10) / 5 should be(Rankine(2))
  }

  they should "compare Temperatures in the like scales (Scales)" in {
    Kelvin(10).compare(Kelvin(10)) should be(0)
    Kelvin(10).compare(Kelvin(10.1)) should be(-1)
    Kelvin(10).compare(Kelvin(9.9)) should be(1)
    Kelvin(10) should be(Kelvin(10))
    Kelvin(10) < Kelvin(10.1) should be(right = true)
    Kelvin(10) > Kelvin(9.9) should be(right = true)

    Fahrenheit(10).compare(Fahrenheit(10)) should be(0)
    Fahrenheit(10).compare(Fahrenheit(10.1)) should be(-1)
    Fahrenheit(10).compare(Fahrenheit(9.9)) should be(1)
    Fahrenheit(10) should be(Fahrenheit(10))
    Fahrenheit(10) < Fahrenheit(10.1) should be(right = true)
    Fahrenheit(10) > Fahrenheit(9.9) should be(right = true)

    Celsius(10).compare(Celsius(10)) should be(0)
    Celsius(10).compare(Celsius(10.1)) should be(-1)
    Celsius(10).compare(Celsius(9.9)) should be(1)
    Celsius(10) should be(Celsius(10))
    Celsius(10) < Celsius(10.1) should be(right = true)
    Celsius(10) > Celsius(9.9) should be(right = true)

    Rankine(10).compare(Rankine(10)) should be(0)
    Rankine(10).compare(Rankine(10.1)) should be(-1)
    Rankine(10).compare(Rankine(9.9)) should be(1)
    Rankine(10) should be(Rankine(10))
    Rankine(10) < Rankine(10.1) should be(right = true)
    Rankine(10) > Rankine(9.9) should be(right = true)
  }

  it should "compare a non-null Quantity to a null and return a proper result" in {
    val x = Kelvin(2.1)
    x == null should be(right = false)
    null == x should be(right = false)
    x != null should be(right = true)
    null != x should be(right = true)
  }

  it should "compare a null Quantity to null and return a proper result" in {
    val x: Temperature = null
    x == null should be(right = true)
    null == x should be(right = true)
    x != null should be(right = false)
    null != x should be(right = false)
  }

  it should "compare a null Quantity to a non-null Quantity" in {
    val x = null
    val y = Kelvin(2.1)
    x == y should be(right = false)
    y == x should be(right = false)
  }

  it should "not equal an equivalent value of a different type" in {
    val x = Kelvin(2.1)
    val y = Kilograms(2.1)
    x.equals(y) should be(right = false)
    x == y should be(right = false)
    x != y should be(right = true)
  }

  they should "return properly formatted strings for all supported Units of Measure" in {
    Kelvin(10).toString should be("10.0 K")
    Fahrenheit(10).toString should be("10.0°F")
    Celsius(10).toString should be("10.0°C")
    Rankine(10).toString should be("10.0°R")

    val k = Kelvin(0)
    k.toString(Kelvin) should be("0.0 K")
    k.toString(Fahrenheit) should be("-459.67°F")
    k.toString(Celsius) should be("-273.15°C")
    k.toString(Rankine) should be("0.0°R")

    val c = Celsius(0)
    c.toString(Kelvin) should be("273.15 K")
    c.toString(Fahrenheit) should be("32.0°F")
    c.toString(Celsius) should be("0.0°C")
    c.toString(Rankine) should be("491.66999999999996°R")

    val f = Fahrenheit(32)
    f.toString(Kelvin) should be("273.15 K")
    f.toString(Fahrenheit) should be("32.0°F")
    f.toString(Celsius) should be("0.0°C")
    f.toString(Rankine) should be("491.67°R")

    val r = Rankine(0)
    r.toString(Kelvin) should be("0.0 K")
    r.toString(Fahrenheit) should be("-459.67°F")
    r.toString(Celsius) should be("-273.15°C")
  }

  they should "return Energy when multiplied by ThermalCapacity" in {
    Kelvin(1) * JoulesPerKelvin(1) should be(Joules(1))
  }

  behavior of "TemperatureConversions"

  they should "provide aliases for single unit values" in {
    import TemperatureConversions._

    kelvin should be(Kelvin(1))
    celsius should be(Celsius(1))
    fahrenheit should be(Fahrenheit(1))
    rankine should be(Rankine(1))
  }

  they should "provide proper formulas for conversion between scales" in {
    import TemperatureConversions._

    kelvinToFahrenheitScale(0) should be(-459.67)
    fahrenheitToKelvinScale(-459.67) should be(0)
    celsiusToFahrenheitScale(0) should be(32)
    fahrenheitToCelsiusScale(32) should be(0)
    kelvinToCelsiusScale(0) should be(-273.15)
    celsiusToKelvinScale(0) should be(273.15)

    kelvinToRankineScale(100) should be(180)
    rankineToKelvinScale(180) should be(100)
    celsiusToRankineScale(0) should be(491.67 +- 0.001)
    rankineToCelsiusScale(491.67) should be(0)
    fahrenheitToRankineScale(0) should be(459.67)
    rankineToFahrenheitScale(0) should be(-459.67)
  }

  they should "provide proper formulas for conversion between degrees" in {
    import TemperatureConversions._

    kelvinToFahrenheitDegrees(5) should be(9)
    fahrenheitToKelvinDegrees(9) should be(5)
    celsiusToFahrenheitDegrees(5) should be(9)
    fahrenheitToCelsiusDegrees(9) should be(5)
    kelvinToCelsiusDegrees(1) should be(1)
    celsiusToKelvinDegrees(1) should be(1)

    kelvinToRankineDegrees(5) should be(9)
    rankineToKelvinDegrees(9) should be(5)
    celsiusToRankineDegrees(5) should be(9)
    rankineToCelsiusDegrees(9) should be(5)
    fahrenheitToRankineDegrees(1) should be(1)
    rankineToFahrenheitDegrees(1) should be(1)
  }

  they should "provide implicit conversion from Double" in {
    import TemperatureConversions._

    val d = 10d
    d.C should be(Celsius(d))
    d.celsius should be(Celsius(d))
    d.degreesCelsius should be(Celsius(d))

    d.F should be(Fahrenheit(d))
    d.Fah should be(Fahrenheit(d))
    d.fahrenheit should be(Fahrenheit(d))
    d.degreesFahrenheit should be(Fahrenheit(d))

    d.K should be(Kelvin(d))
    d.kelvin should be(Kelvin(d))
    d.degreesKelvin should be(Kelvin(d))

    d.R should be(Rankine(d))
    d.rankine should be(Rankine(d))
    d.degreesRankine should be(Rankine(d))
  }

  they should "provide implicit conversions from String" in {
    import TemperatureConversions._

    "10.22°F".toTemperature.get should be(Fahrenheit(10.22))
    "10.22°K".toTemperature.get should be(Kelvin(10.22))
    "10.22°C".toTemperature.get should be(Celsius(10.22))
    "10.22 F".toTemperature.get should be(Fahrenheit(10.22))
    "10.22 K".toTemperature.get should be(Kelvin(10.22))
    "10.22 C".toTemperature.get should be(Celsius(10.22))
    "10.22°R".toTemperature.get should be(Rankine(10.22))
    "10.22 R".toTemperature.get should be(Rankine(10.22))
    "10.22 Z".toTemperature.failed.get should be(QuantityParseException("Unable to parse Temperature", "10.22 Z"))
    "ZZ F".toTemperature.failed.get should be(QuantityParseException("Unable to parse Temperature", "ZZ F"))
  }
}
