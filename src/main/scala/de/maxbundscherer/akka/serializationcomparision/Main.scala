package de.maxbundscherer.akka.serializationcomparision

import akka.actor.ActorSystem
import akka.util.Timeout
import scala.concurrent.duration._

object Main extends App {

  import de.maxbundscherer.akka.serializationcomparision.services._
  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate._

  private implicit  val timeout     : Timeout        = Timeout(5 seconds)
  private           val actorSystem : ActorSystem    = ActorSystem("system")

  private val carGarageService: CarGarageService = new CarGarageService(actorSystem)

  println(s"Ans from carGarageActor: '${carGarageService.askCarGarageActor(SayHello())}'")

  actorSystem.terminate()
}