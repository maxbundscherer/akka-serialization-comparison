package de.maxbundscherer.akka.serializationcomparision.utils

import de.maxbundscherer.akka.serializationcomparision.utils.ExperimentMode.ExperimentMode

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
class ExperimentRunner(mode: ExperimentMode)(implicit timeout: Timeout) {

  import de.maxbundscherer.akka.serializationcomparision.services.CarGarageService
  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate._

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
  log.debug("StartTimeMeasurement: " + carGarageService.startTimeMeasurement)

  // ~ GetAllCar ~
  log.info("GetAllCar: "   + carGarageService.getAllCar)

  // ~ Set Number of Cars ~
  val numberOfCars: Int = 99000

  // ~ Add Loop ~
  for (i <- 0 to numberOfCars) {
    log.debug("AddCar: "   + carGarageService.addCar   ( Car(id = i, horsepower = 200+i, name = "BMW F" + 30+i) ))
  }

  // ~ Update Loop ~
  for (i <- 0 to numberOfCars) {
    log.debug("UpdateCar: " + carGarageService.updateCar( Car(id = i, horsepower = (200+i)*2, name = "BMW F" + 30+i) ))
  }

  // ~ Simulate Crash ~
  //TODO: Simulate Crash and save time
  //carGarageService.simulateCrash()

  // ~ GetAllCar ~
  log.debug("GetAlLCar: "   + carGarageService.getAllCar)

  // ~ Stop Time Measurement ~
  val duration: Duration = Duration.fromNanos(carGarageService.stopTimeMeasurement.value)

  log.info("StopTimeMeasurement: " + duration.toSeconds + " seconds")

  log.info(s"--- End Experiment (modeValue='$modeValue') ---")

  actorSystem.terminate()

}