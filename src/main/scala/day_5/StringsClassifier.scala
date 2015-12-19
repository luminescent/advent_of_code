package day_5

/**
  * Created by christina on 18/12/15.
  */
object StringsClassifier {

  val shouldContain = ((97 until (97 + 26)) map (_.asInstanceOf[Char]) map (c => s"$c$c")) toSet
  val shouldNotContain = Set("ab", "cd", "pq", "xy")
  val vowels = Set('a', 'e', 'i', 'o', 'u')

  def isPalindrome(s: String): Boolean = {
    s.charAt(0) == s.charAt(2)
  }


  def countFirstNiceStrings(input: List[String]): Int = {
    input
      .foldLeft(0)((total, s) =>
        isNice(s) match {
          case true => total + 1
          case _ => total
        })
  }

  def countSecondNiceStrings(input: List[String]): Int = {
    input
      .foldLeft(0)((total, s) =>
        isNice2(s) match {
          case true => total + 1
          case _ => total
        })
  }


  def isNice(s: String): Boolean = {
    shouldContain.exists(s.contains(_)) match {
      case false => false
      case _ => {
        shouldNotContain.exists(s.contains(_)) match {
          case false => {
            s.count(vowels.contains) match {
              case x if x < 3 => false
              case _ =>  true
            }
          }
          case _ => false
        }
      }
    }
  }

  def isNice2(s: String): Boolean = {
    s.sliding(2).exists(a => s.lastIndexOf(a) - s.indexOf(a) > 1) match {
      case false => false
      case _ => s.sliding(3).exists(isPalindrome) match {
        case false => false
        case _ => true
      }
    }
  }

}
