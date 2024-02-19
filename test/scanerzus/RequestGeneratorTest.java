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

    Request request = requestGenerator.generateDownRequest(0);
    assertNull(request);

    request = requestGenerator.generateDownRequest(1);
    assertEquals(request.getStartFloor(), 1);
    assertEquals(request.getEndFloor(), 0);
    assertEquals("1->0", request.toString());

    request = requestGenerator.generateDownRequest(9);
    assertEquals(request.getStartFloor(), 9);
    assertTrue(request.getEndFloor() <= 8);
  }

  /**
   * Test the up request generator.
   */
  @Test
  public void testUpRequest() {
    System.out.println("Testing: testGetNextUpRequest");
    RequestGenerator requestGenerator = new RequestGenerator(10);

    Request request = requestGenerator.generateUpRequest(9);
    assertNull(request);

    request = requestGenerator.generateUpRequest(8);
    assertEquals(8, request.getStartFloor());
    assertEquals(9, request.getEndFloor());
    assertEquals("8->9", request.toString());

    request = requestGenerator.generateUpRequest(0);
    assertEquals(request.getStartFloor(), 0);
    assertTrue(request.getEndFloor() >= 1);
  }
}