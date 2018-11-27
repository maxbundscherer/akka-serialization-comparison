package de.maxbundscherer.akka.serializationcomparision

import de.maxbundscherer.akka.serializationcomparision.utils.{ExperimentMode, ExperimentRunner}

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.util.Timeout
import scala.concurrent.duration._

object Main extends App {

  //TODO: Change root logger
  private          val rootActorSystem  : ActorSystem    = ActorSystem("actorSystem-ROOT")
  private implicit val rootLogger       : LoggingAdapter = rootActorSystem.log
  private implicit val timeout          : Timeout        = Timeout(5 seconds)

  // ~ Run Experiments ~
  new ExperimentRunner( ExperimentMode.JAVA )
  //new ExperimentRunner( ExperimentMode.JSON )
  //new ExperimentRunner( ExperimentMode.PROTOBUF )

  rootActorSystem.terminate()
}