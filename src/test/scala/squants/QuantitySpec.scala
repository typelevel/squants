/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import org.scalatest.{ FlatSpec, Matchers }
import squants.thermal.{ Celsius, Fahrenheit }
import squants.time.Hours

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class QuantitySpec extends FlatSpec with Matchers {

  // TODO Implement Specs for Numeric Support

  /*
    Create a Quantity with two Units of Measure
   */
  final class Thingee private (val value: Double) extends Quantity[Thingee] {
    def valueUnit = Thangs
    def toThangs = to(Thangs)
    def toKilothangs = to(KiloThangs)
  }

  object Thingee {
    private[squants] def apply(value: Double) = new Thingee(value)
  }

  trait ThingeeUnit extends UnitOfMeasure[Thingee] with UnitMultiplier {
    def apply(value: Double) = Thingee(convertFrom(value))
  }

  object Thangs extends ThingeeUnit with ValueUnit {
    val symbol = "th"
  }

  object KiloThangs extends ThingeeUnit {
    val symbol = "kth"
    val multiplier = Thangs.multiplier * MetricSystem.Kilo
  }

  implicit object ThingeeNumeric extends AbstractQuantityNumeric[Thingee](Thangs)

  behavior of "Quantity as implemented in Thingee"

  it should "add two like values and result in a like value" in {
    val x = Thangs(14.999)
    val y = Thangs(0.001)
    assert((x plus y) == Thangs(15))
    assert(x + y == Thangs(15))
  }

  it should "minus two like values and result in a like value" in {
    val x = Thangs(15.0)
    val y = Thangs(0.001)
    assert((x minus y) == Thangs(14.999))
    assert(x - y == Thangs(14.999))
  }

  it should "times by a Double and result in a like value" in {
    val x = Thangs(4.5)
    val y = 2.0
    assert((x times y) == Thangs(9.0))
    assert(x * y == Thangs(9.0))
  }

  it should "divide by a Double and result in a like value" in {
    val x = Thangs(9.0)
    val y = 2.0
    assert((x divide y) == Thangs(4.5))
    assert(x / y == Thangs(4.5))
  }

  it should "divide by a like value and result in a Double" in {
    val x = Thangs(9.0)
    val y = Thangs(2.0)
    assert((x divide y) == 4.5)
    assert(x / y == 4.5)
  }

  it should "remainder by a Double and result in a like value" in {
    val x = Thangs(9.0)
    val y = 2.0
    assert((x remainder y) == Thangs(1))
    assert(x % y == Thangs(1))
  }

  it should "remainder by a like value and result in a Double" in {
    val x = Thangs(9.0)
    val y = Thangs(2.0)
    assert((x remainder y) == 1.0)
    assert(x % y == 1.0)
  }

  it should "divideAndRemainder by a Double and result in a pair of like values" in {
    val x = Thangs(9.0)
    val y = 2.0
    val p1 = x divideAndRemainder y
    assert(p1._1 == Thangs(4))
    assert(p1._2 == Thangs(1))
    val p2 = x /% y
    assert(p2._1 == Thangs(4))
    assert(p2._2 == Thangs(1))
  }

  it should "divideAndRemainder by a like value and result in a Double and like value" in {
    val x = Thangs(9.0)
    val y = Thangs(2.0)
    val p1 = x divideAndRemainder y
    assert(p1._1 == 4)
    assert(p1._2 == Thangs(1))
    val p2 = x /% y
    assert(p2._1 == 4)
    assert(p2._2 == Thangs(1))
  }

  it should "negate a value and result in a like negative value" in {
    val x = Thangs(9.0)
    assert(x.negate == Thangs(-9.0))
    assert(-x == Thangs(-9.0))

    val y = Thangs(-9.0)
    assert(y.negate == Thangs(9.0))
    assert(-y == Thangs(9.0))
  }

  it should "return the absolute value of a Quantity value" in {
    val x = Thangs(9.0)
    assert(x.abs == Thangs(9.0))
    assert((-x).abs == Thangs(9.0))

    val y = Thangs(-9.0)
    assert(y.abs == Thangs(9.0))
    assert((-y).abs == Thangs(9.0))
  }

  it should "return true on comparing two different values with !=" in {
    val x = Thangs(1)
    val y = Thangs(2)
    assert(x != y)
  }

  it should "return false on comparing two equal values with !=" in {
    val x = Thangs(1)
    val y = Thangs(1)
    (x != y) should be(right = false)
  }

  it should "compare a like value values and return 1, 0, or -1" in {
    val x = Thangs(5)
    assert((x compare Thangs(4.999)) == 1)
    assert((x compare Thangs(5)) == 0)
    assert((x compare Thangs(5.001)) == -1)
  }

  it should "max a like value and return the greater of the two" in {
    val x = Thangs(5)
    val y = Thangs(4.999)
    assert((x max y) == Thangs(5))
  }

  it should "min a like value and return the greater of the two" in {
    val x = Thangs(5)
    val y = Thangs(4.999)
    assert((x min y) == Thangs(4.999))
  }

  it should "plusOrMinus a like value and return a QuantityRange" in {
    val x = Thangs(5)
    val y = Thangs(1)
    assert((x plusOrMinus y) == QuantityRange(Thangs(4), Thangs(6)))
    assert(x +- y == QuantityRange(Thangs(4), Thangs(6)))
  }

  it should "to a like value and return a QuantityRange" in {
    val x = Thangs(5)
    val y = Thangs(10)
    val r = x to y
    assert(r == QuantityRange(x, y))
  }

  it should "within a QuantityRange and return a Boolean" in {
    val x = Thangs(10)
    val y = Thangs(15)
    val r = x +- Thangs(1)
    assert(x.within(r))
    assert(!y.within(r))

    assert(Thangs(10) within (Thangs(9) +- Thangs(2)))
    assert(Thangs(10) within (Thangs(9) to Thangs(12)))
  }

  it should "notWithin a QuantityRange and return a Boolean" in {
    val x = Thangs(10)
    val y = Thangs(15)
    val r = x +- Thangs(1)
    assert(!x.notWithin(r))
    assert(y.notWithin(r))

    assert(Thangs(13) notWithin (Thangs(9) +- Thangs(2)))
    assert(Thangs(13) notWithin (Thangs(9) to Thangs(12)))
  }

  it should "to a unit and return a Double" in {
    val x = Thangs(1500)
    assert((x to KiloThangs) == 1.5)
    assert(x.toKilothangs == 1.5)
  }

  it should "in a unit and return a like value in that unit" in {
    // The `in` method is only useful for Quantities that implement quantity classes for each unit
    val x = Fahrenheit(212)
    assert((x in Celsius) == Celsius(100))

    val y = Seconds(3600)
    assert((y in Hours) == Hours(1))
  }

  it should "toString and return a string formatted for the valueUnit" in {
    val x = Thangs(10)
    assert(x.toString == "10.0 th")
  }

  it should "toString a unit and return a string formatted for the valueUnit" in {
    val x = Thangs(1500)
    assert((x toString KiloThangs) == "1.5 kth")
  }

  behavior of "SquantifiedDouble"

  it should "multiply by a Quantity value and return the product as a like value" in {
    val l = 10 * Thangs(1000)
    assert(l.getClass == classOf[Thingee])
    assert((l to Thangs) == 10000)

    val m = 10 * Kilograms(50)
    assert(m.getClass == classOf[Mass])
    assert((m to Kilograms) == 500)
  }

  behavior of "QuantityNumeric"

  it should "provide Numeric support" in {

    assert(ThingeeNumeric.plus(Thangs(1000), KiloThangs(10)) == KiloThangs(11))
    assert(ThingeeNumeric.minus(KiloThangs(10), Thangs(1000)) == KiloThangs(9))

    an[UnsupportedOperationException] should be thrownBy ThingeeNumeric.times(Thangs(1), Thangs(2))

    assert(ThingeeNumeric.negate(Thangs(10.22)) == Thangs(-10.22))
    assert(ThingeeNumeric.fromInt(10) == Thangs(10))
    assert(ThingeeNumeric.toInt(Thangs(10)) == 10)
    assert(ThingeeNumeric.toLong(Thangs(10)) == 10L)
    assert(ThingeeNumeric.toFloat(Thangs(10.22)) == 10.22F)
    assert(ThingeeNumeric.toDouble(Thangs(10.22)) == 10.22)

    assert(ThingeeNumeric.compare(Thangs(1000), KiloThangs(2)) < 0)
    assert(ThingeeNumeric.compare(Thangs(2000), KiloThangs(1)) > 0)
    assert(ThingeeNumeric.compare(Thangs(2000), KiloThangs(2)) == 0)

    val ts = List(Thangs(1000), KiloThangs(10), KiloThangs(100))
    assert(ts.sum == KiloThangs(111))
  }
}
