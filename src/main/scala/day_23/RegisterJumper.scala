package day_23

import scala.collection.mutable

/**
 * Created by christina on 24/12/15.
 */
object RegisterJumper {
  
  def computeB(lines: List[String]): Int = {
    val registers = mutable.Map[String, Int]()
    registers += ("a" -> 1)

    computeInstruction(0, lines, registers)
    
    registers("b")
  }
  
  def computeInstruction(index: Int, lines: List[String], registers: mutable.Map[String, Int]): Unit = {
    if (index < lines.length || index < 0) {
      lines(index).split(" ") match {
        case Array("hlf", r) => {
          val regValue = registers.getOrElse(r, 0)
          registers(r) = regValue/2
          computeInstruction(index + 1, lines, registers)
        }
        case Array("tpl", r) => {
          val regValue = registers.getOrElse(r, 0)
          registers(r) = regValue * 3
          computeInstruction(index + 1, lines, registers)
        }
        case Array("inc", r) => {
          val regValue = registers.getOrElse(r, 0)
          registers(r) = regValue + 1
          computeInstruction(index + 1, lines, registers)
        }
        case Array("jmp", offset) => 
          computeInstruction(index + getOffsetValue(offset), lines, registers)
        case Array("jie", reg, offset) => {
          val r = reg.substring(0, reg.length - 1)

          registers.getOrElse(r, 0) % 2 match  {
            case 0 => computeInstruction(index + getOffsetValue(offset), lines, registers)
            case _ => computeInstruction(index + 1, lines, registers)
          }
        }
        case Array("jio", reg, offset) => {
          val r = reg.substring(0, reg.length - 1)

          registers.getOrElse(r, 0) match {
            case 1 => computeInstruction(index + getOffsetValue(offset), lines, registers)
            case _ => computeInstruction(index + 1, lines, registers)
          }
        }
        case _ => println(s"Unable to parse instruction ${lines(index)}")
      }
    }
  }
  
  private def getOffsetValue(offset: String): Int = {
    offset(0) match {
      case '+' => offset.takeRight(offset.length - 1).toInt
      case '-' => -1 * offset.takeRight(offset.length - 1).toInt
    }
  }

}
