package display;

import building.handlers.RequestHandler;
import building.handlers.StepHandler;
import building.handlers.UpdateHandler;
import building.BuildingReport;

import elevator.ElevatorReport;
import scanerzus.Request;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
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

        case "q":
          System.out.println("Quitting the simulation");
          return;

        case "r":
          if (this.requestHandler != null) {
            int fromFloor = Integer.parseInt(command[1]);
            int toFloor = Integer.parseInt(command[2]);
            try {
              this.requestHandler.handleRequest(new Request(fromFloor, toFloor));
            } catch (IllegalArgumentException e) {
              System.out.println("Request was rejected with the following message: \n\n\t"
                  + e.getMessage());
              // wait for a key press
              System.out.println("\nPress enter to continue");
              scanner.nextLine();
            }
          }
          break;

        case "h":
          if (this.stepHandler != null) {
            System.out.println("Halting the operations of the building");
          }
          break;

        case "c":
          if (this.stepHandler != null) {
            System.out.println("Continuing the operations of the building");
          }
          break;

        default:
          // do nothing
          break;
      }


    }
  }

  private void detailedDisplay(Scanner scanner) {
    System.out.println("Building Display");
    System.out.println("Enter one of the following commands: ");
    System.out.println("s: step the building. (r");
    System.out.println("q: quit the simulation.");
    System.out.println("h: halt the operations of the building.");
    System.out.println("c: continue the operations of the building.");
    System.out.println("r fromFloor, toFloor: request an elevator from fromFloor to toFloor.");
    System.out.println("press enter to continue");

    scanner.nextLine();

  }

  private void runStep(int steps) {

    // add a keyEvent handler
    KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
      @Override
      public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
          if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
            // stop the simulation
            System.out.println("Stopping the simulation");
            return false;
          }
        }
        return false;
      }
    };

    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);

    for (int i = 0; i < steps; i++) {
      if (this.stepHandler != null) {

        this.stepHandler.handleRequest();
        if (i < steps - 1) {
          updateDisplay();
        }

        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          return;
        }
      }
    }
  }

  private void updateDisplay() {
    if (this.updateHandler != null) {
      BuildingReport buildingStatus = this.updateHandler.handleRequest();
      String[] displayArray = computeElevatorDisplayArray(buildingStatus);
      System.out.println(BuildingDisplay(buildingStatus));
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

    switch (firstChar) {
      case 's':
        String[] parts = input.split(" ");
        if (parts.length == 1) {
          return new String[]{"s", "1"};
        } else {

          try {
            int steps = Integer.parseInt(parts[1]);
            return new String[]{"s", Integer.toString(steps)};
          } catch (NumberFormatException e) {
            System.out.println("Invalid input");
            System.out.println("The elevators will all execute a step");
            return new String[]{"s", "1"};
          }
        }

      case 'q':
        return new String[]{"q"};
      case 'r':
        parts = input.split(" ");
        if (parts.length != 3) {
          System.out.println("Invalid input");
          return new String[]{"invalid"};
        } else {
          try {
            int fromFloor = Integer.parseInt(parts[1]);
            int toFloor = Integer.parseInt(parts[2]);
            return new String[]{"r", Integer.toString(fromFloor), Integer.toString(toFloor)};
          } catch (NumberFormatException e) {
            System.out.println("Invalid input");
            System.out.println("The elevators will all execute a step");
            return new String[]{"s", "1"};
          }
        }
      default:
        System.out.println("Invalid input");
        System.out.println("The elevators will all execute a step");
        return new String[]{"s", "1"};
    }
  }


  /**
   * Compute the elevator display array.
   * This method is used to compute the display array for the elevators.
   */
  private String[] computeElevatorDisplayArray(BuildingReport buildingStatus) {
    ElevatorReport[] elevatorReports = buildingStatus.getElevatorReports();

    int numFloors = buildingStatus.getNumFloors();
    String[] displayArray = new String[numFloors];

    for (int floor = numFloors - 1; floor >= 0; floor--) {
      StringBuilder sb = new StringBuilder();
      sb.append(String.format("%2d", floor));
      for (ElevatorReport elevatorReport : elevatorReports) {
        if (elevatorReport.getCurrentFloor() == floor) {
          sb.append(String.format("%2d", elevatorReport.getCurrentFloor()));
        } else {
          sb.append("  ");
        }
      }
      displayArray[floor] = sb.toString();
    }

    return displayArray;
  }

  public void displayPrompt() {
    System.out.print("[s steps], [CR] one step, [r start end] [h] halt [c] continue [q] quit > ");
  }


  private String centreString(String s, int width) {
    int leftPadding = (width - 2 - s.length()) / 2;
    int rightPadding = width - leftPadding - s.length();
    return "*" + " ".repeat(leftPadding) + s + " ".repeat(rightPadding) + "*";
  }

  private String leftString(String s, int width) {
    return "* " + s + " ".repeat(width - s.length() - 1) + "*";
  }

  private String bar(int width) {
    return "*".repeat(width) + "\n";
  }

  private String requestsToString(String title, List<Request> requests) {
    StringBuilder sb = new StringBuilder();
    StringBuilder line = new StringBuilder();
    line.append(title);
    line.append(" ");
    int width = 76;

    for (Request request : requests) {
      if (line.length() + request.toString().length() > width) {
        sb.append(centreString(line.toString(), 78));
        sb.append("\n");
        line = new StringBuilder();
      }
      line.append(request.toString());
      line.append(" ");

    }
    if (line.length() > 0) {
      sb.append(leftString(line.toString(), 78));
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * Building Display method for the BuildingReport.
   *
   * @return the string representation of the BuildingReport
   */

  private String BuildingDisplay(BuildingReport buildingReport) {
    StringBuilder sb = new StringBuilder();
    sb.append("\033[H\033[2J");
    sb.append(this.bar(80));


    String title = String.format("Floors: %d, Elevators: %d, Capacity: %d",
        buildingReport.getNumFloors(),
        buildingReport.getNumElevators(),
        buildingReport.getElevatorCapacity());
    sb.append(centreString(title, 78));
    sb.append("\n");
    sb.append(this.bar(80));
    sb.append(this.requestsToString("Up Requests",
        buildingReport.getUpRequests()));
    sb.append(this.requestsToString("Down Requests",
        buildingReport.getDownRequests()));

    sb.append(this.bar(80));
    sb.append(this.centreString("Elevator Status", 78));
    sb.append("\n");
    // add the elevator status
    for (int i = 0; i < buildingReport.getElevatorReports().length; i++) {
      String elevatorStatus = String.format("Elevator %d: %s", i,
          buildingReport.getElevatorReports()[i].toString());
      sb.append(this.leftString(elevatorStatus, 78));
      sb.append("\n");
    }
    sb.append(this.bar(80));


    return sb.toString();
  }

}