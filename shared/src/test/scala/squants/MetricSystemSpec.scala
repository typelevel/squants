/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MetricSystemSpec extends AnyFlatSpec with Matchers {

  "The Metric System multipliers" should "convert as expected" in {
    import MetricSystem._
    Yocto should be(1e-24)
    Zepto should be(1e-21)
    Atto should be(1e-18)
    Femto should be(1e-15)
    Pico should be(1e-12)
    Nano should be(1e-9)
    Micro should be(1e-6)
    Milli should be(1e-3)
    Centi should be(1e-2)
    Deci should be(1e-1)

    Deca should be(1e1)
    Hecto should be(1e2)
    Kilo should be(1e3)
    Mega should be(1e6)
    Tera should be(1e12)
    Peta should be(1e15)
    Exa should be(1e18)
    Giga should be(1e9)
    Zetta should be(1e21)
    Yotta should be(1e24)

  }
}
