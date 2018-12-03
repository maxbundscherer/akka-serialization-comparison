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

    case AddCarEvtDbManifest      =>

      val value: AddCarEvtDb = fromJavaByteArray[AddCarEvtDb](bytes)
      Converter.FromDbToEntity.addCarEvt(value)

    case UpdateCarEvtDbManifest   =>

      val value: UpdateCarEvtDb = fromJavaByteArray[UpdateCarEvtDb](bytes)
      Converter.FromDbToEntity.updateCarEvt(value)

    case CarGarageStateDbManifest =>

      val value: CarGarageStateDb = fromJavaByteArray[CarGarageStateDb](bytes)
      Converter.FromDbToEntity.carGarageState(value)

    case any: Any => throw new NotImplementedError("Can not deserialize '" + any + "'")

  }

}