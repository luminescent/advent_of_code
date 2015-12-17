package common

/**
  * Created by christina on 16/12/15.
  */
object InputReader {

  def read(filePath: String): String = {

    val source = scala.io.Source.fromFile(filePath)
    val lines = try source.mkString finally source.close()

    lines
  }
}
