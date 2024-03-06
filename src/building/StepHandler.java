package building;

/**
 * This interface is used to represent the update handler.
 * The view will call this method when it needs to update the display.
 */
@FunctionalInterface
public interface StepHandler {
  public void handleRequest();
}
