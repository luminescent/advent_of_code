package day_23

import common.InputReader

/**
 * Created by christina on 24/12/15.
 */
object Runner extends App {
  
  val lines = InputReader readLines "/home/christina/IdeaProjects/advent_of_code/src/main/scala/day_23/A.txt"

  println(s"Value for register b: ${ RegisterJumper computeB lines}")
}
