package controller;

import building.Building;
import controller.BuildingControllerInterface;
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
    display.setUpdateListener(this.building::getElevatorSystemStatus);

    display.setRequestListener(this::processRequest);

    display.setStepListener(this::step);

    display.setStartListener(this.building::start);

    building.start(true);

  }

  /**
   * This method is used to step the building.
   * This can be used to simulate the building
   */
  private void step() {
    building.step();
  }


  /**
   * This method is used to process a request.
   *
   * @param request the request to process.
   */
  private void processRequest(Request request) {
    building.addRequest(request);
  }


  @Override
  public void go() {
    display.start();
  }


}
