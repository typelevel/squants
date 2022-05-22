import scala.annotation.tailrec
import scala.math.BigDecimal.RoundingMode
import scala.math.BigDecimal.RoundingMode.RoundingMode
import scala.math.Numeric.BigDecimalIsConflicted
import scala.math.Numeric.Implicits.infixNumericOps
import scala.math.Ordering.Implicits.infixOrderingOps

package object squants2 {

  case class QuantityParseException(message: String, expression: String) extends Exception(s"$message:$expression")

  type QuantitySeries[A, D <: Dimension] = IndexedSeq[QuantityRange[A, D]]

  type ConversionFactor = Double // Could be replaced with something more robust and precise

  abstract class Converter[A] {
    def apply(factor: ConversionFactor): A
  }

  implicit object DoubleConverter extends Converter[Double] {
    override def apply(factor: ConversionFactor): Double = factor
  }
  implicit object BigDecimalConverter extends Converter[BigDecimal] {
    override def apply(factor: ConversionFactor): BigDecimal = factor
  }
  implicit object FloatConverter extends Converter[Float] {
    override def apply(factor: ConversionFactor): Float = factor.toFloat
  }
  implicit object IntConverter extends Converter[Int] {
    override def apply(factor: ConversionFactor): Int = factor.toInt
  }
  implicit object LongConverter extends Converter[Long] {
    override def apply(factor: ConversionFactor): Long = factor.toLong
  }
  implicit object BigIntConverter extends Converter[BigInt] {
    override def apply(factor: ConversionFactor): BigInt = factor.toLong
  }

  /**
   * Adds extensions to Numeric used by Quantity operations.
   * It `protected` to prevent it leaking to user code scope
   *
   * @param a the Numeric value
   * @param num the Numeric
   * @tparam A the Numeric type
   */
  protected[squants2] implicit class QuantityValueExtensions[A](a: A)(implicit num: Numeric[A]) {

    def /(that: A): A = num match {
      case fNum: Fractional[A] => fNum.div(a, that)
      case iNum: Integral[A] => iNum.quot(a, that)
      case _ => throw new UnsupportedOperationException("Unknown Numeric type")
    }

    def %(that: A): A = num match {
      case _: Fractional[A] => /%(that)._2
      case iNum: Integral[A] => iNum.rem(a, that)
      case _ => throw new UnsupportedOperationException("Unknown Numeric type")
    }

    def /%(that: A): (A, A) = num match {
      case _: Fractional[A] =>
        @tailrec
        def innerMod(rem: A, quot: A, n: Int): (A, A) = {
          if(rem < quot) (num.fromInt(n), rem)
          else innerMod(rem - quot, quot, n + 1)
        }
        val (q, r) = innerMod(a.abs, that.abs, 0)
        val sign = num.fromInt(if(a * that < num.zero) -1 else 1)
        (q * sign, r)

      case iNum: Integral[A] => (iNum.quot(a, that), iNum.rem(a, that))
      case _ => throw new UnsupportedOperationException("Unknown Numeric type")
     }

    def rounded(scale: Int, mode: RoundingMode = RoundingMode.HALF_EVEN): A = num match {
      case _: BigDecimalIsConflicted => a.asInstanceOf[BigDecimal].setScale(scale, mode).asInstanceOf[A]
      case _: Fractional[A] => ??? // TODO
      case _: Integral[A] => a
      case _ => throw new UnsupportedOperationException("Unknown Numeric type")
    }

  }

}

