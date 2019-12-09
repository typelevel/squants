package squants.experimental

import squants.experimental.SquantsNumeric.{DoubleIsSquantsNumeric, FloatIsSquantsNumeric}

trait ScalaVersionSpecificNumericInstances {

  implicit object FloatIsSquantsNumeric extends FloatIsSquantsNumeric with Ordering.FloatOrdering
  implicit object DoubleIsSquantsNumeric extends DoubleIsSquantsNumeric with Ordering.DoubleOrdering

}
