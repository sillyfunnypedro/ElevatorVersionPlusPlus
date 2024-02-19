package scanerzus;

/**
 * This class generates requests for the elevator.
 */
public class RequestGenerator {
  private final int numFloors;

  /**
   * The constructor for the request generator.
   *
   * @param numFloors the number of floors in the building
   */
  public RequestGenerator(int numFloors) {
    this.numFloors = numFloors;
  }

  /**
   * the getter for numFloors.
   *
   * @return the number of floors in the building
   */
  public int getNumFloors() {
    return numFloors;
  }

  /**
   * Generate a request for the elevator.
   *
   * @return the request
   */
  public Request generateRequestRandom() {
    int floor = (int) (Math.random() * this.numFloors);
    // pick a different floor that is at least one floor away
    int nextFloor = (int) (Math.random() * (this.numFloors - 1));
    if (nextFloor >= floor) {
      nextFloor++;
    }

    return new Request(floor, nextFloor);
  }

  /**
   * Generate a up request for the elevator.
   * If the elevator is at the top floor then the request will be null.
   *
   * @param startFloor the floor the request is generated on
   * @return the request
   */
  public Request generateUpRequest(int startFloor) {
    if (startFloor == this.numFloors - 1) {
      return null;
    }

    // Get a random number between 1 and the number of floors left
    int increment = (int) (Math.random() * (this.numFloors - startFloor - 1)) + 1;

    int nextFloor = startFloor + increment;

    return new Request(startFloor, nextFloor);
  }

  /**
   * Generate a down request for the elevator.
   * If the elevator is at the bottom floor then the request will be null.
   *
   * @param startFloor the floor the request is generated on
   * @return the request
   */
  public Request generateDownRequest(int startFloor) {
    if (startFloor == 0) {
      return null;
    }

    int nextFloor = (int) (Math.random() * (startFloor));

    return new Request(startFloor, nextFloor);
  }
}
