package building;

import building.enums.ElevatorSystemStatus;
import elevator.Elevator;
import elevator.ElevatorInterface;
import elevator.ElevatorReport;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import scanerzus.Request;


/**
 * This class represents a building.
 */
public class Building implements BuildingInterface {

  private final int numberOfFloors;
  private final int numberOfElevators;

  private final int elevatorCapacity;

  private final ElevatorInterface[] elevators;

  private final List<Request> upRequests = new ArrayList<>();
  private final List<Request> downRequests = new ArrayList<>();

  private ElevatorSystemStatus elevatorsStatus;


  /**
   * Constructs a Building object and initializes it to the given number of floors and elevators.
   *
   * @param numberOfFloors    the number of floors in the building
   * @param numberOfElevators the number of elevators in the building
   * @param elevatorCapacity  the maximum number of people that can be in an elevator at once
   */
  public Building(int numberOfFloors,
                  int numberOfElevators,
                  int elevatorCapacity) throws IllegalArgumentException {
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


    // Initialize the ElevatorSystemStatus in the building
    this.elevators = new Elevator[numberOfElevators];

    for (int i = 0; i < numberOfElevators; i++) {
      this.elevators[i] = new Elevator(numberOfFloors, this.elevatorCapacity);
    }
  }

  /**
   * This method is used to start the building elevator system.
   */
  @Override
  public void start(boolean startElevators) {
    // for all of the elevators in the building, start them.
    if (startElevators) {
      for (ElevatorInterface elevator : this.elevators) {
        elevator.start();
        this.elevatorsStatus = ElevatorSystemStatus.running;
      }
      return;
    }
    for (ElevatorInterface elevator : this.elevators) {
      elevator.takeOutOfService();
      this.elevatorsStatus = ElevatorSystemStatus.outOfService;
      // delete the requests
      this.upRequests.clear();
      this.downRequests.clear();

    }


  }

  /**
   * This method is used to get the number of floors in the building.
   *
   * @return the number of floors in the building
   */
  @Override
  public int getNumberOfFloors() {
    return this.numberOfFloors;
  }

  /**
   * This method is used to get the number of elevators in the building.
   *
   * @return the number of elevators in the building
   */
  @Override
  public int getNumberOfElevators() {
    return this.numberOfElevators;
  }

  /**
   * This method is used to get the max occupancy of the elevator.
   *
   * @return the max occupancy of the elevator.
   */
  @Override
  public int getElevatorCapacity() {
    return this.elevatorCapacity;
  }

  /**
   * Create a JSON object with the status of the elevator system.
   *
   * @return String of the JSON object.
   */
  @Override
  public BuildingReport getElevatorSystemStatus() {

    ElevatorReport[] elevatorReports = new ElevatorReport[this.elevators.length];
    for (int i = 0; i < this.elevators.length; i++) {
      elevatorReports[i] = this.elevators[i].getElevatorStatus();
    }

    BuildingReport buildingReport = new BuildingReport(this.numberOfFloors,
        this.numberOfElevators,
        this.elevatorCapacity,
        elevatorReports,
        this.upRequests,
        this.downRequests,
        this.elevatorsStatus);

    return buildingReport;
  }

  /**
   * This method is used to add a request to the building.
   *
   * @param request the request to be added to the building
   */
  @Override
  public void addRequest(Request request) {
    if (this.elevatorsStatus == ElevatorSystemStatus.outOfService) {
      throw new IllegalStateException("Elevator system is out of service");
    }
    

    if (request == null) {
      throw new IllegalArgumentException("Request cannot be null");
    }

    if (request.getStartFloor() < 0 || request.getStartFloor() >= this.numberOfFloors) {
      throw new IllegalArgumentException("Start floor must be between 0 and "
          + (this.numberOfFloors - 1));
    }

    if (request.getEndFloor() < 0 || request.getEndFloor() >= this.numberOfFloors) {
      throw new IllegalArgumentException("End floor must be between 0 and "
          + (this.numberOfFloors - 1));
    }

    if (request.getStartFloor() == request.getEndFloor()) {
      throw new IllegalArgumentException("Start floor and end floor cannot be the same");
    }

    if (request.getStartFloor() < request.getEndFloor()) {
      this.upRequests.add(request);
    } else {
      this.downRequests.add(request);
    }

  }

  /**
   * This method is used to step the building elevator system.
   */
  @Override
  public void step() {
    this.distributeRequests();
    for (ElevatorInterface elevator : this.elevators) {
      elevator.step();
    }
  }

  /**
   * This method is used to distribute the requests to the elevators.
   * Only elevators on the ground floor and top floor will be considered.
   */
  private void distributeRequests() {
    if (this.upRequests.isEmpty() && this.downRequests.isEmpty()) {
      return;
    }


    // iterate over the elevators if they are on the ground floor
    // add upRequests up to the capacity of the elevator.
    // if the elevator is on the top floor add downRequests up to the capacity of the elevator.
    for (ElevatorInterface elevator : this.elevators) {
      if (!elevator.isTakingRequests()) {
        continue;
      }
      if (elevator.getCurrentFloor() == 0) {
        List<Request> upRequestsForElevator = getRequests(this.upRequests);
        elevator.processRequests(upRequestsForElevator);
      } else if (elevator.getCurrentFloor() == this.numberOfFloors - 1) {
        List<Request> downRequestsForElevator = getRequests(this.downRequests);
        elevator.processRequests(downRequestsForElevator);
      }

    }
  }

  /**
   * This method is used to get the requests for the elevators.
   *
   * @param requests the requests to get the requests from.
   * @return the requests to return.
   */
  private List<Request> getRequests(List<Request> requests) {
    List<Request> requestsToReturn = new ArrayList<Request>();
    while (!requests.isEmpty() && requestsToReturn.size() < this.elevatorCapacity) {
      requestsToReturn.add(requests.remove(0));
    }
    return requestsToReturn;
  }

}


