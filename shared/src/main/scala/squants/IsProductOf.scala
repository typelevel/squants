package squants

import squants.TypeLevelInt.Sum

trait IsProductOf[P, A, B]

object IsProductOf {
  implicit def isProductIfDimensionTypesSumUp[A, B, P, AN <: TypeLevelInt, BN <: TypeLevelInt, PN <: TypeLevelInt, I](
    implicit dimA: A <:< DimensionType[I, AN],
    dimB: B <:< DimensionType[I, BN],
    dimP: P <:< DimensionType[I, PN],
    baseDimSumCorrect: Sum.Aux[AN, BN, PN]
  ) = new IsProductOf[P, A, B] {}

  val areaIsLengthTimesLength = implicitly[IsProductOf[Area, Length, Length]]
  val volumeIsLengthTimesArea = implicitly[IsProductOf[Volume, Length, Area]]
  val volumeIsAreaTimesLength = implicitly[IsProductOf[Volume, Length, Area]]
  //val volumeIsAreaTimesArea = implicitly[IsProductOf[Volume, Area, Area]] // Does not compile, as desired
}
