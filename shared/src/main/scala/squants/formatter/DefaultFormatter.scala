package squants.formatter

import squants.units.Units
import squants.Quantity

trait DefaultFormatter[A <: Quantity[A]] extends Formatter[A] {
  def units: Units[A]

  override def bestUnit(quantity: Quantity[A]): A = {
    val unit =
      units.sortedUnits
        .takeWhile { u => quantity.to(u).abs >= 1.0 }
        .lastOption
        .getOrElse(units.sortedUnits.head)
    quantity.in(unit)
  }

}
