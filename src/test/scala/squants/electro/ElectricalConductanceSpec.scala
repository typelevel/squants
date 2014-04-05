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
class ElectricalConductanceSpec extends FlatSpec with Matchers {

  behavior of "ElectricalConductance and its Units of Measure"

  it should "create values using UOM factories" in {
    Siemens(1).toSiemens should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Siemens(1)
    x.toSiemens should be(1.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Siemens(1).toString(Siemens) should be("1.0 S")
  }

  it should "return Conductivity when divided by Length" in {
    Siemens(1) / Meters(1) should be(SiemensPerMeter(1))
  }

  it should "return Length when divided by Conductivity" in {
    Siemens(1) / SiemensPerMeter(1) should be(Meters(1))
  }

  it should "return the inverse value as Ohms" in {
    Siemens(5).inOhms should be(Ohms(.2))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Siemens(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[ElectricalConductance](ser)
    x should be(des)
  }

  behavior of "ElectricalConductanceConversions"

  it should "provide aliases for single unit values" in {
    import ElectricalConductanceConversions._

    siemen should be(Siemens(1))
  }

  it should "provide implicit conversion from Double" in {
    import ElectricalConductanceConversions._

    val d = 10d
    d.siemens should be(Siemens(d))
  }
}
