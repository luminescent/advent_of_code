package day_19

import scala.collection.mutable

/**
  * Created by christina on 25/12/15.
  */
object MoleculeGenerator {

  def countMolecules(lines: List[String]) : Int = {

    val moleculeTransformations = lines
      .take(lines.length - 2)
      .map(line =>
        line.split(" => ") match {
          case Array(x, y) => x -> y
        })

    val molecule = lines.last

    val molecules = moleculeTransformations
      .foldLeft(mutable.Set[String]())( (s, r) => s ++ generateTransformations(r._1, r._2, molecule))

    molecules.size
  }

  def findMinNoOfTransformations(lines: List[String]) : Int = {

    val reverseMolecules = lines
      .take(lines.length - 2)
      .map(line =>
        line.split(" => ") match {
          case Array(x, y) => y -> x
        })

    val molecule = lines.last


    generateMolecules("e", reverseMolecules, molecule, 0)

    minNoOfSteps
  }

  val alreadyLookedAt = mutable.Set[String]()

  var minNoOfSteps = 200

  def generateMolecules(target: String, moleculeTransformations: List[(String, String)], molecule: String, steps: Int): Unit = {
    if (steps < minNoOfSteps ) {

      alreadyLookedAt += molecule

      if ((target == molecule) && (steps < minNoOfSteps))
        minNoOfSteps = steps

      if (molecule.length == target.length)
        println(molecule)

      val nextTransformations = moleculeTransformations
        .filter( p => molecule.indexOf(p._1) >= 0)
        .sortBy( p => p._1.length * -1)

      // for the non overlapping transformations we
      // can run all replacements at a time

      val newMolecules = nextTransformations
        .flatMap(t => {
          generateTransformations(t._1, t._2, molecule)
            .filter(m => m.length <= molecule.length)
        })
        .distinct
        .filterNot(m => alreadyLookedAt.contains(m))
        .sortBy(m => m.length)

      newMolecules
        .foreach(m => generateMolecules(target, moleculeTransformations, m, steps + 1))
    }
  }



  def generateTransformations(symbol: String, replacement: String, molecule: String): mutable.Set[String] = {
    val result = mutable.Set[String]()

    var index = molecule.indexOf(symbol)
    while (index >= 0) {
      val left = molecule.substring(0, index)
      val right = molecule.substring(index + symbol.length)

      result += s"$left$replacement$right"
      index = molecule.indexOf(symbol, index + symbol.length)
    }

    result
  }


}
