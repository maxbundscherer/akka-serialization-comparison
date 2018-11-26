package de.maxbundscherer.akka.serializationcomparision

import akka.actor.ActorSystem
import akka.util.Timeout
import scala.concurrent.duration._

object Main extends App {

  import de.maxbundscherer.akka.serializationcomparision.services._
  import de.maxbundscherer.akka.serializationcomparision.persistence.BankAccountAggregate._

  private implicit  val timeout     : Timeout        = Timeout(5 seconds)
  private           val actorSystem : ActorSystem    = ActorSystem("system")

  private val bankAccountService: BankAccountService = new BankAccountService(actorSystem)

  println(s"Ans from bankAccount actor: '${bankAccountService.askBankAccountActor(SayHello())}'")

  actorSystem.terminate()
}