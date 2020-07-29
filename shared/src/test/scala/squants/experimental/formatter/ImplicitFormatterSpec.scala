package squants.experimental.formatter

import squants.UnitOfMeasure
import squants.experimental.formatter.implicits._
import squants.experimental.formatter.syntax._
import squants.mass.{Kilograms, Mass}
import squants.experimental.unitgroups.ImplicitDimensions.space._
import squants.experimental.unitgroups.si.strict.implicits._
import squants.mass.MassConversions._
import squants.space.LengthConversions._
import squants.space.{Centimeters, Kilometers, Meters}
import squants.experimental.unitgroups.UnitGroup
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ImplicitFormatterSpec extends AnyFlatSpec with Matchers {

  behavior of "ImplicitFormatter"

  it should "pick the best unit - when the unit stays the same" in {
    5.cm.inBestUnit.unit should be(Centimeters)
  }

  it should "pick the best unit - when the unit changes" in {
    500.cm.inBestUnit.unit should be (Meters)
    3000.meters.inBestUnit.unit should be (Kilometers)
  }

  it should "use the input quantity's unit the the unitgroup set is empty" in {
    val emptyUnitGroup = new UnitGroup[Mass] { val units = Set.empty[UnitOfMeasure[Mass]] }
    implicit val emptyUnitFormatter = new DefaultFormatter[Mass](emptyUnitGroup)

    500.kg.inBestUnit.unit should be (Kilograms)
  }
}
