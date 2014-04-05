/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import org.scalatest.{ Matchers, FlatSpec }

/**
 * Singleton defining Metric System multipliers
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class BinarySystemSpec extends FlatSpec with Matchers {

  "The Metric System multipliers" should "convert as expected" in {
    import BinarySystem._
    Exa should be(Peta * 1024d)
    Peta should be(Tera * 1024d)
    Tera should be(Giga * 1024d)
    Giga should be(Mega * 1024d)
    Mega should be(Kilo * 1024d)
    Kilo should be(1024d)
  }
}
