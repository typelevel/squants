package squants.formatter

import squants.Quantity

trait Formatter[A <: Quantity[A]] {
  def bestUnit(quantity: Quantity[A]): A
}
