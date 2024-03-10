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
   * This method is used to start the building elevator system.  If the startElevators
   * parameter is true, then the elevators will be started.  If the startElevators
   * parameter is false, then the elevators will not be started.
   *
   * @param startElevators if true, then the elevators will be started.
   *                       If false, then the elevators will be stopped.
   */
  public void start(boolean startElevators);


  /**
   * This method is used to get the elevator system status.
   * See the comment above.
   *
   * @return the elevator system status
   */
  BuildingReport getElevatorSystemStatus();

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
