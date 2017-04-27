package squants.unitgroups.si

import squants.electro._
import squants.mass.Mass
import squants.motion.Velocity
import squants.{SiUnit, UnitOfMeasure}
import squants.space._
import squants.unitgroups.UnitGroup

/**
  * UnitGroups for SI.
  */
package object strict {

  object SiAreas extends UnitGroup[Area] {
    val units: Set[UnitOfMeasure[Area]] = Area.units.collect { case si: SiUnit => si }
  }

  object SiCapacticances extends UnitGroup[Capacitance] {
    val units: Set[UnitOfMeasure[Capacitance]] = Capacitance.units.collect { case si: SiUnit => si }
  }

  object SiElectricalResistance extends UnitGroup[ElectricalResistance] {
    val units: Set[UnitOfMeasure[ElectricalResistance]] = ElectricalResistance.units.collect { case si: SiUnit => si }
  }

  object SiElectricCharges extends UnitGroup[ElectricCharge] {
    val units: Set[UnitOfMeasure[ElectricCharge]] = ElectricCharge.units.collect { case si: SiUnit => si }
  }

  object SiElectricCurrent extends UnitGroup[ElectricCurrent] {
    val units: Set[UnitOfMeasure[ElectricCurrent]] = ElectricCurrent.units.collect { case si: SiUnit => si }
  }

  object SiElectricPotential extends UnitGroup[ElectricPotential] {
    val units: Set[UnitOfMeasure[ElectricPotential]] = ElectricPotential.units.collect { case si: SiUnit => si }
  }

  object SiInductance extends UnitGroup[Inductance] {
    val units: Set[UnitOfMeasure[Inductance]] = Inductance.units.collect { case si: SiUnit => si }
  }

  object SiLengths extends UnitGroup[Length] {
    val units: Set[UnitOfMeasure[Length]] = Length.units.collect { case si: SiUnit => si }
  }

  object SiMasses extends UnitGroup[Mass] {
    val units: Set[UnitOfMeasure[Mass]] = Mass.units.collect { case si: SiUnit => si }
  }

  object SiVelocities extends UnitGroup[Velocity] {
    val units: Set[UnitOfMeasure[Velocity]] = Velocity.units.collect { case si: SiUnit => si }
  }

  object SiVolumes extends UnitGroup[Volume] {
    val units: Set[UnitOfMeasure[Volume]] = Volume.units.collect { case si: SiUnit => si }
  }
}
