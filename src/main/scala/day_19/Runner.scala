package day_19

import common.InputReader

/**
  * Created by christina on 25/12/15.
  */
object Runner extends App {

  val lines = InputReader readLines "/home/christina/projects/advent_of_code/src/main/scala/day_19/A.txt"
  //val lines = InputReader readLines "/home/christina/projects/advent_of_code/src/main/scala/day_19/B_test.txt"

  println(s"Number of distinct molecules: ${ MoleculeGenerator countMolecules lines}")
  println(s"Number of steps: ${ MoleculeGenerator findMinNoOfTransformations  lines}")  // 195 


}
