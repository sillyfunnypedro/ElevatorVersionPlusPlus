package building;

import building.handlers.RequestHandler;
import building.handlers.StepHandler;
import building.handlers.UpdateHandler;
import org.json.JSONObject;
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
    System.out.println("Building Display");
    System.out.println("Enter one of the following commands: ");
    System.out.println("s: step the building.");
    System.out.println("q: quit the simulation.");
    System.out.println("r fromFloor, toFloor: request an elevator from fromFloor to toFloor.");
    Scanner scanner = new Scanner(System.in);


    while (true) {
      this.displayPrompt();

      String[] command = getInput(scanner);
      switch (command[0]) {
        case "s":

          if (this.stepHandler != null) {
            this.stepHandler.handleRequest();
          }
          break;
        case "q":
          System.out.println("Quitting the simulation");
          return;
        case "r":
          if (this.requestHandler != null) {
            int fromFloor = Integer.parseInt(command[1]);
            int toFloor = Integer.parseInt(command[2]);
            this.requestHandler.handleRequest(new Request(fromFloor, toFloor));
          }
          break;
        default:
          // do nothing
          break;
      }
      updateDisplay();

    }
  }

  private void updateDisplay() {
    if (this.updateHandler != null) {
      JSONObject buildingStatus = this.updateHandler.handleRequest();
      System.out.println(buildingStatus.toString(2));
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
      return new String[]{"s"};
    }
    // check the first character of the input
    char firstChar = input.charAt(0);

    switch (firstChar) {
      case 's':
        return new String[]{"s"};
      case 'q':
        return new String[]{"q"};
      case 'r':
        String[] parts = input.split(" ");
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
            return new String[]{"s"};
          }
        }
      default:
        System.out.println("Invalid input");
        System.out.println("The elevators will all execute a step");
        return new String[]{"s"};
    }
  }

  public void displayPrompt() {
    System.out.print("[s] step, [q] quit, [r start end] request > ");
  }
}