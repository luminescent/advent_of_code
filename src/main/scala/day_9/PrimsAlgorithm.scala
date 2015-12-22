package day_9

import scala.collection.mutable

/**
  * Created by christina on 21/12/15.
  */
object PrimsAlgorithm {

  case class Edge(start: String, end: String, cost: Int)

  def getMinSpanningTreeCost(input: List[String]): Int = {

    val graph = input
      .map(line =>
        line.split(" ") match {
          case Array(x, "to", y, "=", z) => Edge(x, y, z.toInt)
          case _ => throw new Exception("unable to parse input")
        })
      .toSet

    val countNodes = graph.map(e => e.start).toSet.union(graph.map(e => e.end)).toSet.size

    val start = graph.minBy[Int](e => e.cost)

    //Console.println(start)

    var cost = 0//start.cost

    val nodes = mutable.Set[String]("AlphaCentauri")//start.start, start.end)

    while (nodes.size < countNodes) {
      val nextEdge = graph.filter(e =>
        (nodes.contains(e.start) && !nodes.contains(e.end))
        || (!nodes.contains(e.start) && nodes.contains(e.end))).minBy[Int](e => e.cost)
      cost += nextEdge.cost
      nodes += {nodes.contains(nextEdge.start) match {
        case true => nextEdge.end
        case _ => nextEdge.start
      }}

      Console.println(nextEdge)
    }

    cost
  }

}
