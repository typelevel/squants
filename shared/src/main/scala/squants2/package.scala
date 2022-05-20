

package object squants2 {

  case class QuantityParseException(message: String, expression: String) extends Exception(s"$message:$expression")

  type QuantitySeries[A, D <: Dimension] = IndexedSeq[QuantityRange[A, D]]

}

