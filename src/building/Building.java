package building;

import elevator.Elevator;

/**
 * This class represents a building.
 */
public class Building implements BuildingInterface {

  private final int numberOfFloors;
  private final int numberOfElevators;

  private final int elevatorCapacity = 30;

  private final Elevator[] elevators;

  /**
   * Constructs a Building object and initializes it to the given number of floors and elevators.
   *
   * @param numberOfFloors    the number of floors in the building
   * @param numberOfElevators the number of elevators in the building
   */
  public Building(int numberOfFloors, int numberOfElevators) throws IllegalArgumentException {
    // check the parameters for validity.

    // are floors greater than or equal to 2.
    if (numberOfFloors < 2) {
      throw new IllegalArgumentException("numberOfFloors must be greater than or equal to 2");
    }

    // are elevators greater than or equal to 1.
    if (numberOfElevators < 1) {
      throw new IllegalArgumentException("numberOfElevators must be greater than or equal to 1");
    }

    this.numberOfFloors = numberOfFloors;
    this.numberOfElevators = numberOfElevators;

    // Initialize the Elevators in the building
    this.elevators = new Elevator[numberOfElevators];

    for (int i = 0; i < numberOfElevators; i++) {
      this.elevators[i] = new Elevator(numberOfFloors, elevatorCapacity);
    }

  }

  /**
   * This method is used to get the number of floors in the building.
   *
   * @return the number of floors in the building
   */
  @Override
  public int getNumberOfFloors() {
    return 0;
  }

  /**
   * This method is used to get the number of Elevators in the building.
   *
   * @return the number of elevators in the building
   */
  @Override
  public int getNumberOfElevators() {
    return 0;
  }
}
