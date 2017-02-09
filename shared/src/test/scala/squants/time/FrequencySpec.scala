/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.time

import org.scalatest.{ FlatSpec, Matchers }
import squants.{ Each, MetricSystem, QuantityParseException }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class FrequencySpec extends FlatSpec with Matchers {

  behavior of "Frequency and its Units of Measure"

  it should "create values using UOM factories" in {
    Hertz(1).toHertz should be(1)
    Kilohertz(1).toKilohertz should be(1)
    Megahertz(1).toMegahertz should be(1)
    Gigahertz(1).toGigahertz should be(1)
    Terahertz(1).toTerahertz should be(1)
    RevolutionsPerMinute(1).toRevolutionsPerMinute should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Frequency("10.22 Hz").get should be(Hertz(10.22))
    Frequency("10.22 kHz").get should be(Kilohertz(10.22))
    Frequency("10.22 MHz").get should be(Megahertz(10.22))
    Frequency("10.22 GHz").get should be(Gigahertz(10.22))
    Frequency("10.22 THz").get should be(Terahertz(10.22))
    Frequency("10.22 rpm").get should be(RevolutionsPerMinute(10.22))
    Frequency("10.45 zz").failed.get should be(QuantityParseException("Unable to parse Frequency", "10.45 zz"))
    Frequency("zz Hz").failed.get should be(QuantityParseException("Unable to parse Frequency", "zz Hz"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Hertz(1)
    x.toHertz should be(1)
    x.toKilohertz should be(1 / MetricSystem.Kilo)
    x.toMegahertz should be(1 / MetricSystem.Mega)
    x.toTerahertz should be(1 / MetricSystem.Tera)
    x.toRevolutionsPerMinute should be(60)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Hertz(1).toString(Hertz) should be("1.0 Hz")
    Kilohertz(1).toString(Kilohertz) should be("1.0 kHz")
    Megahertz(1).toString(Megahertz) should be("1.0 MHz")
    Gigahertz(1).toString(Gigahertz) should be("1.0 GHz")
    Terahertz(1).toString(Terahertz) should be("1.0 THz")
    RevolutionsPerMinute(1).toString(RevolutionsPerMinute) should be("1.0 rpm")
  }

  it should "return Count when multiplied by Time" in {
    Hertz(1) * Seconds(1) should be(Each(1))
  }

  behavior of "FrequencyConversions"

  it should "provide implicit conversion from Double" in {
    import FrequencyConversions._

    val d = 10d
    d.hertz should be(Hertz(d))
    d.kilohertz should be(Kilohertz(d))
    d.megahertz should be(Megahertz(d))
    d.gigahertz should be(Gigahertz(d))
    d.terahertz should be(Terahertz(d))
    d.rpm should be(RevolutionsPerMinute(d))
  }
}
