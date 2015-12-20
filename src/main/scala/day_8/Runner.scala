package day_8

import common.InputReader

/**
  * Created by christina on 20/12/15.
  */
object Runner extends App {

  val lines = InputReader readLines "/home/christina/projects/advent_of_code/src/main/scala/day_8/A.txt"

  Console.println(s"Diff strings count: ${ StringsCounter diffLiteralsMemory lines }")
  Console.println(s"Diff escaped strings count: ${ StringsCounter diffEscapedMemory lines }")

}
