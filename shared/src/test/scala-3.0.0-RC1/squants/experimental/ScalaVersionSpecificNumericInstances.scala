package squants.experimental

import squants.experimental.SquantsNumeric.{DoubleIsSquantsNumeric, FloatIsSquantsNumeric}

trait ScalaVersionSpecificNumericInstances {

  implicit object FloatIsSquantsNumeric extends FloatIsSquantsNumeric with Ordering.Float.IeeeOrdering
  implicit object DoubleIsSquantsNumeric extends DoubleIsSquantsNumeric with Ordering.Double.IeeeOrdering

}
