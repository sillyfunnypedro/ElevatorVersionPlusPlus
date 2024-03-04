package elevator;

import scanerzus.Request;

/**
 * An interface for an elevator.
 */
public interface ElevatorInterface {

  /**
   * Elevator ID getter.
   *
   * @return the elevator ID as a string.
   */
  public String getElevatorId();

  /**
   * Returns the maximum number of floors the elevator can go to.
   *
   * @return the maximum number of floors the elevator can go to.
   */
  public int getMaxFloor();

  /**
   * maxOccupancy getter
   * Notice that it is not the responsibility of the elevator to
   * keep track of the people in the elevator.
   *
   * @return the maximum number of people that can fit in the elevator.
   */
  public int getMaxOccupancy();

  /**
   * Returns the current floor of the elevator.
   *
   * @return the current floor of the elevator.
   */

  public int getCurrentFloor();

  /**
   * Moves the elevator by one floor.
   */
  public void step();

  /**
   * Returns the direction the elevator is moving in.
   *
   * @return the direction the elevator is moving in.
   */

  public Direction getDirection();

  /**
   * Take out of service.
   */
  public void takeOutOfService();

  /**
   * start elevator.
   * This will start the elevator if the elevator is on the ground floor.
   * This means the elevator will accept requests and will start its up and down routine.
   */
  public void start();

  /**
   * processRequest
   * This will add a request to the elevator.
   *
   * @param request the request to add to the elevator.
   */
  public void processRequests(Request[] request);


}
