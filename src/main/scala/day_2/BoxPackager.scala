package day_2

/**
 * Created by christina on 17/12/15.
 */
object BoxPackager {
  
  case class SortedBoxDimensions(smallest: Int, middle: Int, largest: Int)
  
  def computeBoxWrappings(input: List[String]) : Long = {
    computeBoxes(input, computeBoxWrapping)
  }
  
  def computeRibbonLength(input: List[String]) : Long = {
    computeBoxes(input, computeBoxRibbon)
  }
  
  private def computeBoxes(input: List[String], computator: (SortedBoxDimensions => Int)) = {
    input
      .map(line => {
      val values = line
        .split("x")
        .map(_.toInt)
        .sortBy(x => x)
        .toList

      computator(SortedBoxDimensions(values(0), values(1), values(2)))
    })
      .foldLeft(0L)(_ + _)
  }


  private def computeBoxRibbon(boxDimensions: SortedBoxDimensions): Int = {
    boxDimensions.smallest + boxDimensions.smallest + boxDimensions.middle + boxDimensions.middle + 
    boxDimensions.smallest * boxDimensions.middle * boxDimensions.largest
  }
  
  private def computeBoxWrapping(boxDimensions: SortedBoxDimensions): Int = {
    2 * boxDimensions.smallest * boxDimensions.middle + 
    2 * boxDimensions.smallest * boxDimensions.largest +
    2 * boxDimensions.middle * boxDimensions.largest +
    boxDimensions.smallest * boxDimensions.middle
  }
  

}
