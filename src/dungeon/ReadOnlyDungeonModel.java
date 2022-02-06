package dungeon;

import java.util.List;

/**
 * ReadOnly interface for view to access values of different variables and display to the user.
 */
public interface ReadOnlyDungeonModel {
  /**
   * Returns the coordinates of the player during the game.
   * @return - [x,y] coordinates
   */
  int[] getCurrPlayerPosition();

  /**
   * Returns if the player is eaten by an Otyugh or not.
   * @return - true/false
   */
  boolean getEaten();

  /**
   * Return the details of the location.
   * @return - Returns the player details.
   */
  String getGameDetails();

  /**
   * Return the details of the player.
   * @return - Returns the player details.
   */
  String getPlayerDetails();

  /**
   * Tells us if the game has reached its end or not.
   * @return - game state.
   */
  boolean isGameOver();

  /**
   * returns a list of all the caves/tunnels player visited sor far.
   * @return - a list of locations.
   */
  List<Cells> getVisited();


  /**
   * Returns the width of the dungeon.
   * @return - integer.
   */
  int getWidth();

  /**
   * Returns the height of the dungeon.
   * @return - integer.
   */
  int getHeight();

  /**
   * Returns the wrapping property of the dungeon.
   * @return - integer.
   */
  boolean getWrapping();

  /**
   * Returns the interconnectivity of the dungeon.
   * @return - integer.
   */
  int getInterconnectivity();

  /**
   * Returns the Treasure/Arrow percentage int the dungeon.
   * @return - integer.
   */
  int getPercentage();

  /**
   * Returns the number of Otyughs in the dungeon.
   * @return - integer.
   */
  int getOtyughNum();

  /**
   * Returns the number of thieves in the dungeon.
   * @return - integer.
   */
  int getThiefNum();

  /**
   * Returns the health of monster in the cave.
   * @return - integer.
   */
  int getOtyughHealth(Cells cell);

  /**
   * Returns the stench for that cell.
   * @return - integer.
   */
  int getStench(Cells cell);

  /**
   * Returns number of arrows in a cell.
   * @param cell - Cave or Tunnel
   * @return - integer
   */
  int getCellArrows(Cells cell);

  /**
   * Returns the list of treasures in the current cell.
   * @param cell - Cave or Tunnel
   * @return - List of treasures
   */
  List<Treasure> getCellTreasure(Cells cell);

}
