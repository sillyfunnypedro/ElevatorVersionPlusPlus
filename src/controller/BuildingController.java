package controller;

import building.Building;
import display.BuildingDisplayInterface;
import scanerzus.Request;

/**
 * This is the building controller for our building.
 */
public class BuildingController implements BuildingControllerInterface {
  private final Building building;
  private final BuildingDisplayInterface display;

  /**
   * the constructor for the building controller.
   *
   * @param building the building.
   * @param display  the display.
   */
  public BuildingController(Building building, BuildingDisplayInterface display) {
    this.building = building;
    this.display = display;

    // there are two call backs that must be registered
    display.setUpdateListener(this.building::getStatusElevatorSystem);

    display.setRequestListener(this::processRequest);

    display.setStepListener(this::step);

    display.setStartListener(this.building::startElevatorSystem);

    display.setStopListener(this.building::stopElevatorSystem);

    building.startElevatorSystem();

  }

  /**
   * This method is used to step the building.
   * This can be used to simulate the building
   */
  private void step() {
    building.stepElevatorSystem();
  }


  /**
   * This method is used to process a request.
   *
   * @param request the request to process.
   */
  private void processRequest(Request request) {
    building.addRequestToElevatorSystem(request);
  }


  @Override
  public void go() {
    display.start();
  }


}
