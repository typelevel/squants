/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import org.scalatest.{ Matchers, FlatSpec }
import squants.space.Meters
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ResistivitySpec extends FlatSpec with Matchers {

  behavior of "Resistivity and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(OhmMeters(1).toOhmMeters == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = OhmMeters(10)
    assert(x.toOhmMeters == 10.0)
    assert(x.inSiemensPerMeter == SiemensPerMeter(0.1))
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(OhmMeters(1).toString(OhmMeters) == "1.0 Ω⋅m")
  }

  it should "return ElectricalResistance when divided by Length" in {
    assert(OhmMeters(1) / Meters(1) == Ohms(1))
  }

  it should "return Length when divided by ElectricalResistance" in {
    assert(OhmMeters(1) / Ohms(1) == Meters(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = OhmMeters(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Resistivity](ser)
    assert(x == des)
  }

  behavior of "ResistivityConversions"

  it should "provide aliases for single unit values" in {
    import ResistivityConversions._

    assert(ohmMeter == OhmMeters(1))
  }

  it should "provide implicit conversion from Double" in {
    import ResistivityConversions._

    val d = 10.22
    assert(d.ohmMeters == OhmMeters(d))
  }
}
