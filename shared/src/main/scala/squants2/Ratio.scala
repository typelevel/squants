package squants2

abstract class Ratio[A, B, QA[N] <: Quantity[N, QA], QB[N] <: Quantity[N, QB] ] {
  def base: QA[A]
  def counter: QB[B]
  def convertToBase(q: QB[B])(implicit f: B => A): QA[A] = base * (q / counter)
  def convertToCounter(q: QA[A])(implicit f: A => B): QB[B] = counter * (q / base)
}

abstract class LikeRatio[A: Numeric, B, Q[N] <: Quantity[N, Q]] extends Ratio[A, B, Q, Q] {
  def ratio(implicit f: B => A): A = base / counter
  def inverseRatio(implicit f: B => A): A = counter.asNum[A] / base
}
