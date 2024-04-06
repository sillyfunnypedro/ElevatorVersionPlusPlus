package building;

import building.enums.ElevatorSystemStatus;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;
import scanerzus.Request;

/**
 * This class tests the Building class.
 */
public class BuildingTest {
  private Building building;
  private Building tallBuilding;

  @Before
  public void setUp() {
    building = new Building(4, 2, 3);
    tallBuilding = new Building(30, 10, 20);
  }

  @Test
  public void testHappyConstructor() {
    assertEquals(4, building.getNumberOfFloors());
    assertEquals(2, building.getNumberOfElevators());
    assertEquals(3, building.getElevatorCapacity());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeFloors() {
    new Building(-1, 2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeElevators() {
    new Building(10, -1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeCapacity() {
    new Building(10, 2, -1);
  }

  @Test
  public void startElevatorSystem() {
    BuildingReport report = building.getStatusElevatorSystem();
    assertEquals(ElevatorSystemStatus.outOfService, report.getSystemStatus());

    building.startElevatorSystem();
    report = building.getStatusElevatorSystem();
    assertEquals(ElevatorSystemStatus.running, report.getSystemStatus());
  }

  @Test
  public void stopElevatorSystem() {
    BuildingReport report = building.getStatusElevatorSystem();
    assertEquals(ElevatorSystemStatus.outOfService, report.getSystemStatus());

    building.startElevatorSystem();
    report = building.getStatusElevatorSystem();
    assertEquals(ElevatorSystemStatus.running, report.getSystemStatus());

    building.stopElevatorSystem();
    building.stepElevatorSystem();
    report = building.getStatusElevatorSystem();
    assertEquals(ElevatorSystemStatus.outOfService, report.getSystemStatus());
  }

  @Test
  public void testAddRequest() {
    building.startElevatorSystem();
    building.addRequestToElevatorSystem(new Request(1, 2));
    building.stepElevatorSystem();
    // Elevator 1 should be on floor 1 and elevator 2 should be on floor 0

    BuildingReport report = building.getStatusElevatorSystem();
    assertEquals(1, report.getElevatorReports()[0].getCurrentFloor());
    assertEquals(0, report.getElevatorReports()[1].getCurrentFloor());

    // Elevator
    building.addRequestToElevatorSystem(new Request(1, 3));
    building.stepElevatorSystem();
    report = building.getStatusElevatorSystem();
    assertEquals(1, report.getElevatorReports()[0].getCurrentFloor());
    assertEquals(3, report.getElevatorReports()[0].getDoorOpenTimer());
    assertFalse(report.getElevatorReports()[0].isDoorClosed());

    assertEquals(1, report.getElevatorReports()[1].getCurrentFloor());


  }

  /**
   * We are going to add 30 requests to the building and then step the system 20 times.
   */
  @Test
  public void testAddManyRequests() {
    tallBuilding.startElevatorSystem();
    for (int i = 0; i < 100; i++) {
      // create random requests to add to the building
      int startFloor = (int) (Math.random() * 30);
      int endFloor = (int) (Math.random() * 30);
      if (startFloor == endFloor) {
        endFloor = (endFloor + 1) % 30;
      }

      tallBuilding.addRequestToElevatorSystem(new Request(startFloor, endFloor));
    }

    for (int i = 0; i < 20; i++) {
      tallBuilding.stepElevatorSystem();
    }

    BuildingReport report = tallBuilding.getStatusElevatorSystem();
//    assertEquals(1, report.getElevatorReports()[0].getCurrentFloor());
//    assertEquals(1, report.getElevatorReports()[1].getCurrentFloor());
  }


  @Test
  public void takeOutOfService() {
  }

  @Test
  public void getNumberOfFloors() {
  }

  @Test
  public void getNumberOfElevators() {
  }

  @Test
  public void getElevatorCapacity() {
  }

  @Test
  public void getElevatorSystemStatus() {
  }

  @Test
  public void addRequest() {
  }

  @Test
  public void step() {
  }
}