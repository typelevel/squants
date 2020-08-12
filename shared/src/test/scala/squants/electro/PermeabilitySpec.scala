/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2015-2017, Carlos Quiroz                                         **
**                                                                      **
\*                                                                      */

package squants.electro

import squants.QuantityParseException
import squants.space.Meters
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  cquiroz
 * @since   1.4
 *
 */
class PermeabilitySpec extends AnyFlatSpec with Matchers {

  behavior of "Permeability and its Units of Measure"

  it should "create values using UOM factories" in {
    HenriesPerMeter(1).toHenriesPerMeter should be(1)
    NewtonsPerAmperesSquared(1).toNewtonsPerAmpereSquared should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Permeability("10.22 H/m").get should be(HenriesPerMeter(10.22))
    Permeability("10.22 N/A²").get should be(HenriesPerMeter(10.22))
    Permeability("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Permeability", "10.22 zz"))
    Permeability("zz H").failed.get should be(QuantityParseException("Unable to parse Permeability", "zz H"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = HenriesPerMeter(1)
    x.toHenriesPerMeter should be(1.0)
    x.toNewtonsPerAmpereSquared should be(1.0)
    val y = NewtonsPerAmperesSquared(1)
    y.toHenriesPerMeter should be(1.0)
    y.toNewtonsPerAmpereSquared should be(1.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    HenriesPerMeter(1).toString(HenriesPerMeter) should be("1.0 H/m")
    NewtonsPerAmperesSquared(1).toString(NewtonsPerAmperesSquared) should be("1.0 N/A²")
  }

  it should "return Inductance when multiplied by Distance" in {
    HenriesPerMeter(1) * Meters(1) should be(Henry(1))
  }

  behavior of "PermeabilityConversions"

  it should "provide aliases for single unit values" in {
    import PermeabilityConversions._

    henriesPerMeter should be(HenriesPerMeter(1))
    newtonsPerAmperesSquared should be(NewtonsPerAmperesSquared(1))
  }

  it should "provide implicit conversion from Double" in {
    import PermeabilityConversions._

    val d = 10d
    d.henriesPerMeter should be(HenriesPerMeter(d))
    d.newtonsPerAmperesSquared should be(NewtonsPerAmperesSquared(d))
  }

  it should "provide Numeric support" in {
    import PermeabilityConversions.PermeabilityNumeric

    val is = List(HenriesPerMeter(100), HenriesPerMeter(10))
    is.sum should be(HenriesPerMeter(110))
  }
}
