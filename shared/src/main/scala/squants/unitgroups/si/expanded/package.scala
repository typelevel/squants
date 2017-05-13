package squants.unitgroups.si

import squants.UnitOfMeasure
import squants.electro._
import squants.mass.{Mass, Tonnes}
import squants.space._
import squants.unitgroups.UnitGroup
import squants.unitgroups.si.strict.electro.SiElectricCharges
import squants.unitgroups.si.strict.mass.SiMasses
import squants.unitgroups.si.strict.space.{SiAreas, SiLengths, SiVolumes}

/**
  * Contains all of units in [[squants.unitgroups.si]] plus extra units defined by
  * [[http://www.bipm.org/en/publications/si-brochure/table6.html The BIPM SI Brochure, Section 4.1.]]
  */
package object expanded {

  object ExpandedSiAreas extends UnitGroup[Area] {
    val units: Set[UnitOfMeasure[Area]] = SiAreas.units + Hectares
  }

  object ExpandedSiElectricCharge extends UnitGroup[ElectricCharge] {
    val units: Set[UnitOfMeasure[ElectricCharge]] = SiElectricCharges.units ++
      Set(MilliampereSeconds, MilliampereHours, AmpereHours, Abcoulombs)
  }

  object ExpandedSiLengths extends UnitGroup[Length] {
    val units: Set[UnitOfMeasure[Length]] = SiLengths.units + AstronomicalUnits
  }

  object ExpandedSiMasses extends UnitGroup[Mass] {
    val units: Set[UnitOfMeasure[Mass]] = SiMasses.units + Tonnes
  }

  object ExpandedSiVolumes extends UnitGroup[Volume] {
    val units: Set[UnitOfMeasure[Volume]] = SiVolumes.units ++
      Set(Nanolitres, Microlitres, Millilitres, Centilitres, Litres, Hectolitres)
  }
}
