package squants

import squants.TypeLevelInt._

trait DimensionOrder[D] {
  type DimensionIndex <: TypeLevelInt
}

trait DimensionIsEarlierThan[D1, D2]


object DimensionOrder {

  implicit val lengthIs1 = new DimensionOrder[Length] { type DimensionIndex = _1 }
  implicit val massIs2 = new DimensionOrder[Mass] { type DimensionIndex = _2 }
  implicit val timeIs3 = new DimensionOrder[Time] { type DimensionIndex = _3 }
  implicit val energyIs4 = new DimensionOrder[Energy] { type DimensionIndex = _4 }

  /**
    * SingletonOf[T, U] represents an implicit value of type T narrowed to its
    * singleton type U.
    * This crazy trick is described here https://gist.github.com/milessabin/cadd73b7756fe4097ca0
    * and here https://meta.plasm.us/posts/2015/07/11/roll-your-own-scala/
    */
  case class SingletonOf[T, U <: { type DimensionIndex <: TypeLevelInt }](u: U)

  object SingletonOf {
    implicit def mkSingletonOf[T <: { type DimensionIndex <: TypeLevelInt }](implicit
      t: T
    ): SingletonOf[T, t.type] = SingletonOf(t)
  }
  implicit def isEarlierDimensionByDimensionOrderTC[
    D1,
    D2,
    DO1 <: { type DimensionIndex <: TypeLevelInt },
    DO2 <: { type DimensionIndex <: TypeLevelInt }
  ](
    implicit
    singletonOfDO1: SingletonOf[DimensionOrder[D1], DO1],
    singletonOfDO2: SingletonOf[DimensionOrder[D2], DO2],
    isEarlier: TypeLevelInt.LT[DO1#DimensionIndex, DO2#DimensionIndex]
  ) = new DimensionIsEarlierThan[D1, D2] {}
}
