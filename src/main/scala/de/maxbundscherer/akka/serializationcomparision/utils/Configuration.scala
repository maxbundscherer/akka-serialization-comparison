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

      val timeoutInSeconds        : Int = config.getInt("timeoutInSeconds")
      val actorSnapshotInterval   : Int = config.getInt("actorSnapshotInterval")
    }

    object BenchmarkMode {

      private val config: Config = paramsConfig.getConfig("benchmarkMode")

      val testSizesFrom           : Int = config.getInt("testSizesFrom")
      val testSizesUpTo           : Int = config.getInt("testSizesUpTo")
      val testSizesHop            : Int = config.getInt("testSizesHop")
    }

  }

}