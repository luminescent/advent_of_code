package day_24

import scala.collection.mutable

/**
  * Created by christina on 26/12/15.
  */
object SleightBalancer {

  case class Distribution(group1: Set[Long], group2: Set[Long], group3: Set[Long])


  def getQEForSmallestNoOfPackages(lines: List[String]): Long = {

    // we help these a bit
    val weights = lines.map(l => l.toLong).toSet

    computeDistributions(weights.sum / 3, weights, Set[Long]())

    println(minPackagesInFirstGroup)
    solutions.foreach(println)

    solutions.map(d => d.product).min
  }

  var minPackagesInFirstGroup = 7
  var minQE = 10000000000000L
  var solutions = mutable.Set[Set[Long]]()
  var partitionableForGroup2And3 = mutable.Set[Set[Long]]()

  var nonPartitionableForGroup2And3 = mutable.Set[Set[Long]]()

  def getNexts(groupTotal: Long, groupSum: Long, weights: Set[Long], group1: Set[Long]) = {
    weights
      .filterNot(group1)
      .filter(elem => groupSum + elem <= groupTotal)
      .toList
      .sortBy(e => e * -1) // order desc
  }

  // should say if a set can be split into 2 groups of sum groupTotal
  def isPartitionable(groupTotal: Long, weighs: Set[Long]): Boolean = {

    def computeD(groupTotal: Long, weights: Set[Long], group1: Set[Long]): Unit = {
      if (partitionableForGroup2And3.contains(weighs))
        return

      if (nonPartitionableForGroup2And3.contains(weighs))
        return

      group1.sum match {
        case x if x == groupTotal => partitionableForGroup2And3 += weighs
        case x if x < groupTotal => {
          val nexts = getNexts(groupTotal, x, weighs, group1)
          nexts
            .foreach(next => computeD(groupTotal, weighs, group1 + next))
        }

      }
    }

    computeD(groupTotal, weighs, Set[Long]())

    val canBe = partitionableForGroup2And3.contains(weighs)

    if (!canBe)
      nonPartitionableForGroup2And3 += weighs

    canBe

  }

  def computeDistributions(groupTotal: Long, weights: Set[Long], group1: Set[Long]): Unit = {

    if (group1.size <= minPackagesInFirstGroup) {
      group1.sum match {
        case x if x > groupTotal => {}
        case x if x == groupTotal =>
          val qe = group1.product
          // we only check if it's partitionable further if we get a smaller QE or if we get a smaller size
          if (qe < minQE || group1.size < minPackagesInFirstGroup) {
            val remainings = weights.filterNot(group1)
            isPartitionable(groupTotal, remainings) match {
              case true => {
                group1.size match {
                  case a if a < minPackagesInFirstGroup =>
                    minPackagesInFirstGroup = a
                    minQE = group1.product
                    // clear the solutions gathered so far
                    solutions = mutable.Set[Set[Long]]()
                    solutions += group1
                    println(group1, minQE)
                  case a if a == minPackagesInFirstGroup =>
                    solutions += group1
                    val newMinQE = group1.product
                    if (newMinQE < minQE) {
                      minQE = newMinQE
                      println(group1, s"new minQE: $minQE")
                    }
                  case _ => {}
                }
              }
              case false => {}
            }
          }


        case x if x < groupTotal =>
          val nexts = getNexts(groupTotal, x, weights, group1)
          nexts
            .foreach(next => computeDistributions(groupTotal, weights, group1 + next))
      }
    }


  }



}
