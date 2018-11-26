package de.maxbundscherer.akka.serializationcomparision.utils

import de.maxbundscherer.akka.serializationcomparision.utils.ExperimentMode.ExperimentMode

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.util.Timeout

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

  mode match {

    case ExperimentMode.JAVA     =>

      rootLogger.info(s"Start ${this.mode} Experiment")
      runExperiment( ActorSystem(s"actorSystem-${this.mode}") )
      rootLogger.info(s"End ${this.mode} Experiment")

    case ExperimentMode.JSON     =>

      rootLogger.info(s"Start ${this.mode} Experiment")
      runExperiment( ActorSystem(s"actorSystem-${this.mode}") )
      rootLogger.info(s"End ${this.mode} Experiment")

    case ExperimentMode.PROTOBUF =>

      rootLogger.info(s"Start ${this.mode} Experiment")
      runExperiment( ActorSystem(s"actorSystem-${this.mode}") )
      rootLogger.info(s"End ${this.mode} Experiment")

  }

  /**
    * Run Experiment
    * @param actorSystem ActorSystem
    */
  private def runExperiment(actorSystem: ActorSystem): Unit = {

    val carGarageService: CarGarageService = new CarGarageService(actorSystem, actorNamePostfix = this.mode.toString)

    val ans: CarGarageResponse = carGarageService.askCarGarageActor(SayHello())
    rootLogger.info( s"Ans from carGarageActor (mode=${this.mode}) '$ans'" )

  }

}