package squants.unitgroups.si

import squants.electro._
import squants.energy._
import squants.mass.{AreaDensity, ChemicalAmount, Density, Mass}
import squants.motion._
import squants.{SiUnit, UnitOfMeasure}
import squants.photo._
import squants.radio._
import squants.space._
import squants.thermal.{Temperature, ThermalCapacity}
import squants.time.{Frequency, Time}
import squants.unitgroups.UnitGroup

/**
  * UnitGroups for SI.
  */
package object strict {

  object electro {
    object SiCapacticances extends UnitGroup[Capacitance] {
      val units: Set[UnitOfMeasure[Capacitance]] = Capacitance.units.collect { case si: SiUnit => si }
    }

    object SiConductivities extends UnitGroup[Conductivity] {
      val units: Set[UnitOfMeasure[Conductivity]] = Conductivity.units.collect { case si: SiUnit => si }
    }

    object SiElectricalConductances extends UnitGroup[ElectricalConductance] {
      val units: Set[UnitOfMeasure[ElectricalConductance]] = ElectricalConductance.units.collect { case si: SiUnit => si }
    }

    object SiElectricalResistances extends UnitGroup[ElectricalResistance] {
      val units: Set[UnitOfMeasure[ElectricalResistance]] = ElectricalResistance.units.collect { case si: SiUnit => si }
    }

    object SiElectricCharges extends UnitGroup[ElectricCharge] {
      val units: Set[UnitOfMeasure[ElectricCharge]] = ElectricCharge.units.collect { case si: SiUnit => si }
    }

    object SiElectricCurrents extends UnitGroup[ElectricCurrent] {
      val units: Set[UnitOfMeasure[ElectricCurrent]] = ElectricCurrent.units.collect { case si: SiUnit => si }
    }

    object SiElectricPotentials extends UnitGroup[ElectricPotential] {
      val units: Set[UnitOfMeasure[ElectricPotential]] = ElectricPotential.units.collect { case si: SiUnit => si }
    }

    object SiInductances extends UnitGroup[Inductance] {
      val units: Set[UnitOfMeasure[Inductance]] = Inductance.units.collect { case si: SiUnit => si }
    }

    object SiMagneticFluxes extends UnitGroup[MagneticFlux] {
      val units: Set[UnitOfMeasure[MagneticFlux]] = MagneticFlux.units.collect { case si: SiUnit => si }
    }

    object SiMagneticFluxDensities extends UnitGroup[MagneticFluxDensity] {
      val units: Set[UnitOfMeasure[MagneticFluxDensity]] = MagneticFluxDensity.units.collect { case si: SiUnit => si }
    }

    object SiResistivities extends UnitGroup[Resistivity] {
      val units: Set[UnitOfMeasure[Resistivity]] = Resistivity.units.collect { case si: SiUnit => si }
    }
  }

  object energy {
    object SiEnergies extends UnitGroup[Energy] {
      val units: Set[UnitOfMeasure[Energy]] = Energy.units.collect { case si: SiUnit => si }
    }

    object SiEnergyDensities extends UnitGroup[EnergyDensity] {
      val units: Set[UnitOfMeasure[EnergyDensity]] = EnergyDensity.units.collect { case si: SiUnit => si }
    }

    object SiPowers extends UnitGroup[Power] {
      val units: Set[UnitOfMeasure[Power]] = Power.units.collect { case si: SiUnit => si }
    }

    object SiPowerRamp extends UnitGroup[PowerRamp] {
      val units: Set[UnitOfMeasure[PowerRamp]] = PowerRamp.units.collect { case si: SiUnit => si }
    }
    object SiSpecificEnergies extends UnitGroup[SpecificEnergy] {
      val units: Set[UnitOfMeasure[SpecificEnergy]] = SpecificEnergy.units.collect { case si: SiUnit => si }
    }
  }

  object mass {
    object SiAreaDenisities extends UnitGroup[AreaDensity] {
      val units: Set[UnitOfMeasure[AreaDensity]] = AreaDensity.units.collect { case si: SiUnit => si }
    }


    object SiChemicalAmounts extends UnitGroup[ChemicalAmount] {
      val units: Set[UnitOfMeasure[ChemicalAmount]] = ChemicalAmount.units.collect { case si: SiUnit => si }
    }

    object SiDensities extends UnitGroup[Density] {
      val units: Set[UnitOfMeasure[Density]] = Density.units.collect { case si: SiUnit => si }
    }

    object SiMasses extends UnitGroup[Mass] {
      val units: Set[UnitOfMeasure[Mass]] = Mass.units.collect { case si: SiUnit => si }
    }
  }

  object motion {

    object SiAccelerations extends UnitGroup[Acceleration] {
      val units: Set[UnitOfMeasure[Acceleration]] = Acceleration.units.collect { case si: SiUnit => si }
    }

    object SiAngularVelocities extends UnitGroup[AngularVelocity] {
      val units: Set[UnitOfMeasure[AngularVelocity]] = AngularVelocity.units.collect { case si: SiUnit => si }
    }

    object SiForces extends UnitGroup[Force] {
      val units: Set[UnitOfMeasure[Force]] = Force.units.collect { case si: SiUnit => si }
    }

    object SiJerks extends UnitGroup[Jerk] {
      val units: Set[UnitOfMeasure[Jerk]] = Jerk.units.collect { case si: SiUnit => si }
    }

    object SiMassFlows extends UnitGroup[MassFlow] {
      val units: Set[UnitOfMeasure[MassFlow]] = MassFlow.units.collect { case si: SiUnit => si }
    }

    object SiMomentums extends UnitGroup[Momentum] {
      val units: Set[UnitOfMeasure[Momentum]] = Momentum.units.collect { case si: SiUnit => si }
    }

    object SiPressures extends UnitGroup[Pressure] {
      val units: Set[UnitOfMeasure[Pressure]] = Pressure.units.collect { case si: SiUnit => si }
    }

    object SiVelocities extends UnitGroup[Velocity] {
      val units: Set[UnitOfMeasure[Velocity]] = Velocity.units.collect { case si: SiUnit => si }
    }

    object SiVolumesFlows extends UnitGroup[VolumeFlow] {
      val units: Set[UnitOfMeasure[VolumeFlow]] = VolumeFlow.units.collect { case si: SiUnit => si }
    }

    object SiYanks extends UnitGroup[Yank] {
      val units: Set[UnitOfMeasure[Yank]] = Yank.units.collect { case si: SiUnit => si }
    }
  }

  object photo {
    object SiIlluminances extends UnitGroup[Illuminance] {
      val units: Set[UnitOfMeasure[Illuminance]] = Illuminance.units.collect { case si: SiUnit => si }
    }

    object SiLuminances extends UnitGroup[Luminance] {
      val units: Set[UnitOfMeasure[Luminance]] = Luminance.units.collect { case si: SiUnit => si }
    }

    object SiLuminousEnergies extends UnitGroup[LuminousEnergy] {
      val units: Set[UnitOfMeasure[LuminousEnergy]] = LuminousEnergy.units.collect { case si: SiUnit => si }
    }

    object SiLuminousExposures extends UnitGroup[LuminousExposure] {
      val units: Set[UnitOfMeasure[LuminousExposure]] = LuminousExposure.units.collect { case si: SiUnit => si }
    }

    object SiLuminousFluxes extends UnitGroup[LuminousFlux] {
      val units: Set[UnitOfMeasure[LuminousFlux]] = LuminousFlux.units.collect { case si: SiUnit => si }
    }

    object SiLuminousIntensities extends UnitGroup[LuminousIntensity] {
      val units: Set[UnitOfMeasure[LuminousIntensity]] = LuminousIntensity.units.collect { case si: SiUnit => si }
    }

  }

  object radio {
    object SiIrradiances extends UnitGroup[Irradiance] {
      val units: Set[UnitOfMeasure[Irradiance]] = Irradiance.units.collect { case si: SiUnit => si }
    }

    object SiRadiances extends UnitGroup[Radiance] {
      val units: Set[UnitOfMeasure[Radiance]] = Radiance.units.collect { case si: SiUnit => si }
    }

    object SiRadiantIntensities extends UnitGroup[RadiantIntensity] {
      val units: Set[UnitOfMeasure[RadiantIntensity]] = RadiantIntensity.units.collect { case si: SiUnit => si }
    }

    object SiSpectralIntensities extends UnitGroup[SpectralIntensity] {
      val units: Set[UnitOfMeasure[SpectralIntensity]] = SpectralIntensity.units.collect { case si: SiUnit => si }
    }

    object SiSpectralIrradiances extends UnitGroup[SpectralIrradiance] {
      val units: Set[UnitOfMeasure[SpectralIrradiance]] = SpectralIrradiance.units.collect { case si: SiUnit => si }
    }

    object SiSpectralPowers extends UnitGroup[SpectralPower] {
      val units: Set[UnitOfMeasure[SpectralPower]] = SpectralPower.units.collect { case si: SiUnit => si }
    }
  }

  object space {
    object SiAngles extends UnitGroup[Angle] {
      val units: Set[UnitOfMeasure[Angle]] = Angle.units.collect { case si: SiUnit => si }
    }

    object SiAreas extends UnitGroup[Area] {
      val units: Set[UnitOfMeasure[Area]] = Area.units.collect { case si: SiUnit => si }
    }

    object SiLengths extends UnitGroup[Length] {
      val units: Set[UnitOfMeasure[Length]] = Length.units.collect { case si: SiUnit => si }
    }

    object SiSolidAngles extends UnitGroup[SolidAngle] {
      val units: Set[UnitOfMeasure[SolidAngle]] = SolidAngle.units.collect { case si: SiUnit => si }
    }

    object SiVolumes extends UnitGroup[Volume] {
      val units: Set[UnitOfMeasure[Volume]] = Volume.units.collect { case si: SiUnit => si }
    }
  }

  object thermal {
    object SiTemperatures extends UnitGroup[Temperature] {
      val units: Set[UnitOfMeasure[Temperature]] = Temperature.units.collect { case si: SiUnit => si }
    }

    object SiThermalCapacities extends UnitGroup[ThermalCapacity] {
      val units: Set[UnitOfMeasure[ThermalCapacity]] = ThermalCapacity.units.collect { case si: SiUnit => si }
    }
  }

  object time {
    object SiFrequencies extends UnitGroup[Frequency] {
      val units: Set[UnitOfMeasure[Frequency]] = Frequency.units.collect { case si: SiUnit => si }
    }

    object SiTimes extends UnitGroup[Time] {
      val units: Set[UnitOfMeasure[Time]] = Time.units.collect { case si: SiUnit => si }
    }
  }


  import electro._
  import energy._
  import mass._
  import motion._
  import photo._
  import radio._
  import space._
  import thermal._
  import time._
}
