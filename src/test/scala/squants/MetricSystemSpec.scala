/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import org.scalatest.FlatSpec

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MetricSystemSpec extends FlatSpec {

  "The Metric System multipliers" should "convert as expected" in {
    import MetricSystem._
    assert(Exa == Peta * 1000d)
    assert(Peta == Tera * 1000d)
    assert(Tera == Giga * 1000d)
    assert(Giga == Mega * 1000d)
    assert(Mega == Kilo * 1000d)
    assert(Kilo == 1000d)
    assert(Milli == .001)
    assert(Micro == Milli * .001)
    assert(Nano == Micro * .001)
    assert(Pico == Nano * .001)
    assert(Femto == Pico * .001)
    assert(Atto == Femto * .001)
  }
}
