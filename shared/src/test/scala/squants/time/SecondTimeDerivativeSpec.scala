/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.time

import squants.CustomMatchers
import squants.motion.MetersPerSecondSquared
import squants.space.Meters
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.5.0
 *
 */
class SecondTimeDerivativeSpec extends AnyFlatSpec with Matchers with CustomMatchers {

  behavior of "Second Time Derivatives and Integrals as implemented in Distance and Acceleration"

  it should "satisfy Derivative^2 = Integral / TimeSquared" in {
    MetersPerSecondSquared(55) should be(Meters(55) / SecondsSquared(1))
    MetersPerSecondSquared(55) should be(Meters(55) per SecondsSquared(1))
  }

  it should "satisfy Integral = Derivative^2 * TimeSquared" in {
    Meters(55) should be(MetersPerSecondSquared(55) * SecondsSquared(1))
  }
}
