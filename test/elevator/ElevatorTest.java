package elevator;

import building.enums.Direction;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import scanerzus.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * A JUnit test class for the ElevatorStatus class.
 */
public class ElevatorTest {

  List<Request> upTenRequests;
  List<Request> upTenRequestsNotTop;
  List<Request> downTenRequests;
  List<Request> downTenRequestsNotBottom;

  List<Request> upThreeRequests;
  List<Request> downThreeRequests;
  Elevator elevatorTenFloors10Capacity;

  Elevator elevatorThreeFloors3Capacity;

  @Before
  public void setUp() {
    upTenRequests = new ArrayList<>();
    upTenRequests.add(new Request(0, 1));
    upTenRequests.add(new Request(0, 2));
    upTenRequests.add(new Request(0, 3));
    upTenRequests.add(new Request(5, 6));
    upTenRequests.add(new Request(5, 9));

    upTenRequestsNotTop = new ArrayList<>();
    upTenRequestsNotTop.add(new Request(0, 1));
    upTenRequestsNotTop.add(new Request(0, 2));
    upTenRequestsNotTop.add(new Request(0, 3));
    upTenRequestsNotTop.add(new Request(5, 6));


    downTenRequests = new ArrayList<>();
    downTenRequests.add(new Request(9, 8));
    downTenRequests.add(new Request(9, 7));
    downTenRequests.add(new Request(9, 6));
    downTenRequests.add(new Request(5, 4));
    downTenRequests.add(new Request(5, 1));

    downTenRequestsNotBottom = new ArrayList<>();
    downTenRequestsNotBottom.add(new Request(9, 8));
    downTenRequestsNotBottom.add(new Request(9, 7));
    downTenRequestsNotBottom.add(new Request(9, 6));
    downTenRequestsNotBottom.add(new Request(5, 4));


    upThreeRequests = new ArrayList<>();
    upThreeRequests.add(new Request(0, 1));
    upThreeRequests.add(new Request(0, 3));

    downThreeRequests = new ArrayList<>();
    downThreeRequests.add(new Request(3, 2));
    downThreeRequests.add(new Request(3, 1));
    downThreeRequests.add(new Request(2, 1));


    elevatorTenFloors10Capacity = new Elevator(10, 10);
    elevatorThreeFloors3Capacity = new Elevator(3, 3);
  }


  /**
   * Test the constructor.
   */
  @Test
  public void elevatorStartsAtGroundFloor() {
    System.out.println("Testing: elevatorStartsAtGroundFloor");
    elevator.Elevator elevator = new Elevator(10, 5);
    assertEquals(0, elevator.getCurrentFloor());
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
   * Test the constructor for max floor.
   */
  @Test
  public void elevatorMaxFloor() {
    System.out.println("Testing: elevatorMaxFloor");
    Elevator elevator = new Elevator(10, 5);
    assertEquals(10, elevator.getMaxFloor());
  }

  /**
   * Test that the elevator starts out of service.
   */
  @Test
  public void elevatorStartsStopped() {
    System.out.println("Testing: elevatorStartsOutOfService");
    Elevator elevator = new Elevator(10, 5);
    assertEquals(elevator.getDirection(), Direction.STOPPED);
  }

  /**
   * Test the start method.
   */
  @Test
  public void elevatorStart() {
    System.out.println("Testing: elevatorStart");
    Elevator elevator = new Elevator(10, 5);
    elevator.start();
    assertTrue(elevator.isTakingRequests());
  }

  /**
   * Test that the elevator can process a request.
   */
  @Test
  public void elevatorProcessRequest() {
    System.out.println("Testing: elevatorProcessRequest");
    Elevator elevator = new Elevator(10, 5);
    elevator.start();
    List<Request> singleRequest = new ArrayList<>();
    singleRequest.add(new Request(0, 1));
    elevator.processRequests(singleRequest);
    boolean[] floorRequests = elevator.getFloorRequests();
    assertTrue(floorRequests[0]);
    assertTrue(floorRequests[1]);
    for (int i = 2; i < floorRequests.length; i++) {
      assertFalse(floorRequests[i]);
    }
  }

  @Test
  public void elevatorProcessRequest2() {
    System.out.println("Testing: elevatorProcessRequest2");
    Elevator elevator = new Elevator(10, 5);
    elevator.start();
    List<Request> singleRequest = new ArrayList<>();
    singleRequest.add(new Request(0, 1));
    singleRequest.add(new Request(0, 2));
    elevator.processRequests(singleRequest);
    boolean[] floorRequests = elevator.getFloorRequests();
    assertTrue(floorRequests[0]);
    assertTrue(floorRequests[1]);
    assertTrue(floorRequests[2]);
    for (int i = 3; i < floorRequests.length; i++) {
      assertFalse(floorRequests[i]);
    }
  }

  @Test
  public void elevatorProcessesRequests() {
    System.out.println("Testing: elevatorProcessesRequests");
    elevatorTenFloors10Capacity.start();
    elevatorTenFloors10Capacity.processRequests(upTenRequests);
    boolean[] floorRequests = elevatorTenFloors10Capacity.getFloorRequests();
    assertTrue(floorRequests[0]);
    assertTrue(floorRequests[1]);
    assertTrue(floorRequests[2]);
    assertTrue(floorRequests[3]);
    assertFalse(floorRequests[4]);
    assertTrue(floorRequests[5]);
    assertTrue(floorRequests[6]);
    assertFalse(floorRequests[7]);
    assertFalse(floorRequests[8]);
    assertTrue(floorRequests[9]);

    // Elevator should be on floor 0 with the door open for 3 steps.
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    // The elevator has finished on the first floor and is now on the floor 1.
    // The door is closed.
    elevatorTenFloors10Capacity.step();
    // check that the floor is 1
    assertEquals(1, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    // There are requests for this floor so we will open and wait 3 steps
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    // The elevator has finished on the second floor and is now on the floor 2.
    elevatorTenFloors10Capacity.step();
    // check that the floor is 2
    assertEquals(2, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    // The elevator has finished on the third floor and is now on the floor 3.
    elevatorTenFloors10Capacity.step();
    // check that the floor is 3
    assertEquals(3, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    // The elevator has finished on the fourth floor and is now on the floor 4.
    elevatorTenFloors10Capacity.step();
    // check that the floor is 4
    assertEquals(4, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());


    // The elevator has finished on the fifth floor and is now on the floor 5.
    elevatorTenFloors10Capacity.step();
    // check that the floor is 5
    assertEquals(5, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    // The elevator has finished on the sixth floor and is now on the floor 6.
    elevatorTenFloors10Capacity.step();
    // check that the floor is 6
    assertEquals(6, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());


    // The elevator has finished on the seventh floor and is now on the floor 7.
    elevatorTenFloors10Capacity.step();
    // check that the floor is 6
    assertEquals(6, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the floor is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the floor is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the floor is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();


    // check that the floor is 7 and the door is closed
    assertEquals(7, elevatorTenFloors10Capacity.getCurrentFloor());
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();

    // check that the floor is 8
    assertEquals(8, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();

    // check that the floor is 9
    assertEquals(9, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();

    // check that the elevator direction is down
    assertEquals(Direction.DOWN, elevatorTenFloors10Capacity.getDirection());

    // the elevator should now go back to the ground floor and stop
    elevatorTenFloors10Capacity.step();
    // check that the floor is 8
    assertEquals(8, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    elevatorTenFloors10Capacity.step();
    // check that the floor is 7
    assertEquals(7, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    elevatorTenFloors10Capacity.step();
    // check that the floor is 6
    assertEquals(6, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    elevatorTenFloors10Capacity.step();
    // check that the floor is 5
    assertEquals(5, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    elevatorTenFloors10Capacity.step();
    // check that the floor is 4
    assertEquals(4, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    elevatorTenFloors10Capacity.step();
    // check that the floor is 3
    assertEquals(3, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    elevatorTenFloors10Capacity.step();
    // check that the floor is 2
    assertEquals(2, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    elevatorTenFloors10Capacity.step();
    // check that the floor is 1
    assertEquals(1, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    elevatorTenFloors10Capacity.step();
    // check that the floor is 0
    assertEquals(0, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    // the elevator should now be stopped
    assertEquals(Direction.STOPPED, elevatorTenFloors10Capacity.getDirection());

    // stepping again should not change anything
    elevatorTenFloors10Capacity.step();
    // check that the floor is 0
    assertEquals(0, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    // the elevator should now be stopped
    assertEquals(Direction.STOPPED, elevatorTenFloors10Capacity.getDirection());


  }


}