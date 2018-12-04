package de.maxbundscherer.akka.serializationcomparision.serializer

/**
  * JavaSerializer
  */
class JavaSerializer extends AbstractSerializer(serializerIdentifier = 9001) {

  import de.maxbundscherer.akka.serializationcomparision.actors.CarGarageActor.CarGarageState
  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate._

  import java.io._

  /**
    * Models
    */
  import de.maxbundscherer.akka.serializationcomparision.persistence.java._

  /**
    * Converter
    */
  object Converter {

    object FromDbToEntity {

      def car       (dbEntity: CarDb): Car = Car(id = dbEntity.getId, horsepower = dbEntity.getHorsepower, name = dbEntity.getName)

      def addCarEvt     (dbEntity: AddCarEvtDb)     : AddCarEvt      = AddCarEvt      ( car(dbEntity.getValue) )
      def updateCarEvt  (dbEntity: UpdateCarEvtDb)  : UpdateCarEvt   = UpdateCarEvt   ( car(dbEntity.getValue) )
      def carGarageState(dbEntity: CarGarageStateDb): CarGarageState = CarGarageState ( dbEntity.getCars.toVector.map(c => car(c)) )

    }

    object FromEntityToDb {

      def car       (entity: Car): CarDb = new CarDb(entity.id, entity.horsepower, entity.name)

      def addCarEvt     (entity: AddCarEvt)     : AddCarEvtDb      = new AddCarEvtDb      ( car(entity.value) )
      def updateCarEvt  (entity: UpdateCarEvt)  : UpdateCarEvtDb   = new UpdateCarEvtDb   ( car(entity.value) )
      def carGarageState(entity: CarGarageState): CarGarageStateDb = new CarGarageStateDb ( entity.cars.map(c => car(c)).toArray )

    }

  }

  /**
    * Convert to Java Array of Byte
    * @param o java.io.Serializable
    * @return Java Array of Byte
    */
  private def toJavaByteArray(o: java.io.Serializable): Array[Byte] = {

    val byteArrayOutputStream : ByteArrayOutputStream = new ByteArrayOutputStream
    val objectOutputStream    : ObjectOutputStream    = new ObjectOutputStream(byteArrayOutputStream)

    objectOutputStream.writeObject(o)

    objectOutputStream.close()
    byteArrayOutputStream.close()

    byteArrayOutputStream.toByteArray
  }

  /**
    * Convert from Java Array of Byte
    * @param bytes Java Array of Byte
    * @tparam ObjectType ClassOf Object
    * @return Object
    */
  private def fromJavaByteArray[ObjectType](bytes: Array[Byte]) : ObjectType = {

    val byteArrayInputStream  : ByteArrayInputStream = new ByteArrayInputStream(bytes)
    val objectInputStream     : ObjectInputStream    = new ObjectInputStream(byteArrayInputStream)

    val ans: ObjectType = objectInputStream.readObject().asInstanceOf[ObjectType]

    objectInputStream.close()
    byteArrayInputStream.close()

    ans
  }

  /**
    * Convert from entity to binary
    * @param o Entity
    * @return Binary
    */
  override def toBinary(o: AnyRef): Array[Byte] = o match {

    case o: AddCarEvt         =>

      val value: AddCarEvtDb = Converter.FromEntityToDb.addCarEvt(o)
      toJavaByteArray(value)

    case o: UpdateCarEvt      =>

      val value: UpdateCarEvtDb = Converter.FromEntityToDb.updateCarEvt(o)
      toJavaByteArray(value)

    case o: CarGarageState    =>

      val value: CarGarageStateDb = Converter.FromEntityToDb.carGarageState(o)
      toJavaByteArray(value)

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

      val value: AddCarEvtDb = fromJavaByteArray[AddCarEvtDb](bytes)
      Converter.FromDbToEntity.addCarEvt(value)

    case UpdateCarEvtManifest   =>

      val value: UpdateCarEvtDb = fromJavaByteArray[UpdateCarEvtDb](bytes)
      Converter.FromDbToEntity.updateCarEvt(value)

    case CarGarageStateManifest =>

      val value: CarGarageStateDb = fromJavaByteArray[CarGarageStateDb](bytes)
      Converter.FromDbToEntity.carGarageState(value)

    case any: Any => throw new NotImplementedError("Can not deserialize '" + any + "'")

  }

}