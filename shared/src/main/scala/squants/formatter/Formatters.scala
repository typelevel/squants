package squants.formatter

import squants.information.Information
import squants.mass._
import squants.space.Length
import squants.unitgroups.information.{IECInformation, MetricInformation}
import squants.unitgroups.mass.mass.TroyMasses
import squants.unitgroups.space.length.AstronomicalLengthUnitGroup

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
