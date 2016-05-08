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

  implicit def hListPlusHListCancellingOut[
  D,
  E1 <: TypeLevelInt,
  E2 <: TypeLevelInt,
  S <: Negate.Aux[E1, E2],
  R1 <: HList,
  R2 <: HList,
  A <: (D, E1) :: R1,
  B <: (D, E2) :: R2,
  O <: HList
  ](implicit dimSum: Aux[R1, R2, O]): Aux[A, B, O] = new DimensionSum[A, B] {
    type Out = O
  }
  implicit val partiallyCancellingOut = hListPlusHListCancellingOut[
    Length, _1, _M1, Negate.Aux[_1, _M1], HNil, (Mass, _4) :: HNil, (Length, _1) :: HNil, (Length, _M1) :: (Mass, _4) :: HNil, (Mass, _4) :: HNil]
  val test5 = implicitly[DimensionSum[(Length, _1) :: HNil, (Length, _M1) :: (Mass, _4) :: HNil]]

  implicit def hListPlusHListOfLaterDimension[
    D1,
    D2,
    I1 <: Pos,
    I2 <: Pos,
    E1 <: TypeLevelInt,
    R1 <: HList,
    A <: (D1, E1) :: R1,
    B <: HList,
    O <: HList
  ](implicit
    d1HasIndexI1: DimensionOrder.Aux[D1, I1],
    d2HasIndexI2: DimensionOrder.Aux[D2, I2],
    d1beforeD2: TypeLevelInt.LT[I1, I2],
    dimSum: Aux[R1, B, O]
  ): Aux[A, B, (D1, E1) :: O] = new DimensionSum[A, B] {
    type Out = (D1, E1) :: O
  }
  implicit def hListPlusHListOfEarlierDimension[
  D1,
  D2,
  I1 <: Pos,
  I2 <: Pos,
  E1 <: TypeLevelInt,
  R1 <: HList,
  A <: (D1, E1) :: R1,
  B <: HList,
  O <: HList
  ](implicit
    d1HasIndexI1: DimensionOrder.Aux[D1, I1],
    d2HasIndexI2: DimensionOrder.Aux[D2, I2],
    d2beforeD1: TypeLevelInt.LT[I2, I1],
    dimSum: Aux[R1, B, O]
  ): Aux[A, B, (D1, E1) :: O] = new DimensionSum[A, B] {
    type Out = (D1, E1) :: O
  }
  type HLength = (Length, _1) :: HNil
  type HArea = (Length, _2) :: HNil
  type HVolume = (Length, _3) :: HNil

  implicit val volumeIsLengthTimesArea = hListPlusHList[Length, _1, _2, _3, HNil, HNil, (Length, _1) :: HNil, (Length, _2) :: HNil, HNil]
  val test = implicitly[DimensionSum[(Length, _1) :: HNil, (Length, _2) :: HNil]]

  implicit val massCubed: Aux[(Mass, _2) :: HNil, (Mass, _1) :: HNil, (Mass, _3) :: HNil] = hListPlusHList[Mass, _2, _1, _3, HNil, HNil, (Mass, _2) :: HNil, (Mass, _1) :: HNil, HNil]
  implicit val absurdButWorking = hListPlusHList[Length, _1, _2, _3, (Mass, _2) :: HNil, (Mass, _1) :: HNil, (Length, _1) :: (Mass, _2) :: HNil, (Length, _2) :: (Mass, _1) :: HNil, (Mass, _3) :: HNil]
  val test2 = implicitly[DimensionSum[(Length, _1) :: HNil, (Length, _2) :: HNil]]

  implicit val differentLists: Aux[(Length, _4) :: HNil, (Mass, _2) :: HNil, (Length, _4) :: (Mass, _2) :: HNil] = hListPlusHListOfLaterDimension[Length, Mass, _1, _2, _4, HNil, (Length, _4) :: HNil, (Mass, _2) :: HNil, (Mass, _2) :: HNil]
  val test3 = implicitly[DimensionSum[(Length, _4) :: HNil, (Mass, _2) :: HNil]]
}
