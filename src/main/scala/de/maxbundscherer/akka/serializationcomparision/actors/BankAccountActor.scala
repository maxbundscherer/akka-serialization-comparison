package de.maxbundscherer.akka.serializationcomparision.actors

import akka.actor.{Actor, ActorLogging}

class BankAccountActor extends Actor with ActorLogging {

  import de.maxbundscherer.akka.serializationcomparision.persistence.BankAccountAggregate._

  override def receive: Receive = {

    case _: SayHelloCmd =>

      log.info("Hello there!")

    case msg: Any => log.warning("Get unhandled message in default behavior (" + msg + ")")

  }

}