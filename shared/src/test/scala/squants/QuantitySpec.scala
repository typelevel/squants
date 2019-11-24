/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2015, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants

import org.scalatest.{FlatSpec, Matchers, TryValues}

import scala.math.BigDecimal.RoundingMode
import scala.util.{Failure, Try}
import squants.thermal.{Celsius, Fahrenheit}
import squants.time.{Hertz, Hours, Minutes}

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class QuantitySpec extends FlatSpec with Matchers with CustomMatchers with TryValues {

  /*
    Create a Quantity with two Units of Measure
   */
  final class Thingee private (val value: Double, val unit: ThingeeUnit)
      extends Quantity[Thingee] {
    def dimension = Thingee
    def toThangs = to(Thangs)
    def toKilothangs = to(Kilothangs)
  }

  object Thingee extends Dimension[Thingee] {
    private[squants] def apply[A](n: A, unit: ThingeeUnit)(implicit num: Numeric[A]) = new Thingee(num.toDouble(n), unit)
    def apply(value: Any) = parse(value)
    def name = "Thingee"
    def primaryUnit = Thangs
    def siUnit = Thangs
    def units = Set(Thangs, Kilothangs)
  }

  trait ThingeeUnit extends UnitOfMeasure[Thingee] with UnitConverter {
    def apply[A](n: A)(implicit num: Numeric[A]) = Thingee(n, this)
  }

  object Thangs extends ThingeeUnit with PrimaryUnit with SiUnit {
    val symbol = "th"
  }

  object Kilothangs extends ThingeeUnit {
    val symbol = "kth"
    val conversionFactor = Thangs.conversionFactor * MetricSystem.Kilo
  }

  implicit object ThingeeNumeric extends AbstractQuantityNumeric[Thingee](Thangs)

  behavior of "Quantity as implemented in Thingee"

  it should "create values using arbitrary numeric types" in {
    // define Numeric for each type
    abstract class BaseNumeric[T] extends Numeric[T] {
      def plus(x: T, y: T) = x + y
      def minus(x: T, y: T) = x - y
      def times(x: T, y: T) = x * y
      def negate(x: T) = -x
      def toInt(x: T) = x.toInt
      def toLong(x: T) = x.toLong
      def toFloat(x: T) = x.toFloat
      def compare(x: T, y: T) = if (x == y) 0 else if (x.toDouble > y.toDouble) 1 else -1
    }

    implicit val stringNumeric = new BaseNumeric[String] {
      def fromInt(x: Int) = x.toString
      def toDouble(x: String) = augmentString(x).toDouble // augmentString is used to disambiguate implicit conversion
      def parseString(str: String): Option[String] = Some(str) // for 2.13 compability
    }

    // Use them to initialize quantity values
    Thangs("10.22").toThangs should be(10.22)
    (Thangs("10") + Thangs("0.22")).toThangs should be(10.22)
  }

  it should "create values from properly formatted Strings" in {
    Thingee("42 th").get should be(Thangs(42))
    Thingee("10.22 th").get should be(Thangs(10.22))
    Thingee("1e5 th").get should be(Thangs(1e5))
    Thingee("1E5 th").get should be(Thangs(1E5))
    Thingee("1e+5 th").get should be(Thangs(1e+5))
    Thingee("1e-5 th").get should be(Thangs(1e-5))
    Thingee("1.0e5 th").get should be(Thangs(1.0e5))
    Thingee("1.0E5 th").get should be(Thangs(1.0E5))
    Thingee("1.0e+5 th").get should be(Thangs(1.0e+5))
    Thingee("1.01E+5 th").get should be(Thangs(1.01E+5))
    Thingee("1.012e-5 th").get should be(Thangs(1.012e-5))
    Thingee("12.0123E-5 th").get should be(Thangs(12.0123E-5))
    Thingee("10.22 kth").get should be(Kilothangs(10.22))
  }

  it should "return failure on improperly formatted Strings" in {
    Thingee("10.22 xx") should be(Failure(QuantityParseException("Unable to parse Thingee", "10.22 xx")))
    Thingee("10.xx th") should be(Failure(QuantityParseException("Unable to parse Thingee", "10.xx th")))
  }

  it should "create values from properly formatted Tuples with any numeric anyval" in {

    // floating-point conversions introduce error.
    implicit val tolerance = Thangs(0.000001)

    // Using tuples of arbitrary numeric anyval's
    Thingee((10.toByte, "th")).get should be(Thangs(10d))
    Thingee((10.toShort, "th")).get should be(Thangs(10d))
    Thingee((10, "th")).get should be(Thangs(10d))
    Thingee((10L, "th")).get should be(Thangs(10d))
    Thingee((10.22f, "th")).get should beApproximately(Thangs(10.22))
    Thingee((10.22, "th")).get should be(Thangs(10.22))
  }

  it should "create values from tuples with any well-formed unit string" in {
    Thingee((10.22, "kth")).get should be(Kilothangs(10.22))
  }

  it should "create values from well-formed tuples and roundtrip them" in {
    Thingee(Thangs(10.22).toTuple).get should be(Thangs(10.22))
    Thingee(Kilothangs(10.22).toTuple).get should be(Kilothangs(10.22))

    Thingee(Thangs(10.22).toTuple(Kilothangs)).get should be(Thangs(10.22))
    Thingee(Kilothangs(10.22).toTuple(Thangs)).get should be(Kilothangs(10.22))
  }

  it should "return failure on an improperly formatted Tuple" in {
    Thingee((10.22, "xx")) should be(Failure(QuantityParseException("Unable to identify Thingee unit xx", "(10.22,xx)")))
  }

  it should "equal an equivalent like value" in {
    val x = Thangs(2.1)
    val y = Thangs(2.1)
    x.equals(y) should be(right = true)
  }

  it should "equal an equivalent value in a different unit" in {
    val x = Thangs(2100)
    val y = Kilothangs(2.1)
    x.equals(y) should be(right = true)
  }

  it should "compare a non-null Quantity to a null and return a proper result" in {
    val x = Thangs(2.1)
    x == null should be(right = false)
    null == x should be(right = false)
    x != null should be(right = true)
    null != x should be(right = true)
  }

  it should "compare a null Quantity to null and return a proper result" in {
    val x: Thingee = null
    x == null should be(right = true)
    null == x should be(right = true)
    x != null should be(right = false)
    null != x should be(right = false)
  }

  it should "compare a null Quantity to a non-null Quantity" in {
    val x = null
    val y = Thangs(2.1)
    x == y should be(right = false)
    y == x should be(right = false)
  }

  it should "not equal an equivalent value of a different type" in {
    val x = Kilothangs(2.1)
    val y = Kilograms(2.1)
    x.equals(y) should be(right = false)
    x == y should be(right = false)
    x != y should be(right = true)
  }

  it should "approx a like value that is within an implicitly defined tolerance" in {
    implicit val tol = Thangs(.1)
    val x = Kilothangs(2.0)
    val y = Kilothangs(1.9999)
    x approx y should be(right = true)
    x =~ y should be(right = true)
    x ≈ y should be(right = true)
    (x ~= y) should be(right = true)
  }

  it should "not approx a like value that is not within an implicitly defined tolerance" in {
    implicit val tol = Thangs(.1)
    val x = Kilothangs(2.0)
    val y = Kilothangs(1.9998)
    x approx y should be(right = false)
    x =~ y should be(right = false)
    (x ~= y) should be(right = false)
  }

  it should "approx a like value that is within an explicitly passed tolerance" in {
    implicit val tol = Thangs(.1)
    val x = Kilothangs(2.0)
    val y = Kilothangs(1.9999)
    x approx y should be(right = true)
    x =~ y should be(right = true)
    (x ~= y) should be(right = true)
    // apply approx with an explicit override of the tolerance
    x.approx(y)(Thangs(.01)) should be(right = false)
    x.=~(y)(Thangs(.01)) should be(right = false)
    x.~=(y)(Thangs(.01)) should be(right = false)
  }

  it should "add two like values and result in a like value" in {
    val x = Thangs(14.999)
    val y = Thangs(0.001)
    x plus y should be(Thangs(15))
    x + y should be(Thangs(15))
  }

  it should "add two like value in different units and result in a like value" in {
    val x = Kilothangs(14.999)
    val y = Thangs(1)
    x plus y should be(Kilothangs(15))
    x + y should be(Kilothangs(15))
  }

  it should "minus two like values and result in a like value" in {
    val x = Thangs(15.0)
    val y = Thangs(0.001)
    (x minus y) should be(Thangs(14.999))
    x - y should be(Thangs(14.999))
  }

  it should "minus two like value in different units and result in a like value" in {
    val x = Kilothangs(15)
    val y = Thangs(1)
    x minus y should be(Kilothangs(14.999))
    x - y should be(Kilothangs(14.999))
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

  it should "divide by a like value in different units and result in a Double" in {
    val x = Kilothangs(9)
    val y = Thangs(2)
    x divide y should be(4500.0)
    x / y should be(4500.0)
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

  it should "return the ceil value of a Quantity value" in {
    val x = Thangs(1.0)
    x.ceil should be(Thangs(1.0))
    (-x).ceil should be(Thangs(-1.0))

    val y = Thangs(1.1)
    y.ceil should be(Thangs(2.0))
    (-y).ceil should be(Thangs(-1.0))

    val y2 = Thangs(1.9)
    y2.ceil should be(Thangs(2.0))
    (-y2).ceil should be(Thangs(-1.0))

    val z = Thangs(-9.1)
    z.ceil should be(Thangs(-9.0))
    (-z).ceil should be(Thangs(10.0))

    val z2 = Thangs(-9.9)
    z2.ceil should be(Thangs(-9.0))
    (-z2).ceil should be(Thangs(10.0))
  }

  it should "return the floor value of a Quantity value" in {
    val x = Thangs(1.0)
    x.floor should be(Thangs(1.0))
    (-x).floor should be(Thangs(-1.0))

    val y = Thangs(1.1)
    y.floor should be(Thangs(1.0))
    (-y).floor should be(Thangs(-2.0))

    val y2 = Thangs(1.9)
    y2.floor should be(Thangs(1.0))
    (-y2).floor should be(Thangs(-2.0))

    val z = Thangs(-9.1)
    z.floor should be(Thangs(-10.0))
    (-z).floor should be(Thangs(9.0))

    val z2 = Thangs(-9.9)
    z2.floor should be(Thangs(-10.0))
    (-z2).floor should be(Thangs(9.0))
  }

  it should "return the rint value of a Quantity value" in {
    val x = Thangs(1.0)
    x.rint should be(Thangs(1.0))
    (-x).rint should be(Thangs(-1.0))

    val y = Thangs(1.1)
    y.rint should be(Thangs(1.0))
    (-y).rint should be(Thangs(-1.0))

    val y2 = Thangs(1.5)
    y2.rint should be(Thangs(2.0))
    (-y2).rint should be(Thangs(-2.0))

    val y3 = Thangs(1.1)
    y3.rint should be(Thangs(1.0))
    (-y3).rint should be(Thangs(-1.0))

    val z = Thangs(-9.1)
    z.rint should be(Thangs(-9.0))
    (-z).rint should be(Thangs(9.0))

    val z2 = Thangs(-9.9)
    z2.rint should be(Thangs(-10.0))
    (-z2).rint should be(Thangs(10.0))
  }

  it should "return a Quantity rounded to a scale" in {
    val x = Thangs(123.45)
    x.rounded(1) should be(Thangs(123.4))
    x.rounded(1, RoundingMode.HALF_DOWN) should be(Thangs(123.4))
    x.rounded(1, RoundingMode.HALF_UP) should be(Thangs(123.5))
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
    val x = Kilothangs(5)
    (x compare Kilothangs(4.999)) should be(1)
    (x compare Kilothangs(5)) should be(0)
    (x compare Kilothangs(5.001)) should be(-1)

    (x compare Thangs(4999.0)) should be(1)
    (x compare Thangs(5000.0)) should be(0)
    (x compare Thangs(5001.0)) should be(-1)
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
    (x to Kilothangs) should be(1.5)
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

  it should "toString a unit and return a string formatted for the unit" in {
    val x = Thangs(1500)
    (x toString Kilothangs) should be("1.5 kth")
  }

  it should "toString a format and unit and return a string using the format and unit" in {
    val x = Thangs(1.2555555)
    x.toString(Thangs, "%.2f") should be("1.26 th")
    x.toString(Thangs, "%.3f") should be("1.256 th")
  }

  it should "toTuple and return a tuple including the value and the unit's symbol" in {
    val x = Thangs(10.22)
    x.toTuple should be((10.22, "th"))
  }

  it should "toTuple a unit and return a tuple including the value in the supplied unit and that unit's symbol" in {
    val x = Kilothangs(10.22)
    x.toTuple(Thangs) should be((10220, "th"))
  }

  it should "map over the underlying value and return the resulting value in a Quantity of the same Unit" in {
    val x = Kilothangs(10.22)
    x.map(_ * 2) should be(Kilothangs(20.44))
  }

  it should "return the correct Numeric value when pattern matched against a Unit of Measure" in {
    val x = Thangs(1200)
    val thangs = x match {
      case Thangs(v) ⇒ v
    }
    thangs should be(1200)

    val kilothangs = x match {
      case Kilothangs(v) ⇒ v
    }
    kilothangs should be(1.2)
  }

  behavior of "SquantifiedDouble"

  it should "multiply by a Quantity value and return the product as a like value" in {
    val l = 10.22 * Thangs(1000)
    l.getClass should be(classOf[Thingee])
    (l to Thangs) should be(10220)

    val m = 10D * Kilograms(50)
    m.getClass should be(classOf[Mass])
    (m to Kilograms) should be(500)
  }

  it should "divide by a Time value and return a Frequency" in {
    10D / Seconds(1) should be(Hertz(10))
    10D per Seconds(1) should be(Hertz(10))
  }

  behavior of "SquantifiedLong"

  it should "multiply by a Quantity value and return the product as a like value" in {
    val l = 10L * Thangs(1000)
    l.getClass should be(classOf[Thingee])
    (l to Thangs) should be(10000)

    val m = 10L * Kilograms(50)
    m.getClass should be(classOf[Mass])
    (m to Kilograms) should be(500)
  }

  it should "divide by a Time value and return a Frequency" in {
    10L / Seconds(1) should be(Hertz(10))
    10L per Seconds(1) should be(Hertz(10))
  }

  behavior of "SquantifiedBigDecimal"

  it should "multiply by a Quantity value and return the product as a like value" in {
    val multiple = BigDecimal(10)

    val l = multiple * Thangs(1000)
    l.getClass should be(classOf[Thingee])
    (l to Thangs) should be(10000)

    val m = multiple * Kilograms(50)
    m.getClass should be(classOf[Mass])
    (m to Kilograms) should be(500)
  }

  it should "divide by a Time value and return a Frequency" in {
    BigDecimal(10) / Seconds(1) should be(Hertz(10))
    BigDecimal(10) per Seconds(1) should be(Hertz(10))
  }

  behavior of "QuantityNumeric"

  it should "provide Numeric support" in {

    ThingeeNumeric.plus(Thangs(1000), Kilothangs(10)) should be(Kilothangs(11))
    ThingeeNumeric.minus(Kilothangs(10), Thangs(1000)) should be(Kilothangs(9))

    intercept[UnsupportedOperationException] {
      ThingeeNumeric.times(Thangs(1), Thangs(2))
    }

    ThingeeNumeric.negate(Thangs(10.22)) should be(Thangs(-10.22))
    ThingeeNumeric.fromInt(10) should be(Thangs(10))
    ThingeeNumeric.toInt(Thangs(10)) should be(10)
    ThingeeNumeric.toLong(Thangs(10)) should be(10L)
    ThingeeNumeric.toFloat(Thangs(10.22)) should be(10.22F +- 0.000001F)
    ThingeeNumeric.toDouble(Thangs(10.22)) should be(10.22)

    ThingeeNumeric.compare(Thangs(1000), Kilothangs(2)) < 0 should be(right = true)
    ThingeeNumeric.compare(Thangs(2000), Kilothangs(1)) > 0 should be(right = true)
    ThingeeNumeric.compare(Thangs(2000), Kilothangs(2)) should be(0)

    val ts = List(Thangs(1000), Kilothangs(10), Kilothangs(100))
    ts.sum should be(Kilothangs(111))
  }

  behavior of "Dimension"

  it should "Parse a String into a Quantity based on the supplied Type parameter" in {
    import squants.mass.Mass
    import squants.space.Length
    import squants.time.Time

    def parse[A <: Quantity[A]: Dimension](s: String): Try[A] = {
      implicitly[Dimension[A]].parseString(s)
    }

    implicit val length = Length
    implicit val time = Time
    implicit val thingee = Thingee
    implicit val mass = Mass

    val l = parse[Length]("100 m")
    val t = parse[Time]("100 m")
    val th = parse[Thingee]("100 th")
    val m = parse[Mass]("100 m")

    l.success.value should be(Meters(100))
    t.success.value should be(Minutes(100))
    th.success.value should be(Thangs(100))
    m.failure.exception shouldBe a[QuantityParseException]
    m.failure.exception should have message("Unable to parse Mass:100 m")
  }

  it should "Parse a Tuple with a Double into a Quantity based on the supplied Type parameter" in {
    import squants.mass.Mass
    import squants.space.Length
    import squants.time.Time

    def parse[A <: Quantity[A]: Dimension](t: (Double,  String)): Try[A] = {
      implicitly[Dimension[A]].parseTuple[Double](t)
    }

    implicit val length = Length
    implicit val time = Time
    implicit val thingee = Thingee
    implicit val mass = Mass

    val l = parse[Length]((100d, "m"))
    val t = parse[Time]((100d, "m"))
    val th = parse[Thingee]((100d, "th"))
    val m = parse[Mass]((100d, "m"))

    l.success.value should be(Meters(100d))
    t.success.value should be(Minutes(100d))
    th.success.value should be(Thangs(100d))
    m.failure.exception shouldBe a[QuantityParseException]
    m.failure.exception should have message("Unable to identify Mass unit m:(100.0,m)")
  }

  it should "Parse a Tuple with an Int into a Quantity based on the supplied Type parameter" in {
    import squants.mass.Mass
    import squants.space.Length
    import squants.time.Time

    def parse[A <: Quantity[A]: Dimension](t: (Int,  String)): Try[A] = {
      implicitly[Dimension[A]].parseTuple[Int](t)
    }

    implicit val length = Length
    implicit val time = Time
    implicit val thingee = Thingee
    implicit val mass = Mass

    val l = parse[Length]((100, "m"))
    val t = parse[Time]((100, "m"))
    val th = parse[Thingee]((100, "th"))
    val m = parse[Mass]((100, "m"))

    l.success.value should be(Meters(100))
    t.success.value should be(Minutes(100))
    th.success.value should be(Thangs(100))
    m.failure.exception shouldBe a[QuantityParseException]
    m.failure.exception should have message("Unable to identify Mass unit m:(100.0,m)")
  }

  it should "return consistent hashcode" in {
    val timeInMinutes = Minutes(1)

    timeInMinutes.hashCode() shouldBe timeInMinutes.hashCode()
  }

  it should "return equal hashcode, when objects are equal" in {

    val timeInMinutes = Minutes(1)
    val timeInSeconds = Seconds(60)

    timeInMinutes.equals(timeInSeconds) shouldBe true
    timeInMinutes.hashCode() shouldBe timeInSeconds.hashCode()

  }

  it should "provide implicit instance for Dimension" in {
    implicitly[Dimension[Thingee]] shouldBe Thingee
  }
}
