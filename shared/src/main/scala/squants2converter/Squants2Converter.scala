package squants2converter

import squants._

import java.io.{ File, PrintWriter }
import java.nio.file.{ Files, Path }

object Squants2Converter extends App {

//  writeDimensionFile(Frequency)
  Squants1UnitDocGenerator.allDimensions.foreach(writeDimensionFile)

  def writeDimensionFile(d: Dimension[_]): Unit = {

    val packageName = d.getClass.getPackage.getName.replace("squants.", "squants2/")
    val path = s"shared/src/main/scala/$packageName/"
    if(!Files.exists(Path.of(path))) Files.createDirectory(Path.of(path))

//    if(Files.exists(Path.of(s"$path${d.name}.scala"))) return

    val file = new File(s"$path${d.name}.scala")
    val writer = new PrintWriter(file)

    writer.println("/*                                                                      *\\")
    writer.println("** Squants                                                              **")
    writer.println("**                                                                      **")
    writer.println("** Scala Quantities and Units of Measure Library and DSL                **")
    writer.println("** (c) 2013-2022, Gary Keorkunian, et al                                **")
    writer.println("**                                                                      **")
    writer.println("\\*                                                                      */")
    writer.println()
    writer.println(s"package ${d.getClass.getPackage.getName.replace("squants", "squants2")}")
    writer.println()
    writer.println("import squants2._")
    writer.println("import scala.math.Numeric.Implicits.infixNumericOps")
    writer.println()

    writer.println(s"final case class ${d.name}[A: Numeric] private [squants2]  (value: A, unit: ${d.name}Unit)")
    writer.println(s"  extends Quantity[A, ${d.name}.type] {")
    writer.println(s"  override type Q[B] = ${d.name}[B]")
    writer.println()
    writer.println("  // BEGIN CUSTOM OPS")
    writer.println("  // END CUSTOM OPS")
    writer.println()
    d.units.toList
      .sortBy{(u: UnitOfMeasure[_]) => u.convertFrom(1d)}
      .foreach { (u: UnitOfMeasure[_]) =>
        val unitName = u.getClass.getSimpleName.replace("$", "")
        writer.println(s"  def to$unitName: A = to($unitName)")
      }
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
    writer.println(s"    Set($unitList)")
    writer.println()
    writer.println(s"  implicit class ${d.name}Cons[A](a: A)(implicit num: Numeric[A]) {")
    d.units.toList
      .sortBy{(u: UnitOfMeasure[_]) => u.convertFrom(1d)}
      .foreach { (u: UnitOfMeasure[_]) =>
        val unitName = u.getClass.getSimpleName.replace("$", "")
        writer.println(s"    def ${unitName.head.toLower}${unitName.tail}: ${d.name}[A] = $unitName(a)")
      }
    writer.println(s"  }")
    writer.println()

    d.units.toList
      .sortBy{(u: UnitOfMeasure[_]) => u.convertFrom(1d)}
      .foreach { (u: UnitOfMeasure[_]) =>
        val unitName = u.getClass.getSimpleName.replace("$", "")
        writer.println(s"  lazy val ${unitName.head.toLower}${unitName.tail}: ${d.name}[Int] = $unitName(1)")
      }

    val primaryUnitName = d.primaryUnit.getClass.getSimpleName.replace("$", "")
    writer.println()
    writer.println(s"  override def numeric[A: Numeric]: QuantityNumeric[A, this.type] = ${d.name}Numeric[A]()")
    writer.println(s"  private case class ${d.name}Numeric[A: Numeric]() extends QuantityNumeric[A, this.type](this) {")
    writer.println(s"    override def times(x: Quantity[A, ${d.name}.type], y: Quantity[A, ${d.name}.type]): Quantity[A, ${d.name}.this.type] =")
    writer.println(s"      $primaryUnitName(x.to($primaryUnitName) * y.to($primaryUnitName))")
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
