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
class ElectricalConductanceSpec extends AnyFlatSpec with Matchers {

  behavior of "ElectricalConductance and its Units of Measure"

  it should "create values using UOM factories" in {
    Siemens(1).toSiemens should be(1)
  }

  it should "create values from properly formatted Strings" in {
    ElectricalConductance("10.22 S").get should be(Siemens(10.22))
    ElectricalConductance("10.22 zz").failed.get should be(QuantityParseException("Unable to parse ElectricalConductance", "10.22 zz"))
    ElectricalConductance("zz S").failed.get should be(QuantityParseException("Unable to parse ElectricalConductance", "zz S"))
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

  it should "provide Numeric support" in {
    import ElectricalConductanceConversions.ElectricalConductanceNumeric

    val ecs = List(Siemens(100), Siemens(10))
    ecs.sum should be(Siemens(110))
  }
}
