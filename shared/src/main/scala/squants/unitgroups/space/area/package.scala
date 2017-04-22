package squants.unitgroups.space

import squants.{SiUnit, UnitOfMeasure}
import squants.space._
import squants.unitgroups.UnitGroup

/**
  * UnitGroups for [[Area]].
  */
package object area {

  /** Only contains UnitOfMeasure[Area] that are part of SI. */
  object SiAreas extends UnitGroup[Area] {
    val units: Set[UnitOfMeasure[Area]] = Area.units.collect { case si: SiUnit => si }
  }

  /**
    * Contains all of [[SiAreas]] plus extra area units defined by
    * [[http://www.bipm.org/en/publications/si-brochure/table6.html The BIPM SI Brochure, Section 4.1.]]
    */
  object ExpandedSiAreas extends UnitGroup[Area] {
    val units: Set[UnitOfMeasure[Area]] = SiAreas.units + Hectares
  }

  /** U.S. Customary units for area. */
  object UsCustomaryAreas extends UnitGroup[Area] {
    val units: Set[UnitOfMeasure[Area]] = Set(Acres, SquareFeet, SquareInches, SquareYards, SquareUsMiles)
  }
}
