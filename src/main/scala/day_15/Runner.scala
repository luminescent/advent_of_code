package day_15

import common.InputReader

/**
  * Created by christina on 25/12/15.
  */
object Runner extends App {

  val input = InputReader readLines  "/home/christina/projects/advent_of_code/src/main/scala/day_15/A.txt"

  println(s"Total score: ${CookiesTester getMaxValue(input, 100, 500)}")
}
