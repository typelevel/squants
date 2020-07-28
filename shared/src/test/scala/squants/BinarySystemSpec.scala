/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Singleton defining Metric System multipliers
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class BinarySystemSpec extends AnyFlatSpec with Matchers {

  "The Metric System multipliers" should "convert as expected" in {
    import BinarySystem._
    Yotta should be(Zetta * 1024d)
    Zetta should be(Exa * 1024d)
    Exa should be(Peta * 1024d)
    Peta should be(Tera * 1024d)
    Tera should be(Giga * 1024d)
    Giga should be(Mega * 1024d)
    Mega should be(Kilo * 1024d)
    Kilo should be(1024d)
  }
}
