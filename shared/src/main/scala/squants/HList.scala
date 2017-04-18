package squants

/**
  * `HList` ADT base trait.
  *
  * @author Miles Sabin
  */
sealed trait HList extends Product with Serializable

/**
  * Non-empty `HList` element type.
  *
  * @author Miles Sabin
  */
final case class ::[+H, +T <: HList](head : H, tail : T) extends HList {
  override def toString = head match {
    case _: ::[_, _] => "("+head+") :: "+tail.toString
    case _ => head+" :: "+tail.toString
  }
}

/**
  * Empty `HList` element type.
  *
  * @author Miles Sabin
  */
sealed trait HNil extends HList {
  def ::[H](h : H) = squants.::(h, this)
  override def toString = "HNil"
}

/**
  * Empty `HList` value.
  *
  * @author Miles Sabin
  */
case object HNil extends HNil
