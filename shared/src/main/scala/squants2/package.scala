

package object squants2 {

  case class QuantityParseException(message: String, expression: String) extends Exception(s"$message:$expression")

  type QuantitySeries[A, D <: Dimension] = IndexedSeq[QuantityRange[A, D]]

  implicit class QuantityValueDivider[A](a: A)(implicit num: Numeric[A]) {
    def /(that: A): A = num match {
      case fnum: Fractional[A] => fnum.div(a, that)
      case inum: Integral[A] => inum.quot(a, that)
    }
    def %(that: A): A = num match {
      case fnum: Fractional[A] => ??? // TODO:   fnum.div(a, that)
      case inum: Integral[A] => inum.rem(a, that)
    }
    def /%(that: A): (A, A) = num match {
      case fnum: Fractional[A] => ??? // TODO:   fnum.div(a, that)
      case inum: Integral[A] => (inum.quot(a, that), inum.rem(a, that))
    }
  }

}

