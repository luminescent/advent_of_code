package day_10

/**
  * Created by christina on 22/12/15.
  */
object LookAndSay {

  def getResultLength(times: Int, input: String) : Int = {

    (0 until times).foldLeft(input)((s, c) => process(s)).length

  }

  private def process(input: String) : String = {

    var char = input(0)
    var i = 0
    var sameCharLength = 0
    val stringB = new StringBuilder()

    while (i < input.length) {
      while (i < input.length && input.charAt(i) == char ) {
        i = i + 1
        sameCharLength = sameCharLength + 1
      }
      stringB.append(s"$sameCharLength$char")
      if (i <= input.length - 1) {
        char = input.charAt(i)
        sameCharLength = 0
      }
      else {
        i = i + 1
      }

    }

    println(stringB)
    stringB.toString()
  }

}
