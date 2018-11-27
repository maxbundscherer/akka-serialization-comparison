package de.maxbundscherer.akka.serializationcomparision.persistence

object CarGarageAggregate {

  trait CarGarageCmd
  trait CarGarageEvt
  trait CarGarageResponse

  case class SayHello() extends CarGarageCmd
  case class Hello()    extends CarGarageResponse

}