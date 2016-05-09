package squants

trait DimensionType[D <: HList]

object DimensionType {
  type OneBaseDimension[A] = DimensionType[A :: HNil]
  type TwoBaseDimensions[A , B] = DimensionType[A :: B :: HNil]
  type ThreeBaseDimensions[A, B, C] = DimensionType[A :: B :: C :: HNil]
}