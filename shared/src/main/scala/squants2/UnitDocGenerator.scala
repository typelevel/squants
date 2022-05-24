package squants2

import squants2.electro._
import squants2.energy._
import squants2.information._
import squants2.mass._
import squants2.motion._
import squants2.photo._
import squants2.radio._
import squants2.space._
import squants2.thermal._
import squants2.time._

import java.io.{ File, PrintWriter }

object UnitDocGenerator extends App {

  val allDimensions = Set[Dimension](
    ElectricCurrent, Mass, ChemicalAmount, LuminousIntensity, Length, Temperature, Time,

    AreaElectricChargeDensity, Capacitance, Conductivity, ElectricalConductance, ElectricalResistance,
    ElectricCharge, ElectricChargeDensity, ElectricChargeMassRatio, ElectricCurrentDensity,
    ElectricFieldStrength, ElectricPotential, Inductance, LinearElectricChargeDensity,
    MagneticFlux, MagneticFluxDensity, Permeability, Permittivity, Resistivity,

    Energy, EnergyDensity, MolarEnergy, Power, PowerDensity, PowerRamp, SpecificEnergy,

    DataRate, Information,

    AreaDensity, Density, MomentOfInertia,

    Acceleration, AngularAcceleration, AngularVelocity, Force, Jerk,
    MassFlow, Momentum, Pressure, PressureChange, Torque, VolumeFlow, Yank,

    Illuminance, Luminance, LuminousEnergy, LuminousExposure, LuminousFlux,

    Activity, AreaTime, Dose, Irradiance, ParticleFlux, Radiance, RadiantIntensity,
    SpectralIntensity, SpectralIrradiance, SpectralPower,

    Angle, Area, SolidAngle, Volume,

    ThermalCapacity,

    Frequency,

    Dimensionless
  )

  val filePrinter = new PrintWriter(new File("UNITS2.md"))
  printAllDimensions(filePrinter)
  filePrinter.close()


  def printAllDimensions(printer: PrintWriter): Unit = {

    printer.println("# Squants - Supported Dimensions and Units")

    printer.println("## Index")
    printer.println("")
    printer.println("|Package|Dimensions (# of Units)|")
    printer.println("|----------------------------|-----------------------------------------------------------|")
    allDimensions.groupBy(d => d.getClass.getPackage.getName)
      .toList.sortBy(_._1)
      .foreach { case (p, ds) =>
        val dims = ds.toList.sortBy(_.name)
        printer.println(s"|$p|${dims.map(d => s"[${d.name}](#${d.name.toLowerCase.replace(" ", "")}) (${d.units.size})").mkString(", ")}|")
      }

    printer.println(s"#### Dimension Count: ${allDimensions.size}")
    printer.println(s"#### Unit Count: ${allDimensions.map(_.units.size).sum}")

    allDimensions.toList.sortBy(! _.isSiBase).foreach { d =>
      printer.println("")
      d match {
        case bd: BaseDimension =>
          printer.println(s"## ${d.name.replace(" ", "")}")
          printer.println(s"##### Dimensional Symbol:  ${bd.dimensionSymbol}")
          printer.println(s"##### SI Base Unit: ${d.siUnit.getClass.getSimpleName.replace("$", "")} (symbol: ${d.siUnit.symbol})")
          printer.println(s"##### Primary Unit: ${d.primaryUnit.getClass.getSimpleName.replace("$", "")} (symbol: ${d.primaryUnit.symbol})")

        case _ =>
          printer.println(s"## ${d.name}")
          printer.println(s"#### SI Unit: ${d.siUnit.getClass.getSimpleName.replace("$", "")} (symbol: ${d.siUnit.symbol})")
          printer.println(s"#### Primary Unit: ${d.primaryUnit.getClass.getSimpleName.replace("$", "")} (symbol: ${d.primaryUnit.symbol})")
      }
      printer.println("|Unit|Conversion Factor|")
      printer.println("|----------------------------|-----------------------------------------------------------|")
      d.units.filterNot(_ eq d.primaryUnit).toList.sortBy(u => u.conversionFactor).foreach { u =>
        val cf = s"1 ${u.symbol} = ${u.conversionFactor} ${d.primaryUnit.symbol}"
        printer.println(s"|${u.getClass.getSimpleName.replace("$", "")}| $cf|")
      }
      printer.println("")
      printer.println(s"[Go to Code](shared/src/main/scala/${d.getClass.getPackage.getName.replace(".", "/")}/${d.getClass.getSimpleName.replace("$", "")}.scala)")
    }
  }
}
