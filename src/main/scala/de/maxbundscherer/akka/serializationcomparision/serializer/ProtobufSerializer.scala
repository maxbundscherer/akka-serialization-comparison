package de.maxbundscherer.akka.serializationcomparision.serializer

/**
  * Protobuf Serializer
  */
class ProtobufSerializer extends AbstractSerializer(serializerIdentifier = 9003) {

  import de.maxbundscherer.akka.serializationcomparision.actors.CarGarageActor.CarGarageState
  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate._

  /**
    * Models
    */
  import de.maxbundscherer.akka.serializationcomparision.persistence.protobuf._

  /**
    * Converter
    */
  object Converter {

    object FromDbToEntity {

      def car       (dbEntity: CarDb): Car = Car(id = dbEntity.id, horsepower = dbEntity.horsepower, name = dbEntity.name)

      def addCarEvt     (dbEntity: AddCarEvtDb)     : AddCarEvt      = AddCarEvt      ( value = car(dbEntity.value) )
      def updateCarEvt  (dbEntity: UpdateCarEvtDb)  : UpdateCarEvt   = UpdateCarEvt   ( value = car(dbEntity.value) )
      def carGarageState(dbEntity: CarGarageStateDb): CarGarageState = CarGarageState ( cars = dbEntity.cars.map(c => car(c)).toVector )

    }

    object FromEntityToDb {

      def car       (entity: Car): CarDb = CarDb(id = entity.id, horsepower = entity.horsepower, name = entity.name)

      def addCarEvt     (entity: AddCarEvt)     : AddCarEvtDb      = AddCarEvtDb      ( value = car(entity.value) )
      def updateCarEvt  (entity: UpdateCarEvt)  : UpdateCarEvtDb   = UpdateCarEvtDb   ( value = car(entity.value) )
      def carGarageState(entity: CarGarageState): CarGarageStateDb = CarGarageStateDb ( cars = entity.cars.map(c => car(c)) )

    }

  }

  /**
    * Convert from entity to binary
    * @param o Entity
    * @return Binary
    */
  override def toBinary(o: AnyRef): Array[Byte] = o match {

    case o: AddCarEvt         =>

      val value: AddCarEvtDb = Converter.FromEntityToDb.addCarEvt(o)
      value.toByteArray

    case o: UpdateCarEvt      =>

      val value: UpdateCarEvtDb = Converter.FromEntityToDb.updateCarEvt(o)
      value.toByteArray

    case o: CarGarageState    =>

      val value: CarGarageStateDb = Converter.FromEntityToDb.carGarageState(o)
      value.toByteArray

    case any: Any => throw new NotImplementedError("Can not serialize '" + any + "'")

  }

  /**
    * Convert from binary to entity
    * @param bytes Binary
    * @param manifest String
    * @return Entity
    */
  override def fromBinary(bytes: Array[Byte], manifest: String): AnyRef = manifest match {

    case AddCarEvtManifest      =>

      val value: AddCarEvtDb = AddCarEvtDb.parseFrom(bytes)
      Converter.FromDbToEntity.addCarEvt(value)

    case UpdateCarEvtManifest   =>

      val value: UpdateCarEvtDb = UpdateCarEvtDb.parseFrom(bytes)
      Converter.FromDbToEntity.updateCarEvt(value)

    case CarGarageStateManifest =>

      val value: CarGarageStateDb = CarGarageStateDb.parseFrom(bytes)
      Converter.FromDbToEntity.carGarageState(value)

    case any: Any => throw new NotImplementedError("Can not deserialize '" + any + "'")

  }

}