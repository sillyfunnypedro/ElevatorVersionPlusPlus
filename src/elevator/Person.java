package elevator;

/**
 * A person in an elevator.
 * The only thing that matters about a person in an elevator is their travel
 * ticket.  The travel ticket is the floor number that the person wants to go to.
 * The
 */
public class Person {
  private final int startFloor;
  private final int targetFloor;

  /**
   * Constructs a person with a starting floor and an ending floor.
   *
   * @param startFloor the floor the person is starting on
   * @param endFloor   the floor the person wants to go to
   */
  public Person(int startFloor, int endFloor) {
    this.startFloor = startFloor;
    this.targetFloor = endFloor;
  }

  /**
   * Gets the starting floor of the person.
   *
   * @return the starting floor of the person
   */
  public int getStartFloor() {
    return startFloor;
  }

  /**
   * Gets the ending floor of the person.
   *
   * @return the ending floor of the person
   */
  public int getTargetFloor() {
    return targetFloor;
  }

}
