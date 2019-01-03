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
      def complexCar(dbEntity: ComplexCarDb): ComplexCar = ComplexCar(id = dbEntity.id, horsepower = dbEntity.horsepower, name = dbEntity.name, fuelConsumption = dbEntity.fuelConsumption, dieselEngine = dbEntity.dieselEngine, seatAdjustment = dbEntity.seatAdjustment, fuelTank = dbEntity.fuelTank, brakingDistance = dbEntity.brakingDistance, notes = dbEntity.notes)

      def addCarEvt     (dbEntity: AddCarEvtDb)     : AddCarEvt      = AddCarEvt      ( value = car(dbEntity.value) )
      def updateCarEvt  (dbEntity: UpdateCarEvtDb)  : UpdateCarEvt   = UpdateCarEvt   ( value = car(dbEntity.value) )
      def addComplexCarEvt     (dbEntity: AddComplexCarEvtDb)     : AddComplexCarEvt      = AddComplexCarEvt      ( value = complexCar(dbEntity.value) )
      def updateComplexCarEvt  (dbEntity: UpdateComplexCarEvtDb)  : UpdateComplexCarEvt   = UpdateComplexCarEvt   ( value = complexCar(dbEntity.value) )

      def carGarageState(dbEntity: CarGarageStateDb): CarGarageState = {

        val t1: Map[Int, Car]         = dbEntity.cars.values        .map( c => c.id -> car(c) )        toMap
        val t2: Map[Int, ComplexCar]  = dbEntity.complexCars.values .map( c => c.id -> complexCar(c) ) toMap

        CarGarageState(t1, t2)
      }

    }

    object FromEntityToDb {

      def car       (entity: Car): CarDb = CarDb(id = entity.id, horsepower = entity.horsepower, name = entity.name)
      def complexCar(entity: ComplexCar): ComplexCarDb = ComplexCarDb(id = entity.id, horsepower = entity.horsepower, name = entity.name, fuelConsumption = entity.fuelConsumption, dieselEngine = entity.dieselEngine, seatAdjustment = entity.seatAdjustment, fuelTank = entity.fuelTank, brakingDistance = entity.brakingDistance, notes = entity.notes)

      def addCarEvt     (entity: AddCarEvt)     : AddCarEvtDb      = AddCarEvtDb      ( value = car(entity.value) )
      def updateCarEvt  (entity: UpdateCarEvt)  : UpdateCarEvtDb   = UpdateCarEvtDb   ( value = car(entity.value) )
      def addComplexCarEvt     (entity: AddComplexCarEvt)     : AddComplexCarEvtDb      = AddComplexCarEvtDb      ( value = complexCar(entity.value) )
      def updateComplexCarEvt  (entity: UpdateComplexCarEvt)  : UpdateComplexCarEvtDb   = UpdateComplexCarEvtDb   ( value = complexCar(entity.value) )

      def carGarageState(entity: CarGarageState): CarGarageStateDb = {

        val t1: Map[Int, CarDb]         = entity.cars.values        .map(c => c.id -> car(c) )        toMap
        val t2: Map[Int, ComplexCarDb]  = entity.complexCars.values .map(c => c.id -> complexCar(c) ) toMap

        CarGarageStateDb(t1, t2)
      }

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

    case o: AddComplexCarEvt         =>

      val value: AddComplexCarEvtDb = Converter.FromEntityToDb.addComplexCarEvt(o)
      value.toByteArray

    case o: UpdateComplexCarEvt      =>

      val value: UpdateComplexCarEvtDb = Converter.FromEntityToDb.updateComplexCarEvt(o)
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

    case AddComplexCarEvtManifest      =>

      val value: AddComplexCarEvtDb = AddComplexCarEvtDb.parseFrom(bytes)
      Converter.FromDbToEntity.addComplexCarEvt(value)

    case UpdateComplexCarEvtManifest   =>

      val value: UpdateComplexCarEvtDb = UpdateComplexCarEvtDb.parseFrom(bytes)
      Converter.FromDbToEntity.updateComplexCarEvt(value)

    case CarGarageStateManifest =>

      val value: CarGarageStateDb = CarGarageStateDb.parseFrom(bytes)
      Converter.FromDbToEntity.carGarageState(value)

    case any: Any => throw new NotImplementedError("Can not deserialize '" + any + "'")

  }

}