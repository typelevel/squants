/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.energy

import squants.mass.Kilograms
import squants.QuantityParseException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class SpecificEnergySpec extends AnyFlatSpec with Matchers {

  behavior of "SpecificEnergy and its Units of Measure"

  it should "create values using UOM factories" in {

    Grays(1).toGrays should be(1)
    Rads(1).toRads should be(1)
    ErgsPerGram(1).toErgsPerGram should be(1)
  }

  it should "create values from properly formatted Strings" in {
    SpecificEnergy("10.22 Gy").get should be(Grays(10.22))
    SpecificEnergy("8.47 rad").get should be(Rads(8.47))
    SpecificEnergy("6.79 erg/g").get should be(ErgsPerGram(6.79))
    SpecificEnergy("10.22 zz").failed.get should be(QuantityParseException("Unable to parse SpecificEnergy", "10.22 zz"))
    SpecificEnergy("ZZ Gy").failed.get should be(QuantityParseException("Unable to parse SpecificEnergy", "ZZ Gy"))
  }

  it should "properly convert to all supported Units of Measure" in {

    val x = Grays(1)
    x.toGrays should be(1)
    x.toRads should be(100)
    x.toErgsPerGram should be(10000)

    val y = Rads(1)
    y.toRads should be(1)
    y.toGrays should be(0.01)
    y.toErgsPerGram should be(100)

    val z = ErgsPerGram(1)
    z.toErgsPerGram should be(1)
    z.toGrays should be(0.0001)
    z.toRads should be(0.01)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Grays(1).toString(Grays) should be("1.0 Gy")
    Rads(1).toString(Rads) should be("1.0 rad")
    ErgsPerGram(1).toString(ErgsPerGram) should be("1.0 erg/g")
  }

  it should "return Energy when multiplied by Mass" in {
    Grays(1) * Kilograms(10) should be(Joules(10))
    Rads(1) * Kilograms(10) should be(Joules(0.1))
    ErgsPerGram(1) * Kilograms(10) should be(Joules(0.001))
  }

  behavior of "Conversions"

  it should "provide aliases for single unit values" in {
    import SpecificEnergyConversions._

    gray should be(Grays(1))
    rad should be(Rads(1))
    ergsPerGram should be(ErgsPerGram(1))
  }

  it should "provide implicit conversion from Double" in {
    import SpecificEnergyConversions._

    val d = 10d
    d.grays should be(Grays(d))
    d.rads should be(Rads(d))
    d.ergsPerGram should be(ErgsPerGram(d))
  }

  it should "provide Numeric support" in {
    import SpecificEnergyConversions.SpecificEnergyNumeric

    val ses = List(Grays(100), Grays(10))
    ses.sum should be(Grays(110))

    val sesRad = List(Rads(100), Rads(10))
    sesRad.sum should be(Rads(110))

    // The Grays(0) value ensures we get the sum in Grays, otherwise unit depends on Scala version
    // due to changed .sum implementation in 2.13
    val sesErg = List(Grays(0), ErgsPerGram(100), ErgsPerGram(10))
    sesErg.sum should be(Grays(0.011))
  }
}
