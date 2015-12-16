package day_1


/**
  * Created by christina on 16/12/15.
  */
object FloorsOperator {

  def countFloors(input: String): Int = {
    input.foldLeft(0)(_ + countFloor(_))
  }

  // for illustration purposes (no dependencies)
  def countFloors2(input: String): Int = {
    input.foldLeft(0)(
      (total, floor) => total + (floor match {
                                  case '(' => 1
                                  case ')' => -1
                                  case _ => throw new Exception("invalid input")
                                }))
  }

  def findBasement(input: String): Int = findBasement(input, 0, 0)

  private def countFloor(c: Char) : Int = {
    c match {
      case '(' => 1
      case ')' => -1
      case _ => throw new Exception("invalid input")
    }
  }

  private def findBasement(input: String, accumulator: Int, floor: Int): Int = {
    accumulator match {
      case -1 => floor
      case _ => findBasement(input.substring(1), accumulator + countFloor(input.charAt(0)), 1 + floor)
    }
  }
}
