package day_2

/**
 * Created by christina on 17/12/15.
 */
object InputReader {
  
  def read(filePath: String) : Iterator[String] = {

    val source = scala.io.Source.fromFile(filePath)
    source.getLines()
    
  }

}
