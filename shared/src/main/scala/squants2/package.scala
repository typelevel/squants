import scala.math.BigDecimal.RoundingMode
import scala.math.BigDecimal.RoundingMode.RoundingMode
import scala.math.Numeric.BigDecimalIsConflicted

package object squants2 {

  case class QuantityParseException(message: String, expression: String) extends Exception(s"$message:$expression")

  type QuantitySeries[A, D <: Dimension] = IndexedSeq[QuantityRange[A, D]]

  /**
   * Adds extensions to Numeric used by Quantity operations.
   * It `protected` to prevent it leaking to user code scope
   *
   * @param a the Numeric value
   * @param num the Numeric
   * @tparam A the Numeric type
   */
  protected [squants2] implicit class QuantityValueExtensions[A](a: A)(implicit num: Numeric[A]) {
    def /(that: A): A = num match {
      case fNum: Fractional[A] => fNum.div(a, that)
      case iNum: Integral[A]   => iNum.quot(a, that)
    }
    def %(that: A): A = num match {
      case fNum: Fractional[A] => ??? // TODO:   fnum.div(a, that)
      case iNum: Integral[A] => iNum.rem(a, that)
    }
    def /%(that: A): (A, A) = (/(that), %(that))

    def rounded(scale: Int, mode: RoundingMode = RoundingMode.HALF_EVEN): A = num match {
      case _: BigDecimalIsConflicted => a.asInstanceOf[BigDecimal].setScale(scale, mode).asInstanceOf[A]
      case _: Fractional[A]          => BigDecimal(num.toDouble(a)).setScale(scale, mode).asInstanceOf[A]
      case _: Integral[A]            => a
    }

  }

}

