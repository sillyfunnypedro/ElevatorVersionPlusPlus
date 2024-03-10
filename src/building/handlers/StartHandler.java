package building.handlers;

/**
 * This interface is used to represent the start handler.
 * The view will call this method when it needs to start the building.
 */
public interface StartHandler {
  public void requestHandler(boolean start);
}
