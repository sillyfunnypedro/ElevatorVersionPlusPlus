package elevator;

import java.util.ArrayList;
import java.util.List;

import building.enums.Direction;
import org.json.JSONArray;
import org.json.JSONObject;
import scanerzus.Request;


/**
 * An implementation of the ElevatorStatus interface.
 */
public class Elevator implements ElevatorInterface {
  /**
   * The total number of floors in the building.
   */
  private final int maxFloor;

  /**
   * The maximum number of people that can fit in the elevator.
   */
  private final int maxOccupancy;

  /**
   * Is the elevator taking requests.
   */
  private boolean takingRequests = false;

  /**
   * The integer that stores the static value used to initialize the elevator
   * id.
   * This is a unique identifier for the elevator.
   * <p>
   * Added a checkstyle suppression for DeclarationOrder since it is buggy
   */
  private static int newElevatorId = 0;

  /**
   * The id of the elevator.
   * This is a unique identifier for the elevator.
   * This is initialized using a static variable that is incremented each time
   */
  private final int id = newElevatorId++;


  /**
   * The current floor of the elevator.
   * This is initialized to 0, which is the ground floor.
   */
  private int currentFloor;


  /**
   * The direction the elevator is moving.
   */
  private Direction direction;

  private int openDoorTimer = 0;


  private final boolean[] floorRequests; // true if there is a request for the floor.

  /**
   * The state of the door
   */
  private boolean doorClosed = true;


  private boolean outOfService; // start must be issued on the elevator to start it.


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
    if (maxFloor < 1 || maxFloor > 30) {
      throw new IllegalArgumentException("maxFloor must be between 1 and 30");
    }
    if (maxOccupancy < 1 || maxOccupancy > 20) {
      throw new IllegalArgumentException("maxOccupancy must be between 1 and 20");
    }

    this.maxFloor = maxFloor;
    this.maxOccupancy = maxOccupancy;
    this.currentFloor = 0;
    this.direction = Direction.STOPPED;
    this.outOfService = true;
    this.floorRequests = new boolean[maxFloor];
    this.takingRequests = false;

  }

  /**
   * Start the elevator.  This does nothing if the elevator is not on the groundfloor and its doors are open.
   */
  @Override
  public void start() {
    this.outOfService = false;
    this.takingRequests = true;
    clearStopRequests();
    this.doorClosed = true;
  }

  /**
   * Get the current floor, this is a floating point value to allow for partial floors.
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

  private void processStopRequests(List<Request> requests) {
    clearStopRequests();

    for (Request request : requests) {
      this.floorRequests[request.getStartFloor()] = true;
      this.floorRequests[request.getEndFloor()] = true;
    }
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
   * The step function is called to move the elevator one step.
   * If the elevator is moving up we will
   * 1. check to see if there are any requests for the current floor.
   * If there is we will open the door and set a timer for 3 steps.
   * 2. if the door is open we will close the door after the timer expires.
   * 3. if the door is closed we will move the elevator up one floor.
   */
  public void step() {
    if (this.outOfService) {
      return;
    }

    // If the door is open we decrease the door open timer and return.
    if (this.openDoorTimer > 0) {
      this.openDoorTimer--;
      if (this.openDoorTimer == 0) {
        this.doorClosed = true;
      }
      return;
    }

    // now we check to see if there is a request at this floor
    // We open the door and set the timer for 3 steps.
    if (this.floorRequests[this.currentFloor]) {
      this.doorClosed = false;
      this.openDoorTimer = 3;
      this.floorRequests[this.currentFloor] = false;
      return;
    }


    // for the purposes of this assignment we will assume that the elevator
    // can move one floor in one step.
    int floorIncrement = 1;
    if (this.direction == Direction.UP) {
      this.currentFloor += floorIncrement;
      if (this.currentFloor >= this.maxFloor) {
        this.currentFloor = this.maxFloor - 1;
        this.direction = Direction.DOWN; // The elevator never stops at the top floor.
        this.takingRequests = true;
      }
    } else {
      this.currentFloor -= floorIncrement;
      if (this.currentFloor < 0) {
        this.currentFloor = 0;
        this.direction = Direction.STOPPED;
        this.takingRequests = true;
      }
    }

  }

  /**
   * toString implementation.
   *
   * @return string representation of the elevator.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("[%d|%s|%s]<",
        this.currentFloor,
        this.direction,
        this.doorClosed ? "closed" : "open"));
    for (int i = 0; i < this.maxFloor; i++) {
      if (this.floorRequests[i]) {
        sb.append(String.format(" %2d", i));
      } else {
        sb.append(" --");
      }
    }
    sb.append(">");

    return sb.toString();

  }

  /**
   * toJson implementation.
   *
   * @return json representation of the elevator.
   */
  @Override
  public JSONObject toJson() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("elevatorId", this.id);
    jsonObject.put("currentFloor", this.currentFloor);
    jsonObject.put("direction", this.direction);
    JSONObject doorStatus = new JSONObject();
    if (this.doorClosed) {
      doorStatus.put("status", "closed");
    } else {
      doorStatus.put("status", "open");
      doorStatus.put("timer", this.openDoorTimer);
    }
    jsonObject.put("doorStatus", doorStatus);

    JSONArray floorRequestsJson = new JSONArray();
    for (int i = 0; i < this.maxFloor; i++) {
      if (this.floorRequests[i]) {
        floorRequestsJson.put(i);
      }
    }
    jsonObject.put("floorRequests", floorRequestsJson);
    return jsonObject;
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


}
