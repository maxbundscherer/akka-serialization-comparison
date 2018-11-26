package de.maxbundscherer.akka.serializationcomparision.services

import akka.actor.ActorSystem
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.{Await, Future}

/**
  * BankAccount Service
  * @param actorSystem ActorSystem
  * @param timeout Timeout
  */
class BankAccountService(actorSystem: ActorSystem)(implicit val timeout: Timeout) {

  import de.maxbundscherer.akka.serializationcomparision.actors._
  import de.maxbundscherer.akka.serializationcomparision.persistence.BankAccountAggregate._

  /**
    * Start bankAccount actor in actorSystem
    */
  private val bankAccountActor  = actorSystem.actorOf( BankAccountActor.props )

  /**
    * Ask bankAccount actor and wait for response
    * @param req BankAccountRequest
    * @return BankAccountResponse
    */
  def askBankAccountActor(req: BankAccountRequest): BankAccountResponse =

    Await.result( (bankAccountActor ? req).asInstanceOf[Future[BankAccountResponse]], timeout.duration )

}