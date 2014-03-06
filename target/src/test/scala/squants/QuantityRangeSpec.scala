/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import org.scalatest.{ Matchers, FlatSpec }
import squants.thermal.{ Fahrenheit, Celsius }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class QuantityRangeSpec extends FlatSpec with Matchers {

  behavior of "QuantityRange"

  it should "throw an IllegalArgumentException when the lower bound >= upper bound" in {
    an[IllegalArgumentException] should be thrownBy QuantityRange(Meters(2), Meters(1))
  }

  it should "create a range with lower and upper bound" in {
    val r1 = QuantityRange(Meters(1), Meters(2))
    assert(r1.lower == Meters(1))
    assert(r1.upper == Meters(2))

    val r2 = QuantityRange(Celsius(0), Fahrenheit(100))
    assert(r2.lower == Celsius(0))
    assert(r2.upper == Fahrenheit(100))
  }

  it should "times a Double and get back a List of ranges" in {
    val r = QuantityRange(Meters(0), Meters(10))
    val rs = r times 3
    assert(rs.head == QuantityRange(Meters(0), Meters(10)))
    assert(rs.tail.head == QuantityRange(Meters(10), Meters(20)))
    assert(rs.tail.tail.head == QuantityRange(Meters(20), Meters(30)))

    val rs2 = r * 3
    assert(rs2.head == QuantityRange(Meters(0), Meters(10)))
    assert(rs2.tail.head == QuantityRange(Meters(10), Meters(20)))
    assert(rs2.tail.tail.head == QuantityRange(Meters(20), Meters(30)))
  }

  it should "divide a like value and get back a List of ranges" in {
    val r = QuantityRange(Meters(0), Meters(10))
    val rs = r / Meters(4)
    assert(rs.head == QuantityRange(Meters(0), Meters(4)))
    assert(rs.tail.head == QuantityRange(Meters(4), Meters(8)))
    assert(rs.tail.tail.head == QuantityRange(Meters(8), Meters(10)))
  }

  it should "divide a Double and get back a List of ranges" in {
    val r = QuantityRange(Meters(0), Meters(10))
    val rs = r / 2.5
    assert(rs.head == QuantityRange(Meters(0), Meters(4)))
    assert(rs.tail.head == QuantityRange(Meters(4), Meters(8)))
    assert(rs.tail.tail.head == QuantityRange(Meters(8), Meters(10)))
  }

  it should "foreach a like value and execute an operation on each sub range" in {
    val r = QuantityRange(Meters(0), Meters(5))
    var accum = 0d
    r.foreach(Meters(1))(sub ⇒ accum = accum + sub.upper.toMeters)
    assert(accum == 15)
  }

  it should "foreach a Double and execute an operation on each sub range" in {
    val r = QuantityRange(Meters(0), Meters(5))
    var accum = 0d
    r.foreach(5)(sub ⇒ accum = accum + sub.upper.toMeters)
    assert(accum == 15)
  }

  it should "map a like value and applies a map operation on each sub range" in {
    val r = QuantityRange(Meters(0), Meters(5))
    val rs = r / Meters(1)
    val rsMapped = r.map(Meters(1))(x ⇒ x)
    assert(rs.size == rsMapped.size)
    rs.foreach(x ⇒ assert(rsMapped.contains(x)))
  }

  it should "map a Double and applies a map operation on each sub range" in {
    val r = QuantityRange(Meters(0), Meters(35))
    val rs = r / 5
    val rsMapped = r.map(5)(x ⇒ x)
    assert(rs.size == rsMapped.size)
    rs.foreach(x ⇒ assert(rsMapped.contains(x)))
  }

  it should "foldLeft a like value and applies an operation on each sub range" in {
    val r = QuantityRange(Meters(0), Meters(5))
    val total = r.foldLeft(Meters(1), Meters(0))((z, x) ⇒ z + x.upper)
    assert(total == Meters(15))
  }

  it should "foldLeft a Double and applies an operation on each sub range" in {
    val r = QuantityRange(Meters(0), Meters(5))
    val total = r.foldLeft(5, Meters(0))((z, x) ⇒ z + x.upper)
    assert(total == Meters(15))
  }

  it should "foldRight a like value and applies an operation on each sub range" in {
    val r = QuantityRange(Meters(0), Meters(5))
    val total = r.foldRight(Meters(1), Meters(0))((x, z) ⇒ z + x.upper)
    assert(total == Meters(15))
  }

  it should "foldRight a Double and applies an operation on each sub range" in {
    val r = QuantityRange(Meters(0), Meters(5))
    val total = r.foldRight(5, Meters(0))((x, z) ⇒ z + x.upper)
    assert(total == Meters(15))
  }

  it should "inc and return a QualityRange with lower and upper bounds incremented by the range value" in {
    assert(QuantityRange(Meters(0), Meters(10)).++ == QuantityRange(Meters(10), Meters(20)))
  }

  it should "inc a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
    assert(QuantityRange(Meters(0), Meters(10)) ++ Meters(5) == QuantityRange(Meters(5), Meters(15)))
  }

  it should "dec and return a QualityRange with lower and upper bounds incremented by the range value" in {
    assert(QuantityRange(Meters(10), Meters(20)).-- == QuantityRange(Meters(0), Meters(10)))
  }

  it should "dec a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
    assert(QuantityRange(Meters(10), Meters(20)) -- Meters(5) == QuantityRange(Meters(5), Meters(15)))
  }

  it should "incTo a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
    assert(QuantityRange(Meters(0), Meters(10)) =+ Meters(5) == QuantityRange(Meters(0), Meters(15)))
  }

  it should "decTo a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
    assert(QuantityRange(Meters(0), Meters(10)) =- Meters(5) == QuantityRange(Meters(0), Meters(5)))
  }

  it should "incFrom a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
    assert((QuantityRange(Meters(0), Meters(10)) += Meters(5)) == QuantityRange(Meters(5), Meters(10)))
  }

  it should "decFrom a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
    assert((QuantityRange(Meters(0), Meters(10)) -= Meters(5)) == QuantityRange(Meters(-5), Meters(10)))
  }

  it should "incFromDecTo a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
    assert((QuantityRange(Meters(0), Meters(10)) +- Meters(4)) == QuantityRange(Meters(4), Meters(6)))
  }

  it should "decFromIncTo a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
    assert((QuantityRange(Meters(0), Meters(10)) -+ Meters(4)) == QuantityRange(Meters(-4), Meters(14)))
  }

  it should "contains a like value and return true or false" in {
    assert(!QuantityRange(Meters(0), Meters(10)).contains(Meters(-1)))
    assert(QuantityRange(Meters(0), Meters(10)).contains(Meters(0)))
    assert(QuantityRange(Meters(0), Meters(10)).contains(Meters(5)))
    assert(!QuantityRange(Meters(0), Meters(10)).contains(Meters(10)))
    assert(!QuantityRange(Meters(0), Meters(10)).contains(Meters(11)))
  }

  it should "return equality" in {
    val r1 = QuantityRange(Meters(10), Meters(15))
    val r2 = QuantityRange(Meters(10), Meters(15))
    val r3 = QuantityRange(Meters(11), Meters(15))

    assert(r1 == r2)
    assert(r1 != r3)
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
    assert(r.contains(rIn))
    assert(!r.contains(rInOnEnd))
    assert(!r.contains(rBeyondLower))
    assert(!r.contains(rBeyondUpper))
    assert(!r.contains(rBeyondBoth))
    assert(!r.contains(rBelow))
    assert(!r.contains(rOver))
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
    assert(r.partiallyContains(rIn))
    assert(r.partiallyContains(rInOnEnd))
    assert(r.partiallyContains(rBeyondLower))
    assert(r.partiallyContains(rBeyondUpper))
    assert(r.partiallyContains(rBeyondBoth))
    assert(!r.partiallyContains(rBelow))
    assert(!r.partiallyContains(rOver))
  }

  it should "includes a like value and return true or false" in {
    assert(!QuantityRange(Meters(0), Meters(10)).includes(Meters(-1)))
    assert(QuantityRange(Meters(0), Meters(10)).includes(Meters(0)))
    assert(QuantityRange(Meters(0), Meters(10)).includes(Meters(5)))
    assert(QuantityRange(Meters(0), Meters(10)).includes(Meters(10)))
    assert(!QuantityRange(Meters(0), Meters(10)).includes(Meters(11)))
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
    assert(r.includes(rIn))
    assert(r.includes(rInOnEnd))
    assert(!r.includes(rBeyondLower))
    assert(!r.includes(rBeyondUpper))
    assert(!r.includes(rBeyondBoth))
    assert(!r.includes(rBelow))
    assert(!r.includes(rOver))
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
    assert(r.partiallyIncludes(rIn))
    assert(r.partiallyIncludes(rInOnEnd))
    assert(r.partiallyIncludes(rBeyondLower))
    assert(r.partiallyIncludes(rBeyondUpper))
    assert(r.partiallyIncludes(rBeyondBoth))
    assert(!r.partiallyIncludes(rBelow))
    assert(!r.partiallyIncludes(rOver))
  }

  it should "toQuantity and return value representing the difference between the upper and lower bounds" in {
    val x = Meters(10)
    val y = Meters(30)
    val r = QuantityRange(x, y)
    assert(r.toQuantity == Meters(20))
  }

  it should "toSeq and return a list containing the lower and upper bounds" in {
    val r = QuantityRange(Meters(0), Meters(10))
    assert(r.toSeq.size == 2)
    assert(r.toSeq.head == Meters(0))
    assert(r.toSeq.tail.head == Meters(10))
  }

  it should "toList and return a list containing the lower and upper bounds" in {
    val r = QuantityRange(Meters(0), Meters(10))
    assert(r.toList.size == 2)
    assert(r.toList.head == Meters(0))
    assert(r.toList.tail.head == Meters(10))
  }
}
