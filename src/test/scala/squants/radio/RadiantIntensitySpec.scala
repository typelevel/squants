/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.radio

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.energy.Watts
import squants.space.{ SquareMeters, Meters, SquaredRadians }
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class RadiantIntensitySpec extends FlatSpec with Matchers {

  behavior of "RadiantIntensity and its Units of Measure"

  it should "create values using UOM factories" in {
    WattsPerSteradian(1).toWattsPerSteradian should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = WattsPerSteradian(1)
    x.toWattsPerSteradian should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    WattsPerSteradian(1).toString should be("1.0 W/sr")
  }

  it should "return Power when multiplied by SolidAngle" in {
    WattsPerSteradian(1) * SquaredRadians(1) should be(Watts(1))
  }

  it should "return SolidAngle when divided by Power" in {
    WattsPerSteradian(1) / Watts(1) should be(SquaredRadians(1))
  }

  it should "return SpectralIntensity when divided by Length" in {
    WattsPerSteradian(1) / Meters(1) should be(WattsPerSteradianPerMeter(1))
  }

  it should "Radiance when divided by Area" in {
    WattsPerSteradian(1) / SquareMeters(1) should be(WattsPerSteradianPerSquareMeter(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = WattsPerSteradian(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[RadiantIntensity](ser)
    x should be(des)
  }
}
