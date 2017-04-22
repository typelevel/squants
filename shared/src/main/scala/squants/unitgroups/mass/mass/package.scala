package squants.unitgroups.mass

import squants.{SiUnit, UnitOfMeasure}
import squants.mass._
import squants.unitgroups.UnitGroup

/**
* UnitGroups for [[Mass]]
*/
package object mass {

  /** Only contains UnitOfMeasure[Mass] that are part of SI. */
  object SiMasses extends UnitGroup[Mass] {
    val units: Set[UnitOfMeasure[Mass]] = Mass.units.collect { case si: SiUnit => si }
  }

  /**
    * Contains all of [[SiMasses]] plus extra mass units defined by
    * [[http://www.bipm.org/en/publications/si-brochure/table6.html The BIPM SI Brochure, Section 4.1.]]
    */
  object ExpandedSiMasses extends UnitGroup[Mass] {
    val units: Set[UnitOfMeasure[Mass]] = SiMasses.units + Tonnes
  }

  /** U.S. Customary units for mass */
  object UsCustomaryMasses extends UnitGroup[Mass] {
    val units: Set[UnitOfMeasure[Mass]] = Set(Ounces, Pounds, Kilopounds, Megapounds)
  }

  /** U.K. Customary units for mass */
  object UkCustomaryMasses extends UnitGroup[Mass] {
    val units: Set[UnitOfMeasure[Mass]] = Set(Ounces, Pounds, Stone, Kilopounds, Megapounds)
  }

  /** Mass units in the Troy system */
  object TroyMasses extends UnitGroup[Mass] {
    val units: Set[UnitOfMeasure[Mass]] = Set(Pennyweights, TroyGrains, TroyOunces, TroyPounds)
  }
}
