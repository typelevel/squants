package squants.experimental

import squants._
import scala.language.implicitConversions

package object constants {
  trait Constant[A <: Quantity[A]] {
    def value: Quantity[A]
    def symbol: String
    def uncertainty: Quantity[A]
    override def toString: String = symbol
  }

  object universal extends constants.Universal

  object ConstantsConversions {
    implicit def ConstantsToQuantity[A <: Quantity[A]](c: Constant[A]): Quantity[A] =
      c.value
  }
}
