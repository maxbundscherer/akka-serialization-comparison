package de.maxbundscherer.akka.serializationcomparision

import de.maxbundscherer.akka.serializationcomparision.utils.{Configuration, ExperimentMode, ExperimentRunner}

import akka.util.Timeout
import scala.concurrent.duration._

object Main extends App with Configuration {

  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate.{Car, ComplexCar}
  import de.maxbundscherer.akka.serializationcomparision.utils.TestSet

  private implicit val timeout          : Timeout        = Timeout(Config.ExperimentMode.timeoutInSeconds seconds)

  // ~ Generate testSet ~
  var testSet         : Vector[Car]        = TestSet.testSetVector
  var complexTestSet  : Vector[ComplexCar] = TestSet.complexTestSetVector

  // ~ Run Experiments ~
  new ExperimentRunner( ExperimentMode.JAVA,      testSet, complexTestSet  )
  new ExperimentRunner( ExperimentMode.JSON,      testSet, complexTestSet )
  new ExperimentRunner( ExperimentMode.PROTOBUF,  testSet, complexTestSet )

}