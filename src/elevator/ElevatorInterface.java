package elevator;

/**
 * An interface for an elevator.
 */
public interface ElevatorInterface {

  /**
   * Returns the maximum number of floors the elevator can go to.
   *
   * @return the maximum number of floors the elevator can go to
   */
  public int getMaxFloor();

  /**
   * maxOccupancy getter
   * Notice that it is not the responsibility of the elevator to
   * keep track of the people in the elevator.
   *
   * @return the maximum number of people that can fit in the elevator
   */
  public int getMaxOccupancy();

  /**
   * Returns the current floor of the elevator.
   *
   * @return the current floor of the elevator
   */

  public float getCurrentFloor();

  /**
   * Steps by .1 in the current direction
   * If the elevator is at floor 0 and the direction is down the direction is set to STOPPED.
   * If the elevator is at the top floor and the direction is up the direction is set to STOPPED
   */
  public void step();

  /**
   * Returns the direction the elevator is moving in.
   *
   * @return the direction the elevator is moving in
   */

  public Direction getDirection();

  /**
   * set the direction of the elevator
   *
   * @param direction the direction to set
   *                  if the direction is set to up and the elevator is at the top floor
   *                  the direction is set to STOPPED
   *                  if the direction is set to down and the elevator is at the bottom floor
   *                  the direction is set to STOPPED
   *                  if the elevator is not at a floor and the request is set to STOPPED
   *                  it throws an exception
   * @throws IllegalStateException if the elevator is between floors
   */
  public void setDirection(Direction direction);

  /**
   * Open the door of the elevator.
   * If the door is already open, this method does nothing.
   * If the door is closed, it is opened.
   *
   * @throws IllegalStateException if the elevator is between floors
   */
  public void openDoor() throws IllegalStateException;

  /**
   * Close the door of the elevator.
   * If the door is already closed, this method does nothing.
   * If the door is open, it is closed.
   */
  public void closeDoor();

  /**
   * Returns true if the door of the elevator is open, false otherwise.
   *
   * @return true if the door of the elevator is open, false otherwise
   */
  public boolean isDoorClosed();


}
