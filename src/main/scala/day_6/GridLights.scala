package day_6

import scala.collection.mutable

/**
  * Created by christina on 19/12/15.
  */
object GridLights {

  case class House(x: Int, y: Int)

  def countLightsOn(lines: List[String]): Int = {

    val lights = mutable.Map[House, Boolean]()
    for (x <- 0 to 999; y <- 0 to 999)
      lights += House(x, y) -> false

    lines.foreach(processLine(_, lights))

    lights.count(l => l._2 == true)
  }

  def processLine(line: String, lights: mutable.Map[House, Boolean]): Unit = {

    val (xstart, ystart, xend, yend) = getCoordinates(line)

    line.substring(0, 7) match {
      case "toggle " => for (x <- xstart to xend; y <- ystart to yend) {
        val house = House(x, y)
        lights(house) = !lights(house)
      }
      case "turn on" => for (x <- xstart to xend; y <- ystart to yend) {
        val house = House(x, y)
        lights(house) = true
      }
      case "turn of" => for (x <- xstart to xend; y <- ystart to yend) {
        val house = House(x, y)
        lights(house) = false
      }
    }
  }

  def countBrightness(lines: List[String]): Int = {

    val lights = mutable.Map[House, Int]()
    for (x <- 0 to 999; y <- 0 to 999)
      lights += House(x, y) -> 0

    lines.foreach(processLineBrightness(_, lights))

    lights.map(_._2).sum
  }



  def processLineBrightness(line: String, lights: mutable.Map[House, Int]): Unit = {

    val (xstart, ystart, xend, yend) = getCoordinates(line)

    line.substring(0, 7) match {
      case "toggle " => for (x <- xstart to xend; y <- ystart to yend) {
        val house = House(x, y)
        lights(house) = lights(house) + 2
      }
      case "turn on" => for (x <- xstart to xend; y <- ystart to yend) {
        val house = House(x, y)
        lights(house) = lights(house) + 1
      }
      case "turn of" => for (x <- xstart to xend; y <- ystart to yend) {
        val house = House(x, y)
        lights(house) = math.max(0, lights(house) - 1)
      }
    }
  }



  def getCoordinates(line: String) = {
    val parts = line.split(Array(' ', ','))

    (parts(parts.length - 5).toInt, parts(parts.length - 4).toInt, parts(parts.length - 2).toInt, parts(parts.length - 1).toInt)
  }

}
