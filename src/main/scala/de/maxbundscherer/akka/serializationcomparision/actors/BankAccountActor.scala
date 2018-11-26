package de.maxbundscherer.akka.serializationcomparision.actors

import akka.actor.{Actor, ActorLogging, Props}

object BankAccountActor {

  final val persistenceId: String = "bankAccountActor"
  final val props: Props          = Props(classOf[BankAccountActor])

}

class BankAccountActor extends Actor with ActorLogging {

  import BankAccountActor._
  import de.maxbundscherer.akka.serializationcomparision.persistence.BankAccountAggregate._

  override def receive: Receive = {

    case _: SayHelloCmd =>

      log.info("Hello there!")

    case msg: Any => log.warning("Get unhandled message in default behavior (" + msg + ")")

  }

}