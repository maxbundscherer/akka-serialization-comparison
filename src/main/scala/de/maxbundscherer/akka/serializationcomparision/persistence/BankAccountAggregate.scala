package de.maxbundscherer.akka.serializationcomparision.persistence

object BankAccountAggregate {

  sealed trait BankAccountRequest
  sealed trait BankAccountResponse

  case class SayHello() extends BankAccountRequest
  case class Hello()    extends BankAccountResponse

}