/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.photo

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.space.SquareMeters

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class IlluminanceSpec extends FlatSpec with Matchers {

  behavior of "Illuminance and its Units of Measure"

  it should "create values using UOM factories" in {
    assert(Lux(1).toLux == 1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Lux(1)
    assert(x.toLux == 1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    assert(Lux(1).toString(Lux) == "1.0 lx")
  }

  it should "return LuminousFlux when multiplied by Area" in {
    assert(Lux(1) * SquareMeters(1) == Lumens(1))
  }

  behavior of "IlluminanceConversions"

  it should "provide aliases for single unit values" in {
    import IlluminanceConversions._

    assert(lux == Lux(1))
  }
  it should "provide implicit conversion from Double" in {
    import IlluminanceConversions._

    val d = 10d
    assert(d.lux == Lux(d))
  }
}
