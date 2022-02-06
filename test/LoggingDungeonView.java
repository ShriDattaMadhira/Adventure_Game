import java.io.IOException;

import controller.AdvDungeonViewController;
import ui.DungeonView;

/**
 * Mock view for Dungeon. Helps to test how the controller is behaving.
 */
public class LoggingDungeonView implements DungeonView {

  private final Appendable log;

  public LoggingDungeonView(Appendable log) {
    this.log = log;
  }

  @Override
  public void addClickListener(AdvDungeonViewController listener) {

    try {
      log.append("Controller added.\n");
      log.append("User interacted with the view.\n");
    } catch (IOException ioe) {
      // do nothing
    }

  }

  @Override
  public void refresh() {
    try {
      log.append("Refreshing the view.\n");
    } catch (IOException ioe) {
      // do nothing
    }
  }

  @Override
  public void makeVisible(boolean visible) {
    try {
      if (visible) {
        log.append("Making the frame visible.\n");
      } else {
        log.append("Disposing the frame.\n");
      }
    } catch (IOException ioe) {
      // do nothing
    }
  }
  // end of class.
}
