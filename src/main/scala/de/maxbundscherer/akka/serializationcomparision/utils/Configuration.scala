package de.maxbundscherer.akka.serializationcomparision.utils

import com.typesafe.config.{Config, ConfigFactory}

/**
  * Configuration Trait
  */
trait Configuration {

  object Config {

    private val paramsConfig: Config = ConfigFactory.load("params.conf")

    object TestSet {

      private val config: Config = paramsConfig.getConfig("testSet")

      val numberOfTestCars                : Int = config.getInt("numberOfTestCars")
      val carNameStringMaxLength          : Int = config.getInt("carNameStringMaxLength")
      val complexCarNotesStringMaxLength  : Int = config.getInt("complexCarNotesStringMaxLength")
    }

    object ExperimentMode {

      private val config: Config = paramsConfig.getConfig("experimentMode")

      val timeoutInSeconds            : Int = config.getInt("timeoutInSeconds")
      val actorSnapshotInterval       : Int = config.getInt("actorSnapshotInterval")
      val numberOfAdds                : Int = config.getInt("numberOfAdds")
      val numberOfUpdates             : Int = config.getInt("numberOfUpdates")
      val testCar                     : Boolean = config.getBoolean("testCar")
      val testComplexCar              : Boolean = config.getBoolean("testComplexCar")
    }

    object BenchmarkMode {

      private val config: Config = paramsConfig.getConfig("benchmarkMode")

      val numberOfSingleTests     : Int = config.getInt("numberOfSingleTests")
      val testCar                 : Boolean = config.getBoolean("testCar")
      val testComplexCar          : Boolean = config.getBoolean("testComplexCar")
    }

  }

}