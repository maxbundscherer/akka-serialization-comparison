package de.maxbundscherer.akka.serializationcomparision.serializer

import akka.serialization.SerializerWithStringManifest
import java.nio.charset.StandardCharsets

abstract class AbstractSerializer extends SerializerWithStringManifest {

  import de.maxbundscherer.akka.serializationcomparision.actors.CarGarageActor.CarGarageState
  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate._

  /**
    * Charset
    */
  final val UTF_8 = StandardCharsets.UTF_8.name()

  /**
    * Models
    */
  case class CarDb(
                    id: Int,
                    horsepower: Int,
                    name: String
                  )
  case class AddCarEvtDb      (value: CarDb)
  case class UpdateCarEvtDb   (value: CarDb)
  case class CarGarageStateDb (cars: Vector[CarDb])

  /**
    * Manifests
    */
  final val AddCarEvtDbManifest       = classOf[AddCarEvtDb]      .getName
  final val UpdateCarEvtDbManifest    = classOf[UpdateCarEvtDb]   .getName
  final val CarGarageStateDbManifest  = classOf[CarGarageStateDb] .getName

  /**
    * Get manifest (getClass.getName) - same for developer-defined manifests
    * @param o Object
    * @return String
    */
  override def manifest(o: AnyRef): String = o.getClass.getName

  /**
    * Converter
    */
  object Converter {

    object FromDbToEntity {

      def car       (dbEntity: CarDb): Car = Car(id = dbEntity.id, horsepower = dbEntity.horsepower, name = dbEntity.name)

      def addCarEvt     (dbEntity: AddCarEvtDb)     : AddCarEvt      = AddCarEvt      ( value = car(dbEntity.value) )
      def updateCarEvt  (dbEntity: UpdateCarEvtDb)  : UpdateCarEvt   = UpdateCarEvt   ( value = car(dbEntity.value) )
      def carGarageState(dbEntity: CarGarageStateDb): CarGarageState = CarGarageState ( cars = dbEntity.cars.map(c => car(c)) )

    }

    object FromEntityToDb {

      def car       (entity: Car): CarDb = CarDb(id = entity.id, horsepower = entity.horsepower, name = entity.name)

      def addCarEvt     (entity: AddCarEvt)     : AddCarEvtDb      = AddCarEvtDb      ( value = car(entity.value) )
      def updateCarEvt  (entity: UpdateCarEvt)  : UpdateCarEvtDb   = UpdateCarEvtDb   ( value = car(entity.value) )
      def carGarageState(entity: CarGarageState): CarGarageStateDb = CarGarageStateDb ( cars = entity.cars.map(c => car(c)) )

    }

  }

}