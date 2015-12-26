package day_25

/**
  * Created by christina on 26/12/15.
  */
object CodeGenerator {

  // formula: xn = ((x1 % b) * (a^(n - 1) % b))%b
  def getCode(x1: Long, a: Long, b: Long, n: Long): Long = {
    ((x1 % b) * powModulo(a, n - 1, b)) % b
  }

  // (a^n)%b
  private def powModulo(a: Long, n:Long, b:Long): Long = {

    val constant = a % b
    var accumulator = constant

    (2 to n.toInt)
      .foreach(i => accumulator = (constant * accumulator) % b)

    accumulator
  }

}
