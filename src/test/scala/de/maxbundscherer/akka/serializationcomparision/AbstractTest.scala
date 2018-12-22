package de.maxbundscherer.akka.serializationcomparision

import de.maxbundscherer.akka.serializationcomparision.serializer.AbstractSerializer
import de.maxbundscherer.akka.serializationcomparision.utils.Configuration

import org.scalameter.api._

/**
  * Abstract Benchmark Test
  * @param testName String Name of Test
  */
abstract class AbstractTest(testName: String) extends Bench.LocalTime with Configuration {

  /**
    * Print time for log (pipe log to file)
    */
  import de.maxbundscherer.akka.serializationcomparision.utils.TimePrinter
  TimePrinter.printActualTimeForLog(s"Start MicroBench ($testName)")

  // ~ Models ~
  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate._
  import de.maxbundscherer.akka.serializationcomparision.utils.TestSet

  /**
    * Configure Test
    */
  val testSizes: Gen[Int] = Gen.range(s"size-$testName")(Config.BenchmarkMode.testSizesFrom, Config.BenchmarkMode.testSizesUpTo, Config.BenchmarkMode.testSizesHop)

  val testRanges: Gen[Range] = for {
    size <- testSizes
  } yield 0 until size

  /**
    * Call Test
    */
  performance of s"Serialize and Deserialize $testName" in {

    measure method "serializeAndDeserialize" in {

      using(testRanges) in {

        r => r.foreach(t => triggerSingleSerializeAndDeserialize(t))

      }

    }

  }

  /**
    * Set Serializer
    */
  val serializer: AbstractSerializer

  /**
    * SingleTest
    * @param i Int
    */
  private def triggerSingleSerializeAndDeserialize(i: Int): Unit = {

    val testEvt         : AddCarEvt = AddCarEvt( TestSet.testSetArray(i % TestSet.testSetArray.length) )

    val serializedEvt   : Array[Byte] = serializeAddCarEvt(testEvt)
    val deserializedEvt : AddCarEvt   = deserializeAddCarEvt(serializedEvt)

  }

  /**
    * Serialize AddCarEvt
    * @param value AddCarEvt
    * @return Array of Bytes
    */
  private def serializeAddCarEvt(value: AddCarEvt): Array[Byte] = serializer.toBinary(value)

  /**
    * Deserialize AddCarEvt
    * @param value Array of Bytes
    * @return AddCarEvt
    */
  private def deserializeAddCarEvt(value: Array[Byte]): AddCarEvt = serializer.fromBinary(value, serializer.AddCarEvtManifest).asInstanceOf[AddCarEvt]

}