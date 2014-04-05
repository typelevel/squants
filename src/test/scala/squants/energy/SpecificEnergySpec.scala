/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.mass.Kilograms
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class SpecificEnergySpec extends FlatSpec with Matchers {

  behavior of "SpecificEnergy and its Units of Measure"

  it should "create values using UOM factories" in {

    Grays(1).toGrays should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Grays(1)

    x.toGrays should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Grays(1).toString(Grays) should be("1.0 Gy")
  }

  it should "return Energy when multiplied by Mass" in {
    Grays(1) * Kilograms(10) should be(Joules(10))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Grays(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[SpecificEnergy](ser)
    x should be(des)
  }

  behavior of "Conversions"

  it should "provide aliases for single unit values" in {
    import SpecificEnergyConversions._

    gray should be(SpecificEnergy(1))
  }

  it should "provide implicit conversion from Double" in {
    import SpecificEnergyConversions._

    val d = 10d
    d.grays should be(SpecificEnergy(d))
  }
}
