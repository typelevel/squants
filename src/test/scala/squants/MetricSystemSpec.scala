/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import org.scalatest.{ Matchers, FlatSpec }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MetricSystemSpec extends FlatSpec with Matchers {

  "The Metric System multipliers" should "convert as expected" in {
    import MetricSystem._
    Exa should be(Peta * 1000d)
    Peta should be(Tera * 1000d)
    Tera should be(Giga * 1000d)
    Giga should be(Mega * 1000d)
    Mega should be(Kilo * 1000d)
    Kilo should be(1000d)
    Milli should be(.001)
    Micro should be(Milli * .001)
    Nano should be(Micro * .001)
    Pico should be(Nano * .001)
    Femto should be(Pico * .001)
    Atto should be(Femto * .001)
  }
}
