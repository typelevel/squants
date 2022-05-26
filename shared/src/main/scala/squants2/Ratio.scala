package squants2

abstract class Ratio[A, B, QA[_] <: Quantity[_, QA], QB[_] <: Quantity[_, QB]] {
  def base: Quantity[A, QA]
  def counter: Quantity[B, QB]
  def convertToBase(q: Quantity[B, QB])(implicit f: B => A): Quantity[A, QA] = base * (q / counter)
  def convertToCounter(q: Quantity[A, QA])(implicit f: A => B): Quantity[B, QB] = counter * (q / base)
}

abstract class LikeRatio[A: Numeric, B, Q[_] <: Quantity[_, Q]] extends Ratio[A, B, Q, Q] {
  def ratio(implicit f: B => A): A = base / counter
  def inverseRatio(implicit f: B => A): A = counter.asNum[A] / base
}
