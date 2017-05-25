package squants.experimental.unitgroups

import scala.collection.immutable.{Set, SortedSet}

import squants.{Quantity, UnitOfMeasure}

/** A collection of related [[squants.UnitOfMeasure]] */
trait UnitGroup[A <: Quantity[A]] {

  def units: Set[UnitOfMeasure[A]]

  private[squants] implicit val uomOrdering: Ordering[UnitOfMeasure[A]] =
    new UomOrdering[A]

  lazy val sortedUnits: SortedSet[UnitOfMeasure[A]] =
    SortedSet[UnitOfMeasure[A]]() ++ units
}

/** [[scala.math.Ordering]] instance for [[UnitOfMeasure]][A] */
class UomOrdering[A <: Quantity[A]] extends Ordering[UnitOfMeasure[A]] {
  override def compare(x: UnitOfMeasure[A], y: UnitOfMeasure[A]): Int = {
    val siUnit = x(1).dimension.siUnit

    val xSI = x(1).to(siUnit)
    val ySI = y(1).to(siUnit)

    xSI.compare(ySI)
  }
}
