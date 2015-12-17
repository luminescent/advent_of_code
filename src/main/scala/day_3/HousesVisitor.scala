package day_3

import scala.collection.mutable

/**
  * Created by christina on 17/12/15.
  */
object HousesVisitor extends App {

  case class House(x: Int, y: Int)

  def countVisitedHouses(input: String) : Int = {
    input
      .foldLeft(mutable.Set[House](House(0, 0)) -> House(0, 0))(
        (s, char) => {
          val newHouse = moveHouse(char, s._2)
          (s._1 += newHouse) -> newHouse
        })
      ._1.size
  }

  def countVisitedWithRobo(input: String): Int = {
    val santa = input.zipWithIndex.filter(_._2 % 2 == 0).map(_._1).mkString
    val roboSanta = input.zipWithIndex.filter(_._2 % 2 == 1).map(_._1).mkString

    val santaHouses = santa
      .foldLeft(mutable.Set[House](House(0, 0)) -> House(0, 0))(
        (s, char) => {
          val newHouse = moveHouse(char, s._2)
          (s._1 += newHouse) -> newHouse
        })
      ._1

    val roboSantaHouses = roboSanta
      .foldLeft(mutable.Set[House](House(0, 0)) -> House(0, 0))(
        (s, char) => {
          val newHouse = moveHouse(char, s._2)
          (s._1 += newHouse) -> newHouse
        })
      ._1

    (santaHouses ++ roboSantaHouses).size
  }

  private def moveHouse(char: Char, house: House) : House = {
    char match {
      case '>' => house.copy(x = house.x + 1)
      case '<' => house.copy(x = house.x - 1)
      case '^' => house.copy(y = house.y + 1)
      case 'v' => house.copy(y = house.y - 1)
      case _ => throw new Exception("invalid char")
    }
  }

}
