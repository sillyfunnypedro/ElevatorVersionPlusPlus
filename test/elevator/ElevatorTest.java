package elevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * A JUnit test class for the Elevator class.
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
   * Move up by .1 of a floor.
   */
  @Test
  public void elevatorMovesUp() {
    System.out.println("Testing: elevatorMovesUp");
    Elevator elevator = new Elevator(10, 5);
    elevator.setDirection(Direction.UP);
    elevator.step();
    assertEquals(0.1, elevator.getCurrentFloor(), 0.01);
  }

  /**
   * Move to the top of the elevator checking at each floor
   * to see if the floor value is an integer.
   */
  @Test
  public void elevatorMovesUpToTopFloor() {
    System.out.println("Testing: elevatorMovesUpToTopFloor");
    Elevator elevator = new Elevator(10, 5);
    elevator.setDirection(Direction.UP);
    for (int i = 0; i < 100; i++) {

      if (i % 10 == 0) {
        float currentFloor = elevator.getCurrentFloor();
        float expectedCurrentFloor = (float) i / 10;
        // This is about as close to zero as a float can get
        // 1.175494 × 10-38 is the smallest positive non-zero value
        assertEquals(expectedCurrentFloor, currentFloor, 1e-37);
      }
      elevator.step();
    }
  }

  /**
   * Move down by .1 of a floor.
   */
  @Test
  public void elevatorMovesDown() {
    System.out.println("Testing: elevatorMovesDown");
    Elevator elevator = new Elevator(10, 5);
    elevator.setDirection(Direction.DOWN);
    elevator.step();
    assertEquals(0, elevator.getCurrentFloor(), 0.01);
  }

  /**
   * Move up to the top floor.
   */
  @Test
  public void elevatorStopsAtTopFloor() {
    System.out.println("Testing: elevatorStopsAtTopFloor");
    Elevator elevator = new Elevator(10, 5);
    elevator.setDirection(Direction.UP);
    for (int i = 0; i < 99; i++) {
      elevator.step();
    }
    System.out.println("Testing that the elevator is at floor 9.9");
    assertEquals(9.9, elevator.getCurrentFloor(), 0.01);
    assertEquals(Direction.UP, elevator.getDirection());
    System.out.println("Testing that the elevator is at floor 10, after a step");
    elevator.step();
    assertEquals(10, elevator.getCurrentFloor(), 0.01);
    assertEquals(Direction.STOPPED, elevator.getDirection());
  }

  /**
   * Move down to the bottom floor.
   */
  @Test
  public void elevatorStopsAtGroundFloor() {
    System.out.println("Testing: elevatorStopsAtGroundFloor");
    Elevator elevator = new Elevator(10, 5);

    // Move the elevator up to floor 1
    elevator.setDirection(Direction.UP);
    for (int i = 0; i < 10; i++) {
      elevator.step();
    }

    assertEquals(1, elevator.getCurrentFloor(), 0.01);

    // Move the elevator down to floor 0
    elevator.setDirection(Direction.DOWN);
    for (int i = 0; i < 10; i++) {
      elevator.step();
    }
    assertEquals(0, elevator.getCurrentFloor(), 0.01);
    assertEquals(Direction.STOPPED, elevator.getDirection());
  }

  /**
   * Elevator is between floors.
   */
  @Test
  public void elevatorIsBetweenFloors() {
    System.out.println("Testing: elevatorIsBetweenFloors");
    Elevator elevator = new Elevator(10, 5);
    elevator.setDirection(Direction.UP);
    elevator.step();
    assertTrue(elevator.isBetweenFloors());

    // Move the elevator up to floor 1 and one extra step
    for (int i = 0; i < 10; i++) {
      elevator.step();
    }
    assertTrue(elevator.isBetweenFloors());
  }

  /**
   * Elevator cannot change direction.
   */
  @Test
  public void elevatorCannotChangeDirectionBetweenFloors() {
    System.out.println("Testing: elevatorCannotChangeDirectionBetweenFloors");
    Elevator elevator = new Elevator(10, 5);
    elevator.setDirection(Direction.UP);
    for (int i = 0; i < 5; i++) {
      elevator.step();
    }
    try {
      elevator.setDirection(Direction.STOPPED);
    } catch (IllegalStateException e) {
      System.out.println("Caught IllegalStateException: " + e.getMessage());
    }
  }

  @Test
  public void elevatorIsNotBetweenFloors() {
    System.out.println("Testing: elevatorIsNotBetweenFloors");
    Elevator elevator = new Elevator(10, 5);
    boolean result = elevator.isBetweenFloors();
    assertFalse(elevator.isBetweenFloors());
    // Move the elevator up to floor 1
    elevator.setDirection(Direction.UP);
    for (int i = 0; i < 10; i++) {
      elevator.step();
    }
    assertFalse(elevator.isBetweenFloors());
  }

  @Test
  public void stoppedElevatorDoesNotMove() {
    System.out.println("Testing: stoppedElevatorDoesNotMove");
    Elevator elevator = new Elevator(10, 5);
    elevator.setDirection(Direction.STOPPED);
    elevator.step();
    assertEquals(0, elevator.getCurrentFloor(), 0.01);
  }

  @Test
  public void stoppingElevatorBetweenFloorsThrowsException() {
    System.out.println("Testing: stoppingElevatorBetweenFloorsThrowsException");
    Elevator elevator = new Elevator(10, 5);
    elevator.setDirection(Direction.UP);
    for (int i = 0; i < 5; i++) {
      elevator.step();
    }
    try {
      elevator.setDirection(Direction.STOPPED);
    } catch (IllegalStateException e) {
      return;
    }
    throw new IllegalStateException("Elevator is between floors");
  }

  @Test
  public void runUpTheElevatorTestingEverything() {
    System.out.println("Testing: runUpTheElevatorTestingEverything");
    Elevator elevator = new Elevator(10, 5);
    elevator.setDirection(Direction.UP);
    for (int i = 0; i < 100; i++) {

      if (i % 10 == 0) {
        elevator.openDoor();
        String expected = String.format("[%03.1f|^|open]", i / 10.0f);
        String actual = elevator.toString();
        assertEquals(expected, actual);
        assertFalse(elevator.isDoorClosed());
        elevator.closeDoor();
        assertTrue(elevator.isDoorClosed());
        expected = String.format("[%03.1f|^|closed]", i / 10.0f);
        assertEquals(expected, elevator.toString());

        elevator.setDirection(Direction.STOPPED);
        expected = String.format("[%03.1f|-|closed]", i / 10.0f);
        assertEquals(expected, elevator.toString());
        elevator.setDirection(Direction.UP);


      } else {
        String expected = String.format("[%03.1f|^|closed]", i / 10.0f);
        String actual = elevator.toString();
        assertEquals(expected, actual);

        assertTrue(elevator.isDoorClosed());
        try {
          elevator.openDoor();
        } catch (IllegalStateException e) {
          assertTrue(elevator.isDoorClosed());
        }
        try {
          elevator.setDirection(Direction.STOPPED);
        } catch (IllegalStateException e) {
          assertTrue(elevator.isBetweenFloors());
        }
      }
      elevator.step();
    }

  }

  /**
   * Test toString.
   */
  @Test
  public void testToString() {
    System.out.println("Testing: testToString");
    Elevator elevator = new Elevator(10, 5);
    String expected = "[0.0|-|closed]";
    String actual = elevator.toString();
    assertEquals(expected, actual);

    elevator.setDirection(Direction.UP);
    expected = "[0.0|^|closed]";
    actual = elevator.toString();
    assertEquals(expected, actual);

    elevator.openDoor();
    expected = "[0.0|^|open]";
    actual = elevator.toString();
    assertEquals(expected, actual);

    elevator.closeDoor();
    expected = "[0.0|^|closed]";
    actual = elevator.toString();
    assertEquals(expected, actual);

    // now move up by .1
    elevator.step();
    expected = "[0.1|^|closed]";
    actual = elevator.toString();
    assertEquals(expected, actual);

    // now move up to the next floor
    for (int i = 0; i < 9; i++) {
      elevator.step();
    }
    expected = "[1.0|^|closed]";
    actual = elevator.toString();
    assertEquals(expected, actual);

    // open the door
    elevator.openDoor();
    expected = "[1.0|^|open]";
    actual = elevator.toString();
    assertEquals(expected, actual);

    // close the door
    elevator.closeDoor();
    expected = "[1.0|^|closed]";
    actual = elevator.toString();
    assertEquals(expected, actual);

    // now go all the way to the top
    for (int i = 0; i < 90; i++) {
      elevator.step();
    }
    expected = "[10.0|-|closed]";
    actual = elevator.toString();
    assertEquals(expected, actual);

    // now go all the way to the bottom
    elevator.setDirection(Direction.DOWN);
    for (int i = 0; i < 100; i++) {
      elevator.step();
    }

    expected = "[0.0|-|closed]";
    actual = elevator.toString();
    assertEquals(expected, actual);
  }
}