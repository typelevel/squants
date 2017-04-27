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
    val units = AstronomicalLengthUnitGroup
  }

  object TroyFormatter extends DefaultFormatter[Mass] {
    val units = TroyMasses
  }

  object InformationMetricFormatter extends DefaultFormatter[Information] {
    val units = MetricInformation
  }

  object InformationIECFormatter extends DefaultFormatter[Information] {
    val units = IECInformation
  }
}

object Implicits {
  implicit def implicitFormatter[A <: Quantity[A]](implicit unitGroup: UnitGroup[A]): Formatter[A] = {
    new DefaultFormatter[A] { val units: UnitGroup[A] = unitGroup }
  }

  implicit class EnhancedQuantity[A <: Quantity[A]](quantity: Quantity[A]) {
    def inBestUnit(implicit formatter: Formatter[A]): Quantity[A] = {
      formatter.bestUnit(quantity)
    }
  }
}
