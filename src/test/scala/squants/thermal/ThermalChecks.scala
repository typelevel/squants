/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.thermal

import org.scalacheck.Properties
import squants.QuantityChecks
import org.scalacheck.Prop._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
object ThermalChecks extends Properties("Thermal") with QuantityChecks {

  property("Celsius to Fahrenheit") = forAll { (a: Double) ⇒
    Celsius(a).toFahrenheitScale == (a * 9d / 5) + 32d
  }

  property("Fahrenheit to Celsius ") = forAll { (a: Double) ⇒
    Fahrenheit(a).toCelsiusScale == (a - 32d) * 5d / 9d
  }

  // TODO Add Checks for ThermalCapacity
}
