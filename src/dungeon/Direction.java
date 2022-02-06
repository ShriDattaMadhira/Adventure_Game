package dungeon;

/**
 * Direction is the direction the player wants to move.
 * As we already know, it contains NORTH,SOUTH, EAST, and WEST.
 * These are used to calculate player's next location in the dungeon.
 */
public enum Direction {
  NORTH(-1, 0), SOUTH(1, 0), EAST(0, 1), WEST(0, -1);

  private final int x;
  private final int y;

  Direction(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns the x coordinate of the direction.
   * @return - x coordinate
   */
  public int getX() {
    return this.x;
  }

  /**
   * Returns the y coordinate of the direction.
   * @return - y coordinate
   */
  public int getY() {
    return this.y;
  }
}
