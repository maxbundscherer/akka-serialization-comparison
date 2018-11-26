package de.maxbundscherer.akka.serializationcomparision.actors

import akka.actor.{Actor, ActorLogging, Props}

//TODO: Add persistence

object BankAccountActor {

  // ~ Settings ~
  final val persistenceId: String = "bankAccountActor"
  final val props: Props          = Props(new BankAccountActor())

  // ~ State ~
  case class BankAccountState(balance: Int)

}

class BankAccountActor extends Actor with ActorLogging {

  import BankAccountActor._
  import de.maxbundscherer.akka.serializationcomparision.persistence.BankAccountAggregate._

  /**
    * Receive
    * @return Receive
    */
  override def receive: Receive = {

    case req: BankAccountRequest =>

      processRequest(req)

    case msg: Any =>

      log.warning("Get unhandled message in default behavior (" + msg + ")")

  }

  /**
    * Process Request
    * @param req BankAccountRequest
    * @return Unit
    */
  private def processRequest(req: BankAccountRequest): Unit = req match {

    case req: SayHello => sender ! Hello()

  }

}