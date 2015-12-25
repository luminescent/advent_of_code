package day_14

import scala.collection.mutable

/**
  * Created by christina on 24/12/15.
  */
object ReindeerContest {

  case class ReindeerFly(name: String, speed: Int, flyTime: Int, restTime: Int)

  def getMaxDistance(lines: List[String], total: Int) : Int = {

    val regex = List(" can fly ", " km/s for ", " seconds, but then must rest for ", " seconds\\.").mkString("|").r

    lines
      .map(line => regex.split(line) match {
        case Array(name, speed, flyTime, restTime) => ReindeerFly(name, speed.toInt, flyTime.toInt, restTime.toInt)
        case _ => throw new Exception(s"Unable to parse $line")
      })
      .map(rf => computeDistance(rf, total))
      .max
  }


  def getMaxPoints(lines: List[String], total: Int) : Int = {

    val regex = List(" can fly ", " km/s for ", " seconds, but then must rest for ", " seconds\\.").mkString("|").r

    val reindeers = lines
      .map(line => regex.split(line) match {
        case Array(name, speed, flyTime, restTime) => ReindeerFly(name, speed.toInt, flyTime.toInt, restTime.toInt)
        case _ => throw new Exception(s"Unable to parse $line")
      })

    val points = mutable.Map[String, Int]()

    (1 to total)
      .foreach(seconds => {
        val winner = getMaxReindeer(reindeers, seconds)
        val stars = points.getOrElse(winner._1.name, 0)
        points(winner._1.name) = stars + 1
      })

    points.maxBy(_._2)._2
  }

  private def getMaxReindeer(reindeers: List[ReindeerFly], total: Int)  = {

    reindeers
      .map(rf => rf -> computeDistance(rf, total))
      .maxBy(_._2)
  }

  private def computeDistance(rf: ReindeerFly, total: Int): Int = {
    val totalFlyAndRest = (total) / (rf.flyTime + rf.restTime)
    val distance = totalFlyAndRest * rf.speed * rf.flyTime

    val rest = total - totalFlyAndRest * (rf.flyTime + rf.restTime)

    distance + (rest match {
      case x if x <= rf.flyTime => x * rf.speed
      case _ => rf.flyTime * rf.speed
    })
  }

}
