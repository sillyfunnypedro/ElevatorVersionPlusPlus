package elevator;


import building.enums.Direction;

import java.util.List;

import scanerzus.Request;


/**
 * An implementation of the ElevatorStatus interface.
 */
public class Elevator implements ElevatorInterface {
  /************************************************************************
   * static value used to initialize the elevator id.
   ************************************************************************/
  private static int newElevatorId = 0;

  /************************************************************************
   * The id of the elevator.This is read only.
   ************************************************************************/
  private final int id = newElevatorId++;

  /************************************************************************
   * The total number of floors in the building.
   ************************************************************************/
  private final int maxFloor;

  /************************************************************************
   * The maximum number of people that can fit in the elevator.
   ************************************************************************/
  private final int maxOccupancy;

  /************************************************************************
   * The number of steps that the door is open for.
   ************************************************************************/
  private final int doorOpenTimeTotal = 3;

  /************************************************************************
   * The number of steps that the elevator will wait at the top or bottom.
   ************************************************************************/
  private final int stopWaitTimeTotal = 5;

  /************************************************************************
   * The class variables that change as the elevator runs.
   ************************************************************************/
  private boolean takingRequests;


  /************************************************************************
   * The current floor of the elevator. Starts at 0.
   ************************************************************************/
  private int currentFloor;


  /************************************************************************
   * The direction the elevator is moving.
   ************************************************************************/
  private Direction direction;

  /************************************************************************
   * The timer for the door.
   ************************************************************************/
  private int doorOpenTimeLeft = 0;

  /************************************************************************
   * The state of the door.
   ************************************************************************/
  private boolean doorClosed = true;


  /************************************************************************
   * The timer for the end of the run.
   ************************************************************************/
  private int stopWaitTimeLeft = 0;


  /************************************************************************
   * The requests for the floors.
   ************************************************************************/
  private final boolean[] floorRequests; // true if there is a request for the floor.


  private boolean outOfService;  // start must be issued on the elevator to start it.


  /**
   * The constructor for this elevator.
   * The elevator is initially at the ground floor and is not moving.
   *
   * @param maxFloor     the total number of floors in the building
   *                     must be greater than 0
   *                     must be less than 30 (city bylaws)
   * @param maxOccupancy the maximum number of people that can fit in the elevator
   *                     must be greater than 0
   *                     must be less than 20 (fire code)
   * @throws IllegalArgumentException if the maxFloor or maxOccupancy is out of range
   */
  public Elevator(int maxFloor, int maxOccupancy) {
    if (maxFloor < 3 || maxFloor > 30) {
      throw new IllegalArgumentException("maxFloor must be between 3 and 30");
    }
    if (maxOccupancy < 3 || maxOccupancy > 20) {
      throw new IllegalArgumentException("maxOccupancy must be between 3 and 20");
    }

    this.maxFloor = maxFloor;
    this.maxOccupancy = maxOccupancy;
    this.currentFloor = 0;
    this.direction = Direction.STOPPED;
    this.outOfService = true;
    this.floorRequests = new boolean[maxFloor];
    this.takingRequests = false;

  }

  /* ***********************************************************************
   * The following methods are the getters for the ElevatorStatus interface.
   * **********************************************************************/

  /**
   * Get the current floor.
   *
   * @return the current floor of the elevator
   */
  @Override
  public int getCurrentFloor() {
    return this.currentFloor;
  }

  /**
   * maxFloor getter.
   *
   * @return the total number of floors in the building
   */
  @Override
  public int getMaxFloor() {
    return this.maxFloor;
  }

  /**
   * maxOccupancy getter.
   *
   * @return the maximum number of people that can fit in the elevator.
   */
  @Override
  public int getMaxOccupancy() {
    return this.maxOccupancy;
  }

  /**
   * Direction getter.
   *
   * @return the direction the elevator is moving.
   */
  @Override
  public Direction getDirection() {
    return this.direction;
  }

  /**
   * Get ElevatorStatus Id.
   */
  @Override
  public int getElevatorId() {
    return this.id;
  }

  /**
   * Get the door status.
   *
   * @return the door status of the elevator.
   */
  @Override
  public boolean isDoorClosed() {
    return doorClosed;
  }

  /**
   * Return the current stop requests.
   *
   * @return the current stop requests.
   */
  @Override
  public boolean[] getFloorRequests() {
    return this.floorRequests;
  }

  /* ***********************************************************************
   * The following methods are the methods for the ElevatorStatus interface.
   *
   * start() - start the elevator
   * step() - move the elevator one step
   * processRequests() - process the requests
   * takeOutOfService() - take the elevator out of service
   * isTakingRequests() - is the elevator taking requests
   * toString() - string representation of the elevator
   * getElevatorStatus() - generate a report for the elevator in ElevatorReport format
   *
   * **********************************************************************/

  /**
   * Start the elevator.  This does nothing if the elevator is not
   * on the ground floor and its doors are open.
   */
  @Override
  public void start() {
    this.outOfService = false;
    this.takingRequests = true;
    clearStopRequests();
    this.doorClosed = true;
    this.doorOpenTimeLeft = 0;
  }


  /**
   * Step the elevator when out of service.
   *
   * If the elevator is on the ground floor and the door is open return
   * If the elevator is on the ground floor and the door is closed
   * then open the door.
   *
   * If the elevator is not on the ground floor and the door is open
   * then execute stepDoorOpen
   */
  private void stepOutOfService() {
    if (this.currentFloor == 0 && !this.doorClosed) {
      return;
    }

    //
    if (this.currentFloor == 0) {
      this.doorClosed = false;
      // we can clear the floor requests now
      this.floorRequests[this.currentFloor] = false;
      // we can set the elevator to stopped.
      this.direction = Direction.STOPPED;
      return;
    }

    // at this point we know we are not on the ground floor
    // check to see if the door is open.

    if (!this.doorClosed) {
      this.stepDoorOpen();

      return;
    }

    // set the direction of the elevator to down.
    this.direction = Direction.DOWN;
    this.currentFloor--;
  }

  private void stepDoorOpen() {
    this.doorOpenTimeLeft--;
    if (this.doorOpenTimeLeft == 0) {
      this.doorClosed = true;
    }
  }

  /**
   * The step function is called to move the elevator one step.
   * If the elevator is moving up we will
   * 1. check to see if there are any requests for the current floor.
   * If there is we will open the door and set a timer for 3 steps.
   * 2. if the door is open we will close the door after the timer expires.
   * 3. if the door is closed we will move the elevator up one floor.
   */
  public void step() {
    if (this.outOfService) {
      this.stepOutOfService();
      return;
    }


    // If the door is open we decrease the door open timer and return.
    if (this.doorOpenTimeLeft > 0) {
      this.doorOpenTimeLeft--;
      if (this.doorOpenTimeLeft == 0) {
        this.doorClosed = true;
      }
      return;
    }

    // if the elevator is at the top or bottom and we are waiting
    // decrease the wait timer and return.
    if (this.stopWaitTimeLeft > 0) {
      this.stopWaitTimeLeft--;
      this.takingRequests = false;
      return;
    }

    // now we check to see if there is a request at this floor
    // We open the door and set the timer for 3 steps.
    if (this.floorRequests[this.currentFloor]) {
      this.doorClosed = false;
      this.doorOpenTimeLeft = 3;
      this.floorRequests[this.currentFloor] = false;
      return;
    }

    // The logic here is as follows.
    // if we are not at the top or the bottom we will move up or down.
    // the top and bottom logic involves reaching this code twice.

    //*********************************************************************************
    // Reaching the top.
    // The first time we reach the top we set the value of the floor to maxFloor - 1
    // and we are done.
    // then on the next call if the door has to be opened the code above will deal with
    // that.  Once the door has opened and closed then the following code
    // will be executed the next time.
    // Since we are at the top we move past one and we know it is time to wait
    // for 5 steps.
    //*********************************************************************************
    // the same logic applies to the bottom floor.
    //*********************************************************************************

    int floorIncrement = 1;
    if (this.direction == Direction.UP) {
      this.currentFloor += floorIncrement;
      if (this.currentFloor >= this.maxFloor) {
        this.currentFloor = this.maxFloor - 1;
        this.stopWaitTimeLeft = 5;
        this.direction = Direction.DOWN; // The elevator never stops at the top floor.
        this.takingRequests = true;
      }
    } else {
      this.currentFloor -= floorIncrement;
      // If we
      if (this.currentFloor < 0) {
        this.currentFloor = 0;
        this.stopWaitTimeLeft = 5;
        this.direction = Direction.UP;
        this.takingRequests = true;
      }
    }
  }


  /**
   * Process the requests.  The Building will only give us requests
   * that are on the way to our current direction.  That is,
   * if we are at the bottom or the top.
   * If a request is received to processRequests and the elevator
   * is not on the first floor or the top floor then and exception
   * will be thrown.
   */
  @Override
  public void processRequests(List<Request> requests) throws IllegalStateException {
    if (this.currentFloor != 0 && this.currentFloor != this.maxFloor - 1) {
      throw new IllegalStateException("Elevator cannot process requests "
          + "unless it is at the bottom or top floor.");
    }

    if (requests.isEmpty()) {
      return;
    }

    this.processStopRequests(requests);
    if (this.currentFloor == 0) {
      this.direction = Direction.UP;
    } else if (this.currentFloor == this.maxFloor - 1) {
      this.direction = Direction.DOWN;
    }
    this.takingRequests = false;

  }

  /**
   * Take the elevator out of service.
   */
  @Override
  public void takeOutOfService() {
    this.clearStopRequests();
    this.direction = Direction.DOWN;
    this.floorRequests[0] = true;
    this.outOfService = true;
  }

  /**
   * isTakingRequests.
   * This will return true if the elevator is taking requests.
   *
   * @return true if the elevator is taking requests, false otherwise.
   */
  @Override
  public boolean isTakingRequests() {
    return this.takingRequests;
  }


  private void processStopRequests(List<Request> requests) {
    clearStopRequests();

    for (Request request : requests) {
      this.floorRequests[request.getStartFloor()] = true;
      this.floorRequests[request.getEndFloor()] = true;
    }
    // if the elevator was waiting at the top or bottom
    // set the timer to 0 and we are off to the races.
    this.stopWaitTimeLeft = 0;
  }

  /**
   * Clear the Floor Requests.
   */
  private void clearStopRequests() {
    for (int i = 0; i < this.maxFloor; i++) {
      this.floorRequests[i] = false;
    }
  }

  /**
   * toString implementation.
   *
   * @return string representation of the elevator.
   */
  @Override
  public String toString() {
    ElevatorReport report = new ElevatorReport(
        this.id,
        this.currentFloor,
        this.direction,
        this.doorClosed,
        this.floorRequests,
        this.doorOpenTimeLeft,
        this.stopWaitTimeLeft,
        this.outOfService,
        this.takingRequests);

    return report.toString();
  }


  /**
   * Generate a report for the elevator in ElevatorReport format.
   *
   * @return an ElevatorReport object.
   */
  @Override
  public ElevatorReport getElevatorStatus() {
    return new ElevatorReport(
        this.id,
        this.currentFloor,
        this.direction,
        this.doorClosed,
        this.floorRequests,
        this.doorOpenTimeLeft,
        this.stopWaitTimeLeft,
        this.outOfService,
        this.takingRequests);
  }

}
