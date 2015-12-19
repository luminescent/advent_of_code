package day_5

import common.InputReader

/**
  * Created by christina on 18/12/15.
  */
object Runner extends App {


  //Console.println(s"""${StringsClassifier isNice2 "xxyxx" }""")

//  val s = "aaa"
//  val twograms = s.sliding(2).toList
//
//  val fromStart = twograms.zipWithIndex.filter(_._2 % 2 == 0).map(_._1)
//  val oneAfterStart = twograms.zipWithIndex.filter(_._2 % 2 == 1).map(_._1)
//
//  fromStart.foreach(Console.println(_))
//  oneAfterStart.foreach(Console.println(_))

  val lines = InputReader readLines "/home/christina/projects/advent_of_code/src/main/scala/day_5/A.txt"

  Console.println(s"Nice strings: ${ StringsClassifier countSecondNiceStrings lines }")

}
