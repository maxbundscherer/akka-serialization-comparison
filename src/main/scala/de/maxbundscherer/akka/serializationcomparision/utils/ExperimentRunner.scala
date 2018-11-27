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
  private final val modeValue: String = this.mode.toString.toLowerCase

  /**
    * Init experiment (start akka system and load system config)
    */
  rootLogger.info(s"--- Start ${this.modeValue} Experiment ---")
  runExperiment(
    actorSystem = ActorSystem(
      name = s"actorSystem-${this.modeValue}",
      config = ConfigFactory.load(s"${this.modeValue}-akka-system.conf")
    )
  )
  rootLogger.info(s"--- End ${this.modeValue} Experiment ---")

  /**
    * Run Experiment
    * @param actorSystem ActorSystem
    */
  private def runExperiment(actorSystem: ActorSystem): Unit = {

    val carGarageService: CarGarageService = new CarGarageService(actorSystem, actorNamePostfix = this.modeValue)

    val ans: CarGarageResponse = carGarageService.askCarGarageActor(SayHello())
    rootLogger.info( s"Ans from carGarageActor (mode=${this.modeValue}) '$ans'" )

  }

}