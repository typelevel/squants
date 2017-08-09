package squants.experimental.unitgroups

import squants.Dimension
import squants.electro._
import squants.energy._
import squants.information.{DataRate, Information}
import squants.mass._
import squants.motion._
import squants.photo._
import squants.radio._
import squants.space._
import squants.thermal.{Temperature, ThermalCapacity}
import squants.time.{Frequency, Time}

/**
  * Puts [[Dimension]] objects into implicit scope
  * so that implicit [[UnitGroup]] machinery works.
  */
object ImplicitDimensions {

  object electro {
    implicit val implicitCapacitance: Dimension[Capacitance] = Capacitance
    implicit val implicitConductivity: Dimension[Conductivity] = Conductivity
    implicit val implicitElectricalConductance: Dimension[ElectricalConductance] = ElectricalConductance
    implicit val implicitElectricalResistance: Dimension[ElectricalResistance] = ElectricalResistance
    implicit val implicitElectricCharge: Dimension[ElectricCharge] = ElectricCharge
    implicit val implicitElectricFieldStrength: Dimension[ElectricFieldStrength] = ElectricFieldStrength
    implicit val implicitElectricChargeDensity: Dimension[ElectricChargeDensity] = ElectricChargeDensity
    implicit val implicitElectricChargeMassRatio: Dimension[ElectricChargeMassRatio] = ElectricChargeMassRatio
    implicit val implicitElectricPotential: Dimension[ElectricPotential] = ElectricPotential
    implicit val implicitInductance: Dimension[Inductance] = Inductance
    implicit val implicitLinearElectricChargeDensity: Dimension[LinearElectricChargeDensity] = LinearElectricChargeDensity
    implicit val implicitMagneticFlux: Dimension[MagneticFlux] = MagneticFlux
    implicit val implicitMagneticFluxDensity: Dimension[MagneticFluxDensity] = MagneticFluxDensity
    implicit val implicitResistivity: Dimension[Resistivity] = Resistivity
    implicit val implicitPermittivity: Dimension[Permittivity] = Permittivity
  }

  object energy {
    implicit val implicitEnergy: Dimension[Energy] = Energy
    implicit val implicitEnergyDensity: Dimension[EnergyDensity] = EnergyDensity
    implicit val implicitMolarEnergy: Dimension[MolarEnergy] = MolarEnergy
    implicit val implicitPower: Dimension[Power] = Power
    implicit val implicitPowerDensity: Dimension[PowerDensity] = PowerDensity
    implicit val implicitPowerRamp: Dimension[PowerRamp] = PowerRamp
    implicit val implicitSpecificEnergy: Dimension[SpecificEnergy] = SpecificEnergy
  }

  object information {
    implicit val implicitDataRate: Dimension[DataRate] = DataRate
    implicit val implicitInformation: Dimension[Information] = Information
  }

  object mass {
    implicit val implicitAreaDensity: Dimension[AreaDensity] = AreaDensity
    implicit val implicitChemicalAmount: Dimension[ChemicalAmount] = ChemicalAmount
    implicit val implicitDensity: Dimension[Density] = Density
    implicit val implicitMass: Dimension[Mass] = Mass
    implicit val implicitMomentOfInertia: Dimension[MomentOfInertia] = MomentOfInertia
  }

  object motion {
    implicit val implicitAcceleration: Dimension[Acceleration] = Acceleration
    implicit val implicitAngularAcceleration: Dimension[AngularAcceleration] = AngularAcceleration
    implicit val implicitAngularVelocity: Dimension[AngularVelocity] = AngularVelocity
    implicit val implicitForce: Dimension[Force] = Force
    implicit val implicitJerk: Dimension[Jerk] = Jerk
    implicit val implicitMassFlow: Dimension[MassFlow] = MassFlow
    implicit val implicitMomentum: Dimension[Momentum] = Momentum
    implicit val implicitPressure: Dimension[Pressure] = Pressure
    implicit val implicitPressureChange: Dimension[PressureChange] = PressureChange
    implicit val implicitTorque: Dimension[Torque] = Torque
    implicit val implicitVelocity: Dimension[Velocity] = Velocity
    implicit val implicitVolumeFlow: Dimension[VolumeFlow] = VolumeFlow
    implicit val implicitYank: Dimension[Yank] = Yank
  }

  object photo {
    implicit val implicitIlluminance: Dimension[Illuminance] = Illuminance
    implicit val implicitLuminance: Dimension[Luminance] = Luminance
    implicit val implicitLuminousEnergy: Dimension[LuminousEnergy] = LuminousEnergy
    implicit val implicitLuminousExpoure: Dimension[LuminousExposure] = LuminousExposure
    implicit val implicitLuminousFlux: Dimension[LuminousFlux] = LuminousFlux
    implicit val implicitLuminousIntensity: Dimension[LuminousIntensity] = LuminousIntensity
  }

  object radio {
    implicit val implicitIrradiance: Dimension[Irradiance] = Irradiance
    implicit val implicitRadiance: Dimension[Radiance] = Radiance
    implicit val implicitRadiantIntensity: Dimension[RadiantIntensity] = RadiantIntensity
    implicit val implicitSpectralIntensity: Dimension[SpectralIntensity] = SpectralIntensity
    implicit val implicitSpectralIrradiance: Dimension[SpectralIrradiance] = SpectralIrradiance
    implicit val implicitSpectralPower: Dimension[SpectralPower] = SpectralPower
  }

  object space {
    implicit val implicitAngle: Dimension[Angle] = Angle
    implicit val implicitArea: Dimension[Area] = Area
    implicit val implicitLength: Dimension[Length] = Length
    implicit val implicitSolidAngle: Dimension[SolidAngle] = SolidAngle
    implicit val implicitVolume: Dimension[Volume] = Volume
  }

  object thermal {
    implicit val implicitTemperature: Dimension[Temperature] = Temperature
    implicit val implicitThermalCapacity: Dimension[ThermalCapacity] = ThermalCapacity
  }

  object time {
    implicit val implicitFrequency: Dimension[Frequency] = Frequency
    implicit val implicitTime: Dimension[Time] = Time
  }

}
