package de.maxbundscherer.akka.serializationcomparision.utils

import de.maxbundscherer.akka.serializationcomparision.utils.ExperimentMode.ExperimentMode

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

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
  log.info(s"Answer from carGarageActor: '${carGarageService.askCarGarageActor(IncrementCmd())}'")
  log.info(s"--- End Experiment (modeValue='$modeValue') ---")

  actorSystem.terminate()

}