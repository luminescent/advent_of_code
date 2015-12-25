package day_16

/**
  * Created by christina on 25/12/15.
  */
object SueDetector {

  case class Sue(name: Int, children: Option[Int] = None, cats: Option[Int] = None, samoyeds: Option[Int] = None,
                 pomeranians: Option[Int] = None, akitas: Option[Int] = None, vizlas: Option[Int] = None,
                 goldfish: Option[Int] = None, trees: Option[Int] = None, cars: Option[Int] = None,
                perfumes: Option[Int] = None)

  def findSue(lines: List[String]) : Int = {

    val sues = lines.map(parseSue)

    sues
      .find(s =>
        s.children.getOrElse(3) == 3
          && s.cats.getOrElse(8) > 7
          && s.samoyeds.getOrElse(2) == 2
          && s.pomeranians.getOrElse(2) < 3
          && s.akitas.getOrElse(0) == 0
          && s.vizlas.getOrElse(0) == 0
          && s.goldfish.getOrElse(4) < 5
          && s.trees.getOrElse(4) > 3
          && s.cars.getOrElse(2) == 2
          && s.perfumes.getOrElse(1) == 1
      )
      .get.name

  }

  //Sue 1: goldfish: 9, cars: 0, samoyeds: 9
  private def parseSue(line: String) : Sue = {
    val namePart = line.indexOf(':')
    val name = line.substring(4, namePart)

    val sue = Sue(name = name.toInt)

    val attributes = line
      .substring(namePart + 1, line.length)
      .split(',')

    attributes
      .foldLeft(sue)((s, a) => addToSue(a, s))
  }

  //  goldfish: 9
  private def addToSue(attribute: String, sue: Sue) : Sue = {
    val parts = attribute.split(':').map(s => s.trim)

    parts(0) match {
      case "children" => sue.copy(children = Some(parts(1).toInt))
      case "cats" => sue.copy(cats = Some(parts(1).toInt))
      case "samoyeds" => sue.copy(samoyeds = Some(parts(1).toInt))
      case "pomeranians" => sue.copy(pomeranians = Some(parts(1).toInt))
      case "akitas" => sue.copy(akitas = Some(parts(1).toInt))
      case "vizslas" => sue.copy(vizlas = Some(parts(1).toInt))
      case "goldfish" => sue.copy(goldfish = Some(parts(1).toInt))
      case "trees" => sue.copy(trees = Some(parts(1).toInt))
      case "cars" => sue.copy(cars = Some(parts(1).toInt))
      case "perfumes" => sue.copy(perfumes = Some(parts(1).toInt))
      case _ => throw new Exception(s"Unable to parse $attribute")
    }

  }

}
