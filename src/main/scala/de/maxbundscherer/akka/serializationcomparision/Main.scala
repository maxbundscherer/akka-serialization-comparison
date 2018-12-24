package de.maxbundscherer.akka.serializationcomparision

import de.maxbundscherer.akka.serializationcomparision.utils.{Configuration, ExperimentMode, ExperimentRunner}

import akka.util.Timeout
import scala.concurrent.duration._

object Main extends App with Configuration {

  private implicit val timeout          : Timeout        = Timeout(Config.ExperimentMode.timeoutInSeconds seconds)

  // ~ Run Experiments ~
  new ExperimentRunner( ExperimentMode.JAVA  )
  new ExperimentRunner( ExperimentMode.JSON )
  new ExperimentRunner( ExperimentMode.PROTOBUF )

}