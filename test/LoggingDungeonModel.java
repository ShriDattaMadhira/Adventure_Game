import java.util.List;

import dungeon.Cells;
import dungeon.Direction;
import dungeon.Dungeon;
import dungeon.Treasure;

/**
 * Mock model for Dungeon. Helps to test how the controller is behaving.
 */
public class LoggingDungeonModel implements Dungeon {

  private final StringBuilder log;

  public LoggingDungeonModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void move(Direction direction) {
    if (direction == null) {
      log.append("Null Direction specified.\n");
      return;
    }

    if (direction != Direction.NORTH && direction != Direction.SOUTH && direction != Direction.WEST
            && direction != Direction.EAST) {
      log.append("Invalid Direction specified.\n");
      return;
    }

    if (isGameOver()) {
      log.append("GAME OVER!\n");
      return;
    }
    log.append("Moving ").append(direction).append("\n");
  }

  @Override
  public void shoot(Direction direction, int distance) {
    if (direction == null) {
      log.append("Null Direction specified.\n");
    }
    if (direction != Direction.NORTH && direction != Direction.SOUTH && direction != Direction.WEST
            && direction != Direction.EAST) {
      log.append("Invalid Direction specified.\n");
    }
    if (distance <= 0) {
      log.append("Can't shoot. Distance too low.\n");
    }
    log.append("Shooting ").append(direction).append(" by ").append(distance).append("\n");
  }

  @Override
  public void pick(boolean pickTreasure, boolean pickArrows) {
    if (pickTreasure) {
      log.append("Picking Treasure.\n");
    }
    if (pickArrows) {
      log.append("Picking Arrows.\n");
    }
  }

  @Override
  public void resetGame() {
    log.append("Resetting the dungeon.\nResetting player.\nResetting visitors.\n");
  }

  @Override
  public Cells[][] getDungeon() {
    log.append("Returning dungeon copy.\n");
    return new Cells[0][0];
  }

  @Override
  public int[] getStartLocation() {
    log.append("Returning start location coordinates.\n");
    return new int[0];
  }

  @Override
  public int[] getEndLocation() {
    log.append("Returning end location coordinates.\n");
    return new int[0];
  }

  @Override
  public int[] getCurrPlayerPosition() {
    log.append("Returning current player coordinates.\n");
    return new int[] {0, 2};
  }

  @Override
  public boolean getEaten() {
    log.append("Returning if the player is eaten or not.\n");
    return false;
  }

  @Override
  public String getGameDetails() {
    log.append("Returning game details.\n");
    return "";
  }

  @Override
  public String getPlayerDetails() {
    log.append("Returning player details.\n");
    return "";
  }

  @Override
  public boolean isGameOver() {
    log.append("is game over?\n");
    return false;
  }

  @Override
  public List<Cells> getVisited() {
    log.append("Returning visited cells array.\n");
    return null;
  }

  @Override
  public int getWidth() {
    log.append("Returning width of the dungeon.\n");
    return 0;
  }

  @Override
  public int getHeight() {
    log.append("Returning height of the dungeon\n");
    return 0;
  }

  @Override
  public boolean getWrapping() {
    log.append("Returning wrapping of the dungeon [true/false].\n");
    return false;
  }

  @Override
  public int getInterconnectivity() {
    log.append("Returning interconnectivity of the dungeon.\n");
    return 0;
  }

  @Override
  public int getPercentage() {
    log.append("Returning treasure and arrow % of the dungeon.\n");
    return 0;
  }

  @Override
  public int getOtyughNum() {
    log.append("Returning number of Otyughs in the dungeon.\n");
    return 0;
  }

  @Override
  public int getThiefNum() {
    log.append("Returning number of thieves in the dungeon.\n");
    return 0;
  }

  @Override
  public int getOtyughHealth(Cells cell) {
    log.append("Returning Otyugh health.\n");
    return 0;
  }

  @Override
  public int getStench(Cells cell) {
    log.append("Returning the level of stench in the cell.\n");
    return 0;
  }

  @Override
  public int getCellArrows(Cells cell) {
    log.append("Returning the number of arrows in the cell.\n");
    return 0;
  }

  @Override
  public List<Treasure> getCellTreasure(Cells cell) {
    log.append("Returning treasure in the cell.\n");
    return null;
  }
}
