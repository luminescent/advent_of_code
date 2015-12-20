package day_7

import common.InputReader

/**
  * Created by christina on 20/12/15.
  */
object Runner extends App {


  val lines = InputReader readLines "/home/christina/projects/advent_of_code/src/main/scala/day_7/A.txt"

  Console.println(s"Value for a: ${ Assembler computeA lines }")
  Console.println(s"Value for a: ${ Assembler computeAAfterB lines }")

}
