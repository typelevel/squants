package squants.units

import squants.electro._
import squants.mass._
import squants.motion.{KilometersPerHour, KilometersPerSecond, MetersPerSecond, Velocity}
import squants.{Dimension, UnitOfMeasure}
import squants.space._

/**
  * SI units
  */
object SIUnits {

  object SIAreas extends UnitGroup[Area] {
    val units: Set[UnitOfMeasure[Area]] = Set(SquareCentimeters, SquareMeters, SquareKilometers)
  }

  object SICapacticances extends UnitGroup[Capacitance] {
    val units: Set[UnitOfMeasure[Capacitance]] = Set(Picofarads, Nanofarads, Microfarads,
      Millifarads, Farads, Kilofarads)
  }

  object SIElectricCharges extends UnitGroup[ElectricCharge] {
    val units: Set[UnitOfMeasure[ElectricCharge]] = Set(Picocoulombs, Nanocoulombs, Microcoulombs,
      Millicoulombs, Coulombs, Abcoulombs) // xxx: is Abcoulombs part of SI or cgs-emu ?
  }

  object SILengths extends UnitGroup[Length] {
    val units: Set[UnitOfMeasure[Length]] = Set(Millimeters, Centimeters, Meters, Kilometers)
  }

  object SIMasses extends UnitGroup[Mass] {
    val units: Set[UnitOfMeasure[Mass]] = Set(Micrograms, Milligrams, Grams, Kilograms) // XXX: Include Tonne ?
  }

  object SIVolumes extends UnitGroup[Volume] {
    val units: Set[UnitOfMeasure[Volume]] = Set(Nanolitres, Microlitres, Millilitres, Centilitres,
      Decilitres, Litres, CubicMeters)
  }

  object SIVelocities extends UnitGroup[Velocity] {
    val units: Set[UnitOfMeasure[Velocity]] = Set(MetersPerSecond, KilometersPerSecond, KilometersPerHour)
  }

}
