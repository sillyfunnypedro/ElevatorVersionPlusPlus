package scanerzus;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * A JUnit test class for the RequestGenerator class.
 */
public class RequestGeneratorTest {
  @Test
  public void testRequestGenerator() {
    System.out.println("Testing: testRequestGenerator");
    RequestGenerator requestGenerator = new RequestGenerator(10);
    assertEquals(10, requestGenerator.getNumFloors());
  }

  /**
   * Test the random request generator.
   */
  @Test
  public void testRequest() {
    System.out.println("Testing: testGetNextRequest");
    RequestGenerator requestGenerator = new RequestGenerator(10);
    Request request = requestGenerator.generateRequestRandom();
    assertTrue(request.getStartFloor() >= 0);
    assertTrue(request.getStartFloor() < 10);
    assertTrue(request.getEndFloor() >= 0);
    assertTrue(request.getEndFloor() < 10);

    assertTrue(request.getStartFloor() != request.getEndFloor());

  }

  /**
   * Test the down request generator.
   */
  @Test
  public void testDownRequest() {
    System.out.println("Testing: testGetNextDownRequest");
    RequestGenerator requestGenerator = new RequestGenerator(10);

    Request request = requestGenerator.generateDownRequest();
    assertTrue(request.getStartFloor() > request.getEndFloor());


  }

  /**
   * Test the up request generator.
   */
  @Test
  public void testUpRequest() {
    System.out.println("Testing: testGetNextUpRequest");
    RequestGenerator requestGenerator = new RequestGenerator(10);

    Request request = requestGenerator.generateUpRequest();

    assertTrue(request.getStartFloor() < request.getEndFloor());
  }
}