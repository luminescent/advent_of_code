package day_11


/**
  * Created by christina on 23/12/15.
  */
object Runner extends App {

  val password = new Password("cqjxjnds")
  println(s"First one: ${password.findNextValid}")
  println(s"Second one: ${password.findNextValid}")

}
