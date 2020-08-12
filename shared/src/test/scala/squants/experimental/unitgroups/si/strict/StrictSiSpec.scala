package squants.experimental.unitgroups.si.strict

import squants.energy.Energy
import squants.mass.Mass
import squants.space.Length
import squants.experimental.unitgroups.ImplicitDimensions.space._
import squants.experimental.unitgroups.ImplicitDimensions.energy._
import squants.experimental.unitgroups.ImplicitDimensions.mass._
import squants.experimental.unitgroups.UnitGroup
import squants.experimental.unitgroups.si.strict.implicits._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class StrictSiSpec extends AnyFlatSpec with Matchers {

  behavior of "Strict SI UnitGroups"

  "The Strict SI UnitGroups" should "be implicitly derived for arbitrary dimensions" in {
    implicitly[UnitGroup[Length]].units should not be empty
    implicitly[UnitGroup[Mass]].units should not be empty
    implicitly[UnitGroup[Energy]].units should not be empty
  }

}
