package de.maxbundscherer.akka.serializationcomparision.utils

import de.maxbundscherer.akka.serializationcomparision.utils.ExperimentMode.ExperimentMode
import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate.Car

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
  * @param testSet Vector of Cars (TestSet)
  */
class ExperimentRunner(mode: ExperimentMode, testSet: Vector[Car])(implicit timeout: Timeout) extends TimeMeasurement {

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
  startTimeMeasurement()

  // ~ GetAllCar ~
  log.info("GetAllCar: "   + carGarageService.getAllCar)

  // ~ Add Loop ~
  testSet.foreach(car => {
    log.debug("AddCar: " + carGarageService.addCar( car ))
  })

  // ~ Simulate Crash ~
  carGarageService.simulateCrash()

  // ~ Update Loop ~
  testSet.foreach(car => {
    log.debug("UpdateCar: " + carGarageService.updateCar( car.copy(horsepower = car.horsepower * 2) ))
  })

  // ~ Simulate Crash ~
  carGarageService.simulateCrash()

  // ~ GetAllCar ~
  log.debug("GetAllCar: "   + carGarageService.getAllCar)

  // ~ Stop Time Measurement ~
  val duration: Duration = Duration.fromNanos(stopTimeMeasurement())

  log.info("StopTimeMeasurement: " + duration.toSeconds + " seconds")

  log.info(s"--- End Experiment (modeValue='$modeValue') ---")

  //TODO: Remove sleep (wait to show log)
  Thread.sleep(1000)
  actorSystem.terminate()

}