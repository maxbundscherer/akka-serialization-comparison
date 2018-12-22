package de.maxbundscherer.akka.serializationcomparision.samplecode

import akka.actor._

object ExampleActor {

  case object Increment
  case object Decrement

  case object WhatIsYourResult
  case class  MyResultIs(value: Int)

}

class ExampleActor extends Actor {

  import ExampleActor._

  var state: Int = 0

  override def receive: Receive = {

    case Increment =>

      state = state + 1

    case Decrement =>

      state = state - 1

    case WhatIsYourResult =>

      sender ! MyResultIs(value = state)

  }

}