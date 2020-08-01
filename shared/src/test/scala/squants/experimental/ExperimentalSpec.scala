/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.experimental


import scala.util.Success
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExperimentalSpec extends AnyFlatSpec with Matchers {

  behavior of "Experimental Code"

  it should "create an Int based Quantity as previously" in {
    val x = Each(10)
    x.toEach should be(10)
  }

  it should "create an Long based Quantity as previously" in {
    val x = Each(10L)
    x.toEach should be(10L)
  }

  it should "create a Float based Quantity as previously" in {
    val x = Each(10.22F)
    x.toEach should be(10.22F)
  }

  it should "create a Double based Quantity as previously" in {
    val x = Each(10.22)
    x.toEach should be(10.22)
  }

  it should "create a BigDecimal based Quantity as previously" in {
    val x = Each(BigDecimal(10.22))
    x.toEach should be(BigDecimal(10.22))
  }

  it should "create a BigDecimal based Quantity from parsing a String" in {
    val x = Dimensionless("10.22 ea")
    x should be(Success(Each(BigDecimal(10.22))))
  }

  it should "create a Quantity from parsing a Tuple" in {
    Dimensionless((BigDecimal(10.22), "ea")) should be(Success(Each(BigDecimal(10.22))))
    Dimensionless((10.22, "ea")) should be(Success(Each(10.22)))
    Dimensionless((10.22F, "ea")) should be(Success(Each(10.22F)))
    Dimensionless((10L, "ea")) should be(Success(Each(10L)))
    Dimensionless((10, "ea")) should be(Success(Each(10)))
  }

  it should "add two Quantities that are based on the same numeric type" in {
    val x = Each(10) + Each(10)
    x.toEach should be(20)
  }

  it should "add two Quantities of different numeric types" in {
    val lngPlusInt = Each(10L) + Each(10)
    lngPlusInt.toEach should be(20)

    val fltPlusInt = Each(10F) + Each(10)
    fltPlusInt.toEach should be(20F)
    val fltPlusLng = Each(10F) + Each(10L)
    fltPlusLng.toEach should be(20F)

    val dblPlusInt = Each(10.22) + Each(10)
    dblPlusInt.toEach should be(20.22)
    val dblPlusLng = Each(10.22) + Each(10L)
    dblPlusLng.toEach should be(20.22)
    val dblPlusFlt = Each(10.22) + Each(10F)
    dblPlusFlt.toEach should be(20.22)

    val bdPlusInt = Each(BigDecimal(10.22)) + Each(10)
    bdPlusInt.toEach should be(BigDecimal(20.22))
    val bdPlusLng = Each(BigDecimal(10.22)) + Each(10L)
    bdPlusLng.toEach should be(BigDecimal(20.22))
    val bdPlusFlt = Each(BigDecimal(10.22)) + Each(10F)
    bdPlusFlt.toEach should be(BigDecimal(20.22))
    val bdPlusDbl = Each(BigDecimal(10.22)) + Each(10.22)
    bdPlusDbl.toEach should be(BigDecimal(20.44))
  }

  it should "scale when multiplied by a value of the same numeric type" in {
    val x = Each(10.22)
    val dx = x * 2d
    dx.toEach should be(20.44)
  }

  it should "scale when multiplied by a value of a different numeric type" in {
    val x = Each(10.22)
    val dx = x * 10L
    dx.toEach should be(102.2)
  }

}
