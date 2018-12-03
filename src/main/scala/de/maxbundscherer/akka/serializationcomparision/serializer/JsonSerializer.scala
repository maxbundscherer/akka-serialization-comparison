package de.maxbundscherer.akka.serializationcomparision.serializer

class JsonSerializer extends AbstractSerializer {

  import de.maxbundscherer.akka.serializationcomparision.actors.CarGarageActor.CarGarageState
  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate._

  import io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  /**
    * Serializer Identifier
    * @return Int
    */
  override def identifier: Int = 9002

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

    case AddCarEvtDbManifest      =>

      val value: AddCarEvtDb = decode[AddCarEvtDb](new String(bytes, UTF_8)).right.get
      Converter.FromDbToEntity.addCarEvt(value)

    case UpdateCarEvtDbManifest   =>

      val value: UpdateCarEvtDb = decode[UpdateCarEvtDb](new String(bytes, UTF_8)).right.get
      Converter.FromDbToEntity.updateCarEvt(value)

    case CarGarageStateDbManifest =>

      val value: CarGarageStateDb = decode[CarGarageStateDb](new String(bytes, UTF_8)).right.get
      Converter.FromDbToEntity.carGarageState(value)

    case any: Any => throw new NotImplementedError("Can not deserialize '" + any + "'")

  }

}