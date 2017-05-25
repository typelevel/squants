package squants.experimental.formatter

import squants.Quantity
import squants.information.Information
import squants.mass._
import squants.space.Length
import squants.experimental.unitgroups.UnitGroup
import squants.experimental.unitgroups.information.{IECInformation, MetricInformation}
import squants.experimental.unitgroups.misc.{AstronomicalLengthUnitGroup, TroyMasses}

object Formatters {
  val AstroUnitFormatter: DefaultFormatter[Length] = new DefaultFormatter(AstronomicalLengthUnitGroup)

  val TroyFormatter: DefaultFormatter[Mass] = new DefaultFormatter(TroyMasses)

  val InformationMetricFormatter: DefaultFormatter[Information] =  new DefaultFormatter(MetricInformation)

  val InformationIECFormatter: DefaultFormatter[Information] = new DefaultFormatter(IECInformation)
}

object implicits {
  implicit def implicitFormatter[A <: Quantity[A]](implicit implicitUnitGroup: UnitGroup[A]): Formatter[A] = {
    new DefaultFormatter[A](implicitUnitGroup)
  }
}

object syntax {
  implicit class FormattedQuantity[A <: Quantity[A]](quantity: Quantity[A]) {
    def inBestUnit(implicit formatter: Formatter[A]): Quantity[A] = {
      formatter.inBestUnit(quantity)
    }
  }
}
