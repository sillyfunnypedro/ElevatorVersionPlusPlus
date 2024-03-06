package building;

import scanerzus.Request;

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
    display.setUpdateListener(building::getElevatorSystemStatus);

    display.setRequestListener(this::processRequest);

    display.setStepListener(this::step);
  }


  private void processRequest(Request request) {
    building.addRequest(request);
  }


  private void step() {
    building.step();
  }

  @Override
  public void go() {
    display.start();
  }


}
