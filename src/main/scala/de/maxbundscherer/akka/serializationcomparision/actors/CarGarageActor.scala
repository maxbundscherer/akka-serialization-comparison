package de.maxbundscherer.akka.serializationcomparision.actors

import akka.actor.{Actor, ActorLogging, Props}

//TODO: Add persistence

object CarGarageActor {

  // ~ Settings ~
  final val persistenceId: String = "carGarageActor"
  final val props: Props          = Props(new CarGarageActor())

  // ~ State ~
  case class CarGarageState()

}

class CarGarageActor extends Actor with ActorLogging {

  import CarGarageActor._
  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate._

  /**
    * Receive
    * @return Receive
    */
  override def receive: Receive = {

    case cmd: CarGarageCmd =>

      processCommand(cmd)

    case msg: Any =>

      log.warning("Get unhandled message in default behavior (" + msg + ")")

  }

  /**
    * Process Command
    * @param cmd CarGarageCmd
    * @return Unit
    */
  private def processCommand(cmd: CarGarageCmd): Unit = cmd match {

    case _: SayHello => sender ! Hello()

  }

}