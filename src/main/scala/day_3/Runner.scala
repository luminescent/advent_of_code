package day_3

import common.InputReader

/**
  * Created by christina on 17/12/15.
  */
object Runner extends App {

  val input = InputReader read "/home/christina/projects/advent_of_code/src/main/scala/day_3/A.txt"

  Console.println(s"Lucky houses: ${ HousesVisitor countVisitedHouses input}")
  Console.println(s"Lucky houses: ${ HousesVisitor countVisitedWithRobo input}")


}
