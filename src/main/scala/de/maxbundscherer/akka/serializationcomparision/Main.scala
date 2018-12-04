package de.maxbundscherer.akka.serializationcomparision

import de.maxbundscherer.akka.serializationcomparision.utils.{ExperimentMode, ExperimentRunner}

import akka.util.Timeout
import scala.concurrent.duration._
import scala.util.Random

object Main extends App {

  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate.Car

  private implicit val timeout          : Timeout        = Timeout(50 seconds)

  // ~ Set Number of Cars ~
  val numberOfCars: Int = 20

  // ~ Generate testSet ~
  var testSet: Vector[Car] = Vector.empty

  for (i <- 0 until numberOfCars) {
    testSet = testSet :+ Car(id = i, horsepower = 100 + i, name = Random.alphanumeric.take(5 + Random.nextInt(30)).mkString)
  }

  // ~ Run Experiments ~
  new ExperimentRunner( ExperimentMode.JAVA,      testSet )
  new ExperimentRunner( ExperimentMode.JSON,      testSet )
  new ExperimentRunner( ExperimentMode.PROTOBUF,  testSet )

}