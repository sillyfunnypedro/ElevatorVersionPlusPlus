package building;

import scanerzus.Request;

/**
 * This interface is used to represent a building.
 */
public interface BuildingInterface {
  /**
   * This method is used to get the number of floors in the building.
   *
   * @return the number of floors in the building
   */
  int getNumberOfFloors();

  /**
   * This method is used to get the number of Elevators in the building.
   *
   * @return the number of elevators in the building
   */
  int getNumberOfElevators();

  /**
   * This method is used to get the elevator system status.
   *
   * @return the elevator system status
   * json string that contains the status of the elevator system.
   * {
   * "runStatus": "running", // running, stopped, stopping
   * "elevatorStatus": [
   * {
   * "elevatorId": 0,
   * "currentFloor": 1, // current floor of the elevator
   * "direction": "up", // up, down, stopped
   * "doorStatus": "open" // open, closed
   * },
   * {
   * "elevatorId": 1,
   * ....
   * }
   * ]
   * "inputRequests": [
   * {
   * "startFloor": 1,
   * "endFloor": 3
   * }, ......
   * ]
   * }
   */
  String getElevatorSystemStatus();

  /**
   * This method is used to add a request to the building.
   *
   * @param request the request to be added to the building
   */
  public void addRequest(Request request);

  /**
   * This method is used to step the building elevator system.
   */
  public void step();

}
