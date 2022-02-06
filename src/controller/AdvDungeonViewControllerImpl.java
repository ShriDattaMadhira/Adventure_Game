package controller;

import dungeon.AdvancedDungeon;
import dungeon.Direction;
import dungeon.Dungeon;
import ui.DungeonView;
import ui.DungeonViewHandler;

import java.awt.event.ActionEvent;

/**
 * Controller in MVC design pattern. Takes inputs from view and asks model to do something.
 */
public class AdvDungeonViewControllerImpl implements AdvDungeonViewController {

  // model
  private Dungeon dungeonModel;
  // view
  private DungeonView dungeonView;

  /**
   * Constructor for the Controller class.
   * @param model - Dungeon Model
   * @param view - Dungeon User Interface
   */
  public AdvDungeonViewControllerImpl(Dungeon model, DungeonView view) {
    this.dungeonModel = model;
    this.dungeonView = view;
  }

  @Override
  public void playGame() {
    dungeonView.addClickListener(this);
    dungeonView.makeVisible(true);
  }

  @Override
  public void handleMouseInputs(String action) {
    if (action.contains("move")) {
      int[] currPos = dungeonModel.getCurrPlayerPosition();
      int r = Integer.parseInt(action.split(" ")[1]);
      int c = Integer.parseInt(action.split(" ")[2]);
      if (currPos[1] + 1 == c || (currPos[1] == dungeonModel.getWidth() - 1 && c == 0)) {
        dungeonModel.move(Direction.EAST);
      } else if (currPos[1] - 1 == c || (currPos[1] == 0 && c == dungeonModel.getWidth() - 1)) {
        dungeonModel.move(Direction.WEST);
      } else if (currPos[0] + 1 == r || (currPos[0] == dungeonModel.getHeight() - 1 && r == 0)) {
        dungeonModel.move(Direction.SOUTH);
      } else if (currPos[0] - 1 == r || (currPos[0] == 0 && r == dungeonModel.getHeight() - 1)) {
        dungeonModel.move(Direction.NORTH);
      }
    } else if (action.equals("Reset")) {
      dungeonModel.resetGame();
      dungeonView.refresh();
    } else if (action.contains("new")) {
      String[] vars = action.split(" ");
      dungeonModel = new AdvancedDungeon(Integer.parseInt(vars[1]), Integer.parseInt(vars[2]),
              Boolean.parseBoolean(vars[3]), Integer.parseInt(vars[4]), Integer.parseInt(vars[5]),
              Integer.parseInt(vars[6]));
      dungeonView.makeVisible(false);
      dungeonView = new DungeonViewHandler(dungeonModel);
      playGame();
    }
    dungeonView.refresh();
  }

  private Direction getDirection(String d) {
    switch (d) {
      case "north":
        return Direction.NORTH;
      case "south":
        return Direction.SOUTH;
      case "east":
        return Direction.EAST;
      case "west":
        return Direction.WEST;
      default:
        throw new IllegalArgumentException("---Direction can only be North/South/East/West.---");
    }
  }

  @Override
  public void handleKeyboardInputs(String action) {
    try {
      if (action.equals("pickup")) {
        dungeonModel.pick(true, true);
      } else if (action.contains("shoot")) {
        String[] actions = action.split(" ");
        dungeonModel.shoot(getDirection(actions[1]), Integer.parseInt(actions[2]));
      } else if (action.equals("north") || action.equals("south")
              || action.equals("east") || action.equals("west")) {
        dungeonModel.move(getDirection(action));
      }
    } catch (Exception exp) {
      // do nothing.
    }
    dungeonView.refresh();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // not testable.
    //    if (e.getActionCommand().equals("Reset")) {
    //      dungeonModel.resetGame();
    //      dungeonView.refresh();
    //    }

    handleMouseInputs(e.getActionCommand());
  }
}
