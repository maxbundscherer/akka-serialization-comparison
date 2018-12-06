package de.maxbundscherer.akka.serializationcomparision

/**
  * Protobuf Benchmark Test
  */
class ProtobufTest extends AbstractTest(testName = "protobuf") {

  /**
    * Serializer
    */
  import de.maxbundscherer.akka.serializationcomparision.serializer.AbstractSerializer
  import de.maxbundscherer.akka.serializationcomparision.serializer.ProtobufSerializer

  /**
    * Set Serializer
    */
  override val serializer: AbstractSerializer = new ProtobufSerializer()

}