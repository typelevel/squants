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
class ResistivitySpec extends AnyFlatSpec with Matchers {

  behavior of "Resistivity and its Units of Measure"

  it should "create values using UOM factories" in {
    OhmMeters(1).toOhmMeters should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Resistivity("10.22 Ω⋅m").get should be(OhmMeters(10.22))
    Resistivity("10.22 zz").failed.get should be(QuantityParseException("Unable to parse Resistivity", "10.22 zz"))
    Resistivity("zz Ω⋅m").failed.get should be(QuantityParseException("Unable to parse Resistivity", "zz Ω⋅m"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = OhmMeters(10)
    x.toOhmMeters should be(10.0)
    x.inSiemensPerMeter should be(SiemensPerMeter(0.1))
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    OhmMeters(1).toString(OhmMeters) should be("1.0 Ω⋅m")
  }

  it should "return ElectricalResistance when divided by Length" in {
    OhmMeters(1) / Meters(1) should be(Ohms(1))
  }

  it should "return Length when divided by ElectricalResistance" in {
    OhmMeters(1) / Ohms(1) should be(Meters(1))
  }

  behavior of "ResistivityConversions"

  it should "provide aliases for single unit values" in {
    import ResistivityConversions._

    ohmMeter should be(OhmMeters(1))
  }

  it should "provide implicit conversion from Double" in {
    import ResistivityConversions._

    val d = 10.22
    d.ohmMeters should be(OhmMeters(d))
  }

  it should "provide Numeric support" in {
    import ResistivityConversions.ResistivityNumeric

    val rs = List(OhmMeters(100), OhmMeters(10))
    rs.sum should be(OhmMeters(110))
  }
}
