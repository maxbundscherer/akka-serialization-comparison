package de.maxbundscherer.akka.serializationcomparision

import org.scalameter.api._

import scala.collection.immutable

class TestBe extends Bench.LocalTime {

  val sizes: Gen[Int] = Gen.range("size")(1, 5, 1)

  val ranges: Gen[Range] = for {
    size <- sizes
  } yield 0 until size

  performance of "Range" in {

    measure method "map" in {

      using(ranges) in {
        r =>

          r.foreach(t => Thread.sleep(100))

      }

    }

  }

}
