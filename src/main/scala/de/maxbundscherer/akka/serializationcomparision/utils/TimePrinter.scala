package de.maxbundscherer.akka.serializationcomparision.utils

import java.util.Calendar

object TimePrinter {

  /**
    * Prints actual time with prefix
    * @param prefix String
    */
  def printActualTimeForLog(prefix: String): Unit = {

    val now = Calendar.getInstance()

    val h = now.get(Calendar.HOUR)
    val m = now.get(Calendar.MINUTE)
    val s = now.get(Calendar.SECOND)
    val ms = now.get(Calendar.MILLISECOND)

    println(s"$prefix: $h:$m:$s:$ms")

  }

}