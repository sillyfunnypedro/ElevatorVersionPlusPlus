

package building;

import org.json.JSONArray;
import org.json.JSONObject;
import elevator.Elevator;
import scanerzus.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a building.
 */
public class Building implements BuildingInterface {

  private final int numberOfFloors;
  private final int numberOfElevators;

  private int elevatorCapacity = 30;

  private final Elevator[] elevators;

  private final List<Request> requests = new ArrayList<Request>();


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


    // Initialize the Elevators in the building
    this.elevators = new Elevator[numberOfElevators];

    for (int i = 0; i < numberOfElevators; i++) {
      this.elevators[i] = new Elevator(numberOfFloors, this.elevatorCapacity);
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
   * This method is used to get the number of elevators in the building.
   *
   * @return the number of elevators in the building
   */
  @Override
  public int getNumberOfElevators() {
    return 0;
  }


  /**
   * Create a JSON object with the status of the elevator system.
   *
   * @return String of the JSON object.
   */
  @Override
  public String getElevatorSystemStatus() {


    JSONObject jsonObject = new JSONObject();
    jsonObject.put("numElevators", this.elevators.length);
    jsonObject.put("numFloors", this.numberOfFloors);
    jsonObject.put("elevatorCapacity", this.elevatorCapacity);


    // make an array of input requests
    JSONArray inputRequestsJson = new JSONArray();
    for (Request request : this.requests) {
      inputRequestsJson.put(request.toJson());
    }

    jsonObject.put("inputRequests", inputRequestsJson);


    // make an array of elevator status
    JSONArray elevatorStatusJson = new JSONArray();

    for (int i = 0; i < this.elevators.length; i++) {
      elevatorStatusJson.put("elevator" + i + this.elevators[i].getDirection());
    }
    jsonObject.put("elevatorStatus", elevatorStatusJson);

    return jsonObject.toString();

  }

  /**
   * This method is used to add a request to the building.
   *
   * @param request the request to be added to the building
   */
  @Override
  public void addRequest(Request request) {
    this.requests.add(request);
  }

  /**
   * This method is used to step the building elevator system.
   */
  @Override
  public void step() {
    for (Elevator elevator : this.elevators) {
      elevator.step();
    }
  }
}


