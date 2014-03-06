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
import squants.energy.Joules

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class TemperatureSpec extends FlatSpec with Matchers {

  behavior of "Temperature and its Units of Measure"

  they should "create values using UOM factories" in {
    assert(Kelvin(1).toKelvinScale == 1)
    assert(Fahrenheit(1).toFahrenheitScale == 1)
    assert(Celsius(1).toCelsiusScale == 1)
    assert(Kelvin(1).toKelvinDegrees == 1)
    assert(Fahrenheit(1).toFahrenheitDegrees == 1)
    assert(Celsius(1).toCelsiusDegrees == 1)
  }

  they should "properly convert to all supported Units of Measure (Scale)" in {
    val x = Kelvin(0)
    assert(x.toKelvinScale == 0)
    assert(x.toFahrenheitScale == -459.67)
    assert(x.toCelsiusScale == -273.15)
    assert(x.to(Kelvin) == 0)
    assert(x.to(Fahrenheit) == -459.67)
    assert(x.to(Celsius) == -273.15)

    val y = Fahrenheit(32)
    assert(y.toKelvinScale == 273.15)
    assert(y.toFahrenheitScale == 32)
    assert(y.toCelsiusScale == 0)
    assert(y.to(Kelvin) == 273.15)
    assert(y.to(Fahrenheit) == 32)
    assert(y.to(Celsius) == 0)

    val z = Celsius(100)
    assert(z.toKelvinScale == 373.15)
    assert(z.toFahrenheitScale == 212)
    assert(z.toCelsiusScale == 100)
    assert(z.to(Kelvin) == 373.15)
    assert(z.to(Fahrenheit) == 212)
    assert(z.to(Celsius) == 100)
  }

  they should "properly convert to all supported Units of Measure (Degrees)" in {
    val x = Kelvin(5)
    assert(x.toKelvinDegrees == 5)
    assert(x.toFahrenheitDegrees == 9)
    assert(x.toCelsiusDegrees == 5)

    val y = Fahrenheit(9)
    assert(y.toKelvinDegrees == 5)
    assert(y.toFahrenheitDegrees == 9)
    assert(y.toCelsiusDegrees == 5)

    assert(y.toDegrees(Kelvin) == 5)
    assert(y.toDegrees(Fahrenheit) == 9)
    assert(y.toDegrees(Celsius) == 5)
  }

  they should "properly rebox Kelvin to all supported Units of Measure (Scale)" in {
    val x = Kelvin(0)
    assert(x.inFahrenheit == Fahrenheit(-459.67))
    assert(x.inCelsius == Celsius(-273.15))
    assert(x.in(Fahrenheit) == Fahrenheit(-459.67))
    assert(x.in(Celsius) == Celsius(-273.15))
  }

  they should "properly rebox Fahrenheit to all supported Units of Measure (Scale)" in {
    val x = Fahrenheit(0)
    assert((x.inKelvin - Kelvin((5d / 9) * 459.67)).value < 0.000000000001)
    assert(x.inCelsius == Celsius((5d / 9) * -32))
    assert((x.in(Kelvin) - Kelvin((5d / 9) * 459.67)).value < 0.000000000001)
    assert(x.in(Celsius) == Celsius((5d / 9) * -32))
  }

  they should "properly rebox Celsius to all supported Units of Measure (Scale)" in {
    val x = Celsius(0)
    assert(x.inKelvin == Kelvin(273.15))
    assert(x.inFahrenheit == Fahrenheit(32))
    assert(x.in(Kelvin) == Kelvin(273.15))
    assert(x.in(Fahrenheit) == Fahrenheit(32))
  }

  they should "properly plus Temperatures in like scale (Scale + Degrees)" in {
    assert(Kelvin(10) + Kelvin(20) == Kelvin(30))
    assert(Fahrenheit(10) + Fahrenheit(20) == Fahrenheit(30))
    assert(Celsius(10) + Celsius(20) == Celsius(30))
  }

  they should "properly minus Temperatures in like scale (Scale + Degrees)" in {
    assert(Kelvin(30) - Kelvin(20) == Kelvin(10))
    assert(Fahrenheit(30) - Fahrenheit(20) == Fahrenheit(10))
    assert(Celsius(30) - Celsius(20) == Celsius(10))
  }

  they should "properly plus Temperatures in different scales (Scale + Degrees)" in {
    assert(Kelvin(10) + Celsius(20) == Kelvin(30))
    assert(Kelvin(10) + Fahrenheit(18) == Kelvin(20))

    assert(Fahrenheit(10) + Kelvin(20) == Fahrenheit(46))
    assert(Fahrenheit(10) + Celsius(20) == Fahrenheit(46))

    assert(Celsius(10) + Kelvin(20) == Celsius(30))
    assert(Celsius(10) + Fahrenheit(36) == Celsius(30))
  }

  they should "properly minus Temperatures in different scales (Scale + Degrees)" in {
    assert(Kelvin(50) - Celsius(20) == Kelvin(30))
    assert(Kelvin(30) - Fahrenheit(18) == Kelvin(20))

    assert(Fahrenheit(100) - Kelvin(20) == Fahrenheit(64))
    assert(Fahrenheit(100) - Celsius(20) == Fahrenheit(64))

    assert(Celsius(50) - Kelvin(20) == Celsius(30))
    assert(Celsius(50) - Fahrenheit(36) == Celsius(30))
  }

  they should "properly times Double (Degrees)" in {
    assert(Kelvin(10) * 5 == Kelvin(50))
    assert(Fahrenheit(10) * 5 == Fahrenheit(50))
    assert(Celsius(10) * 5 == Celsius(50))
  }

  they should "properly divide Double (Degrees)" in {
    assert(Kelvin(10) / 5 == Kelvin(2))
    assert(Fahrenheit(10) / 5 == Fahrenheit(2))
    assert(Celsius(10) / 5 == Celsius(2))
  }

  they should "compare Temperatures in the like scales (Scales)" in {
    assert(Kelvin(10).compare(Kelvin(10)) == 0)
    assert(Kelvin(10).compare(Kelvin(10.1)) == -1)
    assert(Kelvin(10).compare(Kelvin(9.9)) == 1)
    assert(Kelvin(10) == Kelvin(10))
    assert(Kelvin(10) < Kelvin(10.1))
    assert(Kelvin(10) > Kelvin(9.9))

    assert(Fahrenheit(10).compare(Fahrenheit(10)) == 0)
    assert(Fahrenheit(10).compare(Fahrenheit(10.1)) == -1)
    assert(Fahrenheit(10).compare(Fahrenheit(9.9)) == 1)
    assert(Fahrenheit(10) == Fahrenheit(10))
    assert(Fahrenheit(10) < Fahrenheit(10.1))
    assert(Fahrenheit(10) > Fahrenheit(9.9))

    assert(Celsius(10).compare(Celsius(10)) == 0)
    assert(Celsius(10).compare(Celsius(10.1)) == -1)
    assert(Celsius(10).compare(Celsius(9.9)) == 1)
    assert(Celsius(10) == Celsius(10))
    assert(Celsius(10) < Celsius(10.1))
    assert(Celsius(10) > Celsius(9.9))
  }

  they should "return properly formatted strings for all supported Units of Measure" in {
    assert(Kelvin(10).toString == "10.0°K")
    assert(Fahrenheit(10).toString == "10.0°F")
    assert(Celsius(10).toString == "10.0°C")

    val k = Kelvin(0)
    assert(k.toString(Kelvin) == "0.0°K")
    assert(k.toString(Fahrenheit) == "-459.67°F")
    assert(k.toString(Celsius) == "-273.15°C")

    val c = Celsius(0)
    assert(c.toString(Kelvin) == "273.15°K")
    assert(c.toString(Fahrenheit) == "32.0°F")
    assert(c.toString(Celsius) == "0.0°C")

    val f = Fahrenheit(32)
    assert(f.toString(Kelvin) == "273.15°K")
    assert(f.toString(Fahrenheit) == "32.0°F")
    assert(f.toString(Celsius) == "0.0°C")
  }

  they should "return Energy when multiplied by ThermalCapacity" in {
    assert(Kelvin(1) * JoulesPerKelvin(1) == Joules(1))
  }

  behavior of "TemperatureConversions"

  they should "provide aliases for single unit values" in {
    import TemperatureConversions._

    assert(kelvin == Kelvin(1))
    assert(celsius == Celsius(1))
    assert(fahrenheit == Fahrenheit(1))
  }

  they should "provide proper formulas for conversion between scales" in {
    import TemperatureConversions._

    assert(kelvinToFahrenheitScale(0) == -459.67)
    assert(fahrenheitToKelvinScale(-459.67) == 0)
    assert(celsiusToFahrenheitScale(0) == 32)
    assert(fahrenheitToCelsiusScale(32) == 0)
    assert(kelvinToCelsiusScale(0) == -273.15)
    assert(celsiusToKelvinScale(0) == 273.15)
  }

  they should "provide proper formulas for conversion between degrees" in {
    import TemperatureConversions._

    assert(kelvinToFahrenheitDegrees(5) == 9)
    assert(fahrenheitToKelvinDegrees(9) == 5)
    assert(celsiusToFahrenheitDegrees(5) == 9)
    assert(fahrenheitToCelsiusDegrees(9) == 5)
    assert(kelvinToCelsiusDegrees(1) == 1)
    assert(celsiusToKelvinDegrees(1) == 1)
  }

  they should "provide implicit conversion from Double" in {
    import TemperatureConversions._

    val d = 10d
    assert(d.C == Celsius(d))
    assert(d.celsius == Celsius(d))
    assert(d.degreesCelsius == Celsius(d))
    assert(d.F == Fahrenheit(d))
    assert(d.Fah == Fahrenheit(d))
    assert(d.fahrenheit == Fahrenheit(d))
    assert(d.degreesFahrenheit == Fahrenheit(d))
    assert(d.K == Kelvin(d))
    assert(d.kelvin == Kelvin(d))
    assert(d.degreesKelvin == Kelvin(d))
  }

  they should "provide implicit conversions from String" in {
    import TemperatureConversions._

    assert("10.22°F".toTemperature.get == Fahrenheit(10.22))
    assert("10.22°K".toTemperature.get == Kelvin(10.22))
    assert("10.22°C".toTemperature.get == Celsius(10.22))
    assert("10.22 F".toTemperature.get == Fahrenheit(10.22))
    assert("10.22 K".toTemperature.get == Kelvin(10.22))
    assert("10.22 C".toTemperature.get == Celsius(10.22))
  }
}

object TemperatureChecks extends Properties("Temperature") {

  property("Celsius to Fahrenheit") = forAll { (a: Double) ⇒
    Celsius(a).toFahrenheitScale == (a * 9d / 5) + 32d
  }

  property("Fahrenheit to Celsius ") = forAll { (a: Double) ⇒
    Fahrenheit(a).toCelsiusScale == (a - 32d) * 5d / 9d
  }
}
