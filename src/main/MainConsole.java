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

    // the number of floors, the number of elevators, and the number of people.

    final int numFloors = 11;
    final int numElevators = 8;
    final int numPeople = 3;


    Building building = new Building(numFloors, numElevators, numPeople);
    BuildingDisplayInterface display = new ConsoleBuildingDisplay();

    BuildingController controller = new BuildingController(building, display);

    controller.go();

  }


}