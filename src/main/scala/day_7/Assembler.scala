package day_7


import scala.collection.mutable
import scala.collection.mutable._


/**
  * Created by christina on 20/12/15.
  */
object Assembler {

  def computeA(lines: List[String]): Int = {

    val instructions = lines
      .map(line => {
        val parts = line.split(" -> ")
        parts(1) -> parts(0)
      })
      .toMap

    val circuits = mutable.Map[String, Int]()

    computeWire("a", instructions, circuits)
  }

  def computeAAfterB(lines: List[String]): Int = {
    val instructions = lines
      .map(line => {
        val parts = line.split(" -> ")
        parts(1) -> parts(0)
      })
      .toMap

    val circuits = mutable.Map[String, Int]("b" -> 46065) //this is computeA

    computeWire("a", instructions, circuits)
  }

  def computeWire(wire: String, instructions: scala.collection.immutable.Map[String, String], circuits: Map[String, Int]): Int = {

    circuits.contains(wire) match {
      case true => circuits(wire)
      case _ => {
        val value = isNumber(wire) match {
          case true =>
            wire.toInt
          case _ => {
            val instruction = instructions(wire)
            isNumber(instruction) match {
              case true =>
                instruction.toInt
              case _ => {
                val parts = instruction.split(" ")
                parts.length match {
                  case 1 => computeWire(parts(0), instructions, circuits)
                  case 2 => ~computeWire(parts(1), instructions, circuits)
                  case 3 => parts(1) match {
                    case "AND" => computeWire(parts(0), instructions, circuits) & computeWire(parts(2), instructions, circuits)
                    case "OR" => computeWire(parts(0), instructions, circuits) | computeWire(parts(2), instructions, circuits)
                    case "RSHIFT" => computeWire(parts(0), instructions, circuits) >> parts(2).toInt
                    case "LSHIFT" => computeWire(parts(0), instructions, circuits) << parts(2).toInt
                  }
                }
              }
            }
          }
        }
        circuits += (wire -> value)
        value
      }
    }
  }


  def isNumber(x: String) = x forall Character.isDigit

}
