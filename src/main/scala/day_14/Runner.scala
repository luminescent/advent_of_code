package day_14

import common.InputReader

/**
  * Created by christina on 24/12/15.
  */
object Runner extends App {

  val input = InputReader readLines  "/home/christina/projects/advent_of_code/src/main/scala/day_14/A.txt"

  println(s"WWinning reindeer in distance: ${ReindeerContest getMaxDistance(input, 2503)}")
  println(s"WWinning reindeer in stars: ${ReindeerContest getMaxPoints (input, 2503)}")

}
