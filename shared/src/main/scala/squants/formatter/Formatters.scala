package squants.formatter

import squants.information.Information
import squants.mass._
import squants.space.Length
import squants.units.{AstronomicalLengthUnitGroup$, InformationIECUnitGroup$, InformationSIUnitGroup$, TroyUnitGroup$}

object Formatters {
  object AstroUnitFormatter extends DefaultFormatter[Length] {
    val units = AstronomicalLengthUnitGroup$
  }

  object TroyFormatter extends DefaultFormatter[Mass] {
    val units = TroyUnitGroup$
  }

  object InformationSIFormatter extends DefaultFormatter[Information] {
    val units = InformationSIUnitGroup$
  }

  object InformationIECFormatter extends DefaultFormatter[Information] {
    val units = InformationIECUnitGroup$
  }
}
