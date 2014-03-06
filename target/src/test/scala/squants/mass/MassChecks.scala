/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.mass

import squants.space.CubicMeters
import org.scalacheck.Properties
import squants.QuantityChecks
import org.scalacheck.Prop._

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
object MassChecks extends Properties("Mass") with QuantityChecks {

  property("Mass = Density * Volume") = forAll(posNum, posNum) { (density: TestData, volume: TestData) ⇒
    Kilograms(density * volume) == KilogramsPerCubicMeter(density) * CubicMeters(volume) &&
      Kilograms(density * volume) == CubicMeters(volume) * KilogramsPerCubicMeter(density) &&
      KilogramsPerCubicMeter(density) == Kilograms(density * volume) / CubicMeters(volume) &&
      CubicMeters(volume) == Kilograms(density * volume) / KilogramsPerCubicMeter(density)
  }
}
