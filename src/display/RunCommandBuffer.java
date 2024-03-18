package display;

public class RunCommandBuffer {

  private boolean running = true;

  public synchronized boolean isRunning() {
    return running;
  }

  public synchronized void stopRunning() {
    this.running = false;
  }
}
