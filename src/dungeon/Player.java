package dungeon;

import java.util.Map;

/**
 * Player of the game. As it is a stand-alone game, we will only have one player playing the game.
 */
public interface Player {

  /**
   * Returns the bag of treasure the player has collected while playing the game.
   * @return - bag of treasure.
   */
  Map<Treasure, Integer> getBag();

  /**
   * Returns the coordinates of the position the player is currently in.
   * @return - [x,y] of the player.
   */
  Coordinates getPosition();

  /**
   * Returns the number of arrows the player currently has.
   * @return - number of arrows.
   */
  int getArrowCount();
}
