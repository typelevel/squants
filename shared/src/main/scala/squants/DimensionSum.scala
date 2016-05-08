package squants

import squants.TypeLevelInt._


trait DimensionSum[A <: HList, B <: HList] {
  type Out <: HList
}

object DimensionSum {
  type Aux[A <: HList, B <: HList, O <: HList] = DimensionSum[A, B] { type Out = O }
  def apply[A <: HList, B <: HList](implicit sum: DimensionSum[A, B]): Aux[A, B, sum.Out] = sum

  implicit def hNilPlus[B <: HList]: Aux[HNil, B, B] = new DimensionSum[HNil, B] { type Out = B }
  implicit def hListPlusHList[
    D,
    E1 <: TypeLevelInt,
    E2 <: TypeLevelInt,
    S <: TypeLevelInt,
    R1 <: HList,
    R2 <: HList,
    A <: (D, E1) :: R1,
    B <: (D, E2) :: R2,
    O <: HList
  ](implicit exponentSum: Sum.Aux[E1, E2, S], dimSum: Aux[R1, R2, O]): Aux[A, B, (D, S) :: O] = new DimensionSum[A, B] {
    type Out = (D, S) :: O
  }

  type HLength = (Length, _1) :: HNil
  type HArea = (Length, _2) :: HNil
  type HVolume = (Length, _3) :: HNil

  implicit val volumeIsLengthTimesArea = hListPlusHList[Length, _1, _2, _3, HNil, HNil, (Length, _1) :: HNil, (Length, _2) :: HNil, HNil]
  val test = implicitly[DimensionSum[(Length, _1) :: HNil, (Length, _2) :: HNil]]

  implicit val massCubed: Aux[(Mass, _2) :: HNil, (Mass, _1) :: HNil, (Mass, _3) :: HNil] = hListPlusHList[Mass, _2, _1, _3, HNil, HNil, (Mass, _2) :: HNil, (Mass, _1) :: HNil, HNil]
  implicit val absurdButWorking = hListPlusHList[Length, _1, _2, _3, (Mass, _2) :: HNil, (Mass, _1) :: HNil, (Length, _1) :: (Mass, _2) :: HNil, (Length, _2) :: (Mass, _1) :: HNil, (Mass, _3) :: HNil]
  val test2 = implicitly[DimensionSum[(Length, _1) :: HNil, (Length, _2) :: HNil]]

}
