package day_5

import common.InputReader

/**
  * Created by christina on 18/12/15.
  */
object Runner extends App {

  val lines = InputReader readLines "/home/christina/projects/advent_of_code/src/main/scala/day_5/A.txt"

  Console.println(s"Nice strings: ${ StringsClassifier countSecondNiceStrings lines }")

}
