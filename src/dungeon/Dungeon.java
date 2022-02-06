package dungeon;

/**
 * A network of tunnels and caves that are interconnected so that player can explore the entire
 * world by traveling from cave to cave through the tunnels that connect them.
 */
public interface Dungeon extends ReadOnlyDungeonModel {
  /**
   * Moves the player from one position to another.
   * @param direction - direction the player want to move. (N/S/E/W)
   */
  void move(Direction direction);

  /**
   * Shoots the arrow to kill the monsters by distance specified.
   * @param direction - direction in which the player wants to shoot the arrow. (N/S/E/W)
   * @param distance - distance till which he wants to shoot the arrow.
   */
  void shoot(Direction direction, int distance);

  /**
   * Every cell has either one or both of treasure and arrows available.
   * Player can pick these up for future use and collections.
   * @param pickTreasure - true/false for picking treasure.
   * @param pickArrows - true/false for picking arrows.
   */
  void pick(boolean pickTreasure, boolean pickArrows);

  /**
   * Resets the dungeon and player to their original default state.
   */
  void resetGame();

  /**
   * Returns a copy of the dungeon for testing purposes.
   * @return - copy of dungeon
   */
  Cells[][] getDungeon();

  /**
   * Returns the x amd y coordinates of start location.
   * @return - [x, y]
   */
  int[] getStartLocation();

  /**
   * Returns the x amd y coordinates of end location.
   * @return - [x, y]
   */
  int[] getEndLocation();
}
