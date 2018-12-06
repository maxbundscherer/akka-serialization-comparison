package de.maxbundscherer.akka.serializationcomparision.utils

trait SimpleTimeMeasurement {

  private final var startTime : Long  = System.nanoTime()

  def startTimeMeasurement()  : Unit  = startTime = System.nanoTime()
  def stopTimeMeasurement()   : Long  = System.nanoTime() - startTime

}