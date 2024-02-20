package building;

import elevator.Elevator;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a building.
 */
public class Building implements BuildingInterface {

  private final int numberOfFloors;
  private final int numberOfElevators;

  private int elevatorCapacity = 30;

  private final Elevator[] elevators;

  private final JFrame displayFrame = null;

  private final int sliderWidth = 95;

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

    // Initialize the Elevators in the building
    this.elevators = new Elevator[numberOfElevators];

    for (int i = 0; i < numberOfElevators; i++) {
      this.elevators[i] = new Elevator(numberOfFloors, this.elevatorCapacity);
    }

    this.initializeDisplay();

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
   * This method is used to get the number of Elevators in the building.
   *
   * @return the number of elevators in the building
   */
  @Override
  public int getNumberOfElevators() {
    return 0;
  }

  @Override
  public String toString() {
    return "Building{"
        + "numberOfFloors=" + numberOfFloors
        + ", numberOfElevators=" + numberOfElevators
        + '}';
  }

  /**
   * This method is used to initialize the display for the building.
   */
  private void initializeDisplay1() {
    // Create the display frame
    JFrame displayFrame = new JFrame("Elevator Display");
    displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    displayFrame.setSize(1200, 600);

    JPanel panel = new JPanel();
    panel.setBounds(200, 0, 800, 400);
    panel.setBackground(Color.gray);
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    // Put a border around the panel
    //panel.setBorder(BorderFactory.createLineBorder(new Color(0), 2, true));

//    // add the elevator display
//    for (Elevator elevator : elevators) {
//      JPanel display = elevator.getDisplay();
//      panel.add(display);
//    }


    displayFrame.add(panel);
    displayFrame.setVisible(true);

  }

  private void initializeDisplay3() {
    // Create the display frame
    JFrame displayFrame = new JFrame("Elevator Display");
    displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    displayFrame.setSize(1200, 600);
    displayFrame.setBackground(Color.white); // set the frame background to white

    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(800, 400)); // set the preferred size of the panel
    panel.setBackground(Color.gray);
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    // Put a border around the panel
    //panel.setBorder(BorderFactory.createLineBorder(new Color(0), 2, true));

//    // add the elevator display
//    for (Elevator elevator : elevators) {
//      JPanel display = elevator.getDisplay();
//      panel.add(display);
//    }


    displayFrame.add(panel);
    displayFrame.setVisible(true);

  }

  public void initializeDisplay() {


    JFrame f = new JFrame("Building Manager");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // set up the logo panel
    JPanel logoPanel = new JPanel();
    logoPanel.setBounds(0, 0, 200, 200);
    // set the image to the jpg file
    ImageIcon logo = new ImageIcon("resources/UPnDOWN.jpeg");
    // scale the image to fit the label
    Image scaledImage = logo.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
    ImageIcon scaledLogo = new ImageIcon(scaledImage);

    // create a label with the image
    JLabel logoLabel = new JLabel(scaledLogo);
    logoLabel.setSize(200, 200);
    // add the label to the logoPanel.
    logoPanel.add(logoLabel, BorderLayout.CENTER);

    f.add(logoPanel);

    JComponent panel = this.generateElevatorPanel();
    f.add(panel);
    f.setSize(200 // left margin
            + 10 // right margin
            + (elevators.length) * sliderWidth, // the sliders
        500);
    f.setLayout(null);
    f.setVisible(true);
  }

  /**
   * This generates the title for the elevator display.
   */
  private JComponent generateTitle() {
    JLabel label = new JLabel("The Control Center");
    label.setFont(new Font("Courier", Font.BOLD, 20));
    return label;
  }

  /**
   * This generates the panel of all of the elevators.
   */
  private JComponent generateElevatorPanel() {
    JPanel panel = new JPanel();
    panel.setBounds(200, 40, sliderWidth * elevators.length, 220);
    panel.setBackground(Color.gray);

    // add the elevator display
    for (Elevator elevator : elevators) {
      JComponent display = elevator.getDisplay();
      panel.add(display);
    }

    return panel;
  }


}
