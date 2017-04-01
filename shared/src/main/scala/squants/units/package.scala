package squants

import squants.information._
import squants.mass.{Pennyweights, TroyGrains, TroyOunces, TroyPounds}
import squants.space.{AstronomicalUnits, LightYears, Parsecs}

package object units {

  object TroyUnits extends Units[Mass] {
    val units: Set[UnitOfMeasure[Mass]] = Set(Pennyweights, TroyGrains, TroyOunces, TroyPounds)
  }

  object InformationIECUnits extends Units[Information] {
    val units: Set[UnitOfMeasure[Information]] =
      Set(Bytes, Kibibytes, Mebibytes, Gibibytes,
        Tebibytes, Pebibytes, Exbibytes, Zebibytes, Yobibytes)
  }

  object InformationSIUnits extends Units[Information] {
    val units: Set[UnitOfMeasure[Information]] =
      Set(Bytes, Kilobytes, Megabytes, Gigabytes,
        Terabytes, Petabytes, Exabytes, Zettabytes, Yottabytes)
  }

  object AstronomicalLengthUnits extends Units[Length] {
    val units: Set[UnitOfMeasure[Length]] = Set(AstronomicalUnits, LightYears, Parsecs)
  }
}
