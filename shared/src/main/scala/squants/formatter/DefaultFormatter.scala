package squants.formatter

import squants.Quantity
import squants.unitgroups.UnitGroup

trait DefaultFormatter[A <: Quantity[A]] extends Formatter[A] {
  def unitGroup: UnitGroup[A]

  override def bestUnit(quantity: Quantity[A]): A = {
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
