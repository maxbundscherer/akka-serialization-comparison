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
  * @param rootLogger LoggingAdapter
  * @param timeout Timeout
  */
class ExperimentRunner(mode: ExperimentMode)(implicit rootLogger: LoggingAdapter, timeout: Timeout) {

  import de.maxbundscherer.akka.serializationcomparision.services.CarGarageService
  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate._

  /**
    * Set mode string (string value of mode)
    */
  private final val modeValue: String = mode.toString.toLowerCase

  /**
    * Init experiment (start akka system and load system config)
    */
  rootLogger.info(s"--- Start $modeValue Experiment ---")
  runExperiment(
    actorSystem = ActorSystem(
      name = s"actorSystem-$modeValue",
      config = ConfigFactory.load(s"$modeValue-akka-system.conf")
    )
  )
  rootLogger.info(s"--- End $modeValue Experiment ---")

  /**
    * Run Experiment
    * @param actorSystem ActorSystem
    */
  private def runExperiment(actorSystem: ActorSystem): Unit = {

    val carGarageService: CarGarageService = new CarGarageService(actorSystem, actorNamePostfix = modeValue)

    val ans: CarGarageResponse = carGarageService.askCarGarageActor(IncrementCmd())
    rootLogger.info( s"Ans from carGarageActor (mode=$modeValue) '$ans'" )

    actorSystem.terminate()
  }

}