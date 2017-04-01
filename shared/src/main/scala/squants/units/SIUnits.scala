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

  object SIAreas extends Units[Area] {
    val units: Set[UnitOfMeasure[Area]] = Set(SquareCentimeters, SquareMeters, SquareKilometers)
  }

  object SICapacticances extends Units[Capacitance] {
    val units: Set[UnitOfMeasure[Capacitance]] = Set(Picofarads, Nanofarads, Microfarads,
      Millifarads, Farads, Kilofarads)
  }

  object SIElectricCharges extends Units[ElectricCharge] {
    val units: Set[UnitOfMeasure[ElectricCharge]] = Set(Picocoulombs, Nanocoulombs, Microcoulombs,
      Millicoulombs, Coulombs, Abcoulombs) // xxx: is Abcoulombs part of SI or cgs-emu ?
  }

  object SILengths extends Units[Length] {
    val units: Set[UnitOfMeasure[Length]] = Set(Millimeters, Centimeters, Meters, Kilometers)
  }

  object SIMasses extends Units[Mass] {
    val units: Set[UnitOfMeasure[Mass]] = Set(Micrograms, Milligrams, Grams, Kilograms) // XXX: Include Tonne ?
  }

  object SIVolumes extends Units[Volume] {
    val units: Set[UnitOfMeasure[Volume]] = Set(Nanolitres, Microlitres, Millilitres, Centilitres,
      Decilitres, Litres, CubicMeters)
  }

  object SIVelocities extends Units[Velocity] {
    val units: Set[UnitOfMeasure[Velocity]] = Set(MetersPerSecond, KilometersPerSecond, KilometersPerHour)
  }

}
