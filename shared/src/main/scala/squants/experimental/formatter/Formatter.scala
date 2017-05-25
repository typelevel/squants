package squants.experimental.formatter

import squants.Quantity

trait Formatter[A <: Quantity[A]] {
  def inBestUnit(quantity: Quantity[A]): A
}
