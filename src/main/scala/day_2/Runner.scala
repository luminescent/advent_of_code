package day_2

import common.InputReader

/**
 * Created by christina on 17/12/15.
 */
object Runner extends App {

  val input = InputReader readLines  "/home/christina/IdeaProjects/advent_of_code/src/main/scala/day_2/A.txt" toList

  Console.println(s"Total square feet of wrappings: ${ BoxPackager computeBoxWrappings input }")
  Console.println(s"Total ribbon length: ${ BoxPackager computeRibbonLength input }")

}
