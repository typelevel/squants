package squants.units

import squants.electro._
import squants.mass._
import squants.motion.Velocity
import squants.{UnitOfMeasure, SiUnit}
import squants.space._

/**
  * SI units
  */
object SiUnitGroups {

  object SiAreas extends UnitGroup[Area] {
    val units: Set[UnitOfMeasure[Area]] = Area.units.collect { case si: SiUnit => si }
  }

  object SiCapacticances extends UnitGroup[Capacitance] {
    val units: Set[UnitOfMeasure[Capacitance]] = Set(Picofarads, Nanofarads, Microfarads,
      Millifarads, Farads, Kilofarads)
  }

  object SiElectricCharges extends UnitGroup[ElectricCharge] {
    val units: Set[UnitOfMeasure[ElectricCharge]] = Set(Picocoulombs, Nanocoulombs, Microcoulombs,
      Millicoulombs, Coulombs, Abcoulombs) // xxx: is Abcoulombs part of SI or cgs-emu ?
  }

  object SiLengths extends UnitGroup[Length] {
    val units: Set[UnitOfMeasure[Length]] = Length.units.collect { case si: SiUnit => si }
  }

  object SiMasses extends UnitGroup[Mass] {
    val units: Set[UnitOfMeasure[Mass]] = Mass.units.collect { case si: SiUnit => si }
  }

  object SiVolumes extends UnitGroup[Volume] {
    val units: Set[UnitOfMeasure[Volume]] = Set(Nanolitres, Microlitres, Millilitres, Centilitres,
      Decilitres, Litres, CubicMeters)
  }

  object SiVelocities extends UnitGroup[Velocity] {
    val units: Set[UnitOfMeasure[Velocity]] = Velocity.units.collect { case si: SiUnit => si }
  }

}
