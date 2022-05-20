package squants2

abstract class Ratio[A: QNumeric, B: QNumeric, D <: Dimension, E <: Dimension] {
  def base: Quantity[A, D]
  def counter: Quantity[B, E]
  def convertToBase(q: Quantity[B, E])(implicit f: B => A): Quantity[A, D] = base * (q / counter)
  def convertToCounter(q: Quantity[A, D])(implicit f: A => B): Quantity[B, E] = counter * (q / base)
}

abstract class LikeRatio[A: QNumeric, B: QNumeric, D <: Dimension] extends Ratio[A, B, D, D] {
  def ratio(implicit f: B => A): A = base / counter
  def inverseRatio(implicit f: B => A): A = counter.asNum[A] / base
}
