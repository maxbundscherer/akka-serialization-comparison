package de.maxbundscherer.akka.serializationcomparision.samplecode

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

}

class ConverterExample {

  import ConverterExample._

  def convertCar(value: CarV1): CarV2 = {

    CarV2 (
      title       = value.title,
      horsePower  = value.horsePower,
      color       = value.color match { case 0 => "red" case 1 => "green" case _ => "blue" }
    )

  }

}