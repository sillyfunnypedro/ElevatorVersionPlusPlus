package elevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import scanerzus.Request;

/**
 * A JUnit test class for the ElevatorStatus class.
 */
public class ElevatorTest {

  /**
   * Test the constructor.
   */
  @Test
  public void elevatorStartsAtGroundFloor() {
    System.out.println("Testing: elevatorStartsAtGroundFloor");
    elevator.Elevator elevator = new Elevator(10, 5);
    assertEquals(0, elevator.getCurrentFloor(), 0.01);
  }

  /**
   * Test the constructor for max occupancy.
   */
  @Test
  public void elevatorMaxOccupancy() {
    System.out.println("Testing: elevatorMaxOccupancy");
    Elevator elevator = new Elevator(10, 5);
    assertEquals(5, elevator.getMaxOccupancy());
  }


  /**
   * Move to the top of the elevator checking at each floor
   * to see if the floor value is an integer.
   */
  @Test
  public void elevatorMovesUpToTopFloor() {
    System.out.println("Testing: elevatorMovesUpToTopFloor");
    Elevator elevator = new Elevator(10, 5);
    elevator.processRequest(new Request(0, 9));
    for (int i = 0; i < 10; i++) {
      elevator.step();
    }
  }


}