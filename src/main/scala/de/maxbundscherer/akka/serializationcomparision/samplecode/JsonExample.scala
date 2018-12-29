package de.maxbundscherer.akka.serializationcomparision.samplecode

object JsonExample {

  case class Car(
                  id: Int,
                  name: String,
                  horsePower: Int,
                )

}

class JsonExample {

  import JsonExample._
  import io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  val testCar: Car = Car(id = 0, name = "BMW F30", horsePower = 200)
  val jsonString: String = testCar.asJson.toString()

  println(s"jsonString '$jsonString'")

}