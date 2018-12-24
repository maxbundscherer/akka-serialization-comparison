package de.maxbundscherer.akka.serializationcomparision.utils

import de.maxbundscherer.akka.serializationcomparision.utils.ExperimentMode.ExperimentMode
import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate.{Car, ComplexCar}

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration.Duration

/**
  * ExperimentMode (Enumeration)
  */
object ExperimentMode extends Enumeration {

  type ExperimentMode       = Value
  val JAVA, JSON, PROTOBUF  = Value
}

/**
  * ExperimentRunner
  * @param mode ExperimentMode
  * @param timeout Timeout for asking an actor
  */
class ExperimentRunner(mode: ExperimentMode)(implicit timeout: Timeout) extends SimpleTimeMeasurement with Configuration {

  import de.maxbundscherer.akka.serializationcomparision.services.CarGarageService

  /**
    * ~ Init experiment (start akkaSystem and load system config) ~
    * 1. Set modeValue
    * 2. Start AkkaSystem with config
    * 3. Start CarGarageService
    * 4. Define logger
    */
  private final val modeValue       : String = mode.toString.toLowerCase

  private final val actorSystem     : ActorSystem = ActorSystem(
    name = s"actorSystem-$modeValue",
    config = ConfigFactory.load(s"akka-system-$modeValue.conf")
  )

  private final val carGarageService: CarGarageService = new CarGarageService(
    actorSystem = actorSystem,
    actorNamePostfix = modeValue
  )

  private final val log             : LoggingAdapter = actorSystem.log

  /**
    * ~ Run Experiment ~
    */
  log.info(s"--- Start Experiment (modeValue='$modeValue') ---")

  // ~ Start Time Measurement ~
  startTimeMeasurement()

  // ~ GetAllCar ~
  carGarageService.getAllCar
  carGarageService.getAllComplexCar

  // ~ Add Loop ~
  if(Config.ExperimentMode.testCar) {
    log.info(s"Test AddCar (${Config.ExperimentMode.numberOfAdds} operations)")
    for(i <- 0 until Config.ExperimentMode.numberOfAdds) {
      carGarageService.addCar( TestSet.testSetArray(i % TestSet.testSetArray.length) )
    }
  }
  if(Config.ExperimentMode.testComplexCar) {
    log.info(s"Test AddComplexCar (${Config.ExperimentMode.numberOfAdds} operations)")
    for(i <- 0 until Config.ExperimentMode.numberOfAdds) {
      carGarageService.addComplexCar( TestSet.complexTestSetArray(i % TestSet.complexTestSetArray.length) )
    }
  }

  // ~ Simulate Crash ~
  carGarageService.simulateCrash()

  // ~ Update Loop ~
  if(Config.ExperimentMode.testCar) {
    log.info(s"Test UpdateCar (${Config.ExperimentMode.numberOfUpdates} operations)")
    for(i <- 0 until Config.ExperimentMode.numberOfUpdates) {

      val car: Car          = TestSet.testSetArray(i % TestSet.testSetArray.length)
      val templateCar: Car  = TestSet.testSetArray(i % Config.ExperimentMode.numberOfAdds)

      carGarageService.updateCar( car.copy(id = templateCar.id, horsepower = templateCar.horsepower * 2) )
    }
  }
  if(Config.ExperimentMode.testComplexCar) {
    log.info(s"Test UpdateComplexCar (${Config.ExperimentMode.numberOfUpdates} operations)")
    for(i <- 0 until Config.ExperimentMode.numberOfUpdates) {

      val car: ComplexCar          = TestSet.complexTestSetArray(i % TestSet.complexTestSetArray.length)
      val templateCar: ComplexCar  = TestSet.complexTestSetArray(i % Config.ExperimentMode.numberOfAdds)

      carGarageService.updateComplexCar( car.copy(id = templateCar.id, horsepower = templateCar.horsepower * 2) )
    }
  }

  // ~ Simulate Crash ~
  carGarageService.simulateCrash()

  // ~ GetAllCar ~
  carGarageService.getAllCar
  carGarageService.getAllComplexCar

  // ~ Stop Time Measurement and Print Result ~
  val duration: Duration = Duration.fromNanos(stopTimeMeasurement())
  log.info("StopTimeMeasurement: " + duration.toSeconds + " seconds")
  log.info(s"--- End Experiment (modeValue='$modeValue') ---")

  // ~ End Experiment ~
  //Add Thread sleep (show logger output)
  Thread.sleep(1000)
  actorSystem.terminate()

}