package de.maxbundscherer.akka.serializationcomparision.persistence

object CarGarageAggregate {

  sealed trait CarGarageCmd
  sealed trait CarGarageResponse

  case class SayHello() extends CarGarageCmd
  case class Hello()    extends CarGarageResponse

}