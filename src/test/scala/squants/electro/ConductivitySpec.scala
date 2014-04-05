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
class ConductivitySpec extends FlatSpec with Matchers {

  behavior of "Conductivity and its Units of Measure"

  it should "create values using UOM factories" in {
    SiemensPerMeter(1).toSiemensPerMeter should be(1)
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

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = SiemensPerMeter(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[Conductivity](ser)
    x should be(des)
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
}
