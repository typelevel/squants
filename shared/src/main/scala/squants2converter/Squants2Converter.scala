package squants2converter

import squants._
import squants.electro._
import squants.energy._
import squants.information._
import squants.mass._
import squants.motion._
import squants.photo._
import squants.radio._
import squants.space._
import squants.thermal._
import squants.time._

import java.io.{ File, FileWriter, PrintWriter }
import java.nio.file.{ Files, Path }

object Squants2Converter extends App {

  val allDimensions = Set[Dimension[_]](
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

  writeDimensionFile(Frequency)

  def writeUnitsDoc(): Unit = {
    val file = new FileWriter("UNITS.md")
    printAllDimensions(new PrintWriter(file))
    file.close()
  }

  def printAllDimensions(printer: PrintWriter): Unit = {

    printer.println("# Squants - Supported Dimensions and Units")

    printer.println("## Index")
    printer.println("")
    printer.println("|Package|Dimensions|")
    printer.println("|----------------------------|-----------------------------------------------------------|")
    allDimensions.groupBy(d => d.getClass.getPackage.getName)
      .toList.sortBy(_._1)
      .foreach { case (p, ds) =>
        val dims = ds.toList.sortBy(_.name)
        printer.println(s"|$p|${dims.map(d => s"[${d.name}](#${d.name.toLowerCase})").mkString(", ")}|")
      }

    printer.println(s"#### Dimension Count: ${allDimensions.size}")
    printer.println(s"#### Unit Count: ${allDimensions.map(_.units.size).sum}")

    allDimensions.toList
      .sortBy { d => d.name }
      .sortBy { d => !d.isInstanceOf[BaseDimension] }.foreach { d =>
      printer.println("")
      d match {
        case bd: BaseDimension =>
          printer.println(s"## ${d.name} - [ ${bd.dimensionSymbol} ]")
          printer.println(s"#### Primary Unit: ${d.primaryUnit.getClass.getSimpleName.replace("$", "")} (1 ${d.primaryUnit.symbol})")
          printer.println(s"#### SI Base Unit: ${d.siUnit.getClass.getSimpleName.replace("$", "")} (1 ${d.siUnit.symbol})")

        case _ =>
          printer.println(s"## ${d.name}")
          printer.println(s"#### Primary Unit: ${d.primaryUnit.getClass.getSimpleName.replace("$", "")} (1 ${d.primaryUnit.symbol})")
          printer.println(s"#### SI Unit: ${d.siUnit.getClass.getSimpleName.replace("$", "")} (1 ${d.siUnit.symbol})")
      }
      printer.println("|Unit|Conversion Factor|")
      printer.println("|----------------------------|-----------------------------------------------------------|")
      d.units
        .filterNot{ (u: UnitOfMeasure[_]) => u eq d.primaryUnit }.toList
        .sortBy { (u: UnitOfMeasure[_]) => u.convertFrom(1) }
        .foreach { (u: UnitOfMeasure[_]) =>
          val cf = s"1 ${u.symbol} = ${u.convertFrom(1)} ${d.primaryUnit.symbol}"
          printer.println(s"|${u.getClass.getSimpleName.replace("$", "")}| $cf|")
      }
      printer.println("")
      printer.println(s"[Go to Code](shared/src/main/scala/${d.getClass.getPackage.getName.replace(".", "/")}/${d.getClass.getSimpleName.replace("$", "")}.scala)")
    }

    printer.flush()
  }

  def writeDimensionFile(d: Dimension[_]): Unit = {

    val packageName = d.getClass.getPackage.getName.replace("squants.", "")
    val path = s"shared/src/main/scala/squants2converter/$packageName/"
    if(!Files.exists(Path.of(path))) Files.createDirectory(Path.of(path))
    val file = new File(s"$path${d.name}.scala")
    val writer = new PrintWriter(file)

    writer.println(s"package ${d.getClass.getPackage.getName.replace("squants", "squants2")}")
    writer.println()
    writer.println("import squants2._")
    writer.println()

    writer.println(s"final case class ${d.name}[A: Numeric] private [squants2]  (value: A, unit: ${d.name}Unit)")
    writer.println(s"  extends Quantity[A, ${d.name}.type] {")
    writer.println(s"  override type Q[B] = ${d.name}[B]")
    writer.println(s"}")
    writer.println()

    writer.println(s"object ${d.name} extends Dimension(\"${d.name}\") {")
    writer.println()
    writer.println(s"  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = ${d.primaryUnit.getClass.getSimpleName.replace("$", "")}")
    writer.println(s"  override def siUnit: UnitOfMeasure[this.type] with SiUnit = ${d.siUnit.getClass.getSimpleName.replace("$", "")}")
    val unitList = d.units.toList
      .sortBy{(u: UnitOfMeasure[_]) => u.convertFrom(1d)}
      .map { (u: UnitOfMeasure[_]) => s"${u.getClass.getSimpleName.replace("$", "")}"}
      .mkString(", ")
    writer.println(s"  override lazy val units: Set[UnitOfMeasure[this.type]] = ")
    writer.println(s"    Set(${unitList})")
    writer.println()
    writer.println(s"  implicit class ${d.name}Cons[A](a: A)(implicit num: Numeric[A]) {")
    d.units.toList
      .sortBy{(u: UnitOfMeasure[_]) => u.convertFrom(1d)}
      .foreach { (u: UnitOfMeasure[_]) =>
        val unitName = u.getClass.getSimpleName.replace("$", "")
        writer.println(s"    def ${unitName.head.toLower + unitName.tail}: ${d.name}[A] = $unitName(a)")
      }
    writer.println(s"  }")

    writer.println(s"}")
    writer.println()
    writer.println(s"abstract class ${d.name}Unit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[${d.name}.type] {")
    writer.println(s"  override lazy val dimension: ${d.name}.type = ${d.name}")
    writer.println(s"  override def apply[A: Numeric](value: A): ${d.name}[A] = ${d.name}(value, this)")
    writer.println(s"}")
    writer.println()
    d.units.toList
      .sortBy{(u: UnitOfMeasure[_]) => u.convertFrom(1d)}
      .foreach { (u: UnitOfMeasure[_]) =>
        writer.print(s"case object ${u.getClass.getSimpleName.replace("$", "")} extends ${d.name}Unit(\"${u.symbol}\", ${u.convertFrom(1d)})")
        if(u.isInstanceOf[PrimaryUnit]) writer.print(" with PrimaryUnit")
        if(u.isInstanceOf[SiUnit]) writer.print(" with SiUnit")
        writer.println()
    }

    writer.flush()
    writer.close()
  }


}
