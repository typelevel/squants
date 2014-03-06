/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import org.scalatest.FlatSpec

/**
 * Singleton defining Metric System multipliers
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class BinarySystemSpec extends FlatSpec {

  "The Metric System multipliers" should "convert as expected" in {
    import BinarySystem._
    assert(Exa == Peta * 1024d)
    assert(Peta == Tera * 1024d)
    assert(Tera == Giga * 1024d)
    assert(Giga == Mega * 1024d)
    assert(Mega == Kilo * 1024d)
    assert(Kilo == 1024d)
  }
}
