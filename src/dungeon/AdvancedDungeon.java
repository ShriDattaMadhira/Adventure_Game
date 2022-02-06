package dungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Extension of the dungeon we created for project 3.
 * Has additional features like monsters, smell, arrows that makes the player's job of surviving &
 * coming out of the dungeon alive that much harder.
 */
public class AdvancedDungeon extends DungeonImpl {
  private boolean eaten;
  private final Random rand;
  private final int otyughNum;
  private final  PlayerImpl playerCopy;
  private Cell[][] dungeonCopy;
  // private final Cell startCopy;
  private int arrowSituation;

  /**
   * Constructs a dungeon for the player to play the game.
   *
   * @param width             - width of the dungeon.
   * @param height            - height of the dungeon.
   * @param wrap              - wrap the edge cases.
   * @param interconnectivity - interconnectivity in the dungeon.
   * @param treasurePcnt      - percentage of cells with treasure.
   * @param noOfOtyughs - number of Otyughs
   */
  public AdvancedDungeon(int width, int height, boolean wrap, int interconnectivity,
                         int treasurePcnt, int noOfOtyughs) {
    super(width, height, wrap, interconnectivity, treasurePcnt);
    if (noOfOtyughs < 1) {
      throw new IllegalArgumentException("There must be at least one Otyugh in your dungeon.");
    }
    if (noOfOtyughs > (width * height) / 2) {
      throw new IllegalArgumentException("Too many Otyughs");
    }
    this.otyughNum = noOfOtyughs;
    this.eaten = false;
    this.arrowSituation = -1;
    rand = new Random(0);
    // put arrows in the dungeon.
    putArrows(width, height, treasurePcnt);
    // place Otyughs in the dungeon.
    putOtyugh(noOfOtyughs);
    // places stench in the dungeon initially.
    putStench();
    // places a thief at a random cell.
    putThief(width, height);

    playerCopy = new PlayerImpl(super.player);
    // startCopy = new Cell(super.start);
    dungeonCopy = getDungeon();
  }

  private void putArrows(int width, int height, int treasurePercentage) {
    // Random rand = new Random(0);
    int posNum = (int) Math.ceil((width * height * treasurePercentage) / 100.0);
    for (int i = 0; i < posNum; i++) {
      int x = rand.nextInt(height);
      int y = rand.nextInt(width);
      dungeon[x][y].setArrow(true, 5);
    }
  }

  private void putOtyugh(int noOfOtyughs) {
    end.setOtyugh(new Otyugh(end.getCoordinates()));
    // Random rand = new Random(0);
    List<Cell> caves = getAllCaves();
    int i = 0;
    while (i < noOfOtyughs - 1) {
      Cell cave = caves.get(rand.nextInt(caves.size()));
      if (!cave.equals(end)) {
        cave.setOtyugh(new Otyugh(cave.getCoordinates()));
        i += 1;
      }
    }
  }

  private void putStench() {
    for (Cell[] cellList: dungeon) {
      for (Cell cell: cellList) {
        if (cell.getOtyugh() == null) {
          cell.setStench(getStench(cell));
        }

      }
    }
  }

  private void putThief(int width, int height) {
    while (true) {
      int x = rand.nextInt(height);
      int y = rand.nextInt(width);
      if (dungeon[x][y].getOtyugh() == null && dungeon[x][y].getTreasureList().size() == 0) {
        dungeon[x][y].setThief(new ThiefImpl());
        break;
      }
    }
  }

  @Override
  public void move(Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Null Direction specified.");
    }

    if (direction != Direction.NORTH && direction != Direction.SOUTH && direction != Direction.WEST
            && direction != Direction.EAST) {
      throw new IllegalArgumentException("Invalid Direction specified.");
    }

    if (isGameOver()) {
      return;
    }

    if (calcNextLocation(direction)) {
      if (this.eaten
              || dungeon[player.getPosition().getX()][player.getPosition().getY()].equals(end)) {
        setGameOver(true);
      }
    }
  }

  // calculates the next location, moves the player and sets the player's treasure bag.
  private boolean calcNextLocation(Direction direction) {
    // current position of the player.
    Cell curr = dungeon[player.getPosition().getX()][player.getPosition().getY()];

    if (curr.getNeighborList().containsKey(direction)) {
      // updating player position.
      Coordinates next = curr.getNeighborList().get(direction);
      player.setPosition(next);
      // updating visited list.
      Cell nextCell = dungeon[next.getX()][next.getY()];
      addToVisited(nextCell);

      if (nextCell.getOtyugh() != null) {
        // player.getPosition().getOtyugh()
        if (nextCell.getOtyugh().getHealth() == 100) {
          this.eaten = true;
        } else if (nextCell.getOtyugh().getHealth() == 50) {
          this.eaten = rand.nextDouble() > 0.5;
        }
      } else if (nextCell.getThief() != null) {
        nextCell.getThief().steal(player.getBag());
        player.stolenBag();
      }

      return true;
    } else {
      return false;
    }
  }

  @Override
  public void pick(boolean pickTreasure, boolean pickArrows) {
    Cell curr = dungeon[player.getPosition().getX()][player.getPosition().getY()];
    if (isGameOver()) {
      return;
    }
    super.pick(pickTreasure, false);
    if (pickArrows) {
      player.setArrowCount(true, curr.getArrows());
      curr.setArrow(false, curr.getArrows());
    }
  }

  @Override
  public void shoot(Direction direction, int distance) {
    if (direction == null) {
      throw new IllegalArgumentException("Null Direction specified.");
    }
    if (direction != Direction.NORTH && direction != Direction.SOUTH && direction != Direction.WEST
            && direction != Direction.EAST) {
      throw new IllegalArgumentException("Invalid Direction specified.");
    }
    if (distance <= 0) {
      throw new IllegalArgumentException("Can't shoot. Distance too low.");
    }
    if (player.getArrowCount() == 0) {
      throw new IllegalArgumentException("Can't shoot. No Arrows.");
    }
    if (isGameOver()) {
      return;
    }
    calcArrowDestination(direction, distance);
  }

  private void calcArrowDestination(Direction direc, int dist) {
    arrowSituation = -1;
    Cell currCell = dungeon[player.getPosition().getX()][player.getPosition().getY()];
    boolean flag = false;

    Map<Direction, Direction> opposite = new HashMap<>();
    opposite.put(Direction.NORTH, Direction.SOUTH);
    opposite.put(Direction.SOUTH, Direction.NORTH);
    opposite.put(Direction.EAST, Direction.WEST);
    opposite.put(Direction.WEST, Direction.EAST);

    int i = 0;
    while (i < dist) {
      // if its a tunnel,
      if (currCell.getIsTunnel()) {
        // get its neighbor, go there, flag = true.
        Map<Direction, Coordinates> neighbors = currCell.getNeighborList();
        if (neighbors.containsKey(direc)) {
          // Tunnel contains the direction in which the arrow is travelling.
          Coordinates coordinates = currCell.getNeighborList().get(direc);
          currCell = dungeon[coordinates.getX()][coordinates.getY()];
        } else {
          // If it doesn't, we will choose the neighbor that is not opposite of current direction.
          for (Direction d : neighbors.keySet()) {
            if (d != opposite.get(direc)) {
              Coordinates coordinates = currCell.getNeighborList().get(d);
              currCell = dungeon[coordinates.getX()][coordinates.getY()];
              direc = d;
              break;
            }
          }
        }
        if (currCell.getIsCave()) {
          i++;
        }
        flag = true;
      } else if (currCell.getIsCave()) {
        if (currCell.getNeighborList().containsKey(direc)) {
          Coordinates coordinates = currCell.getNeighborList().get(direc);
          currCell = dungeon[coordinates.getX()][coordinates.getY()];
          if (currCell.getIsCave()) {
            i ++;
          }
          flag = true;
        } else {
          flag = false;
        }
      }
      // if flag = false,
      if (!flag) {
        // break out of the loop (hits the wall).
        break;
      }
    }
    // If the distance mentioned is exact.
    if (i == dist) {
      // If otyugh is in that cell -> HIT
      if (currCell.getOtyugh() != null) {
        currCell.getOtyugh().setHealth();
        if (currCell.getOtyugh().getHealth() == 0) {
          currCell.setStench(0);
        }
        arrowSituation = 2;
      } else {
        arrowSituation = 1;
      }
    } else {
      arrowSituation = 0;
    }
    // subtract 1 arrow from player as he will shoot it now.
    player.setArrowCount(false, 1);
  }

  @Override
  public String getGameDetails() {
    Cell curr = dungeon[super.player.getPosition().getX()][super.player.getPosition().getY()];

    // is this a cave or tunnel.
    int[] loc = super.getCurrPlayerPosition();
    String location = "Current location: " + Arrays.toString(loc);
    if (curr.getIsTunnel()) {
      location += "\nThis is a Tunnel.\n";
    } else {
      location += "\nThis is a Cave.\n";
    }

    // is there a thief here.
    String thief = "";
    if (curr.getThief() != null) {
      thief += "Encountered a Thief. Your bag is stolen.\n";
    }

    // is there treasure here.
    String treasure = "";
    if (curr.getTreasureList().size() > 0) {
      treasure += "There is treasure here.\n";
    } else {
      treasure += "There is no treasure here.\n";
    }
    // are there arrows here.
    String arrows = "";
    if (curr.getArrows() > 0) {
      arrows += "There are " + curr.getArrows() + " arrows here.\n";
    } else {
      arrows += "There are no arrows here.\n";
    }

    StringBuilder sb = new StringBuilder();

    sb.append(location).append(treasure).append(arrows).append(thief).append("\n");

    return sb.toString();
  }

  @Override
  public String getPlayerDetails() {
    Cells curr = dungeon[player.getPosition().getX()][player.getPosition().getY()];

    // where can we go from here.
    StringBuilder move = new StringBuilder("You can go to ");
    Set<Direction> d = curr.getNeighborList().keySet();
    for (Direction direction: d) {
      move.append(direction.name()).append(", ");
    }
    move.delete(move.length() - 2, move.length()).append("\n");

    // is there any smell coming.
    String smell = "";
    if (curr.getOtyugh() != null) {
      if (curr.getOtyugh().getHealth() == 0) {
        smell += "Otyugh in this cave is dead.\n";
      } else if (curr.getOtyugh().getHealth() == 50) {
        smell += "Otyugh here is injured. You have a chance to escape.\n";
      }
    }
    int r = getStench(curr);
    if (r == 2) {
      smell += "Pungent smell.\n";
    } else if (r == 1) {
      smell += "Faint smell.\n";
    } else if (r == -2) {
      smell += "Nearby Otyugh Injured.\n";
    } else if (r == -1) {
      smell += "Nearby Otyugh Dead.\n";
    } else {
      smell += "No Otyughs Nearby.\n";
    }

    String arrows = "";
    if (arrowSituation == 0) {
      arrows += "Arrow Wasted.\n";
    } else if (arrowSituation == 1) {
      arrows += "Arrow Missed - Distance too far/close.\n";
    } else if (arrowSituation == 2) {
      arrows += "Nice Shot.\n";
    }

    StringBuilder playerDetails = new StringBuilder();

    playerDetails.append("Current bag: ").append(player.getBag()).append("\nCurrent Arrow Count: ")
            .append(player.getArrowCount()).append("\n").append(arrows).append(smell).append(move);
    return playerDetails.toString();
  }

  @Override
  public int getOtyughNum() {
    return this.otyughNum;
  }

  @Override
  public int getThiefNum() {
    return 1;
  }

  @Override
  public int getStench(Cells curr) {
    int otyughCount = 0;
    Set<Direction> neighborDirections = curr.getNeighborList().keySet();

    for (Direction direction1: neighborDirections) {
      // checking +1 from current player's position.
      Coordinates coordinates = curr.getNeighborList().get(direction1);
      if (dungeon[coordinates.getX()][coordinates.getY()].getOtyugh() != null) {
        if (dungeon[coordinates.getX()][coordinates.getY()].getOtyugh().getHealth() == 100) {
          return 2;
        } else if (dungeon[coordinates.getX()][coordinates.getY()].getOtyugh().getHealth() == 50) {
          return -2;
        } else {
          return -1;
        }
      }
      // checking +2 from current player's position.
      Coordinates coordinates1 = curr.getNeighborList().get(direction1);
      Cell neighbor = dungeon[coordinates1.getX()][coordinates1.getY()];
      for (Direction direction2: neighbor.getNeighborList().keySet()) {
        Coordinates coordinates2 = neighbor.getNeighborList().get(direction2);
        if (dungeon[coordinates2.getX()][coordinates2.getY()].getOtyugh() != null) {
          otyughCount ++;
        }
      }
    }
    if (otyughCount > 1) {
      return 2;
    } else if (otyughCount == 1) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public boolean getEaten() {
    return this.eaten;
  }

  @Override
  public int getOtyughHealth(Cells cell) {
    if (cell.getOtyugh() == null) {
      return -1;
    }
    return cell.getOtyugh().getHealth();
  }

  @Override
  public void resetGame() {
    super.player = new PlayerImpl(playerCopy);
    super.dungeon = dungeonCopy;
    dungeonCopy = getDungeon();
    super.gameOver = false;
    this.eaten = false;
    this.arrowSituation = -1;
    super.visited = new ArrayList<>(List.of(
            dungeon[start.getCoordinates()[0]][start.getCoordinates()[1]]));
  }

  @Override
  public int getCellArrows(Cells cell) {
    return cell.getArrows();
  }

  @Override
  public List<Treasure> getCellTreasure(Cells cell) {
    return cell.getTreasureList();
  }
  // end of class.
}
