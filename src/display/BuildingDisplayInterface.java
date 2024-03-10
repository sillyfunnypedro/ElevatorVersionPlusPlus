package display;

import building.handlers.RequestHandler;
import building.handlers.StartHandler;
import building.handlers.StepHandler;
import building.handlers.UpdateHandler;

/**
 * This interface is used to represent the building display.
 */
public interface BuildingDisplayInterface {

  public void setRequestListener(RequestHandler requestHandler);

  public void setUpdateListener(UpdateHandler updateHandler);

  public void setStepListener(StepHandler stepHandler);

  public void setStartListener(StartHandler startHandler);


  public void start();
}
