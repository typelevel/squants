/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.thermal

import org.scalatest.{ Matchers, FlatSpec }
import org.scalacheck.Properties
import org.scalacheck.Prop._
import org.json4s.native.Serialization
import org.json4s.{ ShortTypeHints, DefaultFormats }
import squants.energy.Joules

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class TemperatureSpec extends FlatSpec with Matchers {

  behavior of "Temperature and its Units of Measure"

  they should "create values using UOM factories" in {
    Kelvin(1).toKelvinScale should be(1)
    Fahrenheit(1).toFahrenheitScale should be(1)
    Celsius(1).toCelsiusScale should be(1)
    Kelvin(1).toKelvinDegrees should be(1)
    Fahrenheit(1).toFahrenheitDegrees should be(1)
    Celsius(1).toCelsiusDegrees should be(1)
  }

  they should "properly convert to all supported Units of Measure (Scale)" in {
    val x = Kelvin(0)
    x.toKelvinScale should be(0)
    x.toFahrenheitScale should be(-459.67)
    x.toCelsiusScale should be(-273.15)
    x.to(Kelvin) should be(0)
    x.to(Fahrenheit) should be(-459.67)
    x.to(Celsius) should be(-273.15)

    val y = Fahrenheit(32)
    y.toKelvinScale should be(273.15)
    y.toFahrenheitScale should be(32)
    y.toCelsiusScale should be(0)
    y.to(Kelvin) should be(273.15)
    y.to(Fahrenheit) should be(32)
    y.to(Celsius) should be(0)

    val z = Celsius(100)
    z.toKelvinScale should be(373.15)
    z.toFahrenheitScale should be(212)
    z.toCelsiusScale should be(100)
    z.to(Kelvin) should be(373.15)
    z.to(Fahrenheit) should be(212)
    z.to(Celsius) should be(100)
  }

  they should "properly convert to all supported Units of Measure (Degrees)" in {
    val x = Kelvin(5)
    x.toKelvinDegrees should be(5)
    x.toFahrenheitDegrees should be(9)
    x.toCelsiusDegrees should be(5)

    val y = Fahrenheit(9)
    y.toKelvinDegrees should be(5)
    y.toFahrenheitDegrees should be(9)
    y.toCelsiusDegrees should be(5)

    y.toDegrees(Kelvin) should be(5)
    y.toDegrees(Fahrenheit) should be(9)
    y.toDegrees(Celsius) should be(5)
  }

  they should "properly rebox Kelvin to all supported Units of Measure (Scale)" in {
    val x = Kelvin(0)
    x.inFahrenheit should be(Fahrenheit(-459.67))
    x.inCelsius should be(Celsius(-273.15))
    x.in(Fahrenheit) should be(Fahrenheit(-459.67))
    x.in(Celsius) should be(Celsius(-273.15))
  }

  they should "properly rebox Fahrenheit to all supported Units of Measure (Scale)" in {
    val x = Fahrenheit(0)
    (x.inKelvin - Kelvin((5d / 9) * 459.67)).value < 0.000000000001 should be(right = true)
    x.inCelsius should be(Celsius((5d / 9) * -32))
    (x.in(Kelvin) - Kelvin((5d / 9) * 459.67)).value < 0.000000000001 should be(right = true)
    x.in(Celsius) should be(Celsius((5d / 9) * -32))
  }

  they should "properly rebox Celsius to all supported Units of Measure (Scale)" in {
    val x = Celsius(0)
    x.inKelvin should be(Kelvin(273.15))
    x.inFahrenheit should be(Fahrenheit(32))
    x.in(Kelvin) should be(Kelvin(273.15))
    x.in(Fahrenheit) should be(Fahrenheit(32))
  }

  they should "properly plus Temperatures in like scale (Scale + Degrees)" in {
    Kelvin(10) + Kelvin(20) should be(Kelvin(30))
    Fahrenheit(10) + Fahrenheit(20) should be(Fahrenheit(30))
    Celsius(10) + Celsius(20) should be(Celsius(30))
  }

  they should "properly minus Temperatures in like scale (Scale + Degrees)" in {
    Kelvin(30) - Kelvin(20) should be(Kelvin(10))
    Fahrenheit(30) - Fahrenheit(20) should be(Fahrenheit(10))
    Celsius(30) - Celsius(20) should be(Celsius(10))
  }

  they should "properly plus Temperatures in different scales (Scale + Degrees)" in {
    Kelvin(10) + Celsius(20) should be(Kelvin(30))
    Kelvin(10) + Fahrenheit(18) should be(Kelvin(20))

    Fahrenheit(10) + Kelvin(20) should be(Fahrenheit(46))
    Fahrenheit(10) + Celsius(20) should be(Fahrenheit(46))

    Celsius(10) + Kelvin(20) should be(Celsius(30))
    Celsius(10) + Fahrenheit(36) should be(Celsius(30))
  }

  they should "properly minus Temperatures in different scales (Scale + Degrees)" in {
    Kelvin(50) - Celsius(20) should be(Kelvin(30))
    Kelvin(30) - Fahrenheit(18) should be(Kelvin(20))

    Fahrenheit(100) - Kelvin(20) should be(Fahrenheit(64))
    Fahrenheit(100) - Celsius(20) should be(Fahrenheit(64))

    Celsius(50) - Kelvin(20) should be(Celsius(30))
    Celsius(50) - Fahrenheit(36) should be(Celsius(30))
  }

  they should "properly times Double (Degrees)" in {
    Kelvin(10) * 5 should be(Kelvin(50))
    Fahrenheit(10) * 5 should be(Fahrenheit(50))
    Celsius(10) * 5 should be(Celsius(50))
  }

  they should "properly divide Double (Degrees)" in {
    Kelvin(10) / 5 should be(Kelvin(2))
    Fahrenheit(10) / 5 should be(Fahrenheit(2))
    Celsius(10) / 5 should be(Celsius(2))
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
  }

  they should "return properly formatted strings for all supported Units of Measure" in {
    Kelvin(10).toString should be("10.0°K")
    Fahrenheit(10).toString should be("10.0°F")
    Celsius(10).toString should be("10.0°C")

    val k = Kelvin(0)
    k.toString(Kelvin) should be("0.0°K")
    k.toString(Fahrenheit) should be("-459.67°F")
    k.toString(Celsius) should be("-273.15°C")

    val c = Celsius(0)
    c.toString(Kelvin) should be("273.15°K")
    c.toString(Fahrenheit) should be("32.0°F")
    c.toString(Celsius) should be("0.0°C")

    val f = Fahrenheit(32)
    f.toString(Kelvin) should be("273.15°K")
    f.toString(Fahrenheit) should be("32.0°F")
    f.toString(Celsius) should be("0.0°C")
  }

  they should "return Energy when multiplied by ThermalCapacity" in {
    Kelvin(1) * JoulesPerKelvin(1) should be(Joules(1))
  }

  they should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats.withBigDecimal + ShortTypeHints(List(classOf[Fahrenheit], classOf[Celsius], classOf[Kelvin]))

    val cel = Celsius(99)
    val serC = Serialization.write(cel)
    val desC = Serialization.read[Celsius](serC)
    desC should be(cel)

    val fah = Fahrenheit(100)
    val serF = Serialization.write(fah)
    val desF = Serialization.read[Fahrenheit](serF)
    desF should be(fah)

    val kel = Kelvin(101)
    val serK = Serialization.write(kel)
    val desK = Serialization.read[Kelvin](serK)
    desK should be(kel)

    val cel3 = Serialization.read[Temperature](serC)
    cel3 should be(cel)
    val fah3 = Serialization.read[Temperature](serF)
    fah3 should be(fah)
    val kel3 = Serialization.read[Temperature](serK)
    kel3 should be(kel)
  }

  behavior of "TemperatureConversions"

  they should "provide aliases for single unit values" in {
    import TemperatureConversions._

    kelvin should be(Kelvin(1))
    celsius should be(Celsius(1))
    fahrenheit should be(Fahrenheit(1))
  }

  they should "provide proper formulas for conversion between scales" in {
    import TemperatureConversions._

    kelvinToFahrenheitScale(0) should be(-459.67)
    fahrenheitToKelvinScale(-459.67) should be(0)
    celsiusToFahrenheitScale(0) should be(32)
    fahrenheitToCelsiusScale(32) should be(0)
    kelvinToCelsiusScale(0) should be(-273.15)
    celsiusToKelvinScale(0) should be(273.15)
  }

  they should "provide proper formulas for conversion between degrees" in {
    import TemperatureConversions._

    kelvinToFahrenheitDegrees(5) should be(9)
    fahrenheitToKelvinDegrees(9) should be(5)
    celsiusToFahrenheitDegrees(5) should be(9)
    fahrenheitToCelsiusDegrees(9) should be(5)
    kelvinToCelsiusDegrees(1) should be(1)
    celsiusToKelvinDegrees(1) should be(1)
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
  }

  they should "provide implicit conversions from String" in {
    import TemperatureConversions._

    "10.22°F".toTemperature.right.get should be(Fahrenheit(10.22))
    "10.22°K".toTemperature.right.get should be(Kelvin(10.22))
    "10.22°C".toTemperature.right.get should be(Celsius(10.22))
    "10.22 F".toTemperature.right.get should be(Fahrenheit(10.22))
    "10.22 K".toTemperature.right.get should be(Kelvin(10.22))
    "10.22 C".toTemperature.right.get should be(Celsius(10.22))
    "10.22 Z".toTemperature.left.get should be("Unable to parse 10.22 Z as Temperature")
  }
}
