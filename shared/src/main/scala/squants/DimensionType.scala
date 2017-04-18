package squants

trait DimensionType {
  type Dimension <: HList
}

object DimensionType {
  type Aux[D] = DimensionType { type Dimension = D }

  case class SingletonOf[T, U <: { type Dimension <: HList }](u: U)
  object SingletonOf {
    implicit def mkSingleton[T <: { type Dimension <: HList }](implicit t: T):  SingletonOf[T, t.type] =
      SingletonOf[T, t.type](t)
  }
}