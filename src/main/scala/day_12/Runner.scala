package day_12

import common.InputReader

/**
 * Created by christina on 24/12/15.
 */
object Runner extends App {
  
  
  val input = InputReader read "/home/christina/IdeaProjects/advent_of_code/src/main/scala/day_12/A.txt"
  println(s"Total: ${Numberer sum input}")

  println(s"Total without red properties: ${Numberer sumWithoutRed input}")


}
