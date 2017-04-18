package squants

trait IsProductOf[P, T <: (_ <: DimensionType, _ <: DimensionType)]

object IsProductOf {
  implicit def isProductIfDimensionTypesSumUp[
    A <: DimensionType,
    B <: DimensionType,
    P <: DimensionType
  ](
    implicit
    baseDimSumCorrect: DimensionSum.Aux[A#Dimension, B#Dimension, P#Dimension]
  ) = new IsProductOf[P, (A, B)] {}
}
