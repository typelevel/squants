package squants.formatter

import org.scalatest.{FlatSpec, Matchers}
import squants.formatter.Implicits._
import squants.space.LengthConversions._
import squants.space.{Centimeters, Kilometers, Meters}
import squants.unitgroups.si.strict.space.SiLengths

class ImplicitFormatterSpec extends FlatSpec with Matchers {

  implicit val SiLengthImplicit = SiLengths

  behavior of "ImplicitFormatter"

  it should "pick the best unit" in {

    // keeping the unit the same
    5.cm.inBestUnit.unit should be (Centimeters)

    // changing the unit
    500.cm.inBestUnit.unit should be (Meters)
    3000.meters.inBestUnit.unit should be (Kilometers)
  }
}
