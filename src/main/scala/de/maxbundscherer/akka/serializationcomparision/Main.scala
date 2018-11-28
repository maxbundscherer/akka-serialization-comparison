package de.maxbundscherer.akka.serializationcomparision

import de.maxbundscherer.akka.serializationcomparision.utils.{ExperimentMode, ExperimentRunner}

import akka.util.Timeout
import scala.concurrent.duration._

object Main extends App {

  private implicit val timeout          : Timeout        = Timeout(5 seconds)

  // ~ Run Experiments ~
  new ExperimentRunner( ExperimentMode.JAVA )
  new ExperimentRunner( ExperimentMode.JSON )
  new ExperimentRunner( ExperimentMode.PROTOBUF )

}