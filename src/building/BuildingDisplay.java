package building;

import elevator.Elevator;

import javax.swing.*;
import java.awt.*;

/**
 * This class is responsible for displaying the building.
 * it uses java swing to display the building.
 */
public class BuildingDisplay {
  private Elevator[] elevators;
  private Runnable stepCallback = null;
  private Runnable runCallback = null;
  private Runnable stopCallback = null;
  private Runnable resetCallback = null;

  private Runnable randomCallback = null;
  private Runnable randomUpCallback = null;
  private Runnable randomDownCallback = null;


  private static final int sliderWidth = 95;


  /**
   * @param elevators     the elevators to display
   * @param stepCallback  the callback to run when the step button is pressed
   * @param runCallback   the callback to run when the run button is pressed
   * @param stopCallback  the callback to run when the stop button is pressed
   * @param resetCallback the callback to run when the reset button is pressed
   */
  public BuildingDisplay(Elevator[] elevators, Runnable stepCallback,
                         Runnable runCallback,
                         Runnable stopCallback,
                         Runnable resetCallback,
                         Runnable randomCallback,
                         Runnable randomUpCallback,
                         Runnable randomDownCallback) {
    this.elevators = elevators;
    this.stepCallback = stepCallback;
    this.runCallback = runCallback;
    this.stopCallback = stopCallback;
    this.resetCallback = resetCallback;
    this.randomCallback = randomCallback;
    this.randomUpCallback = randomUpCallback;
    this.randomDownCallback = randomDownCallback;

  }

  /*
   * This method initializes the display.
   */
  public void initializeDisplay() {
    this.initializeMainPane();
  }


  /**
   * This method initializes the display.
   * Its parameters are callbacks to the elevator system.
   */
  public void initializeMainPane() {


    JFrame f = new JFrame("Building Manager");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel titlePanel = new JPanel();
    titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
    JComponent leftPanel = this.leftPanel();

    titlePanel.add(leftPanel);

    JComponent rightPanel = this.rightPanel();
    titlePanel.add(rightPanel);
    titlePanel.setSize(200 // left margin
            + 10 // right margin
            + (elevators.length) * sliderWidth, // the sliders
        400);

    f.add(titlePanel);
    f.setSize(200 // left margin
            + 10 // right margin
            + (elevators.length) * sliderWidth, // the sliders
        500);

    f.setLayout(null);
    f.setVisible(true);
  }

  /**
   * make the logo panel
   */
  private JComponent leftPanel() {
    int logoSize = 150;
    // set up the logo panel
    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

    JPanel logoPanel = new JPanel();
    logoPanel.setBounds(0, 0, logoSize, logoSize);
    // set the image to the jpg file
    ImageIcon logo = new ImageIcon("resources/UPnDOWN.jpeg");
    // scale the image to fit the label
    Image scaledImage = logo.getImage().getScaledInstance(logoSize, logoSize, Image.SCALE_SMOOTH);
    ImageIcon scaledLogo = new ImageIcon(scaledImage);

    // create a label with the image
    JLabel logoLabel = new JLabel(scaledLogo);
    logoLabel.setSize(logoSize, logoSize);
    // add the label to the logoPanel.
    logoPanel.add(logoLabel, BorderLayout.CENTER);
    leftPanel.add(logoPanel);

    JButton stepButton = new JButton("Step");
    stepButton.addActionListener(e -> stepCallback.run());
    leftPanel.add(stepButton);

    JButton runButton = new JButton("Run");
    runButton.addActionListener(e -> runCallback.run());
    leftPanel.add(runButton);

    JButton stopButton = new JButton("Stop");
    stopButton.addActionListener(e -> stopCallback.run());
    leftPanel.add(stopButton);

    JButton resetButton = new JButton("Reset");
    resetButton.addActionListener(e -> resetCallback.run());
    leftPanel.add(resetButton);

    leftPanel.setBackground(Color.pink);
    //leftPanel.setBounds(0, 0, 150, 500);
    return leftPanel;
  }


  /**
   * This generates the panel of all of the elevators.
   */
  private JComponent rightPanel() {

    JPanel containerPanel = new JPanel();
    containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));


    JPanel panel = new JPanel();


    panel.setBounds(150, 0, sliderWidth * elevators.length, 220);
    panel.setBackground(Color.gray);

    // add the elevator display
    for (Elevator elevator : elevators) {
      JComponent display = elevator.getDisplay();
      panel.add(display);
    }

    panel.setSize(200 // left margin
            + 10 // right margin
            + (elevators.length) * sliderWidth, // the sliders
        300);
    containerPanel.add(panel);

    JComponent requestPanel = requestPanel();
    containerPanel.add(requestPanel);

    return containerPanel;
  }

  private JComponent requestPanel() {
    JPanel panel = new JPanel();
    panel.setBounds(150, 220, sliderWidth * elevators.length, 220);
    panel.setBackground(Color.gray);
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

    JButton randomButton = new JButton("Random");
    randomButton.addActionListener(e -> randomCallback.run());

    JButton randomUpButton = new JButton("Up");
    randomUpButton.addActionListener(e -> randomUpCallback.run());

    JButton randomDownButton = new JButton("Down");
    randomDownButton.addActionListener(e -> randomDownCallback.run());


    panel.add(randomButton);
    panel.add(randomUpButton);
    panel.add(randomDownButton);
    
    return panel;

  }


}
