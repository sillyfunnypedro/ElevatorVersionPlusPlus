package elevator;

import building.enums.Direction;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class is used to represent the status of the elevators.
 */
public class ElevatorReport {
  private int elevatorId;
  private int currentFloor;
  private boolean doorClosed;
  private boolean[] floorRequests;
  private Direction direction;

  private int doorOpenTimer;
  private int endWaitTimer;

  private boolean outOfService;


  /**
   * This constructor is used to create a new ElevatorReport object.
   *
   * @param elevatorId    The id of the elevator.
   * @param currentFloor  The current floor of the elevator.
   * @param doorClosed    The status of the door.
   * @param floorRequests The requests for the floors.
   * @param direction     The direction of the elevator.
   * @param doorOpenTimer The timer for the door.
   * @param endWaitTimer  The timer for the end of the run.
   * @param outOfService  The status of the elevator.
   */
  public ElevatorReport(int elevatorId,
                        int currentFloor,
                        Direction direction,
                        boolean doorClosed,
                        boolean[] floorRequests,

                        int doorOpenTimer,
                        int endWaitTimer,
                        boolean outOfService) {
    this.elevatorId = elevatorId;
    this.currentFloor = currentFloor;
    this.doorClosed = doorClosed;
    this.floorRequests = floorRequests;
    this.direction = direction;
    this.doorOpenTimer = doorOpenTimer;
    this.endWaitTimer = endWaitTimer;
    this.outOfService = outOfService;
  }

  /**
   * This constructor is used to create a new ElevatorReport object.
   *
   * @param elevatorJson the json object representing the elevator.
   */
  public ElevatorReport(JSONObject elevatorJson) {
    this.elevatorId = elevatorJson.getInt("elevatorId");
    this.currentFloor = elevatorJson.getInt("currentFloor");
    this.direction = Direction.valueOf(elevatorJson.getString("direction"));

    // the door status is an object
    JSONObject doorStatus = elevatorJson.getJSONObject("doorStatus");
    if (doorStatus.getString("status").equals("closed")) {
      this.doorClosed = true;
      this.doorOpenTimer = 0;
    } else {
      this.doorClosed = false;
      this.doorOpenTimer = doorStatus.getInt("timer");
    }

    // make an array of floorRequests, only list the true ones
    JSONArray floorRequestsJson = elevatorJson.getJSONArray("floorRequests");
    this.floorRequests = new boolean[floorRequestsJson.length()];
    for (int i = 0; i < floorRequestsJson.length(); i++) {
      this.floorRequests[i] = true;
    }
  }

  // getters, no setters

  /**
   * This method is used to get the id of the elevator.
   *
   * @return The id of the elevator.
   */
  public int getElevatorId() {
    return elevatorId;
  }

  /**
   * This method is used to get the current floor of the elevator.
   *
   * @return The current floor of the elevator.
   */
  public int getCurrentFloor() {
    return currentFloor;
  }

  /**
   * This method is used to get the status of the door.
   *
   * @return The status of the door.
   */
  public boolean isDoorClosed() {
    return doorClosed;
  }

  /**
   * This method is used to get the requests for the floors.
   *
   * @return The requests for the floors.
   */
  public boolean[] getFloorRequests() {
    return floorRequests;
  }

  /**
   * This method is used to get the direction of the elevator.
   *
   * @return The direction of the elevator.
   */
  public Direction getDirection() {
    return direction;
  }

  /**
   * This method is used to get the timer for the door.
   *
   * @return The timer for the door.
   */
  public int getDoorOpenTimer() {
    return doorOpenTimer;
  }

  /**
   * This method is used to get the timer for the end of the run.
   *
   * @return The timer for the end of the run.
   */
  public int getEndWaitTimer() {
    return endWaitTimer;
  }

  /**
   * This method is used to get the status of the elevator.
   *
   * @return The status of the elevator.
   */
  public boolean isOutOfService() {
    return outOfService;
  }

  /**
   * This method is used to get the status of the elevator.
   *
   * @return The status of the elevator.
   */
  public boolean getOutOfService() {
    return outOfService;
  }

  /**
   * This method is used to get the JSON representation.
   *
   * @return The status of the elevator as JSON object.
   */
  public JSONObject toJson() {
    JSONObject elevatorJson = new JSONObject();
    elevatorJson.put("elevatorId", this.elevatorId);
    elevatorJson.put("currentFloor", this.currentFloor);
    elevatorJson.put("direction", this.direction.toString());

    // the door status is an object
    JSONObject doorStatus = new JSONObject();
    if (this.doorClosed) {
      doorStatus.put("status", "closed");
    } else {
      doorStatus.put("status", "open");
      doorStatus.put("timer", this.doorOpenTimer);
    }
    elevatorJson.put("doorStatus", doorStatus);


    // make an array of floorRequests, only list the true ones
    JSONArray floorRequestsJson = new JSONArray();
    for (int i = 0; i < this.floorRequests.length; i++) {
      if (this.floorRequests[i]) {
        floorRequestsJson.put(i);
      }
    }
    elevatorJson.put("floorRequests", floorRequestsJson);
    //elevatorJson.put("endWaitTimer", this.endWaitTimer);
    //elevatorJson.put("outOfService", this.outOfService);


    return elevatorJson;
  }

  /**
   * The equals method for the ElevatorReport.
   *
   * @param o the object to compare to.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ElevatorReport)) {
      return false;
    }
    ElevatorReport that = (ElevatorReport) o;
    if (this.elevatorId != that.elevatorId) {
      return false;
    }
    if (this.currentFloor != that.currentFloor) {
      return false;
    }
    if (this.doorClosed != that.doorClosed) {
      return false;
    }
    if (this.doorOpenTimer != that.doorOpenTimer) {
      return false;
    }
    if (this.endWaitTimer != that.endWaitTimer) {
      return false;
    }
    if (this.outOfService != that.outOfService) {
      return false;
    }
    if (this.direction != that.direction) {
      return false;
    }
    for (int i = 0; i < this.floorRequests.length; i++) {
      if (this.floorRequests[i] != that.floorRequests[i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * The hashcode method for the ElevatorReport.
   */
  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + this.elevatorId;
    result = 31 * result + this.currentFloor;
    result = 31 * result + (this.doorClosed ? 1 : 0);
    result = 31 * result + this.doorOpenTimer;
    result = 31 * result + this.endWaitTimer;
    result = 31 * result + (this.outOfService ? 1 : 0);
    result = 31 * result + this.direction.hashCode();
    for (boolean floorRequest : this.floorRequests) {
      result = 31 * result + (floorRequest ? 1 : 0);
    }
    return result;
  }


}
