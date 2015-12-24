package day_13

import common.InputReader

/**
  * Created by christina on 24/12/15.
  */
object Runner extends App {

  val input = InputReader readLines  "/home/christina/projects/advent_of_code/src/main/scala/day_13/A.txt"

  println(s"Optimal arrangement cost: ${TableSitter computeHappiness input}")


}
