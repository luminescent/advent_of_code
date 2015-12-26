package day_20

import scala.collection.mutable.ArrayBuffer

/**
  * Created by christina on 26/12/15.
  */
object HousesVisitors {

  def findHouseWithSmallestNumber(total: Int): Int = {

    // build a sieve with 3 mil
    val sieve = ArrayBuffer[Int](0)

    val max = total/10

    (1 to max)
      .foreach(i => sieve += 10)

    (2 to max)
      .foreach(i =>
        (1 to max/i)
          .foreach(j => sieve(i * j) = sieve(i * j) + i * 10 ))

    sieve.indexWhere(p => p >= total)
  }


  def findHouseWithSmallestNumber2(total: Int): Int = {

    // build a sieve with 3 mil
    val sieve = ArrayBuffer[Int](0)

    val max = total/10

    (1 to 50)
      .foreach(i => sieve += 11)
    (51 to max)
      .foreach(i => sieve += 0)

    (2 to max)
      .foreach(i =>
        (1 to 50)
          .foreach(j => if ((i * j) < max) sieve(i * j) = sieve(i * j) + i * 11 ))

    sieve.indexWhere(p => p >= total)
  }


}
