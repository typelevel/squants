package squants.unitgroups.space

import squants.{SiUnit, UnitOfMeasure}
import squants.space._
import squants.unitgroups.UnitGroup

/**
  * UnitGroups for [[Volume]]
  */
package object volume {

  /** Only contains UnitOfMeasure[Volume] that are part of SI. */
  object SiVolumes extends UnitGroup[Volume] {
    val units: Set[UnitOfMeasure[Volume]] = Volume.units.collect { case si: SiUnit => si }
  }

  /**
    * Contains all of [SiVolumes] plus extra volume units defined by
    * [[http://www.bipm.org/en/publications/si-brochure/table6.html The BIPM SI Brochure, Section 4.1.]]
    */
  object ExpandedSiVolumes extends UnitGroup[Volume] {
    val units: Set[UnitOfMeasure[Volume]] = SiVolumes.units ++
      Set(Centilitres, Hectolitres, Litres, Microlitres, Millilitres, Nanolitres)
  }

  object UsCustomaryGeneralVolumes extends UnitGroup[Volume] {
    val units: Set[UnitOfMeasure[Volume]] = Set(CubicInches, CubicFeet, CubicYards, AcreFeet)
  }

  object UsCustomaryLiquidVolumes extends UnitGroup[Volume] {
    val units: Set[UnitOfMeasure[Volume]] = Set(Teaspoons, Tablespoons, FluidOunces, UsCups, UsPints, UsQuarts, UsGallons)
  }

  object UsCustomaryDrylVolumes extends UnitGroup[Volume] {
    val units: Set[UnitOfMeasure[Volume]] = Set(UsDryPints, UsDryCups, UsDryQuarts, UsDryGallons)
  }
}
