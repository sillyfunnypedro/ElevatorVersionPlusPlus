package display;

import building.handlers.RequestHandler;
import building.handlers.StartElevatorSystemHandler;
import building.handlers.StepHandler;
import building.handlers.StopElevatorSystemHandler;
import building.handlers.UpdateHandler;
import building.BuildingReport;

import scanerzus.Request;

import java.util.Scanner;

/**
 * This class is used to display the building information in the console.
 * It also acts as the input view for the user where at each iteration.
 * the user can enter one of the following commands.
 * s: step the building.
 * q: quit the simulation.
 * r fromFloor, toFloor: request an elevator from fromFloor to toFloor.
 */
public class ConsoleBuildingDisplay implements BuildingDisplayInterface {
  private RequestHandler requestHandler = null;
  private UpdateHandler updateHandler = null;

  private StepHandler stepHandler = null;

  private StartElevatorSystemHandler startHandler = null;

  private StopElevatorSystemHandler stopHandler = null;


  public ConsoleBuildingDisplay() {
  }


  /**
   * This method is used to set the request listener.
   *
   * @param requestHandler the request handler
   */
  @Override
  public void setRequestListener(RequestHandler requestHandler) {
    this.requestHandler = requestHandler;
  }


  /**
   * This method is used to set the update listener.
   *
   * @param updateHandler the update handler
   */

  @Override
  public void setUpdateListener(UpdateHandler updateHandler) {
    this.updateHandler = updateHandler;
  }

  /**
   * This method is used to set the start listener.
   *
   * @param startHandler the start handler
   */
  @Override
  public void setStartListener(StartElevatorSystemHandler startHandler) {
    this.startHandler = startHandler;
  }

  /**
   * This method is used to set the stop listener.
   *
   * @param stopHandler the stop handler
   */
  @Override
  public void setStopListener(StopElevatorSystemHandler stopHandler) {
    this.stopHandler = stopHandler;
  }

  /**
   * This method is used to set the step listener.
   *
   * @param stepHandler the step handler
   */
  @Override
  public void setStepListener(StepHandler stepHandler) {
    this.stepHandler = stepHandler;
  }


  /**
   * This method is used to start the building display.
   */
  @Override
  public void start() {

    Scanner scanner = new Scanner(System.in);
    detailedDisplay(scanner);


    while (true) {
      updateDisplay();
      this.displayPrompt();

      String[] command = getInput(scanner);
      // The result from getInput is guaranteed to be valid so
      // we do no error checking here.
      switch (command[0]) {
        case "s":
          // the second parameter is the number of steps to execute
          int steps = Integer.parseInt(command[1]);
          runStep(steps);
          break;


        case "r":
          if (this.requestHandler != null) {
            int fromFloor = Integer.parseInt(command[1]);
            int toFloor = Integer.parseInt(command[2]);
            try {
              this.requestHandler.handleRequest(new Request(fromFloor, toFloor));
            } catch (IllegalArgumentException | IllegalStateException e) {
              System.out.println("Request was rejected with the following message: \n\n\t"
                  + e.getMessage());
              // wait for a key press
              System.out.println("\nPress enter to continue");
              scanner.nextLine();
            }
          }
          break;

        case "h":
          if (this.stopHandler != null) {
            this.stopHandler.requestHandler();
            System.out.println("Halting the operations of the building");
          }
          break;

        case "c":

          if (this.startHandler != null) {
            try {
              this.startHandler.requestHandler();
              System.out.println("Continuing the operations of the building");
            } catch (IllegalStateException e) {
              System.out.println(e.getMessage());
              System.out.println("Press enter to continue");
              scanner.nextLine();
              break;
            }

          }
          break;

        case "t":
          // the second parameter is the number random requests to generate
          int requests = Integer.parseInt(command[1]);
          // no more than 10 requests

          // get the number of floors in the building
          BuildingReport buildingReport = this.updateHandler.handleRequest();
          int numFloors = buildingReport.getNumFloors();
          for (int i = 0; i < requests; i++) {
            int fromFloor = (int) (Math.random() * numFloors);
            int toFloor = (int) (Math.random() * numFloors);
            if (fromFloor == toFloor) {
              continue;
            }
            try {
              this.requestHandler.handleRequest(new Request(fromFloor, toFloor));
            } catch (IllegalArgumentException | IllegalStateException e) {
              System.out.println("Request was rejected with the following message: \n\n\t"
                  + e.getMessage());
              System.out.println("No further requests will be generated");
              System.out.println("c will resume the operations of the building");
              // wait for a key press
              System.out.println("\nPress enter to continue");
              scanner.nextLine();
              break;
            }
            this.requestHandler.handleRequest(new Request(fromFloor, toFloor));

          }

          break;


        case "q":
          System.out.println("Quitting the simulation");
          return;


        default:
          // do nothing
          break;
      }


    }
  }

  private void detailedDisplay(Scanner scanner) {
    System.out.println("Building Display");
    System.out.println("Enter one of the following commands: ");
    System.out.println("s: <steps> step the building by <steps> (max 100)");
    System.out.println("q: quit the simulation.");
    System.out.println("h: halt the operations of the building.");
    System.out.println("c: continue the operations of the building.");
    System.out.println("r: fromFloor, toFloor: request an elevator from fromFloor to toFloor.");
    System.out.println("press enter to continue");

    scanner.nextLine();

  }

  private void runStep(int steps) {
    if (steps > 1000) {
      System.out.println("The number of steps is too large.  The maximum number of steps is 1000");
      System.out.println("press enter to continue");
      Scanner scanner = new Scanner(System.in);
      scanner.nextLine();
      return;
    }

    for (int i = 0; i < steps; i++) {
      if (this.stepHandler != null) {

        this.stepHandler.handleRequest();
        if (i < steps - 1) {
          updateDisplay();
        }

        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          return;
        }
      }
    }
  }

  private void updateDisplay() {
    if (this.updateHandler != null) {
      BuildingReport buildingStatus = this.updateHandler.handleRequest();

      String buildingDisplay = new AsciiBuildingDisplay().display(buildingStatus);

      System.out.println(buildingDisplay);
    }
  }

  /**
   * Get the input from the user, validate it and return it.
   *
   * @param scanner the scanner to get the input from.
   * @return array of valid inputs from the user.
   */
  public String[] getInput(Scanner scanner) {
    String input = scanner.nextLine();
    if (input.isEmpty()) {
      System.out.println("The elevators will all execute a step");
      return new String[]{"s", "1"};
    }
    // check the first character of the input
    char firstChar = input.charAt(0);
    String[] tokens;
    switch (firstChar) {
      case 's':
        tokens = input.split(" ");
        if (tokens.length == 1) {
          return new String[]{"s", "1"};
        } else {

          try {
            int steps = Integer.parseInt(tokens[1]);
            return new String[]{"s", Integer.toString(steps)};
          } catch (NumberFormatException e) {
            System.out.println("Invalid input");
            System.out.println("The elevators will all execute a step");
            return new String[]{"s", "1"};
          }
        }


      case 'r':
        tokens = input.split(" ");
        if (tokens.length != 3) {
          System.out.println("Invalid input");
          return new String[]{"invalid"};
        } else {
          try {
            int fromFloor = Integer.parseInt(tokens[1]);
            int toFloor = Integer.parseInt(tokens[2]);
            return new String[]{"r", Integer.toString(fromFloor), Integer.toString(toFloor)};
          } catch (NumberFormatException e) {
            System.out.println("Invalid input");
            System.out.println("The elevators will all execute a step");
            return new String[]{"s", "1"};
          }
        }
      case 't':
        tokens = input.split(" ");
        if (tokens.length == 1) {
          return new String[]{"t", "1"};
        } else {
          // we will try to get the number.
          // if it is not a number we will default to 1
          try {
            int requests = Integer.parseInt(tokens[1]);
            return new String[]{"t", Integer.toString(requests)};
          } catch (NumberFormatException e) {
            return new String[]{"t", "1"};
          }
        }

      case 'h':
        return new String[]{"h"};

      case 'c':
        return new String[]{"c"};

      case 'q':
        return new String[]{"q"};

      default:
        System.out.println("Invalid input");
        System.out.println("The elevators will all execute a step");
        return new String[]{"s", "1"};
    }
  }


  public void displayPrompt() {
    System.out.print("[s steps], [CR] one step, [r start end] [h] halt [c] continue [q] quit > ");
  }


}