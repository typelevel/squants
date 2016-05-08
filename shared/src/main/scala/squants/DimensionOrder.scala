package squants

import squants.TypeLevelInt._

trait DimensionOrder[D] {
  type I <: Pos
}
object DimensionOrder {
  type Aux[D, Index] = DimensionOrder[D] { type I = Index }
  implicit val lengthIs1: DimensionOrder[Length] { type I = _1 } = null
  implicit val massIs2: Dimension[Mass] { type I = _2 } = null
  implicit val timeIs3: Dimension[Time] { type I = _3 } = null
  implicit val energyIs4: Dimension[Energy] { type I = _4 } = null
}
