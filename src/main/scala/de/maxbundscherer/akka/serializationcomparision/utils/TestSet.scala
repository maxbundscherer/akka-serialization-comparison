package de.maxbundscherer.akka.serializationcomparision.utils

import scala.util.Random

/**
  * TestSet for Testing
  */
object TestSet extends Configuration {

  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate.Car

  //~ Config TestSet ~
  private val numberOfTestCars            : Int = Config.TestSet.numberOfTestCars

  //~ Generate TestSet ~
  private var testSetData: Vector[Car] = Vector.empty
  for(i <- 0 until numberOfTestCars) testSetData = testSetData :+ Car(
    id = i,
    horsepower = 100 + i,
    name = Random.alphanumeric.take(Random.nextInt(Config.TestSet.carNameStringMaxLength)).mkString
  )

  //~ Generated TestSet ~
  val testSetVector : Vector[Car] = testSetData
  val testSetArray  : Array[Car]  = testSetData.toArray

}