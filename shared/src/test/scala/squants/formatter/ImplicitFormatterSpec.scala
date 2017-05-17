package squants.formatter

import org.scalatest.{FlatSpec, Matchers}
import squants.UnitOfMeasure
import squants.formatter.Implicits._
import squants.mass.{Kilograms, Mass}
import squants.unitgroups.ImplicitDimensions.space._
import squants.unitgroups.si.strict.implicits._
import squants.mass.MassConversions._
import squants.space.LengthConversions._
import squants.space.{Centimeters, Kilometers, Meters}
import squants.unitgroups.UnitGroup

class ImplicitFormatterSpec extends FlatSpec with Matchers {

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
    implicit val emptyUnitFormatter = new DefaultFormatter[Mass] { val unitGroup = emptyUnitGroup }

    500.kg.inBestUnit.unit should be (Kilograms)
  }
}
