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

  it should "equal an equivalent like value" in {
    val x = Thangs(2.1)
    val y = Thangs(2.1)
    x.equals(y) should be(right = true)
  }

  it should "not equal an equivalent value of a differnt type" in {
    val x = KiloThangs(2.1)
    val y = Kilograms(2.1)
    x.equals(y) should be(right = false)
  }

  it should "add two like values and result in a like value" in {
    val x = Thangs(14.999)
    val y = Thangs(0.001)
    x plus y should be(Thangs(15))
    x + y should be(Thangs(15))
  }

  it should "minus two like values and result in a like value" in {
    val x = Thangs(15.0)
    val y = Thangs(0.001)
    (x minus y) should be(Thangs(14.999))
    x - y should be(Thangs(14.999))
  }

  it should "times by a Double and result in a like value" in {
    val x = Thangs(4.5)
    val y = 2.0
    (x times y) should be(Thangs(9.0))
    x * y should be(Thangs(9.0))
  }

  it should "divide by a Double and result in a like value" in {
    val x = Thangs(9.0)
    val y = 2.0
    (x divide y) should be(Thangs(4.5))
    x / y should be(Thangs(4.5))
  }

  it should "divide by a like value and result in a Double" in {
    val x = Thangs(9.0)
    val y = Thangs(2.0)
    (x divide y) should be(4.5)
    x / y should be(4.5)
  }

  it should "remainder by a Double and result in a like value" in {
    val x = Thangs(9.0)
    val y = 2.0
    x remainder y should be(Thangs(1))
    x % y should be(Thangs(1))
  }

  it should "remainder by a like value and result in a Double" in {
    val x = Thangs(9.0)
    val y = Thangs(2.0)
    (x remainder y) should be(1.0)
    x % y should be(1.0)
  }

  it should "divideAndRemainder by a Double and result in a pair of like values" in {
    val x = Thangs(9.0)
    val y = 2.0
    val p1 = x divideAndRemainder y
    p1._1 should be(Thangs(4))
    p1._2 should be(Thangs(1))
    val p2 = x /% y
    p2._1 should be(Thangs(4))
    p2._2 should be(Thangs(1))
  }

  it should "divideAndRemainder by a like value and result in a Double and like value" in {
    val x = Thangs(9.0)
    val y = Thangs(2.0)
    val p1 = x divideAndRemainder y
    p1._1 should be(4)
    p1._2 should be(Thangs(1))
    val p2 = x /% y
    p2._1 should be(4)
    p2._2 should be(Thangs(1))
  }

  it should "negate a value and result in a like negative value" in {
    val x = Thangs(9.0)
    x.negate should be(Thangs(-9.0))
    -x should be(Thangs(-9.0))

    val y = Thangs(-9.0)
    y.negate should be(Thangs(9.0))
    -y should be(Thangs(9.0))
  }

  it should "return the absolute value of a Quantity value" in {
    val x = Thangs(9.0)
    x.abs should be(Thangs(9.0))
    (-x).abs should be(Thangs(9.0))

    val y = Thangs(-9.0)
    y.abs should be(Thangs(9.0))
    (-y).abs should be(Thangs(9.0))
  }

  it should "return true on comparing two different values with !=" in {
    val x = Thangs(1)
    val y = Thangs(2)
    x should not be y
  }

  it should "return false on comparing two equal values with !=" in {
    val x = Thangs(1)
    val y = Thangs(1)
    (x != y) should be(right = false)
  }

  it should "compare a like value values and return 1, 0, or -1" in {
    val x = Thangs(5)
    (x compare Thangs(4.999)) should be(1)
    (x compare Thangs(5)) should be(0)
    (x compare Thangs(5.001)) should be(-1)
  }

  it should "max a like value and return the greater of the two" in {
    val x = Thangs(5)
    val y = Thangs(4.999)
    (x max y) should be(Thangs(5))

    (x max y) should be(Thangs(5))
  }

  it should "min a like value and return the greater of the two" in {
    val x = Thangs(5)
    val y = Thangs(4.999)
    (x min y) should be(Thangs(4.999))
  }

  it should "plusOrMinus a like value and return a QuantityRange" in {
    val x = Thangs(5)
    val y = Thangs(1)
    (x plusOrMinus y) should be(QuantityRange(Thangs(4), Thangs(6)))
    x +- y should be(QuantityRange(Thangs(4), Thangs(6)))
  }

  it should "to a like value and return a QuantityRange" in {
    val x = Thangs(5)
    val y = Thangs(10)
    val r = x to y
    r should be(QuantityRange(x, y))
  }

  it should "within a QuantityRange and return a Boolean" in {
    val x = Thangs(10)
    val y = Thangs(15)
    val r = x +- Thangs(1)
    x.within(r) should be(right = true)
    y.within(r) should be(right = false)

    Thangs(10) within (Thangs(9) +- Thangs(2)) should be(right = true)
    Thangs(10) within (Thangs(9) to Thangs(12)) should be(right = true)
  }

  it should "notWithin a QuantityRange and return a Boolean" in {
    val x = Thangs(10)
    val y = Thangs(15)
    val r = x +- Thangs(1)
    !x.notWithin(r) should be(right = true)
    y.notWithin(r) should be(right = true)

    Thangs(13) notWithin (Thangs(9) +- Thangs(2)) should be(right = true)
    Thangs(13) notWithin (Thangs(9) to Thangs(12)) should be(right = true)
  }

  it should "to a unit and return a Double" in {
    val x = Thangs(1500)
    (x to KiloThangs) should be(1.5)
    x.toKilothangs should be(1.5)
  }

  it should "in a unit and return a like value in that unit" in {
    // The `in` method is only useful for Quantities that implement quantity classes for each unit
    val x = Fahrenheit(212)
    (x in Celsius) should be(Celsius(100))

    val y = Seconds(3600)
    (y in Hours) should be(Hours(1))
  }

  it should "toString and return a string formatted for the valueUnit" in {
    val x = Thangs(10)
    x.toString should be("10.0 th")
  }

  it should "toString a unit and return a string formatted for the valueUnit" in {
    val x = Thangs(1500)
    (x toString KiloThangs) should be("1.5 kth")
  }

  behavior of "SquantifiedDouble"

  it should "multiply by a Quantity value and return the product as a like value" in {
    val l = 10 * Thangs(1000)
    l.getClass should be(classOf[Thingee])
    (l to Thangs) should be(10000)

    val m = 10 * Kilograms(50)
    m.getClass should be(classOf[Mass])
    (m to Kilograms) should be(500)
  }

  behavior of "QuantityNumeric"

  it should "provide Numeric support" in {

    ThingeeNumeric.plus(Thangs(1000), KiloThangs(10)) should be(KiloThangs(11))
    ThingeeNumeric.minus(KiloThangs(10), Thangs(1000)) should be(KiloThangs(9))

    an[UnsupportedOperationException] should be thrownBy ThingeeNumeric.times(Thangs(1), Thangs(2))

    ThingeeNumeric.negate(Thangs(10.22)) should be(Thangs(-10.22))
    ThingeeNumeric.fromInt(10) should be(Thangs(10))
    ThingeeNumeric.toInt(Thangs(10)) should be(10)
    ThingeeNumeric.toLong(Thangs(10)) should be(10L)
    ThingeeNumeric.toFloat(Thangs(10.22)) should be(10.22F)
    ThingeeNumeric.toDouble(Thangs(10.22)) should be(10.22)

    ThingeeNumeric.compare(Thangs(1000), KiloThangs(2)) < 0 should be(right = true)
    ThingeeNumeric.compare(Thangs(2000), KiloThangs(1)) > 0 should be(right = true)
    ThingeeNumeric.compare(Thangs(2000), KiloThangs(2)) should be(0)

    val ts = List(Thangs(1000), KiloThangs(10), KiloThangs(100))
    ts.sum should be(KiloThangs(111))
  }
}
