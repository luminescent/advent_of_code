package day_15

/**
  * Created by christina on 25/12/15.
  */
object CookiesTester {

  case class Cookie(name: String, capacity: Int, durability: Int, flavour: Int, texture: Int, calories: Int)

  def getMaxValue(lines: List[String], quantity: Int, caloriesTotal: Int) = {
    val regex = List(": capacity ", ", durability ", ", flavor ", ", texture ", ", calories ").mkString("|").r

    val cookies = lines
      .map(line =>
        regex.split(line) match {
          case Array(name, capacity, durability, flavour, texture, calories) =>
            Cookie(name, capacity.toInt, durability.toInt, flavour.toInt, texture.toInt, calories.toInt)
          case _ => throw new Exception(s"Unable to parse $line")
        })

    generateAndEvaluatePartition(quantity, caloriesTotal, List[Int](), cookies)

    max

  }


  var max = -1000

  def generateAndEvaluatePartition(partitionTotal: Int, caloriesTotal: Int, partition: List[Int], cookies: List[Cookie]) : Unit = {
    evaluateCalories(cookies, partition) match {
      case x if x <= caloriesTotal =>
        partition.length match {
          case x if x == cookies.length =>
            val cookiesScore = evaluateCookies(caloriesTotal, cookies, partition)
            if (max < cookiesScore) {
              println(partition)
              max = cookiesScore
            }
          case x if x == cookies.length - 1 =>
            generateAndEvaluatePartition(partitionTotal, caloriesTotal, partition :+ (partitionTotal - partition.sum), cookies)
          case _ =>
            (0 to partitionTotal - partition.sum)
              .foreach(next => generateAndEvaluatePartition(partitionTotal, caloriesTotal, partition :+ next, cookies))
        }
      case _ =>
    }
  }

  def evaluateCalories(cookies: List[Cookie], distribution: List[Int]): Int = {
    (0 until Math.min(cookies.length, distribution.length))
      .map(c => cookies(c).calories * distribution(c))
      .sum
  }

  def evaluateCookies(caloriesTotal: Int, cookies: List[Cookie], distribution: List[Int]): Int = {
    val capacity = cookies.indices
      .map(c => cookies(c).capacity * distribution(c))
      .sum

    val flavour = cookies.indices
      .map(c => cookies(c).flavour * distribution(c))
      .sum

    val durability = cookies.indices
      .map(c => cookies(c).durability * distribution(c))
      .sum

    val texture = cookies.indices
      .map(c => cookies(c).texture * distribution(c))
      .sum

    val calories = cookies.indices
      .map(c => cookies(c).calories * distribution(c))
      .sum

    calories match {
      case x if x == caloriesTotal => math.max(capacity, 0) * math.max(flavour, 0) * math.max(durability, 0) * math.max(texture, 0)
      case _ => 0
    }

  }

}
