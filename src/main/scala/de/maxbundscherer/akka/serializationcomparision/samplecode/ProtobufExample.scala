package de.maxbundscherer.akka.serializationcomparision.samplecode

class ProtobufExample {

  import de.maxbundscherer.akka.serializationcomparision.persistence.protobuf.CarDb

  val testCar: CarDb = CarDb(id = 0, name = "BMW F30", horsepower = 200)
  val protobufOutput: Array[Byte] = testCar.toByteArray

  println(s"protobufString '${ (protobufOutput.map(_.toChar)).mkString }'")

}