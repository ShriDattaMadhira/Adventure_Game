package dungeon;

import java.util.Map;

/**
 * A thief enters the dungeon along with the player who tries to steal player's bag if they
 * encounter each other.
 */
public interface Thief {

  /**
   * Steals the player's bag.
   * @param playerBag - player's bag.
   */
  void steal(Map<Treasure, Integer> playerBag);

}
