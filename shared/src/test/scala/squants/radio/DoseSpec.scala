/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2018, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.radio

import org.scalatest.{FlatSpec, Matchers}
import squants.QuantityParseException

/**
 * @author  Hunter Payne
 *
 */
class DoseSpec extends FlatSpec with Matchers {

  behavior of "Dose and its Units of Measure"

  it should "create values using UOM factories" in {
    Rems(1).toRems should be(1)
    Sieverts(1).toSieverts should be(1)
  }

  it should "create values from properly formatted Strings" in {
    Dose("10.22 rem").get should be(Rems(10.22))
    Dose("5 Sv").get should be(Sieverts(5))
    Dose("10.22 zz").failed.get should be(
      QuantityParseException("Unable to parse Dose", "10.22 zz"))
    Dose("zz W/m²").failed.get should be(
      QuantityParseException("Unable to parse Dose", "zz W/m²"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Rems(1)
    x.toRems should be(1)
    x.toSieverts should be(0.01)

    val y = Sieverts(1)
    y.toSieverts should be(1)
    y.toRems should be(100.0)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Sieverts(1).toString should be("1.0 Sv")
    Rems(1).toString should be("1.0 rem")
  }

  behavior of "DoseConversions"

  it should "provide aliases for single unit values" in {
    import DoseConversions._

    sievert should be(Sieverts(1))
    rem should be(Rems(1))
  }

  it should "provide implicit conversion from Double" in {
    import DoseConversions._

    val d = 10.22d
    d.sieverts should be(Sieverts(d))
    d.rems should be(Rems(d))
  }

  it should "provide Numeric support" in {
    import DoseConversions.DoseNumeric

    val irrs = List(Sieverts(10), Sieverts(100))
    irrs.sum should be(Sieverts(110))

    val irrsRem = List(Rems(10), Rems(100))
    irrsRem.sum should be(Rems(110))
  }
}
