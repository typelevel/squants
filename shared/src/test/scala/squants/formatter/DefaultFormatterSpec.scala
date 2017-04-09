package squants.formatter

import org.scalatest.{FlatSpec, Matchers}
import squants.space.{Centimeters, Kilometers, Length, Meters}
import squants.space.LengthConversions._
import squants.unitgroups.si.strict.SiLengths


class DefaultFormatterSpec extends FlatSpec with Matchers {

  behavior of "DefaultFormatter"

  it should "pick the best unit" in {
    val siLengthFormatter = new DefaultFormatter[Length] { val units = SiLengths }

    // keeping the unit the same
    siLengthFormatter.bestUnit(5.cm).unit should be (Centimeters)

    // changing the unit
    siLengthFormatter.bestUnit(500.cm).unit should be (Meters)
    siLengthFormatter.bestUnit(3000.meters).unit should be (Kilometers)
  }
}
