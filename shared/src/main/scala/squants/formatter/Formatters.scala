package squants.formatter

import squants.information.Information
import squants.mass._
import squants.space.Length
import squants.units.{AstronomicalLengthUnits, InformationIECUnits, InformationSIUnits, TroyUnits}

object Formatters {
  object AstroUnitFormatter extends DefaultFormatter[Length] {
    val units = AstronomicalLengthUnits
  }

  object TroyFormatter extends DefaultFormatter[Mass] {
    val units = TroyUnits
  }

  object InformationSIFormatter extends DefaultFormatter[Information] {
    val units = InformationSIUnits
  }

  object InformationIECFormatter extends DefaultFormatter[Information] {
    val units = InformationIECUnits
  }
}
