package day_9

import common.InputReader

/**
  * Created by christina on 21/12/15.
  */
object Runner extends App {

  val lines = InputReader readLines "/home/christina/projects/advent_of_code/src/main/scala/day_9/A.txt"

  println(s"Prim's algo: ${ PrimsAlgorithm getMinSpanningTreeCost lines}")
  println()
  //println(s"Brute force: ${ BruteForce getMinTSP lines}")
  println()
  println(s"Brute force: ${ BruteForce getMaxTSP  lines}")

}
