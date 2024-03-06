package building;

/**
 * This interface is used to represent the building display.
 */
public interface BuildingDisplayInterface {

  public void setRequestListener(RequestHandler requestHandler);

  public void setUpdateListener(UpdateHandler updateHandler);

  public void setStepListener(StepHandler stepHandler);

  public void start();
}
