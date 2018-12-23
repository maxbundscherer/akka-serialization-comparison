package de.maxbundscherer.akka.serializationcomparision.persistence.json

case class ComplexCarDb(
                       id: Int,
                       horsepower: Int,
                       name: String,
                       fuelConsumption: Float,
                       dieselEngine: Boolean,
                       seatAdjustment: Boolean,
                       fuelTank: Double,
                       brakingDistance: Double,
                       notes: String
                     )