package de.maxbundscherer.akka.serializationcomparision.persistence

object CarGarageAggregate {

  trait CarGarageCmd
  trait CarGarageEvt
  trait CarGarageResponse

  case class IncrementCmd()                 extends CarGarageCmd
  case class IncrementEvt()                 extends CarGarageEvt
  case class IncrementSuccess(balance: Int) extends CarGarageResponse

}