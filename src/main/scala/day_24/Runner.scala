package day_24

import common.InputReader

/**
  * Created by christina on 26/12/15.
  */
object Runner extends App {

  val lines = InputReader readLines "/home/christina/projects/advent_of_code/src/main/scala/day_24/A.txt"
  println(lines
    .map(l => l.toInt)
    .sum / 3, lines.length)

  //println(s"Smalled QE of ideal group: ${SleightBalancer getQEForSmallestNoOfPackages lines}" ) // 11846773891
  println(s"Smalled QE of ideal group: ${SleighBalancerPart2 getQEForSmallestNoOfPackages lines}" ) // 11846773891
}
