package day_1

import FloorsOperator._
import InputReader._

/**
  * Created by christina on 16/12/15.
  */
object Runner extends App {

  val input = InputReader read "/home/christina/projects/advent_of_code/src/main/scala/day_1/A.txt"

  Console.println(s"Floor: ${ FloorsOperator countFloors input}")
  Console.println(s"Basement: ${ FloorsOperator findBasement input}")

}
