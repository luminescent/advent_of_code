package day_25


/**
  * Created by christina on 26/12/15.
  */
object DiagonalMatrixValueFinder {

  def getFor(line: Long, column: Long): Long = getForWith0BasedIndexes(line - 1, column - 1)

  private def getForWith0BasedIndexes(line: Long, column: Long): Long =
    column match {
      case 0 => line * (line + 1) / 2  + 1
      case _ => column + getForWith0BasedIndexes(line + column, 0)
    }
}


