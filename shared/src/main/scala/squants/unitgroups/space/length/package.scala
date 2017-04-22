package squants.unitgroups.space

import squants.{SiUnit, UnitOfMeasure}
import squants.space._
import squants.unitgroups.UnitGroup

/**
  * UnitGroups for [[Length]]
  */
package object length {

  /** Only contains UnitOfMeasure[Length] that are part of SI. */
  object SiLengths extends UnitGroup[Length] {
    val units: Set[UnitOfMeasure[Length]] = Length.units.collect { case si: SiUnit => si }
  }

  /**
    * Contains all of [[SiLengths]] plus extra length units defined by
    * [[http://www.bipm.org/en/publications/si-brochure/table6.html The BIPM SI Brochure, Section 4.1.]]
    */
  object ExpandedSiLengths extends UnitGroup[Length] {
    val units: Set[UnitOfMeasure[Length]] = SiLengths.units + AstronomicalUnits
  }

  /** U.S. Customary units for length */
  object UsCustomaryLengths extends UnitGroup[Length] {
    val units: Set[UnitOfMeasure[Length]] = Set(Inches, Feet, Yards, UsMiles)
  }

  object AstronomicalLengthUnitGroup extends UnitGroup[Length] {
    val units: Set[UnitOfMeasure[Length]] = Set(AstronomicalUnits, LightYears, Parsecs)
  }
}
