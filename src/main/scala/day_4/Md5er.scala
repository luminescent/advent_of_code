package day_4

import java.security.MessageDigest

/**
  * Created by christina on 17/12/15.
  */
object Md5er {

  def findMinSeedIterator(input: String): Int = {
    Iterator
      .from(1)
      .find(i => {
        val s = s"$input$i"
        val md5s = md5(s).map("%02X".format(_)).mkString
        md5s.startsWith("00000")
      })
      .get
  }


  private def md5(s: String) = {
    MessageDigest.getInstance("MD5").digest(s.getBytes)
  }

}
