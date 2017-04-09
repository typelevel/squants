package squants

import squants.information._
import squants.mass.{Pennyweights, TroyGrains, TroyOunces, TroyPounds}
import squants.space.{AstronomicalUnits, LightYears, Parsecs}

package object units {

  object TroyUnitGroup extends UnitGroup[Mass] {
    val units: Set[UnitOfMeasure[Mass]] = Set(Pennyweights, TroyGrains, TroyOunces, TroyPounds)
  }

  object InformationIECUnitGroup extends UnitGroup[Information] {
    val units: Set[UnitOfMeasure[Information]] =
      Set(Bytes, Kibibytes, Mebibytes, Gibibytes,
        Tebibytes, Pebibytes, Exbibytes, Zebibytes, Yobibytes)
  }

  object InformationSIUnitGroup extends UnitGroup[Information] {
    val units: Set[UnitOfMeasure[Information]] =
      Set(Bytes, Kilobytes, Megabytes, Gigabytes,
        Terabytes, Petabytes, Exabytes, Zettabytes, Yottabytes)
  }

  object AstronomicalLengthUnitGroup extends UnitGroup[Length] {
    val units: Set[UnitOfMeasure[Length]] = Set(AstronomicalUnits, LightYears, Parsecs)
  }
}
