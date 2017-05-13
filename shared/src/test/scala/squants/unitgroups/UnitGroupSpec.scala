package squants.unitgroups

import scala.collection.immutable.Set

import org.scalatest.{FlatSpec, Matchers}
import squants.UnitOfMeasure
import squants.space.{Kilometers, Length, Meters, Nanometers}

class UnitGroupSpec extends FlatSpec with Matchers {

  behavior of "UnitGroup"

  it should "generate a SortedUnits, in increasing order, regardless of input order" in {
    val unitGroup = new UnitGroup[Length] {
      val units: Set[UnitOfMeasure[Length]] = Set(Kilometers, Nanometers, Meters)
    }

    unitGroup.sortedUnits should be (Set(Nanometers, Meters, Kilometers))
  }

}
