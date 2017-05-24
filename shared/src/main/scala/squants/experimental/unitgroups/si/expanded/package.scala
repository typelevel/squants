package squants.experimental.unitgroups.si

import squants.UnitOfMeasure
import squants.electro._
import squants.mass.{AreaDensity, KilogramsPerHectare, Mass, Tonnes}
import squants.motion.{KilometersPerHour, Velocity}
import squants.space._
import squants.time.{Days, Hours, Minutes, Time}
import squants.experimental.unitgroups.UnitGroup
import squants.experimental.unitgroups.si.strict.implicits.mkSiUnitGroup

/**
  * Contains all of units in [[squants.experimental.unitgroups.si.strict ]] plus extra units defined by
  * [[http://www.bipm.org/en/publications/si-brochure/table6.html The BIPM SI Brochure, Section 4.1.]]
  */
package object expanded {

  object electro {
    object ExpandedSiElectricCharge extends UnitGroup[ElectricCharge] {
      val units: Set[UnitOfMeasure[ElectricCharge]] = mkSiUnitGroup(ElectricCharge).units ++
        Set(MilliampereSeconds, MilliampereHours, AmpereHours, Abcoulombs)
    }
  }

  object mass {
    object ExpandedSiAreaDensities extends UnitGroup[AreaDensity] {
      val units: Set[UnitOfMeasure[AreaDensity]] = mkSiUnitGroup(AreaDensity).units + KilogramsPerHectare
    }

    object ExpandedSiMasses extends UnitGroup[Mass] {
      val units: Set[UnitOfMeasure[Mass]] = mkSiUnitGroup(Mass).units + Tonnes
    }
  }

  object motion {
    object ExpandedSiVelocities extends UnitGroup[Velocity] {
      val units: Set[UnitOfMeasure[Velocity]] = mkSiUnitGroup(Velocity).units + KilometersPerHour
    }
  }

  object space {
    object ExpandedSiAngles extends UnitGroup[Angle] {
      val units: Set[UnitOfMeasure[Angle]] = mkSiUnitGroup(Angle).units ++ Set(Degrees, Arcminutes)
    }

    object ExpandedSiAreas extends UnitGroup[Area] {
      val units: Set[UnitOfMeasure[Area]] = mkSiUnitGroup(Area).units + Hectares
    }

    object ExpandedSiLengths extends UnitGroup[Length] {
      val units: Set[UnitOfMeasure[Length]] = mkSiUnitGroup(Length).units + AstronomicalUnits
    }

    object ExpandedSiVolumes extends UnitGroup[Volume] {
      val units: Set[UnitOfMeasure[Volume]] = mkSiUnitGroup(Volume).units ++
        Set(Nanolitres, Microlitres, Millilitres, Centilitres, Litres, Hectolitres)
    }
  }

  object time {
    object ExpandedSiTimes extends UnitGroup[Time] {
      val units: Set[UnitOfMeasure[Time]] = mkSiUnitGroup(Time).units ++ Set(Minutes, Hours, Days)
    }
  }
}
