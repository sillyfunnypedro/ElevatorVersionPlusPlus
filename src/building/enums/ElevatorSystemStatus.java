package building.enums;

/**
 * This enum is used to represent the status of the elevators.
 */
public enum ElevatorSystemStatus {
  running("Running"),
  stopping("Stopping"),
  outOfService("outOfService");
  final String display;

  ElevatorSystemStatus(String display) {
    this.display = display;
  }
  
  @Override
  public String toString() {
    return this.display;
  }
}
