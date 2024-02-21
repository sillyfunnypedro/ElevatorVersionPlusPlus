package elevator;

import static org.junit.Assert.assertEquals;


import org.junit.Test;


public class PersonTest {

  @Test
  public void personStartsAtCorrectFloor() {
    Person person = new Person(1, 5);
    assertEquals(1, person.getStartFloor());
  }

  @Test
  public void personEndsAtCorrectFloor() {
    Person person = new Person(1, 5);
    assertEquals(5, person.getTargetFloor());
  }

  @Test
  public void personStartsAndEndsAtSameFloor() {
    Person person = new Person(3, 3);
    assertEquals(3, person.getStartFloor());
    assertEquals(3, person.getTargetFloor());
  }

  @Test
  public void personStartsAtNegativeFloor() {
    Person person = new Person(-1, 5);
    assertEquals(-1, person.getStartFloor());
  }

  @Test
  public void personEndsAtNegativeFloor() {
    Person person = new Person(1, -5);
    assertEquals(-5, person.getTargetFloor());
  }


}
