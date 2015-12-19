package day_6

import common.InputReader

/**
  * Created by christina on 19/12/15.
  */
object Runner extends App {

  val lines = InputReader readLines "/home/christina/projects/advent_of_code/src/main/scala/day_6/A.txt"

  Console.println(s"Lights on: ${ GridLights countLightsOn lines }")
  Console.println(s"Total brightness: ${ GridLights countBrightness lines }")

}
