package day_18

import common.InputReader

/**
  * Created by christina on 25/12/15.
  */
object Runner extends App {

  val input = InputReader readLines  "/home/christina/projects/advent_of_code/src/main/scala/day_18/A.txt"

  println(s"Lights on: ${ GridLighter countLightsOn(input, 100) }")

}
