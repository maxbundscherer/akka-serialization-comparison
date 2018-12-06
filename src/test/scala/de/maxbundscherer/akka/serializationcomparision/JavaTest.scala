package de.maxbundscherer.akka.serializationcomparision

/**
  * Java Benchmark Test
  */
class JavaTest extends AbstractTest(testName = "java") {

  /**
    * Serializer
    */
  import de.maxbundscherer.akka.serializationcomparision.serializer.AbstractSerializer
  import de.maxbundscherer.akka.serializationcomparision.serializer.JavaSerializer

  /**
    * Set Serializer
    */
  override val serializer: AbstractSerializer = new JavaSerializer()

}