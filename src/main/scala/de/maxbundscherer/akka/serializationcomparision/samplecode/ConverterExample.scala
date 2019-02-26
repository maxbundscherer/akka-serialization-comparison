package de.maxbundscherer.akka.serializationcomparision.samplecode

import akka.serialization.SerializerWithStringManifest

object ConverterExample {

  case class CarV1(
                    title: String,
                    horsePower: Int,
                    color: Int,
                  )

  case class CarV2(
                    title: String,
                    horsePower: Int,
                    color: String
                  )

  def convertCar(value: CarV1): CarV2 = {

    CarV2 (
      title       = value.title,
      horsePower  = value.horsePower,
      color       = value.color match { case 0 => "red" case 1 => "green" case _ => "blue" }
    )

  }

}

class ConverterExample extends SerializerWithStringManifest {

  import ConverterExample._

  override def identifier: Int = 9001

  override def manifest(o: AnyRef): String = o match {
    case CarV1 => "CarV1"
    case CarV2 => "CarV2"
  }

  override def toBinary(o: AnyRef): Array[Byte] = o match {
    case obj: CarV1 => ???
    case obj: CarV2 => ???
  }

  override def fromBinary(bytes: Array[Byte], manifest: String): AnyRef = manifest match {

    case "CarV1" =>

      val obj: CarV1 = ???
      convertCar(obj)

    case "CarV2" =>

      ???
  }

}