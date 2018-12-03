package de.maxbundscherer.akka.serializationcomparision.serializer

import akka.serialization.SerializerWithStringManifest

/**
  * AbstractSerializer
  * @param serializerIdentifier Int
  */
abstract class AbstractSerializer(serializerIdentifier: Int) extends SerializerWithStringManifest {

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

}