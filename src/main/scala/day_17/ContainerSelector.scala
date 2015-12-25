package day_17

import scala.collection.mutable

/**
  * Created by christina on 25/12/15.
  */
object ContainerSelector {

  def countContainers(lines: List[String], total: Int) : Int = {

    val containers = lines.map(s => s.toInt)

    selectContainers(containers, total, List[Int]())

    containersByCount(containersByCount.keys.min)
  }

  var containersByCount = mutable.Map[Int, Int]()

  def selectContainers(containers: List[Int], total: Int, selection: List[Int]): Unit = {
    val totalForSelection = getTotalForSelection(containers, selection)

    totalForSelection match {
      case x if x <= total =>
        selection.length match {
          case y if y == containers.length => totalForSelection match {
            case z if z == total =>
              val selected = selection.count(c => c == 1)
              val selectedCount = containersByCount.getOrElse(selected, 0)
              containersByCount(selected) = selectedCount + 1
            case _ =>
          }
          case _ =>
            (0 to 1)
              .foreach(i => selectContainers(containers, total, selection :+ i))
        }
      case _ =>
    }
  }

  def getTotalForSelection(containers: List[Int], selection: List[Int]) = {
    (0 until Math.min(containers.length, selection.length))
      .map(i => containers(i) * selection(i))
      .sum
  }


}
