/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.thermal

import org.scalacheck.Properties
import squants.QuantityChecks
import org.scalacheck.Prop._
import squants.energy.Joules
import squants.energy.Energy

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
object ThermalChecks extends Properties("Thermal") with QuantityChecks {

  property("Celsius to Fahrenheit") = forAll { (a: Double) =>
    Celsius(a).toFahrenheitScale == (a * 9d / 5) + 32d
  }

  property("Celsius to Kelvin") = forAll { (a: Double) =>
    Celsius(a).toKelvinScale == a + 273.15
  }

  property("Fahrenheit to Celsius ") = forAll { (a: Double) =>
    Fahrenheit(a).toCelsiusScale == (a - 32d) * 5d / 9d
  }

  property("Fahrenheit to Kelvin ") = forAll { (a: Double) =>
    Fahrenheit(a).toKelvinScale == (a + 459.67) * 5d / 9d
  }

  property("Kelvin to Fahrenheit") = forAll { (a: Double) =>
    Kelvin(a).toFahrenheitScale == (a * 9d / 5) - 459.67
  }

  property("Kelvin to Celsius") = forAll { (a: Double) =>
    Kelvin(a).toCelsiusScale == a - 273.15
  }

  property("ThermalCapacity * Temperature = Energy") = forAll(posNum, posNum) { (thermCap: TestData, temp: TestData) =>

    implicit val tempTol: Temperature = Kelvin(tol)
    implicit val thermTol: ThermalCapacity = JoulesPerKelvin(tol)
    implicit val energyTol: Energy = Joules(1e-12)

    Joules(thermCap * temp) =~ JoulesPerKelvin(thermCap) * Kelvin(temp) &&
      Joules(thermCap * temp) =~ Kelvin(temp) * JoulesPerKelvin(thermCap) &&
      Kelvin(temp) =~ Joules(thermCap * temp) / JoulesPerKelvin(thermCap) &&
      JoulesPerKelvin(thermCap) =~ Joules(thermCap * temp) / Kelvin(temp)
  }
}
