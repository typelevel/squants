/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.time

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.{ Each, MetricSystem }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class FrequencySpec extends FlatSpec with Matchers {

  behavior of "Frequency and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(Hertz(1).toHertz == 1)
    assert(Kilohertz(1).toKilohertz == 1)
    assert(Megahertz(1).toMegahertz == 1)
    assert(Gigahertz(1).toGigahertz == 1)
    assert(Terahertz(1).toTerahertz == 1)
    assert(RevolutionsPerMinute(1).toRevolutionsPerMinute == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Hertz(1)
    assert(x.toHertz == 1)
    assert(x.toKilohertz == 1 / MetricSystem.Kilo)
    assert(x.toMegahertz == 1 / MetricSystem.Mega)
    assert(x.toTerahertz == 1 / MetricSystem.Tera)
    assert(x.toRevolutionsPerMinute == 60)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Hertz(1).toString(Hertz) == "1.0 Hz")
    assert(Kilohertz(1).toString(Kilohertz) == "1.0 kHz")
    assert(Megahertz(1).toString(Megahertz) == "1.0 MHz")
    assert(Gigahertz(1).toString(Gigahertz) == "1.0 GHz")
    assert(Terahertz(1).toString(Terahertz) == "1.0 THz")
    assert(RevolutionsPerMinute(1).toString(RevolutionsPerMinute) == "1.0 rpm")
  }

  it should "return Count when multiplied by Time" in {
    assert(Hertz(1) * Seconds(1) == Each(1))
  }

  behavior of "FrequencyConversions"

  it should "provide implicit conversion from Double" in {
    import FrequencyConversions._

    val d = 10d
    assert(d.hertz == Hertz(d))
    assert(d.kilohertz == Kilohertz(d))
    assert(d.megahertz == Megahertz(d))
    assert(d.gigahertz == Gigahertz(d))
    assert(d.terahertz == Terahertz(d))
    assert(d.rpm == RevolutionsPerMinute(d))
  }
}
