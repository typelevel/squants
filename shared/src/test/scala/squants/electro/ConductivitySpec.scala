/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import squants.space.Meters
import squants.QuantityParseException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ConductivitySpec extends AnyFlatSpec with Matchers {

  behavior of "Conductivity and its Units of Measure"

  it should "create values using UOM factories" in {
    SiemensPerMeter(1).toSiemensPerMeter should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Conductivity("10.22 S/m").get should be(SiemensPerMeter(10.22))
    Conductivity("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Conductivity", "10.22 zz"))
    Conductivity("zz S/m").failed.get should be(QuantityParseException("Unable to parse Conductivity", "zz S/m"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = SiemensPerMeter(10)
    x.toSiemensPerMeter should be(10.0)
    x.inOhmMeters should be(OhmMeters(0.1))
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    SiemensPerMeter(1).toString(SiemensPerMeter) should be("1.0 S/m")
  }

  it should "return ElectricalConductance when multiplied by Length" in {
    SiemensPerMeter(1) * Meters(1) should be(Siemens(1))
  }

  behavior of "ConductivityConversions"

  it should "provide aliases for single unit values" in {
    import ConductivityConversions._

    siemenPerMeter should be(SiemensPerMeter(1))
  }

  it should "provide implicit conversion from Double" in {
    import ConductivityConversions._

    val d = 10.22
    d.siemensPerMeter should be(SiemensPerMeter(d))
  }

  it should "provide Numeric support" in {
    import ConductivityConversions.ConductivityNumeric

    val cs = List(SiemensPerMeter(100), SiemensPerMeter(10))
    cs.sum should be(SiemensPerMeter(110))
  }
}
