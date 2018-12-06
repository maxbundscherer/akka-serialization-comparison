package de.maxbundscherer.akka.serializationcomparision

import de.maxbundscherer.akka.serializationcomparision.utils.{ExperimentMode, ExperimentRunner}

import akka.util.Timeout
import scala.concurrent.duration._

object Main extends App {

  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate.Car
  import de.maxbundscherer.akka.serializationcomparision.utils.TestSet

  private implicit val timeout          : Timeout        = Timeout(5000 seconds)

  // ~ Generate testSet ~
  var testSet: Vector[Car] = TestSet.testSetVector

  // ~ Run Experiments ~
  new ExperimentRunner( ExperimentMode.JAVA,      testSet )
  new ExperimentRunner( ExperimentMode.JSON,      testSet )
  new ExperimentRunner( ExperimentMode.PROTOBUF,  testSet )

}