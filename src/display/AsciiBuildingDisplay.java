package display;

import building.BuildingReport;
import building.enums.ElevatorSystemStatus;
import elevator.ElevatorReport;
import scanerzus.Request;

import java.util.List;

/**
 * This generates the ascii display for the building.
 */

public class AsciiBuildingDisplay {


  public String display(BuildingReport buildingReport) {
    return buildingDisplay(buildingReport);
  }

  private String centreString(String s, int width) {
    int leftPadding = (width - 2 - s.length()) / 2;
    int rightPadding = width - leftPadding - s.length();
    return "*" + " ".repeat(leftPadding) + s + " ".repeat(rightPadding) + "*";
  }

  private String emptyLine(int width) {
    return "*" + " ".repeat(width - 2) + "*";
  }


  private String leftString(String s, int width) {
    if (width - s.length() - 1 < 0) {
      return s;
    }
    return "* " + s + " ".repeat(width - s.length() - 1) + "*";
  }

  private String bar(int width) {
    return "*".repeat(width) + "\n";
  }

  private String requestsToString(String title, List<Request> requests) {
    StringBuilder sb = new StringBuilder();
    StringBuilder line = new StringBuilder();
    line.append(title);
    // pad the title to be 4 wide
    if (line.length() < 4) {
      line.append(" ".repeat(4 - line.length()));
    }
    // add the count of requests
    line.append(String.format("[(%03d)]", requests.size()));

    line.append(" ");
    int width = 65;
    int requestsProcessed = 0;
    for (Request request : requests) {
      if (line.length() + request.toString().length() > width) {
        line.append("...");
        break;
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
   * This method is used to clear the screen.
   *
   * @return the string to clear the screen.
   */
  private String clearScreen() {
    return "\033[H\033[2J";
  }

  /**
   * Building Display method for the BuildingReport.
   *
   * @return the string representation of the BuildingReport
   */
  private String buildingDisplay(BuildingReport buildingReport) {
    StringBuilder sb = new StringBuilder();
    sb.append(this.clearScreen());
    sb.append(this.bar(80));


    String title = String.format("Floors: %d, Elevators: %d, Capacity: %d",
        buildingReport.getNumFloors(),
        buildingReport.getNumElevators(),
        buildingReport.getElevatorCapacity());


    sb.append(centreString(title, 78));
    sb.append("\n");

    ElevatorSystemStatus sysStatus = buildingReport.getSystemStatus();

    switch (sysStatus) {
      case stopping:
        sb.append(centreString("Elevator System Stopping", 78));

        break;
      case outOfService:
        sb.append(centreString("Elevator System Out of Service", 78));
        break;
      case running:
        sb.append(centreString("Elevator System Running", 78));
        break;
      default:
        break;
    }
    sb.append("\n");
    sb.append(this.bar(80));

    sb.append(this.requestsToString("Up",
        buildingReport.getUpRequests()));
    sb.append(this.requestsToString("Down",
        buildingReport.getDownRequests()));


    sb.append(this.bar(80));
    sb.append(this.centreString("Elevator Status", 78));
    sb.append("\n");


    // add the elevator status
    String[] elevatorStatus = elevatorWithGraphic(buildingReport);
    for (String status : elevatorStatus) {
      sb.append(status);
      sb.append("\n");
    }
    sb.append(this.bar(80));


    return sb.toString();
  }

  /**
   * This method is used to generate the welcome string.
   *
   * @return the welcome string.
   */
  public String welcomeString() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.clearScreen());
    sb.append(this.bar(80));
    sb.append(this.centreString("Welcome to the Elevator System", 78)).append("\n");
    sb.append(this.bar(80));
    sb.append(this.emptyLine(80)).append("\n");
    sb.append(this.centreString("IMPORTANT NOTICE", 78)).append("\n");
    sb.append(this.emptyLine(80)).append("\n");
    sb.append(this.centreString("This system is for your experimentation only", 78)).append("\n");
    sb.append(this.centreString("your final project will not look like this.", 78)).append("\n");
    sb.append(this.emptyLine(80)).append("\n");
    sb.append(this.bar(80));

    sb.append(this.centreString("You might find this useful when you are building",
        78)).append("\n");
    sb.append(this.centreString("your final project.", 78)).append("\n");
    sb.append(this.emptyLine(80)).append("\n");
    sb.append(this.centreString("In particular you can use this to develop tests for",
        78)).append("\n");
    sb.append(this.centreString("your final project.", 78)).append("\n");
    sb.append(this.emptyLine(80)).append("\n");


    sb.append(this.bar(80));
    sb.append("Press any key to continue");
    return sb.toString();
  }

  private String[] elevatorWithGraphic(BuildingReport buildingReport) {
    String[] elevators = elevatorStatusDetail(buildingReport);
    String[] displayArray = computeElevatorDisplayArray(buildingReport);

    int rows = Math.max(elevators.length, displayArray.length);

    String[] elevatorStatus = new String[rows];

    int desiredWidth = 78;
    for (int i = 0; i < rows; i++) {
      String elevator = i < elevators.length ? elevators[i] : "";
      String display = i < displayArray.length ? displayArray[i] : "";
      StringBuilder sb = new StringBuilder();
      sb.append("* ");
      sb.append(elevator);
      int currentLength = elevator.length() + 3;
      int graphicOffset = 79 - currentLength - display.length();
      sb.append(" ".repeat(graphicOffset));
      sb.append(display);
      sb.append(" *");
      elevatorStatus[i] = sb.toString();


    }

    return elevatorStatus;
  }

  private String[] elevatorStatusDetail(BuildingReport buildingReport) {
    String[] elevators = new String[buildingReport.getElevatorReports().length];
    // add the elevator status
    for (int i = 0; i < buildingReport.getElevatorReports().length; i++) {
      String elevatorStatus = String.format("Elevator %d: %s", i,
          buildingReport.getElevatorReports()[i].toString());
      elevators[i] = elevatorStatus;

    }
    return elevators;
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
      sb.append(String.format("%2d]", floor));

      for (int j = 0; j < elevatorReports.length; j++) {
        ElevatorReport elevatorReport = elevatorReports[j];
        if (elevatorReport.getCurrentFloor() == floor) {
          sb.append(String.format("%2d", j));
        } else {
          sb.append("  ");
        }
      }
      int flipFloor = numFloors - floor - 1;
      displayArray[flipFloor] = sb.toString();
    }

    return displayArray;
  }

}
