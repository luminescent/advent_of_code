package day_11

import scala.collection.mutable

/**
  * Created by christina on 23/12/15.
  */


class Password(value: String)  {

  val inner = new mutable.MutableList[Int]()
  value.reverse.foreach(c => inner += (c - 97))

  val excluded = Set('i', 'o', 'l').map(_ - 97)

  def findNextValid: String = {
    Iterator
      .from(1)
      .find ( i => {
          addOne()
          isValid
        }
      )

    toString
  }



  def isDecreasing(seq: Seq[Int]): Boolean = {
    seq match  {
      case Seq(a, b, c) => (a - 1 == b) && (a - 2 == c)
      case _ => false
    }
  }

  def hasDecreasingSequence(list: mutable.MutableList[Int]): Boolean = {
    list
      .sliding(3)
      .exists(isDecreasing)
  }

  def hasDistinctNonOverlappingGroupsOfDoubleLetters(list: mutable.MutableList[Int]): Boolean = {
    list
      .sliding(2)
      .zipWithIndex
      .filter{ case (l, i) => l(0) == l(1)}
      .sliding(2)
      .exists {
        case Seq(a, b) => b._2 - a._2 > 1
        case _ => false
      }
  }

  def isValid: Boolean = {
    (!inner.exists(i => excluded.contains(i))) && hasDecreasingSequence(inner) && hasDistinctNonOverlappingGroupsOfDoubleLetters(inner)
  }

  def addOne() = {
    var position = 0
    var carry = true

    while (carry && position < inner.length) {
      val valueTotal = inner(position) + 1
      val valueAt = valueTotal % 26
      carry = valueTotal match {
        case 26 => true
        case _ => false
      }
      inner(position) = valueAt
      position += 1
    }
  }

  override def toString: String = {
    inner.reverse.map(i => (i + 97).asInstanceOf[Char]).mkString("")
  }

}
