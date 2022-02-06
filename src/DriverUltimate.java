import controller.AdvDungeonConsoleController;
import controller.AdvDungeonConsoleControllerImpl;
import controller.AdvDungeonViewController;
import controller.AdvDungeonViewControllerImpl;
import dungeon.AdvancedDungeon;
import dungeon.Dungeon;
import ui.DungeonView;
import ui.DungeonViewHandler;

import java.io.InputStreamReader;

/**
 * Driver class that executes UI mode or Console mode depending on given requirements.
 */
public class DriverUltimate {

  /**
   * Main method.
   * @param args - CLI Arguments.
   */
  public static void main(String[] args) {
    Dungeon d;

    if (args.length == 0) {
      d = new AdvancedDungeon(7, 5, true, 8,
              25, 4);
      DungeonView view = new DungeonViewHandler(d);
      AdvDungeonViewController viewController = new AdvDungeonViewControllerImpl(d, view);
      viewController.playGame();
    } else if (args.length == 6) {
      int width = Integer.parseInt(args[0]);
      int height = Integer.parseInt(args[1]);
      boolean wrap = Integer.parseInt(args[2]) == 1;
      int interconnectivity = Integer.parseInt(args[3]);
      int treasure_pcnt = Integer.parseInt(args[4]);
      int noOfOtyughs = Integer.parseInt(args[5]);
      System.out.println("width = " + width + "\nheight = " + height + "\nwrapping = " + wrap
              + "\ninterconnectivity = " + interconnectivity + "\ntreasure percentage = "
              + treasure_pcnt + "\nnumber of otyughs = " + noOfOtyughs + "\n");
      try {
        d = new AdvancedDungeon(width, height, wrap, interconnectivity, treasure_pcnt,
                noOfOtyughs);
        AdvDungeonConsoleController consoleController = new AdvDungeonConsoleControllerImpl(
                new InputStreamReader(System.in), System.out, d);
        consoleController.playGame();
      } catch (IllegalArgumentException iae) {
        System.out.println(iae.getMessage() + "\n");
      }
    } else {
      System.out.println(" No CLI args - GUI Mode.\n Exactly 6 CLI args - Console Mode.");
    }
  }

}
