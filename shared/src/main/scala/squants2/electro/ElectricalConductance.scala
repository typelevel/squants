/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.electro

import squants2._

final case class ElectricalConductance[A: Numeric] private[squants2] (value: A, unit: ElectricalConductanceUnit)
  extends Quantity[A, ElectricalConductance] {

  // BEGIN CUSTOM OPS

  //  def /[B](that: Length[B])(implicit f: B => A): Conductivity[A] = ???
  //  def /[B](that: Conductivity[B])(implicit f: B => A): Length[A] = ???
  //  def inOhms[B]()(implicit f: B => A): ElectricalResistance[A] = ???
  // END CUSTOM OPS

  def toSiemens[B: Numeric](implicit f: A => B): B = toNum[B](Siemens)
}

object ElectricalConductance extends Dimension[ElectricalConductance]("Electrical Conductance") {

  override def primaryUnit: UnitOfMeasure[ElectricalConductance] with PrimaryUnit[ElectricalConductance] = Siemens
  override def siUnit: UnitOfMeasure[ElectricalConductance] with SiUnit[ElectricalConductance] = Siemens
  override lazy val units: Set[UnitOfMeasure[ElectricalConductance]] = 
    Set(Siemens)

  implicit class ElectricalConductanceCons[A](a: A)(implicit num: Numeric[A]) {
    def siemens: ElectricalConductance[A] = Siemens(a)
  }

  lazy val siemens: ElectricalConductance[Int] = Siemens(1)

}

abstract class ElectricalConductanceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricalConductance] {
  override def dimension: Dimension[ElectricalConductance] = ElectricalConductance
  override def apply[A: Numeric](value: A): ElectricalConductance[A] = ElectricalConductance(value, this)
}

case object Siemens extends ElectricalConductanceUnit("S", 1) with PrimaryUnit[ElectricalConductance] with SiUnit[ElectricalConductance]
