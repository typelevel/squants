package squants.experimental.constants

import squants.electro.ElectricCharge
import squants.electro.Coulombs
import squants.electro.MagneticFlux
import squants.electro.Webers
import squants.electro.ElectricalConductance
import squants.electro.Siemens

trait ElectroMagnetic {

  val ElementaryCharge = new Constant[ElectricCharge] {
    val value = Coulombs(1.6021765314e-19)
    val symbol = "𝑒"
    val uncertainty = Coulombs(8.5e-8)
  }

  val MagneticFluxQuantum = new Constant[MagneticFlux] {
    val value = Webers(2.0678337218e-15)
    val symbol = "Φ₀"
    val uncertainty = Webers(8.5e-8)
  }

  val ConductanceQuantum = new Constant[ElectricalConductance] {
    val value = Siemens(7.74809173326e-5)
    val symbol = "G₀"
    val uncertainty = Siemens(3.3e-9)
  }

}
