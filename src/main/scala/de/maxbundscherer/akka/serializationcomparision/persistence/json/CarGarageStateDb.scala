package de.maxbundscherer.akka.serializationcomparision.persistence.json

case class CarGarageStateDb(cars: Map[Int, CarDb], complexCars: Map[Int, ComplexCarDb])