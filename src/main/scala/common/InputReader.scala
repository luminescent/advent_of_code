package common

import scala.io.Source

/**
  * Created by christina on 16/12/15.
  */
object InputReader {

  def read(filePath: String): String = {

    val source = Source.fromFile(filePath)
    val lines = try source.mkString finally source.close()

    lines
  }

  def readLines(filePath: String) : List[String] = {

    val source = Source.fromFile(filePath)
    val lines = try source.getLines() toList finally source.close()

    lines
  }
}
