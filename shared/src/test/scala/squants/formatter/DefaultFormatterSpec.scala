package squants.formatter

import org.scalatest.{FlatSpec, Matchers}
import squants.UnitOfMeasure
import squants.mass.{Kilograms, Mass}
import squants.mass.MassConversions._
import squants.space.{Inches, Length, Yards, UsMiles}
import squants.space.LengthConversions._
import squants.unitgroups.UnitGroup
import squants.unitgroups.uscustomary.space.UsCustomaryLengths

class DefaultFormatterSpec extends FlatSpec with Matchers {

  val usLengthFormatter = new DefaultFormatter[Length] { val unitGroup = UsCustomaryLengths }

  behavior of "DefaultFormatter"

  it should "pick the best unit - when the unit stays the same" in {
    usLengthFormatter.bestUnit(5.inches).unit should be (Inches)
  }

  it should "pick the best unit - when the unit changes" in {
    usLengthFormatter.bestUnit(12.feet).unit should be (Yards)
    usLengthFormatter.bestUnit(1760.yards).unit should be (UsMiles)
  }

  it should "use the input quantity's unit when the unitgroup set is empty" in {
    val emptyUnitGroup = new UnitGroup[Mass] { val units = Set.empty[UnitOfMeasure[Mass]] }
    val emptyUnitFormatter = new DefaultFormatter[Mass] { val unitGroup = emptyUnitGroup }

    emptyUnitFormatter.bestUnit(500.kg).unit should be (Kilograms)
  }
}
