package day_13

import scala.collection.mutable

/**
  * Created by christina on 24/12/15.
  */
object TableSitter {

  case class Pair(main: String, nextTo: String)

  var max = 0

  def computeHappiness(lines: List[String]): Int = {

    val regexGain = List(" would gain ", " happiness units by sitting next to ", "\\.").mkString("|").r
    val regexLoose = List(" would lose ", " happiness units by sitting next to ", "\\.").mkString("|").r


    val graph = lines
      .map(line => regexGain.split(line) match {
        case Array(main, happiness, nextTo) => Pair(main, nextTo) -> happiness.toInt
        case _ => regexLoose.split(line) match {
          case Array(main, happiness, nextTo) => Pair(main, nextTo) -> -1 * happiness.toInt
          case _ => throw new Exception(s"Unable to parse $line")
        }
      })
      .toMap

    val nodes = (graph.map(_._1.main) ++ graph.map(_._1.nextTo)).toSet

    val meGraph = nodes
      .flatMap(node => List(Pair("me", node) -> 0, Pair(node, "me") -> 0))
      .toMap

    computeHappiness(nodes + "me", graph ++ meGraph, List("Alice"))

    max
  }



  def computeHappiness(nodes: Set[String], graph: Map[Pair, Int], elements: List[String]): Unit = {

    elements.length match {
      case x if x == nodes.size =>
        val happiness = elements
          .sliding(2)
          .map{ case Seq(i, j) => graph(Pair(i, j)) + graph(Pair(j, i))}
          .sum
          + graph(Pair(elements.last, elements.head)) + graph(Pair(elements.head, elements.last))

        if (happiness > max) {
          max = happiness
          println(elements.mkString(", ") + " = " + happiness)
        }
      case _ =>
        (nodes -- elements)
          .foreach(next => computeHappiness(nodes, graph, elements :+ next))
    }
  }


}
