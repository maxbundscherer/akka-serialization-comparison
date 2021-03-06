package de.maxbundscherer.akka.serializationcomparision.actors

import de.maxbundscherer.akka.serializationcomparision.utils.Configuration

import akka.actor.{ActorLogging, Props}
import akka.persistence.{PersistentActor, RecoveryCompleted, SaveSnapshotSuccess, SnapshotOffer}

/**
  * CarGarageActor (Singleton)
  */
object CarGarageActor extends Configuration {

  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate._

  // ~ Settings ~
  final val persistenceIdPrefix            : String = "carGarageActor"
  final val snapshotInterval               : Int    = Config.ExperimentMode.actorSnapshotInterval
  final def props(actorNamePostfix: String): Props  = Props(new CarGarageActor(actorNamePostfix))

  // ~ State ~
  case class CarGarageState(
                             cars: Map[Int, Car] = Map.empty,
                             complexCars: Map[Int, ComplexCar] = Map.empty
                           ) {

    def addCar(evt: AddCarEvt)      : CarGarageState  = copy( cars = cars + (evt.value.id -> evt.value) )
    def updateCar(evt: UpdateCarEvt): CarGarageState  = copy( cars = cars + (evt.value.id -> evt.value) )
    def addComplexCar(evt: AddComplexCarEvt)      : CarGarageState  = copy( complexCars = complexCars + (evt.value.id -> evt.value) )
    def updateComplexCar(evt: UpdateComplexCarEvt): CarGarageState  = copy( complexCars = complexCars + (evt.value.id -> evt.value) )

  }

}

/**
  * CarGarageActor
  * @param actorNamePostfix String
  */
private class CarGarageActor(actorNamePostfix: String) extends PersistentActor with ActorLogging {

  import CarGarageActor._
  import de.maxbundscherer.akka.serializationcomparision.persistence.CarGarageAggregate._

  /**
    * Set persistenceId (must be unique)
    * @return String
    */
  override def persistenceId: String = s"$persistenceIdPrefix-$actorNamePostfix"

  /**
    * Mutable actor state
    */
  private var state: CarGarageState = CarGarageState()

  /**
    * Show debug print (actor online)
    */
  log.debug("Actor online")

  /**
    * Recovery behavior
    * @return Receive
    */
  override def receiveRecover: Receive = {

    case SnapshotOffer(_, snapshot: CarGarageState)  =>

      log.debug(s"Restore from snapshot")
      state = snapshot

    case _: RecoveryCompleted =>

      log.debug("RecoveryCompleted")

    case evt: CarGarageEvt =>

      updateState(evt)

    case msg: Any =>

      log.warning("Get unhandled message in recovery behavior (" + msg + ")")
  }

  /**
    * Default behavior
    * @return Receive
    */
  override def receiveCommand: Receive = {

    case cmd: CarGarageCmd =>

      processCommand(cmd)

    case _: SaveSnapshotSuccess =>

    log.debug("SaveSnapshotSuccess")

    case msg: Any =>

      log.warning("Get unhandled message in default behavior (" + msg + ")")
  }

  /**
    * Process Command
    * @param cmd CarGarageCmd
    * @return Unit
    */
  private def processCommand(cmd: CarGarageCmd): Unit = cmd match {

    case cmd: AddCarCmd =>

      state.cars.get(cmd.value.id) match {
        case Some(_) => tellSender( CarAlreadyExists() )
        case None    => persistAndUpdateState( AddCarEvt(cmd.value) )
      }

    case cmd: UpdateCarCmd =>

      state.cars.get(cmd.value.id) match {
        case None    => tellSender( CarNotFound() )
        case Some(_) => persistAndUpdateState( UpdateCarEvt(cmd.value) )
      }

    case cmd: AddComplexCarCmd =>

      state.complexCars.get(cmd.value.id) match {
        case Some(_) => tellSender( CarAlreadyExists() )
        case None    => persistAndUpdateState( AddComplexCarEvt(cmd.value) )
      }

    case cmd: UpdateComplexCarCmd =>

      state.complexCars.get(cmd.value.id) match {
        case None    => tellSender( CarNotFound() )
        case Some(_) => persistAndUpdateState( UpdateComplexCarEvt(cmd.value) )
      }

    case _: GetAllCarCmd =>

      tellSender( GetAllCar(state.cars.values.toVector) )

    case _: GetAllComplexCarCmd =>

      tellSender( GetAllComplexCar(state.complexCars.values.toVector) )

    case _: SimulateCrashCmd =>

      throw new RuntimeException("Simulate crash")

  }

  /**
    * Write event to journal and update state
    * @param evt CarGarageEvt
    * @return Unit
    */
  private def persistAndUpdateState(evt: CarGarageEvt): Unit = persist(evt) { evt => updateState(evt) }

  /**
    * Update state
    * @param evt CarGarageEvt
    * @return Unit
    */
  private def updateState(evt: CarGarageEvt): Unit = evt match {

    case evt: AddCarEvt =>

      state = state addCar evt
      snapshot()
      tellSender( CarGarageSuccess() )

    case evt: UpdateCarEvt =>

      state = state updateCar evt
      snapshot()
      tellSender( CarGarageSuccess() )

    case evt: AddComplexCarEvt =>

      state = state addComplexCar evt
      snapshot()
      tellSender( CarGarageSuccess() )

    case evt: UpdateComplexCarEvt =>

      state = state updateComplexCar evt
      snapshot()
      tellSender( CarGarageSuccess() )

    case _: Any => log.error(s"Get unhandled evt ('$evt')")
  }

  /**
    * Tell sender if recovery is not running
    * @param res CarGarageResponse
    * @return Unit
    */
  private def tellSender(res: CarGarageResponse): Unit = { if(!recoveryRunning) sender ! res }

  /**
    * Do snapshot if recovery mode is not running and snapshot interval is reached and is not the first message
    */
  private def snapshot(): Unit = {

    if (
       !recoveryRunning &&
        lastSequenceNr % snapshotInterval == 0 &&
        lastSequenceNr != 0
    ) {

      log.debug(s"Do snapshot now")
      saveSnapshot(state)
    }

  }

}