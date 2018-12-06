package de.maxbundscherer.akka.serializationcomparision

/**
  * Json Benchmark Test
  */
class JsonTest extends AbstractTest(testName = "json") {

  /**
    * Serializer
    */
  import de.maxbundscherer.akka.serializationcomparision.serializer.AbstractSerializer
  import de.maxbundscherer.akka.serializationcomparision.serializer.JsonSerializer

  /**
    * Set Serializer
    */
  override val serializer: AbstractSerializer = new JsonSerializer()

}