/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants.mass.Kilograms
import squants.time.Seconds
import squants.QuantityParseException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class MassFlowSpec extends AnyFlatSpec with Matchers {

  behavior of "MassFlow and its Units of Measure"

  it should "create values using UOM factories" in {
    KilogramsPerSecond(10.22).toKilogramsPerSecond should be(10.22)
    PoundsPerSecond(10.22).toPoundsPerSecond should be(10.22 +- 1e-15)
    PoundsPerHour(10.22).toPoundsPerHour should be(10.22 +- 1e-15)
    KilopoundsPerHour(10.22).toKilopoundsPerHour should be(10.22 +- 1e-15)
    MegapoundsPerHour(10.22).toMegapoundsPerHour should be(10.22 +- 1e-15)
  }

  it should "create values from properly formatted Strings" in {
    MassFlow("10.22 kg/s").get should be(KilogramsPerSecond(10.22))
    MassFlow("10.22 lb/s").get should be(PoundsPerSecond(10.22))
    MassFlow("10.22 lb/hr").get should be(PoundsPerHour(10.22))
    MassFlow("10.22 klb/hr").get should be(KilopoundsPerHour(10.22))
    MassFlow("10.22 Mlb/hr").get should be(MegapoundsPerHour(10.22))
    MassFlow("10.22 zz").failed.get should be(QuantityParseException("Unable to parse MassFlow", "10.22 zz"))
    MassFlow("zz kg/s").failed.get should be(QuantityParseException("Unable to parse MassFlow", "zz kg/s"))
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = KilogramsPerSecond(1)
    x.toKilogramsPerSecond should be(1)
    x.toPoundsPerSecond should be(1 / PoundsPerSecond.conversionFactor)
    x.toPoundsPerHour should be(1 / (PoundsPerSecond.conversionFactor / 3600))
    x.toKilopoundsPerHour should be(1 / (PoundsPerSecond.conversionFactor * 1000 / 3600))
    x.toMegapoundsPerHour should be(1 / (PoundsPerSecond.conversionFactor * 1000000 / 3600))
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    KilogramsPerSecond(1).toString should be("1.0 kg/s")
    PoundsPerSecond(1).toString should be("1.0 lb/s")
    PoundsPerHour(1).toString should be("1.0 lb/hr")
    KilopoundsPerHour(1).toString should be("1.0 klb/hr")
    MegapoundsPerHour(1).toString should be("1.0 Mlb/hr")
  }

  it should "return Mass when multiplied by Time" in {
    KilogramsPerSecond(1) * Seconds(1) should be(Kilograms(1))
  }

  behavior of "MassFlowConversions"

  it should "provide aliases for single unit values" in {
    import MassFlowConversions._

    kilogramPerSecond should be(KilogramsPerSecond(1))
    poundPerSecond should be(PoundsPerSecond(1))
    poundPerHour should be(PoundsPerHour(1))
    kilopoundPerHour should be(KilopoundsPerHour(1))
    megapoundPerHour should be(MegapoundsPerHour(1))
  }

  it should "provide implicit conversion from Double" in {
    import MassFlowConversions._

    val d = 10.22d
    d.kilogramsPerSecond should be(KilogramsPerSecond(d))
    d.poundsPerSecond should be(PoundsPerSecond(d))
    d.poundsPerHour should be(PoundsPerHour(d))
    d.kilopoundsPerHour should be(KilopoundsPerHour(d))
    d.megapoundsPerHour should be(MegapoundsPerHour(d))
  }

  it should "provide Numeric support" in {
    import MassFlowConversions.MassFlowNumeric

    val mfrs = List(KilogramsPerSecond(100), KilogramsPerSecond(10))
    mfrs.sum should be(KilogramsPerSecond(110))
  }
}
