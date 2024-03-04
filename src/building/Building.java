package building;

import elevator.Elevator;
import scanerzus.Request;
import scanerzus.RequestGenerator;

import javax.swing.*;

/**
 * This class represents a building.
 */
public class Building implements BuildingInterface {

  private final int numberOfFloors;
  private final int numberOfElevators;

  private final RequestGenerator requestGenerator;

  private int elevatorCapacity = 30;

  private final Elevator[] elevators;

  private final JFrame displayFrame = null;

  private final int sliderWidth = 95;


  /**
   * Constructs a Building object and initializes it to the given number of floors and elevators.
   *
   * @param numberOfFloors    the number of floors in the building
   * @param numberOfElevators the number of elevators in the building
   * @param elevatorCapacity  the maximum number of people that can be in an elevator at once
   */
  public Building(int numberOfFloors, int numberOfElevators, int elevatorCapacity) throws IllegalArgumentException {
    // check the parameters for validity.

    // are floors greater than or equal to 2.
    if (numberOfFloors < 2) {
      throw new IllegalArgumentException("numberOfFloors must be greater than or equal to 2");
    }

    // are elevators greater than or equal to 1.
    if (numberOfElevators < 1) {
      throw new IllegalArgumentException("numberOfElevators must be greater than or equal to 1");
    }

    // The max occupancy of the elevator must be greater than 1.
    if (elevatorCapacity < 1) {
      throw new IllegalArgumentException("maxOccupancy must be greater than or equal to 1");
    }


    this.numberOfFloors = numberOfFloors;
    this.numberOfElevators = numberOfElevators;
    this.elevatorCapacity = elevatorCapacity;

    // Initialize the RequestGenerator
    this.requestGenerator = new RequestGenerator(numberOfFloors);

    // Initialize the Elevators in the building
    this.elevators = new Elevator[numberOfElevators];

    for (int i = 0; i < numberOfElevators; i++) {
      this.elevators[i] = new Elevator(numberOfFloors, this.elevatorCapacity);
    }

    BuildingDisplay display = new BuildingDisplay(this.elevators,
        this.makeStepRunnable(),
        this.makeRunRunnable(),
        this.makeStopRunnable(),
        this.makeResetRunnable(),
        this.makeRandomRequestRunnable(),
        this.makeRandomUpRequestRunnable(),
        this.makeRandomDownRequestRunnable());


    display.initializeDisplay();
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

  @Override
  public String toString() {
    return "Building{"
        + "numberOfFloors=" + numberOfFloors
        + ", numberOfElevators=" + numberOfElevators
        + '}';
  }

  @Override
  public boolean receiveRequest(Request request) {
    return false;
  }


  /**
   * returns a Runnable that will call step() on the building.
   *
   * @return a Runnable that will call step() on the building.
   */
  public Runnable makeStepRunnable() {
    return new Runnable() {
      public void run() {
        step();
      }
    };

  }

  /**
   * This method is used to step the building.
   */
  private void step() {
    System.out.println("Stepping the building");
    for (Elevator elevator : elevators) {
      elevator.step();
    }
  }

  /**
   * returns a Runnable that will call stop() on the building.
   *
   * @return a Runnable that will call stop() on the building.
   */
  public Runnable makeStopRunnable() {
    return new Runnable() {
      public void run() {
        stop();
      }
    };
  }

  /**
   * This method is used to stop the building.
   */
  private void stop() {
    System.out.println("Stopping the building");
  }

  /**
   * returns a Runnable that will call run() on the building.
   *
   * @return a Runnable that will call run() on the building.
   */
  public Runnable makeRunRunnable() {
    return new Runnable() {
      public void run() {
        runBuilding();
      }
    };
  }

  /**
   * This method is used to run the building.
   */
  private void runBuilding() {
    System.out.println("Running the building");
  }


  /**
   * returns a Runnable that will call reset() on the building.
   *
   * @return a Runnable that will call reset() on the building.
   */
  public Runnable makeResetRunnable() {
    return new Runnable() {
      public void run() {
        resetBuilding();
      }
    };
  }


  /**
   * This method is used to reset the building.
   */
  private void resetBuilding() {
    System.out.println("Resetting the building");
  }


  /**
   * returns a runnable that will get a random request for the building.
   *
   * @return a runnable that will get a random request for the building.
   */
  public Runnable makeRandomRequestRunnable() {
    return new Runnable() {
      public void run() {
        randomRequest();
      }
    };
  }

  /**
   * This method is used to get a random request for the building.
   */
  private void randomRequest() {
    Request request = requestGenerator.generateRequestRandom();

    System.out.println("Found a random request: " + request);
  }

  /**
   * returns a runnable that will get a random up request for the building.
   *
   * @return a runnable that will get a random up request for the building.
   */
  public Runnable makeRandomUpRequestRunnable() {
    return new Runnable() {
      public void run() {
        randomUpRequest();
      }
    };
  }

  /**
   * This method is used to get a random up request for the building.
   */
  private void randomUpRequest() {
    Request request = requestGenerator.generateUpRequest();

    System.out.println("Found a random up request: " + request);
  }

  /**
   * returns a runnable that will get a random down request for the building.
   *
   * @return a runnable that will get a random down request for the building.
   */
  public Runnable makeRandomDownRequestRunnable() {
    return new Runnable() {
      public void run() {
        randomDownRequest();
      }
    };
  }

  /**
   * This method is used to get a random down request for the building.
   */
  private void randomDownRequest() {
    Request request = requestGenerator.generateDownRequest();

    System.out.println("Found a random down request: " + request);
  }


}


