package squants

object Platform {
  /**
   * Helper function to achieve uniform Double formatting over JVM, JS, and native platforms.
   * Simple Double.toString will format 1.0 as "1.0" on JVM and as "1" on JS
   * @param d Double number to be formatted
   * @return
   */
  private[squants] def crossFormat(d: Double): String = {
    // we cannot use the same logic as JS and JVM because string formatting is not supported
    // by Scala Native yet
    d.toString
  }
}
