package squants

trait IsProductOf[P, T <: (_ <: DimensionType[_], _ <: DimensionType[_])]

object IsProductOf {
  implicit def isProductIfDimensionTypesSumUp[
    LA <: HList,
    LB <: HList,
    LP <: HList,
    DS <: { type Out <: HList }
  ](
    implicit singleton: DimensionSum.SingletonOf[DimensionSum[LA, LB], DS],
    baseDimSumCorrect: DS#Out =:= LP
  ) = new IsProductOf[DimensionType[LP], (DimensionType[LA], DimensionType[LB])] {}
}
