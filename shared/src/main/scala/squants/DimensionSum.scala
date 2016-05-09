package squants

import squants.TypeLevelInt._


trait DimensionSum[A <: HList, B <: HList] {
  type Out <: HList
}

object DimensionSum {
  type Aux[A <: HList, B <: HList, O <: HList] = DimensionSum[A, B] { type Out = O }
  def apply[A <: HList, B <: HList](implicit sum: DimensionSum[A, B]): Aux[A, B, sum.Out] = sum

  implicit def plusHNil[A <: HList]: Aux[A, HNil, A] = new DimensionSum[A, HNil] {
    type Out = A
  }

  implicit def hNilPlus[B <: HList](implicit
    bNotNil: B <:!< HNil
  ): Aux[HNil, B, B] = new DimensionSum[HNil, B] {
    type Out = B
  }

  implicit def hListPlusHList[
    D <: Quantity[D],
    E1 <: TypeLevelInt,
    E2 <: TypeLevelInt,
    S <: { type Out <: TypeLevelInt },
    R1 <: HList,
    R2 <: HList
  ](implicit
    negateSingleton: TypeLevelInt.SingletonOf[Negate[E1], S],
    doesNotCancelOut: S#Out <:!< E2,
    exponentSum: Sum[E1, E2],
    dimSum: DimensionSum[R1, R2]
  ): Aux[(D, E1) :: R1, (D, E2) :: R2, (D, exponentSum.Out) :: dimSum.Out] = new DimensionSum[(D, E1) :: R1, (D, E2) :: R2] {
    type Out = (D, exponentSum.Out) :: dimSum.Out
  }

  implicit def hListPlusHListCancellingOut[
    D <: Quantity[D],
    S <: { type Out <: TypeLevelInt },
    E1 <: TypeLevelInt,
    E2 <: TypeLevelInt,
    R1 <: HList,
    R2 <: HList
  ](
    implicit dimSum: DimensionSum[R1, R2],
    negateSingleton: TypeLevelInt.SingletonOf[Negate[E1], S],
    cancelsOut: S#Out =:= E2
  ): Aux[(D, E1) :: R1, (D, E2) :: R2, dimSum.Out] = new DimensionSum[(D, E1) :: R1, (D, E2) :: R2] {
    type Out = dimSum.Out
  }

  implicit def hListPlusHListOfLaterDimension[
    D1 <: Quantity[D1],
    D2 <: Quantity[D2],
    E1 <: TypeLevelInt,
    E2 <: TypeLevelInt,
    R1 <: HList,
    R2 <: HList
  ](implicit
    d1beforeD2: D1 DimensionIsEarlierThan D2,
    dimSum: DimensionSum[R1, (D2, E2) :: R2]
  ): Aux[(D1, E1) :: R1, (D2, E2) :: R2, (D1, E1) :: dimSum.Out] = new DimensionSum[(D1, E1) :: R1, (D2, E2) :: R2] {
    type Out = (D1, E1) :: dimSum.Out
  }

  implicit def hListPlusHListOfEarlierDimension[
    D1 <: Quantity[D1],
    D2 <: Quantity[D2],
    E1 <: TypeLevelInt,
    E2 <: TypeLevelInt,
    R1 <: HList,
    R2 <: HList
  ](implicit
    d2beforeD1: D2 DimensionIsEarlierThan D1,
    dimSum: DimensionSum[(D1, E1) :: R1, R2]
  ): Aux[(D1, E1) :: R1, (D2, E2) :: R2, (D2, E2) :: dimSum.Out] = new DimensionSum[(D1, E1) :: R1, (D2, E2) :: R2] {
    type Out = (D2, E2) :: dimSum.Out
  }
  trait <:!<[A, B]

  implicit def nsub[A, B] : A <:!< B = new <:!<[A, B] {}
  implicit def nsubAmbig1[A, B >: A] : A <:!< B = sys.error("Unexpected call")
  implicit def nsubAmbig2[A, B >: A] : A <:!< B = sys.error("Unexpected call")

  class SingletonOf[T, U <: { type Out <: HList }](u: U)
  object SingletonOf {
    implicit def mkSingleton[T <: { type Out <: HList }](implicit t: T): SingletonOf[T, t.type] = new SingletonOf(t)
  }
}
