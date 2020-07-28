/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import squants.thermal.{ Celsius, Fahrenheit }
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class QuantityRangeSpec extends AnyFlatSpec with Matchers {

  behavior of "QuantityRange"

  it should "throw an IllegalArgumentException when the lower bound > upper bound" in {
    an[IllegalArgumentException] should be thrownBy QuantityRange(Meters(2), Meters(1))
  }

  it should "throw an IllegalArgumentException when the lower bound = upper bound" in {
    an[IllegalArgumentException] should be thrownBy QuantityRange(Meters(2), Meters(2))
  }

  it should "create a range with lower and upper bound" in {
    val r1 = QuantityRange(Meters(1), Meters(2))
    r1.lower should be(Meters(1))
    r1.upper should be(Meters(2))

    val r2 = QuantityRange(Celsius(0), Fahrenheit(100))
    r2.lower should be(Celsius(0))
    r2.upper should be(Fahrenheit(100))
  }

  it should "times a Double and get back a List of ranges" in {
    val r = QuantityRange(Meters(0), Meters(10))
    val rs = r times 3
    rs.head should be(QuantityRange(Meters(0), Meters(10)))
    rs.tail.head should be(QuantityRange(Meters(10), Meters(20)))
    rs.tail.tail.head should be(QuantityRange(Meters(20), Meters(30)))

    val rs2 = r * 3
    rs2.head should be(QuantityRange(Meters(0), Meters(10)))
    rs2.tail.head should be(QuantityRange(Meters(10), Meters(20)))
    rs2.tail.tail.head should be(QuantityRange(Meters(20), Meters(30)))
  }

  it should "divide a like value and get back a List of ranges" in {
    val r = QuantityRange(Meters(0), Meters(10))
    val rs = r / Meters(4)
    rs.head should be(QuantityRange(Meters(0), Meters(4)))
    rs.tail.head should be(QuantityRange(Meters(4), Meters(8)))
    rs.tail.tail.head should be(QuantityRange(Meters(8), Meters(10)))
  }

  it should "divide a Double and get back a List of ranges" in {
    val r = QuantityRange(Meters(0), Meters(10))
    val rs = r / 2.5
    rs.head should be(QuantityRange(Meters(0), Meters(4)))
    rs.tail.head should be(QuantityRange(Meters(4), Meters(8)))
    rs.tail.tail.head should be(QuantityRange(Meters(8), Meters(10)))
  }

  it should "foreach a like value and execute an operation on each sub range" in {
    val r = QuantityRange(Meters(0), Meters(5))
    var accum = 0d
    r.foreach(Meters(1))(sub ⇒ accum = accum + sub.upper.toMeters)
    accum should be(15)
  }

  it should "foreach a Double and execute an operation on each sub range" in {
    val r = QuantityRange(Meters(0), Meters(5))
    var accum = 0d
    r.foreach(5)(sub ⇒ accum = accum + sub.upper.toMeters)
    accum should be(15)
  }

  it should "map a like value and applies a map operation on each sub range" in {
    val r = QuantityRange(Meters(0), Meters(5))
    val rs = r / Meters(1)
    val rsMapped = r.map(Meters(1))(x ⇒ x)
    rsMapped should be(rs)
  }

  it should "map a Double and applies a map operation on each sub range" in {
    val r = QuantityRange(Meters(0), Meters(35))
    val rs = r / 5
    val rsMapped = r.map(5)(x ⇒ x)
    rsMapped should be(rs)
  }

  it should "foldLeft a like value and applies an operation on each sub range" in {
    val r = QuantityRange(Meters(0), Meters(5))
    val total = r.foldLeft(Meters(1), Meters(0))((z, x) ⇒ z + x.upper)
    total should be(Meters(15))
  }

  it should "foldLeft a Double and applies an operation on each sub range" in {
    val r = QuantityRange(Meters(0), Meters(5))
    val total = r.foldLeft(5, Meters(0))((z, x) ⇒ z + x.upper)
    total should be(Meters(15))
  }

  it should "foldRight a like value and applies an operation on each sub range" in {
    val r = QuantityRange(Meters(0), Meters(5))
    val total = r.foldRight(Meters(1), Meters(0))((x, z) ⇒ z + x.upper)
    total should be(Meters(15))
  }

  it should "foldRight a Double and applies an operation on each sub range" in {
    val r = QuantityRange(Meters(0), Meters(5))
    val total = r.foldRight(5, Meters(0))((x, z) ⇒ z + x.upper)
    total should be(Meters(15))
  }

  it should "inc and return a QualityRange with lower and upper bounds incremented by the range value" in {
    QuantityRange(Meters(0), Meters(10)).++ should be(QuantityRange(Meters(10), Meters(20)))
  }

  it should "inc a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
    QuantityRange(Meters(0), Meters(10)) ++ Meters(5) should be(QuantityRange(Meters(5), Meters(15)))
  }

  it should "dec and return a QualityRange with lower and upper bounds incremented by the range value" in {
    QuantityRange(Meters(10), Meters(20)).-- should be(QuantityRange(Meters(0), Meters(10)))
  }

  it should "dec a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
    QuantityRange(Meters(10), Meters(20)) -- Meters(5) should be(QuantityRange(Meters(5), Meters(15)))
  }

  it should "incTo a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
    QuantityRange(Meters(0), Meters(10)) =+ Meters(5) should be(QuantityRange(Meters(0), Meters(15)))
  }

  it should "decTo a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
    QuantityRange(Meters(0), Meters(10)) =- Meters(5) should be(QuantityRange(Meters(0), Meters(5)))
  }

  it should "incFrom a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
    (QuantityRange(Meters(0), Meters(10)) += Meters(5)) should be(QuantityRange(Meters(5), Meters(10)))
  }

  it should "decFrom a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
    (QuantityRange(Meters(0), Meters(10)) -= Meters(5)) should be(QuantityRange(Meters(-5), Meters(10)))
  }

  it should "incFromDecTo a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
    (QuantityRange(Meters(0), Meters(10)) +- Meters(4)) should be(QuantityRange(Meters(4), Meters(6)))
  }

  it should "decFromIncTo a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
    (QuantityRange(Meters(0), Meters(10)) -+ Meters(4)) should be(QuantityRange(Meters(-4), Meters(14)))
  }

  it should "contains a like value and return true or false" in {
    !QuantityRange(Meters(0), Meters(10)).contains(Meters(-1)) should be(right = true)
    QuantityRange(Meters(0), Meters(10)).contains(Meters(0)) should be(right = true)
    QuantityRange(Meters(0), Meters(10)).contains(Meters(5)) should be(right = true)
    !QuantityRange(Meters(0), Meters(10)).contains(Meters(10)) should be(right = true)
    !QuantityRange(Meters(0), Meters(10)).contains(Meters(11)) should be(right = true)
  }

  it should "return equality" in {
    val r1 = QuantityRange(Meters(10), Meters(15))
    val r2 = QuantityRange(Meters(10), Meters(15))
    val r3 = QuantityRange(Meters(11), Meters(15))

    r1 should be(r2)
    r1 != r3 should be(right = true)
  }

  it should "contains a another range and return true or false" in {
    val r = QuantityRange(Meters(30), Meters(100))
    val rIn = QuantityRange(Meters(30), Meters(99))
    val rInOnEnd = QuantityRange(Meters(30), Meters(100))
    val rBeyondLower = QuantityRange(Meters(25), Meters(50))
    val rBeyondUpper = QuantityRange(Meters(50), Meters(110))
    val rBeyondBoth = QuantityRange(Meters(25), Meters(105))
    val rBelow = QuantityRange(Meters(10), Meters(20))
    val rOver = QuantityRange(Meters(105), Meters(205))
    r.contains(rIn) should be(right = true)
    !r.contains(rInOnEnd) should be(right = true)
    !r.contains(rBeyondLower) should be(right = true)
    !r.contains(rBeyondUpper) should be(right = true)
    !r.contains(rBeyondBoth) should be(right = true)
    !r.contains(rBelow) should be(right = true)
    !r.contains(rOver) should be(right = true)
  }

  it should "partiallyContains a another range and return true or false" in {
    val r = QuantityRange(Meters(30), Meters(100))
    val rIn = QuantityRange(Meters(30), Meters(99))
    val rInOnEnd = QuantityRange(Meters(30), Meters(100))
    val rBeyondLower = QuantityRange(Meters(25), Meters(50))
    val rBeyondUpper = QuantityRange(Meters(50), Meters(110))
    val rBeyondBoth = QuantityRange(Meters(25), Meters(105))
    val rBelow = QuantityRange(Meters(10), Meters(20))
    val rOver = QuantityRange(Meters(105), Meters(205))
    r.partiallyContains(rIn) should be(right = true)
    r.partiallyContains(rInOnEnd) should be(right = true)
    r.partiallyContains(rBeyondLower) should be(right = true)
    r.partiallyContains(rBeyondUpper) should be(right = true)
    r.partiallyContains(rBeyondBoth) should be(right = true)
    !r.partiallyContains(rBelow) should be(right = true)
    !r.partiallyContains(rOver) should be(right = true)
  }

  it should "includes a like value and return true or false" in {
    !QuantityRange(Meters(0), Meters(10)).includes(Meters(-1)) should be(right = true)
    QuantityRange(Meters(0), Meters(10)).includes(Meters(0)) should be(right = true)
    QuantityRange(Meters(0), Meters(10)).includes(Meters(5)) should be(right = true)
    QuantityRange(Meters(0), Meters(10)).includes(Meters(10)) should be(right = true)
    !QuantityRange(Meters(0), Meters(10)).includes(Meters(11)) should be(right = true)
  }

  it should "includes a another range and return true or false" in {
    val r = QuantityRange(Meters(30), Meters(100))
    val rIn = QuantityRange(Meters(30), Meters(99))
    val rInOnEnd = QuantityRange(Meters(30), Meters(100))
    val rBeyondLower = QuantityRange(Meters(25), Meters(50))
    val rBeyondUpper = QuantityRange(Meters(50), Meters(110))
    val rBeyondBoth = QuantityRange(Meters(25), Meters(105))
    val rBelow = QuantityRange(Meters(10), Meters(20))
    val rOver = QuantityRange(Meters(105), Meters(205))
    r.includes(rIn) should be(right = true)
    r.includes(rInOnEnd) should be(right = true)
    !r.includes(rBeyondLower) should be(right = true)
    !r.includes(rBeyondUpper) should be(right = true)
    !r.includes(rBeyondBoth) should be(right = true)
    !r.includes(rBelow) should be(right = true)
    !r.includes(rOver) should be(right = true)
  }

  it should "partiallyIncludes a another range and return true or false" in {
    val r = QuantityRange(Meters(30), Meters(100))
    val rIn = QuantityRange(Meters(30), Meters(99))
    val rInOnEnd = QuantityRange(Meters(30), Meters(100))
    val rBeyondLower = QuantityRange(Meters(25), Meters(50))
    val rBeyondUpper = QuantityRange(Meters(50), Meters(110))
    val rBeyondBoth = QuantityRange(Meters(25), Meters(105))
    val rBelow = QuantityRange(Meters(10), Meters(20))
    val rOver = QuantityRange(Meters(105), Meters(205))
    r.partiallyIncludes(rIn) should be(right = true)
    r.partiallyIncludes(rInOnEnd) should be(right = true)
    r.partiallyIncludes(rBeyondLower) should be(right = true)
    r.partiallyIncludes(rBeyondUpper) should be(right = true)
    r.partiallyIncludes(rBeyondBoth) should be(right = true)
    !r.partiallyIncludes(rBelow) should be(right = true)
    !r.partiallyIncludes(rOver) should be(right = true)
  }

  it should "toQuantity and return value representing the difference between the upper and lower bounds" in {
    val x = Meters(10)
    val y = Meters(30)
    val r = QuantityRange(x, y)
    r.toQuantity should be(Meters(20))
  }

  it should "toSeq and return a list containing the lower and upper bounds" in {
    val r = QuantityRange(Meters(0), Meters(10))
    r.toSeq.size should be(2)
    r.toSeq.head should be(Meters(0))
    r.toSeq.tail.head should be(Meters(10))
  }

  it should "toList and return a list containing the lower and upper bounds" in {
    val r = QuantityRange(Meters(0), Meters(10))
    r.toList.size should be(2)
    r.toList.head should be(Meters(0))
    r.toList.tail.head should be(Meters(10))
  }
}
