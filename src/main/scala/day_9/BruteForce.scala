package day_9

/**
  * Created by christina on 21/12/15.
  */
object BruteForce {

  case class Edge(start: String, end: String)

  def getMinTSP(input: List[String]): Int = {

    val graph = input
      .map(line =>
        line.split(" ") match {
          case Array(x, "to", y, "=", z) => Edge(x, y) -> z.toInt
          case _ => throw new Exception("unable to parse input")
        })
      .toMap


    val winning = graph.keys
      .flatMap {
        case Edge(a, b) => Seq(a, b)
      }
      .toVector
      .permutations
      .minBy[Int](path =>
        path
          .sliding(2)
          .map {
            case Seq(a, b) =>
              graph.getOrElse(Edge(a, b), graph.getOrElse(Edge(b, a), 10000))
          }
          .sum
      )

    winning.foreach(println)

    winning
      .sliding(2)
      .map {
        case Seq(a, b) =>
          graph.getOrElse(Edge(a, b), graph.getOrElse(Edge(b, a), 10000))
      }
      .sum


  }

  def getMaxTSP(input: List[String]): Int = {

    val graph = input
      .map(line =>
        line.split(" ") match {
          case Array(x, "to", y, "=", z) => Edge(x, y) -> z.toInt
          case _ => throw new Exception("unable to parse input")
        })
      .toMap


    val winning = graph.keys
      .flatMap {
        case Edge(a, b) => Seq(a, b)
      }
      .toVector
      .permutations
      .maxBy[Int](path =>
        path
          .sliding(2)
          .map {
            case Seq(a, b) =>
              graph.getOrElse(Edge(a, b), graph.getOrElse(Edge(b, a), 0))
          }
          .sum
    )

    winning.foreach(println)

    winning
      .sliding(2)
      .map {
        case Seq(a, b) =>
          graph.getOrElse(Edge(a, b), graph.getOrElse(Edge(b, a), 10000))
      }
      .sum


  }

}
