package elevator;

/**
 * The direction of the elevator.
 */
public enum Direction {
  UP("^"),
  DOWN("v"),
  STOPPED("-");

  private final String symbol;

  Direction(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return this.symbol;
  }
}
