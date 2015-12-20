package day_8

/**
  * Created by christina on 20/12/15.
  */
object StringsCounter {

  def diffLiteralsMemory(lines: List[String]): Int = {
    val counts = lines.map(computeLine)

    counts.map(_._1).sum - counts.map(_._2).sum
  }

  def diffEscapedMemory(lines: List[String]): Int = {

    val escapedLines = lines
      .map(line => s""""${line.replace("""\""", """\\""").replace(""""""", """\"""")}"""")

    diffLiteralsMemory(escapedLines)
  }

  def computeLine(line: String): (Int, Int) = {
    var literals = 2
    var memory = 0

    var i = 1
    while (i < line.length - 1) {
      line(i) match {
        case '\\' => {
          i = i + 1
          line(i) match {
            case 'x' => {
              i = i + 2
              literals += 4
              memory += 1
            }
            case '\\' => {
              literals += 2
              memory += 1
            }
            case '"' => {
              literals += 2
              memory += 1
            }
          }
        }
        case _ => {
          literals += 1
          memory += 1
        }
      }
      i = i + 1
    }


    (literals, memory)
  }


}
