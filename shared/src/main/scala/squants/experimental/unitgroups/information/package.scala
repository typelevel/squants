package squants.experimental.unitgroups

import squants.UnitOfMeasure
import squants.information._

/**
  * UnitGroups for [[squants.information.Information]]
  */
package object information {

  /** Base-2 units for Information defined by the IEC */
  object IECInformation extends UnitGroup[Information] {
    val units: Set[UnitOfMeasure[Information]] =
      Set(Bytes, Kibibytes, Mebibytes, Gibibytes, Tebibytes, Pebibytes, Exbibytes, Zebibytes, Yobibytes)
  }

  /** Base-10 units for Information */
  object MetricInformation extends UnitGroup[Information] {
    val units: Set[UnitOfMeasure[Information]] =
      Set(Bytes, Kilobytes, Megabytes, Gigabytes, Terabytes, Petabytes, Exabytes, Zettabytes, Yottabytes)
  }

}
