package main;


import building.Building;
import controller.BuildingController;
import display.BuildingDisplayInterface;
import display.ConsoleBuildingDisplay;

/**
 * The driver for the elevator system.
 * This class will create the elevator system and run it.
 * this is for testing the elevator system.
 * <p>
 * It provides a user interface to the elevator system.
 */
public class MainConsole {

  /**
   * The main method for the elevator system.
   * This method creates the elevator system and runs it.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    if (args.length != 0 && args.length != 3) {
      System.out.println("Usage: java -jar ElevatorSample floors elevators, capacity");
      System.exit(1);
    }

    // The number of floors, elevators, and people can be passed in as command line arguments.
    // If they are not passed in, the default values will be used.


    // the number of floors, the number of elevators, and the number of people.

    int numFloors = 11;
    int numElevators = 8;
    int numPeople = 3;

    if (args.length == 3) {
      numFloors = Integer.parseInt(args[0]);
      numElevators = Integer.parseInt(args[1]);
      numPeople = Integer.parseInt(args[2]);
    }


    Building building = new Building(numFloors, numElevators, numPeople);
    BuildingDisplayInterface display = new ConsoleBuildingDisplay();

    BuildingController controller = new BuildingController(building, display);

    controller.go();

  }


}