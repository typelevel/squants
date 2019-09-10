package squants.experimental.formatter

import org.scalatest.{FlatSpec, Matchers}
import squants.UnitOfMeasure
import squants.mass.{Kilograms, Mass}
import squants.mass.MassConversions._
import squants.space.{Inches, Yards, UsMiles}
import squants.space.LengthConversions._
import squants.experimental.unitgroups.UnitGroup
import squants.experimental.unitgroups.uscustomary.space.UsCustomaryLengths

class DefaultFormatterSpec extends FlatSpec with Matchers {

  val usLengthFormatter = new DefaultFormatter(UsCustomaryLengths)

  behavior of "DefaultFormatter"

  it should "pick the best unit - when the unit stays the same" in {
    usLengthFormatter.inBestUnit(5.inches).unit should be (Inches)
  }

  it should "pick the best unit - when the unit changes" in {
    usLengthFormatter.inBestUnit(12.feet).unit should be (Yards)
    usLengthFormatter.inBestUnit(1760.yards).unit should be (UsMiles)
  }

  it should "use the input quantity's unit when the unitgroup set is empty" in {
    val emptyUnitGroup = new UnitGroup[Mass] { val units = Set.empty[UnitOfMeasure[Mass]] }
    val emptyUnitFormatter = new DefaultFormatter(emptyUnitGroup)

    emptyUnitFormatter.inBestUnit(500.kg).unit should be (Kilograms)
  }
}
