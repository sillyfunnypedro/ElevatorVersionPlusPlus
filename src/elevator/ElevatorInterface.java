package elevator;

import building.enums.Direction;

import java.util.List;

import org.json.JSONObject;
import scanerzus.Request;


/**
 * An interface for an elevator.
 */
public interface ElevatorInterface {

  /**
   * ElevatorStatus ID getter.
   *
   * @return the elevator ID as a string.
   */
  public int getElevatorId();

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
   * Returns the direction the elevator is moving in.
   *
   * @return the direction the elevator is moving in.
   */
  public Direction getDirection();

  /**
   * Returns the door status of the elevator.
   *
   * @return the door status of the elevator.
   */
  public boolean isDoorClosed();

  /**
   * Return the current stop requests.
   *
   * @return the current stop requests.
   */
  public boolean[] getFloorRequests();

  /**
   * start elevator.
   * This will start the elevator if the elevator is on the ground floor.
   * This means the elevator will accept requests and will start its up and down routine.
   */
  public void start();


  /**
   * Take out of service.
   */
  public void takeOutOfService();

  /**
   * Moves the elevator by one floor.
   * The elevator is going to move by one floor in the direction it is currently moving.
   * If the elevator is stopped, it will not move.
   * If the elevator arrives at a floor where it is supposed to stop then it will open
   * its doors and let people out.
   * The elevator will stop for three-step increments then it will close its doors and move on.
   * If the elevator is moving up, and it arrives at the top floor, it will change direction.
   */
  public void step();

  /**
   * processRequest
   * This will tell the elevator to process these requests on the next run.
   *
   * @param requests the request to add to the elevator.
   */
  public void processRequests(List<Request> requests);

  /**
   * This method is used to get the elevator status as a JSON object.
   *
   * @return the elevator status.
   */
  public JSONObject toJson();

}
