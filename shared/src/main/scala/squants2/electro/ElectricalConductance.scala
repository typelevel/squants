/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2022, Gary Keorkunian, et al                                **
**                                                                      **
\*                                                                      */

package squants2.electro

import squants2._
import scala.math.Numeric.Implicits.infixNumericOps

final case class ElectricalConductance[A: Numeric] private [squants2]  (value: A, unit: ElectricalConductanceUnit)
  extends Quantity[A, ElectricalConductance.type] {
  override type Q[B] = ElectricalConductance[B]

  def toSiemens: A = to(Siemens)
}

object ElectricalConductance extends Dimension("ElectricalConductance") {

  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = Siemens
  override def siUnit: UnitOfMeasure[this.type] with SiUnit = Siemens
  override lazy val units: Set[UnitOfMeasure[this.type]] = 
    Set(Siemens)

  implicit class ElectricalConductanceCons[A](a: A)(implicit num: Numeric[A]) {
    def siemens: ElectricalConductance[A] = Siemens(a)
  }

  lazy val siemens: ElectricalConductance[Int] = Siemens(1)

  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = ElectricalConductanceNumeric[A]()
  private case class ElectricalConductanceNumeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {
    override def times(x: Quantity[A, ElectricalConductance.type], y: Quantity[A, ElectricalConductance.type]): Quantity[A, ElectricalConductance.this.type] =
      Siemens(x.to(Siemens) * y.to(Siemens))
  }
}

abstract class ElectricalConductanceUnit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[ElectricalConductance.type] {
  override lazy val dimension: ElectricalConductance.type = ElectricalConductance
  override def apply[A: Numeric](value: A): ElectricalConductance[A] = ElectricalConductance(value, this)
}

case object Siemens extends ElectricalConductanceUnit("S", 1.0) with PrimaryUnit with SiUnit
