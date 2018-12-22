package de.maxbundscherer.akka.serializationcomparision.samplecode

import akka.actor._
import akka.persistence._

object PersistentExampleActor {

  sealed trait Evt

  case object Increment extends Evt
  case object Decrement extends Evt

  case object WhatIsYourResult
  case class  MyResultIs(value: Int)

  val snapshotInterval: Int = 5

}

class PersistentExampleActor extends PersistentActor {

  import PersistentExampleActor._

  var state: Int = 0

  override def persistenceId: String = "persistentExampleActorId"

  override def receiveRecover: Receive = {

    case SnapshotOffer(_, snapshot: Int)  =>

      state = snapshot

    case evt: Evt =>

      updateState(evt)

  }

  override def receiveCommand: Receive = {

    case Increment =>

      persist(Increment) { evt => updateState(evt) }

    case Decrement =>

      persist(Decrement) { evt => updateState(evt) }

    case WhatIsYourResult =>

      sender ! MyResultIs(value = state)

  }

  private def updateState(evt: Evt): Unit = evt match {

    case Increment =>

      state = state + 1
      snapshot()

    case Decrement =>

      state = state - 1
      snapshot()

  }

  private def snapshot(): Unit = {

    if (
        !recoveryRunning &&
        lastSequenceNr % snapshotInterval == 0 &&
        lastSequenceNr != 0
    )   saveSnapshot(state)

  }

}