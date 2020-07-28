package squants.experimental.unitgroups

import scala.collection.immutable.Set

import squants.UnitOfMeasure
import squants.space.{Kilometers, Length, Meters, Nanometers}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UnitGroupSpec extends AnyFlatSpec with Matchers {

  behavior of "UnitGroup"

  it should "generate a SortedUnits, in increasing order, regardless of input order" in {
    val unitGroup = new UnitGroup[Length] {
      val units: Set[UnitOfMeasure[Length]] = Set(Kilometers, Nanometers, Meters)
    }

    unitGroup.sortedUnits should be (Set(Nanometers, Meters, Kilometers))
  }

}
