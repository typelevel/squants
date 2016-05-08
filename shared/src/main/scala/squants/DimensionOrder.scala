package squants

import squants.TypeLevelInt._

trait DimensionOrder[D] {
  type I <: Pos
}
object DimensionOrder {
  type Aux[D, Index] = DimensionOrder[D] { type I = Index }
  implicit val lengthIs1: Aux[Length, _1] = null
  implicit val massIs2: Aux[Mass, _2] = null
  implicit val timeIs3: Aux[Time, _3] = null
  implicit val energyIs4: Aux[Energy, _4] = null
}
