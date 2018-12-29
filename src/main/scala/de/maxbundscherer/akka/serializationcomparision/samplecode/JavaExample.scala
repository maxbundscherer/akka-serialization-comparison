package de.maxbundscherer.akka.serializationcomparision.samplecode

object JavaExample {

  case class Car(
                  id: Int,
                  name: String,
                  horsePower: Int,
                )

}

class JavaExample {

  import JavaExample._
  import java.io._

  /**
    * Convert to Java Array of Byte
    * @param o java.io.Serializable
    * @return Java Array of Byte
    */
  private def toJavaByteArray(o: java.io.Serializable): Array[Byte] = {

    val byteArrayOutputStream : ByteArrayOutputStream = new ByteArrayOutputStream
    val objectOutputStream    : ObjectOutputStream    = new ObjectOutputStream(byteArrayOutputStream)

    objectOutputStream.writeObject(o)

    objectOutputStream.close()
    byteArrayOutputStream.close()

    byteArrayOutputStream.toByteArray
  }

  val testCar: Car = Car(id = 0, name = "BMW F30", horsePower = 200)
  val javaOuput: Array[Byte] = toJavaByteArray(testCar)

  println(s"javaString '${ (javaOuput.map(_.toChar)).mkString }'")

}