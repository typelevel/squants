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
    assert(Siemens(1).toSiemens == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Siemens(1)
    assert(x.toSiemens == 1.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Siemens(1).toString(Siemens) == "1.0 S")
  }

  it should "return Conductivity when divided by Length" in {
    assert(Siemens(1) / Meters(1) == SiemensPerMeter(1))
  }

  it should "return Length when divided by Conductivity" in {
    assert(Siemens(1) / SiemensPerMeter(1) == Meters(1))
  }

  it should "return the inverse value as Ohms" in {
    assert(Siemens(5).inOhms == Ohms(.2))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Siemens(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[ElectricalConductance](ser)
    assert(x == des)
  }

  behavior of "ElectricalConductanceConversions"

  it should "provide aliases for single unit values" in {
    import ElectricalConductanceConversions._

    assert(siemen == Siemens(1))
  }

  it should "provide implicit conversion from Double" in {
    import ElectricalConductanceConversions._

    val d = 10d
    assert(d.siemens == Siemens(d))
  }
}
