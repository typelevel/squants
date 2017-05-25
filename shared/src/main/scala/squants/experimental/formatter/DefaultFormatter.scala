package squants.experimental.formatter

import squants.Quantity
import squants.experimental.unitgroups.UnitGroup

class DefaultFormatter[A <: Quantity[A]](unitGroup: UnitGroup[A]) extends Formatter[A] {
  override def inBestUnit(quantity: Quantity[A]): A = {
    if (unitGroup.units.isEmpty)
      quantity.in(quantity.unit)
    else {
      val unit =
        unitGroup.sortedUnits
          .takeWhile { u => quantity.to(u).abs >= 1.0 }
          .lastOption
          .getOrElse(unitGroup.sortedUnits.head)
      quantity.in(unit) 
    }
  }
}
