package building;

import org.json.JSONObject;
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
   * This method is used to get the number of ElevatorSystemStatus in the building.
   *
   * @return the number of elevators in the building
   */
  int getNumberOfElevators();

  /**
   * This method is used to get the max occupancy of the elevator.
   *
   * @return the max occupancy of the elevator
   */
  int getElevatorCapacity();

  /**
   * Start the building elevator system.
   * This method is used to start the building elevator system.
   */
  public void start();



  /* For a building with 4 floors and 1 elevator, the JSON object returned by getElevatorSystemStatus
   * would look like this:
   *
  {
  "numElevators": 1,
  "buildingStatus: "running",
  "inputRequests": [],
  "elevatorStatus": [{
    "elevatorId": 0,
    "doorStatus": "closed",
    "currentFloor": 0,
    "floorRequests": [
      0,
      1,
      2
    ],
    "direction": "STOPPED"
  }],
  "numFloors": 4,
  "elevatorCapacity": 3
}
   */

  /**
   * This method is used to get the elevator system status.
   * See the comment above.
   *
   * @return the elevator system status
   */
  JSONObject getElevatorSystemStatus();

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
