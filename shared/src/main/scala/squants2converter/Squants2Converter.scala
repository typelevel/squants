package squants2converter

import squants._
//import squants.motion.Velocity
//import squants.space.Length
import squants.thermal.Temperature
import squants.time._

import java.io.{ File, PrintWriter }
import java.lang.reflect.Modifier
import java.nio.file.{ Files, Path }
import scala.language.existentials

object Squants2Converter extends App {

  val primaryUnits: Map[String, String] = Squants1UnitDocGenerator.allDimensions.map { d =>
    d.name -> d.primaryUnit.getClass.getSimpleName.replace("$", "")
  }.toMap

//  writeDimensionFile(Frequency)
//  writeDimensionFile(Time)
  val dontProcess = Set(Temperature, Time, Dimensionless, Frequency)
  Squants1UnitDocGenerator.allDimensions.--(dontProcess).foreach(writeDimensionFile)

  def writeDimensionFile(d: Dimension[_]): Unit = {

    val packageName = d.getClass.getPackage.getName.replace("squants", "squants2").replace(".", "/")
    val path = s"shared/src/main/scala/$packageName/"
    if (!Files.exists(Path.of(path))) Files.createDirectory(Path.of(path))
    println(packageName)

    //    if(Files.exists(Path.of(s"$path${d.name}.scala"))) return

    val file = new File(s"$path${d.name}.scala")
    val writer = new PrintWriter(file)

    val units = d.units.toList.sortBy { (u: UnitOfMeasure[_]) => u.symbol }.sortBy { (u: UnitOfMeasure[_]) => u.convertFrom(1d) }

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
    writer.println("import squants2.electro._")
    writer.println("import squants2.energy._")
    writer.println("import squants2.information._")
    writer.println("import squants2.mass._")
    writer.println("import squants2.motion._")
    writer.println("import squants2.photo._")
    writer.println("import squants2.radio._")
    writer.println("import squants2.space._")
    writer.println("import squants2.thermal._")
    writer.println("import squants2.time._")
    writer.println("import scala.math.Numeric.Implicits.infixNumericOps")

    var isTD = false
    var isTI = false
    var timeDerived: String = ""
    var timeIntegrated: String = ""
    d.primaryUnit(1d).getClass.getGenericInterfaces
      .withFilter(i => i.getTypeName.contains(".TimeIntegral") || i.getTypeName.contains(".TimeDerivative"))
      .map { i =>
        if(i.getTypeName.contains(".TimeIntegral")) {
          isTI = true
          timeDerived = i.getTypeName.replace("squants.time.TimeIntegral<", "").replace(">", "")
          timeDerived = timeDerived.substring(timeDerived.lastIndexOf(".")+1)
        }
        if(i.getTypeName.contains(".TimeDerivative")) {
          isTD = true
          timeIntegrated = i.getTypeName.replace("squants.time.TimeDerivative<", "").replace(">", "")
          timeIntegrated = timeIntegrated.substring(timeIntegrated.lastIndexOf(".")+1)
        }
        s" with ${i.getTypeName.replace("squants.time.", "").replace("<", "[A, ").replace(">", "]")}"
      }
    val mixins = {
      if(isTD && isTI) s" with TimeDerivative[A, $timeIntegrated] with TimeIntegral[A, $timeDerived]"
      else if(isTD) s" with TimeDerivative[A, $timeIntegrated]"
      else if(isTI) s" with TimeIntegral[A, $timeDerived]"
      else ""
    }


    writer.println()
    // TODO: Add TimeIntegral and TimeDerivative mixins
    writer.println(s"final case class ${d.name}[A: Numeric] private[squants2] (value: A, unit: ${d.name}Unit)")
    writer.println(s"  extends Quantity[A, ${d.name}]$mixins {")
    writer.println()
    if(isTI) {
      writer.println(s"  override protected[squants2] def timeDerived: $timeDerived[A] = ${primaryUnits(timeDerived)}(num.one)")
      writer.println("  override protected[squants2] def integralTime: Time[A] = Seconds(num.one)")
    }
    if(isTD) {
      writer.println(s"  override protected[squants2] def timeIntegrated: $timeIntegrated[A] = ${primaryUnits(timeIntegrated)}(num.one)")
      writer.println("  override protected[squants2] def derivativeTime: Time[A] = Seconds(num.one)")
    }
    if(isTD || isTI)
      writer.println()
    writer.println("  // BEGIN CUSTOM OPS")
    writer.println()
    d.primaryUnit(1d).getClass.getDeclaredMethods
      .withFilter(m => !Modifier.isStatic(m.getModifiers))
      .withFilter(m => !Set("Quantity", "Time", "TimeSquared").contains(m.getReturnType.getSimpleName))
      .withFilter(m => !Set("value", "unit", "dimension", "time", "timeDerived", "timeIntegrated").contains(m.getName))
      .withFilter(m => !m.getName.startsWith("to"))
      .foreach { m =>
        val op = if (m.getName == "$div") "/" else if (m.getName == "$times") "*" else if (m.getName == "$plus") "+" else if (m.getName == "$minus") "-" else m.getName
        val params = m.getParameters.map { p =>
          val paramType = if (p.getType.getSimpleName == "double") "B" else s"${p.getType.getSimpleName}[B]"
          s"${p.getName}: $paramType"
        }.mkString(", ")
        val returnType = {
          if(m.getReturnType.getSimpleName == "double") "A"
          else if(m.getReturnType.getSimpleName == "long") "Long"
          else if(m.getReturnType.getPackage.getName.contains("squants")) s"${m.getReturnType.getSimpleName}[A]"
          else m.getReturnType.getSimpleName
        }

        writer.println(s"  //  def $op[B]($params)(implicit f: B => A): $returnType = ???")
      }
    writer.println("  // END CUSTOM OPS")
    writer.println()
    units.foreach { (u: UnitOfMeasure[_]) =>
      val unitName = u.getClass.getSimpleName.replace("$", "")
      writer.println(s"  def to$unitName[B: Numeric](implicit f: A => B): B = toNum[B]($unitName)")
    }
    writer.println(s"}")
    writer.println()

    d match {
      case baseDimension: BaseDimension =>
        writer.println(s"object ${d.name} extends BaseDimension[${d.name}](\"${d.name.withSpaces}\", \"${baseDimension.dimensionSymbol}\") {")
      case _ =>
        writer.println(s"object ${d.name} extends Dimension[${d.name}](\"${d.name.withSpaces}\") {")
    }
    writer.println()
    writer.println(s"  override def primaryUnit: UnitOfMeasure[${d.name}] with PrimaryUnit[${d.name}] = ${d.primaryUnit.getClass.getSimpleName.replace("$", "")}")
    d match {
      case _: BaseDimension =>
        writer.println(s"  override def siUnit: UnitOfMeasure[${d.name}] with SiBaseUnit[${d.name}] = ${d.siUnit.getClass.getSimpleName.replace("$", "")}")
      case _ =>
        writer.println(s"  override def siUnit: UnitOfMeasure[${d.name}] with SiUnit[${d.name}] = ${d.siUnit.getClass.getSimpleName.replace("$", "")}")
    }
    val unitList = units.map { (u: UnitOfMeasure[_]) => s"${u.getClass.getSimpleName.replace("$", "")}" }.mkString(", ")
    writer.println(s"  override lazy val units: Set[UnitOfMeasure[${d.name}]] = ")
    writer.println(s"    Set($unitList)")
    writer.println()
    writer.println(s"  implicit class ${d.name}Cons[A](a: A)(implicit num: Numeric[A]) {")
    units.foreach { (u: UnitOfMeasure[_]) =>
      val unitName = u.getClass.getSimpleName.replace("$", "")
      writer.println(s"    def ${unitName.head.toLower+unitName.tail}: ${d.name}[A] = $unitName(a)")
    }
    writer.println(s"  }")
    writer.println()

    units.foreach { (u: UnitOfMeasure[_]) =>
      val unitName = u.getClass.getSimpleName.replace("$", "")
      writer.println(s"  lazy val ${removePlural(unitName.head.toLower + unitName.tail)}: ${d.name}[Int] = $unitName(1)")
    }
    writer.println()
    writer.println(s"}")

    writer.println()
    writer.println(s"abstract class ${d.name}Unit(val symbol: String, val conversionFactor: ConversionFactor) extends UnitOfMeasure[${d.name}] {")
    writer.println(s"  override def dimension: Dimension[${d.name}] = ${d.name}")
    writer.println(s"  override def apply[A: Numeric](value: A): ${d.name}[A] = ${d.name}(value, this)")
    writer.println(s"}")
    writer.println()
    units.foreach { (u: UnitOfMeasure[_]) =>
      val convFactor = u.convertFrom(1d)
      val convExp = BigDecimal(convFactor) match {
        case x if x == BigDecimal(MetricSystem.Yocto) => "MetricSystem.Yocto"
        case x if x == MetricSystem.Zepto => "MetricSystem.Zepto"
        case x if x == MetricSystem.Atto => "MetricSystem.Atto"
        case x if x == MetricSystem.Femto => "MetricSystem.Femto"
        case x if x == MetricSystem.Pico => "MetricSystem.Pico"
        case x if x == MetricSystem.Nano => "MetricSystem.Nano"
        case x if x == MetricSystem.Micro => "MetricSystem.Micro"
        case x if x == MetricSystem.Milli => "MetricSystem.Milli"
        case x if x == MetricSystem.Centi => "MetricSystem.Centi"
        case x if x == MetricSystem.Deci => "MetricSystem.Deci"

        case x if x == MetricSystem.Deca => "MetricSystem.Deca"
        case x if x == MetricSystem.Hecto => "MetricSystem.Hecto"
        case x if x == MetricSystem.Kilo => "MetricSystem.Kilo"
        case x if x == MetricSystem.Mega => "MetricSystem.Mega"
        case x if x == MetricSystem.Giga => "MetricSystem.Giga"
        case x if x == MetricSystem.Tera => "MetricSystem.Tera"
        case x if x == MetricSystem.Peta => "MetricSystem.Peta"
        case x if x == MetricSystem.Exa => "MetricSystem.Exa"
        case x if x == MetricSystem.Zetta => "MetricSystem.Zetta"
        case x if x == MetricSystem.Yotta => "MetricSystem.Yotta"

        case x if x == BinarySystem.Kilo => "BinarySystem.Kilo"
        case x if x == BinarySystem.Mega => "BinarySystem.Mega"
        case x if x == BinarySystem.Giga => "BinarySystem.Giga"
        case x if x == BinarySystem.Tera => "BinarySystem.Tera"
        case x if x == BinarySystem.Peta => "BinarySystem.Peta"
        case x if x == BinarySystem.Exa => "BinarySystem.Exa"
        case x if x == BinarySystem.Zetta => "BinarySystem.Zetta"
        case x if x == BinarySystem.Yotta => "BinarySystem.Yotta"

        case x if x.toInt == x => convFactor.toInt.toString
        case _ => convFactor.toString
      }

      u match {
        case _: PrimaryUnit with SiBaseUnit =>
          writer.print(s"case object ${u.getClass.getSimpleName.replace("$", "")} extends ${d.name}Unit(\"${u.symbol}\", 1) with PrimaryUnit[${d.name}] with SiBaseUnit[${d.name}]")
        case _: PrimaryUnit with SiUnit =>
          writer.print(s"case object ${u.getClass.getSimpleName.replace("$", "")} extends ${d.name}Unit(\"${u.symbol}\", 1) with PrimaryUnit[${d.name}] with SiUnit[${d.name}]")
        case _: PrimaryUnit =>
          writer.print(s"case object ${u.getClass.getSimpleName.replace("$", "")} extends ${d.name}Unit(\"${u.symbol}\", 1) with PrimaryUnit[${d.name}]")
        case _: SiBaseUnit =>
          writer.print(s"case object ${u.getClass.getSimpleName.replace("$", "")} extends ${d.name}Unit(\"${u.symbol}\", $convExp) with SiBaseUnit[${d.name}]")
        case _: SiUnit =>
          writer.print(s"case object ${u.getClass.getSimpleName.replace("$", "")} extends ${d.name}Unit(\"${u.symbol}\", $convExp) with SiUnit[${d.name}]")
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

  private def removePlural(s: String): String = {
    val x = s.lastIndexWhere(_.isUpper)
    val index = if(x >= 0) x else s.length
    if(s.charAt(index) == 's') {
      val (f, l) =      s.splitAt(index)
      f.substring(0, index) + l
    } else s
  }

}
