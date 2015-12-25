package day_18

import sun.font.TrueTypeFont

import scala.collection.mutable

/**
  * Created by christina on 25/12/15.
  */
object GridLighter {

  case class Position(x: Int, y: Int)

  def countLightsOn(lines: List[String], steps: Int) : Int = {

    var grid = mutable.Map[Position, Boolean]()

    // put on borders
    (0 to lines.length + 1)
      .foreach(i => {
        grid += Position(0, i) -> false
        grid += Position(i, 0) -> false
        grid += Position(lines.length + 1, i) -> false
        grid += Position(i, lines.length + 1) -> false
      })

    lines.indices
      .foreach(x =>
        lines.indices
          .foreach(y =>
            grid += Position(x + 1, y + 1) -> (lines(x).charAt(y) match {
              case '#' => true
              case '.' => false
            })))

    // corners are always on
    grid(Position(1, 1)) = true
    grid(Position(lines.length, lines.length)) = true
    grid(Position(1, lines.length)) = true
    grid(Position(lines.length, 1)) = true

    val lastGrid = (1 to steps)
      .foldLeft(grid)((g, i) => runStep(lines.length, g))

    lastGrid.count { case (p, s) => s }
  }

  def runStep(dimension: Int, grid: mutable.Map[Position, Boolean]): mutable.Map[Position, Boolean] = {

    val newGrid = mutable.Map[Position, Boolean]()

    // put on borders
    (0 to dimension + 1)
      .foreach(i => {
        newGrid += Position(0, i) -> false
        newGrid += Position(i, 0) -> false
        newGrid += Position(dimension + 1, i) -> false
        newGrid += Position(i, dimension + 1) -> false
      })

    (1 to dimension)
      .foreach(x =>
        (1 to dimension)
          .foreach(y =>
            newGrid += Position(x, y) -> newCellState(Position(x, y), grid)))

    newGrid(Position(1, 1)) = true
    newGrid(Position(dimension, dimension)) = true
    newGrid(Position(1, dimension)) = true
    newGrid(Position(dimension, 1)) = true

    newGrid
  }

  def newCellState(position: Position, grid: mutable.Map[Position, Boolean]): Boolean = {
    val neighboursOn = List(grid(Position(position.x - 1, position.y - 1)),
      grid(Position(position.x - 1, position.y)),
      grid(Position(position.x - 1, position.y + 1)),
      grid(Position(position.x, position.y - 1)),
      grid(Position(position.x, position.y + 1)),
      grid(Position(position.x + 1, position.y - 1)),
      grid(Position(position.x + 1, position.y)),
      grid(Position(position.x + 1, position.y + 1))
    ).count(p => p)

    grid(position) match {
      case true => neighboursOn == 2 || neighboursOn == 3
      case _ => neighboursOn == 3
    }
  }

}
