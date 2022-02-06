package dungeon;

import java.util.HashMap;
import java.util.Map;

/**
 * This class brings the idea of the Thief to life.
 */
public class ThiefImpl implements Thief {
  private Map<Treasure, Integer> bag;

  /**
   * Constructor for Player class.
   */
  ThiefImpl() {
    this.bag = new HashMap<>();
  }

  @Override
  public void steal(Map<Treasure, Integer> playerBag) {
    if (playerBag == null) {
      throw new IllegalArgumentException("Bag is null.");
    }

    if (playerBag.size() != 0) {
      for (Treasure treasure: playerBag.keySet()) {
        if (bag.containsKey(treasure)) {
          bag.put(treasure, bag.get(treasure) + playerBag.get(treasure));
        } else {
          bag.put(treasure, playerBag.get(treasure));
        }
      }
    }
  }
}
