package de.maxbundscherer.akka.serializationcomparision.persistence

object CarGarageAggregate {

  // ~ Traits ~
  trait CarGarageCmd
  trait CarGarageEvt
  trait CarGarageResponse

  // ~ Models ~
  case class Car(
                  id: Int,
                  horsepower: Int,
                  name: String
                )

  // ~ Command and Evt ~
  case class AddCarCmd(value: Car)            extends CarGarageCmd
  case class AddCarEvt(value: Car)            extends CarGarageEvt

  case class UpdateCarCmd(value: Car)         extends CarGarageCmd
  case class UpdateCarEvt(value: Car)         extends CarGarageEvt

  // ~ Response ~
  case class CarGarageSuccess()               extends CarGarageResponse
  case class CarNotFound()                    extends CarGarageResponse
  case class CarAlreadyExists()               extends CarGarageResponse

  // ~ Request  ~
  case class StartTimeMeasurementCmd()        extends CarGarageCmd
  case class SimulateCrashCmd()               extends CarGarageCmd

  // ~ Request and Response
  case class GetAllCarCmd()                   extends CarGarageCmd
  case class GetAllCar(value: Vector[Car])    extends CarGarageResponse

}