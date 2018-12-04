package de.maxbundscherer.akka.serializationcomparision.serializer

import akka.serialization.SerializerWithStringManifest

/**
  * AbstractSerializer
  * @param serializerIdentifier Int
  */
abstract class AbstractSerializer(serializerIdentifier: Int) extends SerializerWithStringManifest {

  import de.maxbundscherer.akka.serializationcomparision.actors.CarGarageActor.CarGarageState
  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate._

  /**
    * Get manifest (getClass.getName) - same for developer-defined manifests
    * @param o Object
    * @return String
    */
  override def manifest(o: AnyRef): String = o.getClass.getName

  /**
    * Serializer Identifier
    * @return Int
    */
  override def identifier: Int = serializerIdentifier

  /**
    * Manifests
    */
  final val AddCarEvtManifest       = classOf[AddCarEvt]      .getName
  final val UpdateCarEvtManifest    = classOf[UpdateCarEvt]   .getName
  final val CarGarageStateManifest  = classOf[CarGarageState] .getName

}