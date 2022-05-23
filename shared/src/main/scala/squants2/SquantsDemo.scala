package squants2

import squants2.mass._
import squants2.Dimensionless._

object SquantsDemo extends App {

  val massN = Kilograms(1) // Mass[Int]
  val massD = Kilograms(10.22) // Mass[Double]
  val massBD = Kilograms(BigDecimal(10.22)) // Mass[BigDecimal]
  val massStr = Mass.parseString[BigDecimal]("10.22 kg").get

  println(massD)
  println(massD.in(Pounds))
  println(massBD)
  println(massBD.in(Pounds))
  println(massStr)
  println(massStr.in(Pounds))

  // convert numbers to others types (requires implicit conversion to QNumeric in scope)
  val massN2D: Mass[Double] = massN.asNum[Double]
  val massD2B: Mass[BigDecimal] = massD.asNum[BigDecimal]

  val massQR: (Mass[BigDecimal], Mass[BigDecimal]) = Kilograms(33.4).asNum[BigDecimal] /% 2

  // the right-side operand is automatically converted before operations are applied
  // For standard types, built-in implicit conversions are used.

  val massSum = massBD + massD + massN // implicitly converted to Mass[BigDecimal]
  val massSum2 = massN.asNum[BigDecimal] + massD + massBD // a bit more explicit

  // No default implicit conversions from Double to Int (and none provided as it creates precision loss)
  //  val massSum3 = massN + massD // No implicit prevents this code from compiling - GOOD!

  // But you can be explicit
  val massSumN = massN + massD.map(_.toInt)  // Mass[Int]
  val massSumD = massN.asNum[Double] + massD // Mass[Double]

  val ms = Seq(12.28.each, Each(12.28), Each(-10.22), 1.1.each).map(_.asNum[BigDecimal])
  println(ms)
  println(ms.sorted)
  println(ms.sorted.reverse)
  println(ms.sum(Dimensionless.numeric[BigDecimal]))


  printAllDimensions()

}
