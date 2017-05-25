package squants.experimental.unitgroups

import squants.UnitOfMeasure
import squants.mass._
import squants.space.{AstronomicalUnits, Length, LightYears, Parsecs}

/**
  * Miscellaneous [[UnitGroup]]s
  */
package object misc {

  /** U.K. Customary units for mass */
  object UkCustomaryMasses extends UnitGroup[Mass] {
    val units: Set[UnitOfMeasure[Mass]] = Set(Ounces, Pounds, Stone, Kilopounds, Megapounds)
  }

  /** Mass units in the Troy system */
  object TroyMasses extends UnitGroup[Mass] {
    val units: Set[UnitOfMeasure[Mass]] = Set(Pennyweights, TroyGrains, TroyOunces, TroyPounds)
  }

  object AstronomicalLengthUnitGroup extends UnitGroup[Length] {
    val units: Set[UnitOfMeasure[Length]] = Set(AstronomicalUnits, LightYears, Parsecs)
  }
}
