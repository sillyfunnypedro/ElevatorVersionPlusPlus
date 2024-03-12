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
  public void startElevatorSystem();

  /**
   * This method is used to take the elevator out of service.
   */
  public void stopElevatorSystem();

  /**
   * This method is used to get the elevator system status.
   * See the comment above.
   *
   * @return the elevator system status
   */
  BuildingReport getStatusElevatorSystem();

  /**
   * This method is used to add a request to the building.
   *
   * @param request the request to be added to the building
   */
  public void addRequestToElevatorSystem(Request request);

  /**
   * This method is used to step the building elevator system.
   */
  public void stepElevatorSystem();

}
