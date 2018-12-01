package de.maxbundscherer.akka.serializationcomparision.serializer

import akka.serialization.SerializerWithStringManifest
import java.nio.charset.StandardCharsets

import io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

class JsonSerializer extends SerializerWithStringManifest {

  import de.maxbundscherer.akka.serializationcomparision.actors.CarGarageActor.CarGarageState
  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate._

  /**
    * Charset
    */
  final val UTF_8 = StandardCharsets.UTF_8.name()

  /**
    * Manifests
    */
  final val AddCarEvtManifest       = classOf[AddCarEvt]      .getName
  final val UpdateCarEvtManifest    = classOf[UpdateCarEvt]   .getName
  final val CarGarageStateManifest  = classOf[CarGarageState] .getName

  /**
    * Serializer Identifier
    * @return Int
    */
  override def identifier: Int = 9001

  /**
    * Get manifest (getClass.getName) - same for developer-defined manifests
    * @param o Object
    * @return String
    */
  override def manifest(o: AnyRef): String = o.getClass.getName

  /**
    * Convert from entity to binary
    * @param o Entity
    * @return Binary
    */
  override def toBinary(o: AnyRef): Array[Byte] = o match {

    case AddCarEvt(_)       =>   o.asInstanceOf[AddCarEvt]      .asJson.toString().getBytes(UTF_8)
    case UpdateCarEvt(_)    =>   o.asInstanceOf[UpdateCarEvt]   .asJson.toString().getBytes(UTF_8)
    case CarGarageState(_)  =>   o.asInstanceOf[CarGarageState] .asJson.toString().getBytes(UTF_8)

    case any: Any => throw new NotImplementedError("Can not serialize '" + any + "'")

  }

  /**
    * Convert from binary to entity
    * @param bytes Binary
    * @param manifest String
    * @return Entity
    */
  override def fromBinary(bytes: Array[Byte], manifest: String): AnyRef = manifest match {


    case AddCarEvtManifest      => decode[AddCarEvt]      (new String(bytes, UTF_8)).right.get
    case UpdateCarEvtManifest   => decode[UpdateCarEvt]   (new String(bytes, UTF_8)).right.get
    case CarGarageStateManifest => decode[CarGarageState] (new String(bytes, UTF_8)).right.get

    case any: Any => throw new NotImplementedError("Can not deserialize '" + any + "'")

  }

}