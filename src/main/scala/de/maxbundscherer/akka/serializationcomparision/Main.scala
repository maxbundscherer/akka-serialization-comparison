package de.maxbundscherer.akka.serializationcomparision

import de.maxbundscherer.akka.serializationcomparision.utils.{Configuration, ExperimentMode, ExperimentRunner}

import akka.util.Timeout
import scala.concurrent.duration._

object Main extends App with Configuration {

  if(Config.ExperimentMode.waitForProfilerEnter) {
    println("(Opt.) Please start profiler and press enter")
    scala.io.StdIn.readLine()
    println("Fine!")
  }

  private implicit val timeout          : Timeout        = Timeout(Config.ExperimentMode.timeoutInSeconds seconds)

  // ~ Run Experiments ~
  new ExperimentRunner( ExperimentMode.JAVA  )
  new ExperimentRunner( ExperimentMode.JSON )
  new ExperimentRunner( ExperimentMode.PROTOBUF )

}