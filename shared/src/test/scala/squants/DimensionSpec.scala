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

class DimensionSpec extends AnyFlatSpec with Matchers {

  "Dimensions" should "have a stable hashCode" in {
    space.Length.hashCode.toHexString should be("29d67c0b")
  }
}
