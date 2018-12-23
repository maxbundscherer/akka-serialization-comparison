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

  case class ComplexCar(
                  id: Int,
                  horsepower: Int,
                  name: String,
                  fuelConsumption: Float,
                  dieselEngine: Boolean,
                  seatAdjustment: Boolean,
                  fuelTank: Double,
                  brakingDistance: Double,
                  notes: String
                )

  // ~ Command and Evt ~
  case class AddCarCmd(value: Car)            extends CarGarageCmd
  case class AddCarEvt(value: Car)            extends CarGarageEvt

  case class AddComplexCarCmd(value: ComplexCar) extends CarGarageCmd
  case class AddComplexCarEvt(value: ComplexCar) extends CarGarageEvt

  case class UpdateCarCmd(value: Car)         extends CarGarageCmd
  case class UpdateCarEvt(value: Car)         extends CarGarageEvt

  case class UpdateComplexCarCmd(value: ComplexCar) extends CarGarageCmd
  case class UpdateComplexCarEvt(value: ComplexCar) extends CarGarageEvt

  // ~ Response ~
  case class CarGarageSuccess()               extends CarGarageResponse
  case class CarNotFound()                    extends CarGarageResponse
  case class CarAlreadyExists()               extends CarGarageResponse

  // ~ Request  ~
  case class SimulateCrashCmd()               extends CarGarageCmd

  // ~ Request and Response
  case class GetAllCarCmd()                   extends CarGarageCmd
  case class GetAllCar(value: Vector[Car])    extends CarGarageResponse

  case class GetAllComplexCarCmd()                   extends CarGarageCmd
  case class GetAllComplexCar(value: Vector[ComplexCar])    extends CarGarageResponse

}