package de.maxbundscherer.akka.serializationcomparision.services

import akka.actor.ActorSystem
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.{Await, Future}

/**
  * CarGarage Service
  * @param actorSystem ActorSystem
  * @param timeout Timeout
  */
class CarGarageService(actorSystem: ActorSystem, actorNamePostfix: String)(implicit timeout: Timeout) {

  import de.maxbundscherer.akka.serializationcomparision.actors._
  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate._

  /**
    * Start carGarageActor with custom postfix in actorSystem
    */
  private val carGarageActor  = actorSystem.actorOf( CarGarageActor.props(actorNamePostfix) )

  /**
    * Ask carGarageActor and wait for response
    * @param cmd CarGarageCmd
    * @return CarGarageResponse
    */
  def askCarGarageActor(cmd: CarGarageCmd): CarGarageResponse =

    Await.result( (carGarageActor ? cmd).asInstanceOf[Future[CarGarageResponse]], timeout.duration )

}