package squants2converter

import squants._
import squants.thermal.Temperature

import java.io.{ File, PrintWriter }
import java.nio.file.{ Files, Path }
import scala.language.existentials


object Squants2Converter extends App {

  //  writeDimensionFile(Frequency)
  val dontProcess = Set(Temperature)
  Squants1UnitDocGenerator.allDimensions.--(dontProcess).foreach(writeDimensionFile)

  def writeDimensionFile(d: Dimension[_]): Unit = {

    val packageName = d.getClass.getPackage.getName.replace("squants", "squants2").replace(".", "/")
    val path = s"shared/src/main/scala/$packageName/"
    if(!Files.exists(Path.of(path))) Files.createDirectory(Path.of(path))
    println(packageName)

    //    if(Files.exists(Path.of(s"$path${d.name}.scala"))) return

    val file = new File(s"$path${d.name}.scala")
    val writer = new PrintWriter(file)

    val units = d.units.toList.sortBy{ (u: UnitOfMeasure[_]) => u.symbol }.sortBy{ (u: UnitOfMeasure[_]) => u.convertFrom(1d) }

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
    if (!packageName.equals("squants2")) writer.println("import squants2._")
    writer.println("import scala.math.Numeric.Implicits.infixNumericOps")
    writer.println()

    writer.println(s"final case class ${d.name}[A: Numeric] private [squants2]  (value: A, unit: ${d.name}Unit)")
    writer.println(s"  extends Quantity[A, ${d.name}.type] {")
    writer.println(s"  override type Q[B] = ${d.name}[B]")
    writer.println()
    writer.println("  // BEGIN CUSTOM OPS")
    d.primaryUnit(1d).getClass.getDeclaredMethods.withFilter(_.getName.startsWith("$")).foreach{ m =>
      val op = if(m.getName == "$div") "/" else if(m.getName == "$times") "*" else if(m.getName == "$plus") "+" else if(m.getName == "$minus") "-" else "?"
      val param = m.getParameters.head
      val paramName = param.getName
      val paramType = if(param.getType.getSimpleName == "double") "B" else s"${param.getType.getSimpleName}[B]"
      val returnType = m.getReturnType.getSimpleName
      if((paramType.contains("Time")
        || returnType.contains("Time")) &&
        param.getType.getInterfaces.map(_.getName).exists(_.contains("Time"))) {}
      else if (paramType.contains("Quantity")) {
        writer.println(s"  //  def $op[B, E <: Dimension]($paramName: Quantity[B, E])(implicit f: B => A): Quantity[A, E] = ???")
      } else {
        writer.println(s"  //  def $op[B]($paramName: $paramType)(implicit f: B => A): $returnType[A] = ???")
      }
    }
    writer.println("  // END CUSTOM OPS")
    writer.println()
    units.foreach { (u: UnitOfMeasure[_]) =>
      val unitName = u.getClass.getSimpleName.replace("$", "")
      writer.println(s"  def to$unitName: A = to($unitName)")
    }
    writer.println(s"}")
    writer.println()

    d match {
      case baseDimension: BaseDimension =>
        writer.println(s"object ${d.name} extends BaseDimension(\"${d.name.withSpaces}\", \"${baseDimension.dimensionSymbol}\") {")
      case _ =>
        writer.println(s"object ${d.name} extends Dimension(\"${d.name.withSpaces}\") {")
    }
    writer.println()
    writer.println(s"  override def primaryUnit: UnitOfMeasure[this.type] with PrimaryUnit = ${d.primaryUnit.getClass.getSimpleName.replace("$", "")}")
    d match {
      case _: BaseDimension =>
        writer.println(s"  override def siUnit: UnitOfMeasure[this.type] with SiBaseUnit = ${d.siUnit.getClass.getSimpleName.replace("$", "")}")
      case _ =>
        writer.println(s"  override def siUnit: UnitOfMeasure[this.type] with SiUnit = ${d.siUnit.getClass.getSimpleName.replace("$", "")}")
    }
    val unitList = units.map { (u: UnitOfMeasure[_]) => s"${u.getClass.getSimpleName.replace("$", "")}"}.mkString(", ")
    writer.println(s"  override lazy val units: Set[UnitOfMeasure[this.type]] = ")
    writer.println(s"    Set($unitList)")
    writer.println()
    writer.println(s"  implicit class ${d.name}Cons[A](a: A)(implicit num: Numeric[A]) {")
    units.foreach { (u: UnitOfMeasure[_]) =>
      val unitName = u.getClass.getSimpleName.replace("$", "")
      writer.println(s"    def ${unitName.head.toLower}${unitName.tail}: ${d.name}[A] = $unitName(a)")
    }
    writer.println(s"  }")
    writer.println()

    units.foreach { (u: UnitOfMeasure[_]) =>
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
    writer.println(s"  override def dimension: ${d.name}.type = ${d.name}")
    writer.println(s"  override def apply[A: Numeric](value: A): ${d.name}[A] = ${d.name}(value, this)")
    writer.println(s"}")
    writer.println()
    units.foreach { (u: UnitOfMeasure[_]) =>
      val convFactor = u.convertFrom(1d)
      val convExp = BigDecimal(convFactor) match {
          case x if x == BigDecimal(MetricSystem.Yocto) => "MetricSystem.Yocto"
          case x if x == MetricSystem.Zepto => "MetricSystem.Zepto"
          case x if x == MetricSystem.Atto  => "MetricSystem.Atto"
          case x if x == MetricSystem.Femto => "MetricSystem.Femto"
          case x if x == MetricSystem.Pico  => "MetricSystem.Pico"
          case x if x == MetricSystem.Nano  => "MetricSystem.Nano"
          case x if x == MetricSystem.Micro => "MetricSystem.Micro"
          case x if x == MetricSystem.Milli => "MetricSystem.Milli"
          case x if x == MetricSystem.Centi => "MetricSystem.Centi"
          case x if x == MetricSystem.Deci  => "MetricSystem.Deci"

          case x if x == MetricSystem.Deca  => "MetricSystem.Deca"
          case x if x == MetricSystem.Hecto => "MetricSystem.Hecto"
          case x if x == MetricSystem.Kilo  => "MetricSystem.Kilo"
          case x if x == MetricSystem.Mega  => "MetricSystem.Mega"
          case x if x == MetricSystem.Giga  => "MetricSystem.Giga"
          case x if x == MetricSystem.Tera  => "MetricSystem.Tera"
          case x if x == MetricSystem.Peta  => "MetricSystem.Peta"
          case x if x == MetricSystem.Exa   => "MetricSystem.Exa"
          case x if x == MetricSystem.Zetta => "MetricSystem.Zetta"
          case x if x == MetricSystem.Yotta => "MetricSystem.Yotta"

          case x if x == BinarySystem.Kilo  => "BinarySystem.Kilo"
          case x if x == BinarySystem.Mega  => "BinarySystem.Mega"
          case x if x == BinarySystem.Giga  => "BinarySystem.Giga"
          case x if x == BinarySystem.Tera  => "BinarySystem.Tera"
          case x if x == BinarySystem.Peta  => "BinarySystem.Peta"
          case x if x == BinarySystem.Exa   => "BinarySystem.Exa"
          case x if x == BinarySystem.Zetta => "BinarySystem.Zetta"
          case x if x == BinarySystem.Yotta => "BinarySystem.Yotta"
          
          case x if x.toInt == x => convFactor.toInt.toString
          case _ => convFactor.toString
        }


      u match {
        case _: PrimaryUnit with SiBaseUnit =>
          writer.print(s"case object ${u.getClass.getSimpleName.replace("$", "")} extends ${d.name}Unit(\"${u.symbol}\", 1) with PrimaryUnit with SiBaseUnit")
        case _: PrimaryUnit with SiUnit =>
          writer.print(s"case object ${u.getClass.getSimpleName.replace("$", "")} extends ${d.name}Unit(\"${u.symbol}\", 1) with PrimaryUnit with SiUnit")
        case _: PrimaryUnit =>
          writer.print(s"case object ${u.getClass.getSimpleName.replace("$", "")} extends ${d.name}Unit(\"${u.symbol}\", 1) with PrimaryUnit")
        case _: SiBaseUnit =>
          writer.print(s"case object ${u.getClass.getSimpleName.replace("$", "")} extends ${d.name}Unit(\"${u.symbol}\", $convExp) with SiBaseUnit")
        case _: SiUnit =>
          writer.print(s"case object ${u.getClass.getSimpleName.replace("$", "")} extends ${d.name}Unit(\"${u.symbol}\", $convExp) with SiUnit")
        case _ =>
          writer.print(s"case object ${u.getClass.getSimpleName.replace("$", "")} extends ${d.name}Unit(\"${u.symbol}\", $convExp)")
      }
      writer.println()
    }

    writer.flush()
    writer.close()
  }

  private implicit class CamelToSpaces(s: String) {
    def withSpaces: String = s"${s.head}" + s.tail.map { c =>
      if(c.isUpper) s" $c"
      else s"$c"
    }.mkString
  }

}
