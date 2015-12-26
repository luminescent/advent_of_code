package day_21

/**
  * Created by christina on 26/12/15.
  */
object Runner extends App {

  println(s"Min gold to spend: ${ Game findMinGold() }")
  println(s"Max gold to loose: ${ Game findMaxGoldToLoose() }")
}
