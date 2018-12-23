package de.maxbundscherer.akka.serializationcomparision.serializer

/**
  * JsonSerializer
  */
class JsonSerializer extends AbstractSerializer(serializerIdentifier = 9002) {

  import de.maxbundscherer.akka.serializationcomparision.actors.CarGarageActor.CarGarageState
  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate._

  import java.nio.charset.StandardCharsets
  import io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  /**
    * Models
    */
  import de.maxbundscherer.akka.serializationcomparision.persistence.json._

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

      def carGarageState(dbEntity: CarGarageStateDb): CarGarageState = CarGarageState ( cars = dbEntity.cars.map(c => car(c)).toVector, complexCars = dbEntity.complexCars.map(c => complexCar(c)).toVector )

    }

    object FromEntityToDb {

      def car       (entity: Car): CarDb = CarDb(id = entity.id, horsepower = entity.horsepower, name = entity.name)
      def complexCar(entity: ComplexCar): ComplexCarDb = ComplexCarDb(id = entity.id, horsepower = entity.horsepower, name = entity.name, fuelConsumption = entity.fuelConsumption, dieselEngine = entity.dieselEngine, seatAdjustment = entity.seatAdjustment, fuelTank = entity.fuelTank, brakingDistance = entity.brakingDistance, notes = entity.notes)

      def addCarEvt     (entity: AddCarEvt)     : AddCarEvtDb      = AddCarEvtDb      ( value = car(entity.value) )
      def updateCarEvt  (entity: UpdateCarEvt)  : UpdateCarEvtDb   = UpdateCarEvtDb   ( value = car(entity.value) )
      def addComplexCarEvt     (entity: AddComplexCarEvt)     : AddComplexCarEvtDb      = AddComplexCarEvtDb      ( value = complexCar(entity.value) )
      def updateComplexCarEvt  (entity: UpdateComplexCarEvt)  : UpdateComplexCarEvtDb   = UpdateComplexCarEvtDb   ( value = complexCar(entity.value) )

      def carGarageState(entity: CarGarageState): CarGarageStateDb = CarGarageStateDb ( cars = entity.cars.map(c => car(c)), complexCars = entity.complexCars.map(c => complexCar(c)))

    }

  }

  /**
    * Charset
    */
  final val UTF_8 = StandardCharsets.UTF_8.name()

  /**
    * Convert from entity to binary
    * @param o Entity
    * @return Binary
    */
  override def toBinary(o: AnyRef): Array[Byte] = o match {

    case o: AddCarEvt         =>

      val value: AddCarEvtDb = Converter.FromEntityToDb.addCarEvt(o)
      value.asJson.toString().getBytes(UTF_8)

    case o: UpdateCarEvt      =>

      val value: UpdateCarEvtDb = Converter.FromEntityToDb.updateCarEvt(o)
      value.asJson.toString().getBytes(UTF_8)

    case o: AddComplexCarEvt         =>

      val value: AddComplexCarEvtDb = Converter.FromEntityToDb.addComplexCarEvt(o)
      value.asJson.toString().getBytes(UTF_8)

    case o: UpdateComplexCarEvt      =>

      val value: UpdateComplexCarEvtDb = Converter.FromEntityToDb.updateComplexCarEvt(o)
      value.asJson.toString().getBytes(UTF_8)

    case o: CarGarageState    =>

      val value: CarGarageStateDb = Converter.FromEntityToDb.carGarageState(o)
      value.asJson.toString().getBytes(UTF_8)

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

      val value: AddCarEvtDb = decode[AddCarEvtDb](new String(bytes, UTF_8)).right.get
      Converter.FromDbToEntity.addCarEvt(value)

    case UpdateCarEvtManifest   =>

      val value: UpdateCarEvtDb = decode[UpdateCarEvtDb](new String(bytes, UTF_8)).right.get
      Converter.FromDbToEntity.updateCarEvt(value)

    case AddComplexCarEvtManifest      =>

      val value: AddComplexCarEvtDb = decode[AddComplexCarEvtDb](new String(bytes, UTF_8)).right.get
      Converter.FromDbToEntity.addComplexCarEvt(value)

    case UpdateComplexCarEvtManifest   =>

      val value: UpdateComplexCarEvtDb = decode[UpdateComplexCarEvtDb](new String(bytes, UTF_8)).right.get
      Converter.FromDbToEntity.updateComplexCarEvt(value)

    case CarGarageStateManifest =>

      val value: CarGarageStateDb = decode[CarGarageStateDb](new String(bytes, UTF_8)).right.get
      Converter.FromDbToEntity.carGarageState(value)

    case any: Any => throw new NotImplementedError("Can not deserialize '" + any + "'")

  }

}