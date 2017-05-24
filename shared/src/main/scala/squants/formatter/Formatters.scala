package squants.formatter

import squants.Quantity
import squants.information.Information
import squants.mass._
import squants.space.Length
import squants.unitgroups.UnitGroup
import squants.unitgroups.information.{IECInformation, MetricInformation}
import squants.unitgroups.misc.{AstronomicalLengthUnitGroup, TroyMasses}

object Formatters {
  object AstroUnitFormatter extends DefaultFormatter[Length] {
    val unitGroup = AstronomicalLengthUnitGroup
  }

  object TroyFormatter extends DefaultFormatter[Mass] {
    val unitGroup = TroyMasses
  }

  object InformationMetricFormatter extends DefaultFormatter[Information] {
    val unitGroup = MetricInformation
  }

  object InformationIECFormatter extends DefaultFormatter[Information] {
    val unitGroup = IECInformation
  }
}

object implicits {
  implicit def implicitFormatter[A <: Quantity[A]](implicit implicitUnitGroup: UnitGroup[A]): Formatter[A] = {
    new DefaultFormatter[A] { val unitGroup: UnitGroup[A] = implicitUnitGroup }
  }
}

object syntax {
  implicit class FormattedQuantity[A <: Quantity[A]](quantity: Quantity[A]) {
    def inBestUnit(implicit formatter: Formatter[A]): Quantity[A] = {
      formatter.inBestUnit(quantity)
    }
  }
}
