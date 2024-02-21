package elevator;

import javax.swing.*;
import java.awt.*;

/**
 * An implementation of the Elevator interface.
 */
public class Elevator implements ElevatorInterface {
  /**
   * The total number of floors in the building.
   */
  private final int maxFloor;

  /**
   * The maximum number of people that can fit in the elevator.
   */
  private final int maxOccupancy;

  /**
   * The current floor of the elevator.
   * This is initialized to 0, which is the ground floor.
   */
  private float currentFloor;

  /**
   * The amount of a floor that the elevator will move each step.
   */
  private final float floorIncrement = 0.1f; // This is set by the manufacturer.

  /**
   * The direction the elevator is moving.
   */
  private Direction direction;

  /**
   * The state of the door
   */
  private boolean doorClosed = true;

  private JSlider displaySlider = null;
  private JLabel displayLabel = null;


  /**
   * The constructor for this elevator.
   * The elevator is initially at the ground floor and is not moving.
   *
   * @param maxFloor     the total number of floors in the building
   *                     must be greater than 0
   *                     must be less than 30 (city bylaws)
   * @param maxOccupancy the maximum number of people that can fit in the elevator
   *                     must be greater than 0
   *                     must be less than 20 (fire code)
   * @throws IllegalArgumentException if the maxFloor or maxOccupancy is out of range
   */
  public Elevator(int maxFloor, int maxOccupancy) {
    if (maxFloor < 1 || maxFloor > 30) {
      throw new IllegalArgumentException("maxFloor must be between 1 and 30");
    }
    if (maxOccupancy < 1 || maxOccupancy > 20) {
      throw new IllegalArgumentException("maxOccupancy must be between 1 and 20");
    }

    this.maxFloor = maxFloor;
    this.maxOccupancy = maxOccupancy;
    this.currentFloor = 0;
    this.direction = Direction.STOPPED;
  }

  /**
   * Get the current floor, this is a floating point value to allow for partial floors.
   *
   * @return the current floor of the elevator
   */
  @Override
  public float getCurrentFloor() {
    return this.currentFloor;
  }

  /**
   * maxFloor getter
   *
   * @return the total number of floors in the building
   */
  @Override
  public int getMaxFloor() {
    return this.maxFloor;
  }

  /**
   * maxOccupancy getter
   * Notice that it is not the responsibility of the elevator to keep track of the people in the elevator.
   *
   * @return the maximum number of people that can fit in the elevator
   */
  @Override
  public int getMaxOccupancy() {
    return this.maxOccupancy;
  }

  /**
   * Direction getter
   *
   * @return the direction the elevator is moving
   */
  @Override
  public Direction getDirection() {
    return this.direction;
  }

  /**
   * set the direction of the elevator
   *
   * @param direction the direction to set
   *                  if the direction is set to up and the elevator is at the top floor
   *                  the direction is set to STOPPED
   *                  if the direction is set to down and the elevator is at the bottom floor
   *                  the direction is set to STOPPED
   *                  if the elevator is not at a floor and the request is set to STOPPED
   *                  it throws an exception
   * @throws IllegalStateException if the elevator is between floors
   */
  @Override
  public void setDirection(Direction direction) throws IllegalStateException {

    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }

    if (direction == this.direction) {
      return;
    }

    if (direction == Direction.UP && this.currentFloor == this.maxFloor) {
      this.direction = Direction.STOPPED;
    } else if (direction == Direction.DOWN && this.currentFloor == 0) {
      this.direction = Direction.STOPPED;
    } else if (direction == Direction.STOPPED && this.isBetweenFloors()) {
      throw new IllegalStateException("Elevator is between floors");
    } else {
      this.direction = direction;
    }
    this.updateDisplay();
  }

  /**
   * isDoorClosed.
   *
   * @return true if the door is closed, false otherwise.
   */
  @Override
  public boolean isDoorClosed() {
    return this.doorClosed;
  }

  /**
   * open the door.
   */
  @Override
  public void openDoor() throws IllegalStateException {
    if (this.isBetweenFloors()) {
      throw new IllegalStateException("You cannot open the door between floors");
    }
    this.doorClosed = false;
    this.updateDisplay();
  }

  /**
   * close the door, no exception needs to be thrown.
   * If the door is already closed, then nothing happens.
   * If the door is open then we know the elevator is at a floor
   * and the door can be closed.
   */
  @Override
  public void closeDoor() {
    this.doorClosed = true;
    this.updateDisplay();
  }


  /**
   * The step function is called to move the elevator one step.
   */
  public void step() {
    if (this.direction == Direction.STOPPED) {
      return;
    }

    // if the door is open we cannot move.
    if (!this.isDoorClosed()) {
      return;
    }

    // we move the elevator by the floor increment.
    // if we reach the top or bottom floor we stop.
    if (this.direction == Direction.UP) {
      this.currentFloor += this.floorIncrement;
      if (this.currentFloor > this.maxFloor - 1) {
        this.currentFloor = this.maxFloor - 1;
        this.direction = Direction.STOPPED;
      }
    } else if (this.direction == Direction.DOWN) {
      this.currentFloor -= this.floorIncrement;
      if (this.currentFloor <= 0) {
        this.currentFloor = 0;
        this.direction = Direction.STOPPED;
      }
    }

    // Just to make sure that we are not adding a floating point error.
    // when we hit a floor we set the current floor to the floor.
    if (Math.abs(this.currentFloor - Math.round(this.currentFloor)) < 0.01) {
      this.currentFloor = Math.round(this.currentFloor);
    }

    this.updateDisplay();
  }


  /**
   * Is the elevator at a floor?
   * We store the elevator as a float so we need to check to see if the floor is
   * within 0e-5 of an integer value.
   *
   * @return true if the elevator is at a floor, false otherwise
   */
  boolean isBetweenFloors() {
    // Calculate the delta of the position of the floor and the rounded position of the floor.
    float delta = Math.abs(this.currentFloor - Math.round(this.currentFloor));

    return !(delta < 1e-37);
  }

  /**
   * toString implementation.
   *
   * @return string representation of the elevator.
   */
  @Override
  public String toString() {

    return String.format("[%03.1f|%s|%s]",
        this.currentFloor,
        this.direction,
        this.doorClosed ? "closed" : "open");

  }

  /**
   * update the status of the elevator
   * This is used to update the status of the elevator in the display.
   */
  private void updateDisplay() {
    if (this.displayLabel != null) {
      this.displayLabel.setText(this.toString());
    }
    if (this.displaySlider != null) {
      this.displaySlider.setValue((int) (this.currentFloor * 10));
    }
  }

  /**
   * The panel for the elevator   .
   *
   * @return a swing component that has the information of the elevator.
   */
  private JComponent getElevatorPositionPanel() {
    JSlider slider = new JSlider(JSlider.VERTICAL,
        0, (this.maxFloor - 1) * 10,
        (int) (this.currentFloor * 100));
    slider.setPaintTicks(true);
    slider.setMajorTickSpacing(10);


    slider.setEnabled(false);

    slider.setPaintTicks(true);
    slider.setValue((int) this.currentFloor * 10);
    this.displaySlider = slider;

    return slider;
  }

  /**
   * The panel for the elevator.
   *
   * @return the swing component that has the information of the elevator.
   */
  private JComponent getElevatorPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    panel.add(this.getElevatorPositionPanel());
    panel.setVisible(true);
    return panel;
  }

  /**
   * the Status panel.
   *
   * @return a swing component that has the information of the elevator.
   */
  private JComponent getStatusPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    JLabel display = new JLabel(this.toString());
    display.setText(this.toString());
    this.displayLabel = display;
    panel.add(display);
    return panel;
  }

  /**
   * debug control buttons for the elevator.
   *
   * @return a swing component that has debug buttons for the elevator
   */
  private JComponent getControlPanel() {
    int buttonWidth = 10;
    int buttonHeight = 20;
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(1, 3, 0, 0));
    JButton upButton = new JButton("^");
    upButton.addActionListener(e -> this.setDirection(Direction.UP));
    upButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
    upButton.setMargin(new Insets(0, 0, 0, 0));
    panel.add(upButton);

    JButton downButton = new JButton("v");
    downButton.addActionListener(e -> this.setDirection(Direction.DOWN));
    downButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
    downButton.setMargin(new Insets(0, 0, 0, 0));
    panel.add(downButton);

    JButton stopButton = new JButton("-");
    stopButton.addActionListener(e -> this.setDirection(Direction.STOPPED));
    stopButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
    stopButton.setMargin(new Insets(0, 0, 0, 0));
    panel.add(stopButton);
    return panel;
  }

  /**
   * getDisplay implementation.
   *
   * @return a swing component that has the information of the elevator.
   */
  public JComponent getDisplay() {
    // We will make a panel with the total number of floors.
    // the label for the elevator will be positioned at the correct floor.
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    panel.add(this.getElevatorPanel());
    panel.add(this.getStatusPanel());
    panel.add(this.getControlPanel());
    panel.setVisible(true);

    return panel;


  }


}
