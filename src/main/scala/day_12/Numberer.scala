package day_12

import scala.StringBuilder
import scala.collection.mutable

/**
 * Created by christina on 24/12/15.
 */
object Numberer {
  
  class TokenPositions(var start: Int, var end: Int)
  
  def sum(input: String) = {
    val parts = input.split(Array('[', ',', ']', '}', '{', ':', ' '))

    parts
      .filter(s => s.length > 0)
      .map(s => s(0) match {
            case '"' => 0
            case _ => s.toInt
          }
      )
      .sum
  }
  
  def sumWithoutRed(input: String): Int = {
    val tokensPositionsStack = new mutable.Stack[TokenPositions]()
    val tokensPositions = new mutable.MutableList[TokenPositions]()

    (0 until input.length).foreach(index => input(index) match {
        case '[' | '{' => {
          tokensPositionsStack.push(new TokenPositions(index, 0))
        }
        case ']' | '}' => {
          val token = tokensPositionsStack.pop()
          token.end = index
          tokensPositions += token
        }
        case _ => {}
      }
    )
     
    sumToken(input, tokensPositions.find(_.start == 0).get, tokensPositions)
  }

  def sumToken(input: String, token: TokenPositions, tokenPositions: mutable.MutableList[TokenPositions]): Int = {
    
    // a token sum is a sum of itself + sum of inner tokens (but we consider only the top most tokens)
    val innerTokens = tokenPositions
      .filter { t => t.start > token.start && t.end < token.end}
      .toList
    
    
    val topMostNonOverlappingTokens = innerTokens
      .filter(t => !innerTokens.exists(biggerToken => biggerToken.start < t.start && biggerToken.end > t.end))
      .sortBy(t1 => t1.start)
    

    topMostNonOverlappingTokens.size match {
      case 0 => sumTokenWithoutInners(input.substring(token.start, token.end))
      case _ => {
        val tokenText = getTextInBetweenTokens(input, token, topMostNonOverlappingTokens)
        
        tokenText.indexOf(""":"red"""") match {
          case x if x > 0 => 0
          case _ => {
            val tokenSum = sumTokenWithoutInners(tokenText)
            val innerTokensSum = topMostNonOverlappingTokens
              .map(t => sumToken(input, t, tokenPositions))
              .sum

            tokenSum + innerTokensSum
          }
        }
      }
    }
  }  
  
  def getTextInBetweenTokens(input: String, outerToken: TokenPositions, sortedInnerTokens: List[TokenPositions]): String = {
    val sb = new StringBuilder()
    sb.append(input.substring(outerToken.start, sortedInnerTokens(0).start))

    (0 until sortedInnerTokens.length - 1)
      .map(index => (sortedInnerTokens(index).end, sortedInnerTokens(index + 1).start))
      .foreach { case (i, j) => sb.append(input.substring(i, j))}

    sb.append(input.substring(sortedInnerTokens(sortedInnerTokens.length - 1).end, outerToken.end))

    sb.toString()
  }
  
  def sumTokenWithoutInners(input: String): Int = {
    input.indexOf(""":"red"""") match  {
      case x if x >= 0 => 0
      case _ => sum(input)
    }
  }
  

}
