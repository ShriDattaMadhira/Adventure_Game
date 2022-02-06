package dungeon;

import java.util.List;
import java.util.Map;

/**
 * A cell is a node in our dungeon. It has entrances and in some cases treasure.
 * A player can go through it to go to neighboring cells in his search of the end location.
 * Depending on the number of entrances, the cell can be either a Cave or a Tunnel.
 */
public interface Cells {
  /**
   * Returns the coordinates of the cell.
   * @return - Coordinates of the cell.
   */
  int[] getCoordinates();

  /**
   * Returns the list of treasures available in the cell.
   * @return - treasure list of the cave.
   */
  List<Treasure> getTreasureList();

  /**
   * Returns the list of neighbors for the cell.
   * It is only a neighbor if this cell has path from itself to the destination cell.
   * @return - list of neighbors.
   */
  Map<Direction, Coordinates> getNeighborList();

  /**
   * Returns the number of entrances for this cell.
   * @return - number of entrances for a cell.
   */
  int getEntrances();

  /**
   * Returns if the cell is a cave or not.
   * @return - true/false.
   */
  boolean getIsCave();

  /**
   * Returns if the cell is a tunnel or not.
   * @return - true/false.
   */
  boolean getIsTunnel();

  /**
   * Returns the Otyugh in the cell if its present.
   * @return - Otyugh.
   */
  Otyugh getOtyugh();


  /**
   * Returns the number of arrows available in the cell.
   * @return - number of arrows
   */
  int getArrows();

  /**
   * Tells you if there is thief in this cell.
   * @return - Thief
   */
  Thief getThief();

  /**
   * Returns the stench in the cave depending on the position of monsters.
   * @return - Stench
   */
  int getStench();
}
