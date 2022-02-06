import org.junit.Test;

import java.util.Arrays;

import dungeon.AdvancedDungeon;
import dungeon.Cells;
import dungeon.Direction;
import dungeon.Dungeon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test to check if the dungeon creation and gameplay are behaving as expected.
 */
public class DungeonTest {
  private Dungeon dungeon;

  /**
   * Test if start and end locations are generated as per the conditions.
   */
  @Test(expected = IllegalArgumentException.class) // done
  public void testGetStartNEndLocation() {
    // none of the nodes selected are caves.
    dungeon = new AdvancedDungeon(6, 4, true, 3, 25,
            4);
    // dungeon is too small.
    dungeon = new AdvancedDungeon(3, 3, false, 4, 10,
            2);

    // testing for wrapping dungeon.
    dungeon = new AdvancedDungeon(7, 5, true, 8, 20,
            4);
    // testing the start location generation.
    int[] start = dungeon.getStartLocation();
    assertEquals(0, start[0]);
    assertEquals(2, start[1]);
    // testing the end location generation.
    int[] end = dungeon.getEndLocation();
    assertEquals(4, end[0]);
    assertEquals(5, end[1]);
    // if distance between start and end is > 5.
    double distance = Math.sqrt(Math.pow((end[0] - start[0]), 2)
            + (Math.pow((end[1] - start[1]), 2)));
    assertTrue(distance >= 5);

    // testing for non-wrapping dungeon.
    dungeon = new AdvancedDungeon(6, 6, false, 4, 5,
            4);
    start = dungeon.getStartLocation();
    assertEquals(0, start[0]);
    assertEquals(4, start[1]);
    end = dungeon.getEndLocation();
    assertEquals(5, end[0]);
    assertEquals(1, end[1]);
    distance = Math.sqrt(Math.pow((end[0] - start[0]), 2) + (Math.pow((end[1] - start[1]), 2)));
    assertTrue(distance >= 5);
  }

  /**
   * Test if the details of the current player and game state are printed as expected.
   */
  @Test // done
  public void testGameDetails() {
    // testing for wrapping dungeon.
    dungeon = new AdvancedDungeon(7, 5, true, 8, 20,
            4);
    dungeon.pick(false, true);
    dungeon.move(Direction.SOUTH);
    String expected = "Current location: [1, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n\n";
    assertEquals(expected, dungeon.getGameDetails());
    dungeon.move(Direction.SOUTH);
    expected = "Current location: [2, 2]\n"
            + "This is a Cave.\n"
            + "There is treasure here.\n"
            + "There are 5 arrows here.\n\n";
    assertEquals(expected, dungeon.getGameDetails());
    expected = "Current bag: {}\n"
            + "Current Arrow Count: 8\n"
            + "Pungent smell.\n"
            + "You can go to WEST, EAST, NORTH, SOUTH\n";
    assertEquals(expected, dungeon.getPlayerDetails());

    // testing for non-wrapping dungeon.
    dungeon = new AdvancedDungeon(6, 6, false, 4, 21,
            5);
    expected = "Current location: [0, 4]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n\n";
    assertEquals(expected, dungeon.getGameDetails());
    dungeon.pick(false, true);
    dungeon.move(Direction.SOUTH);
    expected = "Current location: [1, 4]\n"
            + "This is a Cave.\n"
            + "There is treasure here.\n"
            + "There are no arrows here.\n\n";
    assertEquals(expected, dungeon.getGameDetails());
    expected = "Current bag: {}\n"
            + "Current Arrow Count: 8\n"
            + "Pungent smell.\n"
            + "You can go to WEST, EAST, NORTH\n";
    assertEquals(expected, dungeon.getPlayerDetails());
  }

  /**
   * Test if a player can traverse through all the cells in the dungeon.
   */
  @Test // done
  public void testDungeonConnectivity() {
    dungeon = new AdvancedDungeon(6, 6, false, 4, 21,
            5);
    dungeon.pick(false, true);
    dungeon.move(Direction.EAST);
    dungeon.move(Direction.SOUTH);
    dungeon.pick(false, true);
    dungeon.move(Direction.SOUTH);
    dungeon.move(Direction.SOUTH);
    dungeon.move(Direction.SOUTH);
    dungeon.move(Direction.SOUTH);
    System.out.println(dungeon.getGameDetails());
    dungeon.move(Direction.NORTH);
    dungeon.move(Direction.NORTH);
    dungeon.move(Direction.NORTH);
    dungeon.move(Direction.NORTH);
    System.out.println(dungeon.getGameDetails());
    dungeon.shoot(Direction.WEST, 1);
    dungeon.shoot(Direction.WEST, 1);
    dungeon.move(Direction.WEST);
    dungeon.move(Direction.WEST);
    System.out.println(dungeon.getGameDetails());
    dungeon.move(Direction.NORTH);
    dungeon.move(Direction.WEST);
    dungeon.move(Direction.SOUTH);
    System.out.println(dungeon.getGameDetails());
    dungeon.shoot(Direction.WEST, 1);
    dungeon.shoot(Direction.WEST, 1);
    dungeon.move(Direction.WEST);
    dungeon.move(Direction.NORTH);
    dungeon.move(Direction.WEST);
    dungeon.move(Direction.SOUTH);
    dungeon.move(Direction.SOUTH);
    dungeon.move(Direction.EAST);
    System.out.println(dungeon.getGameDetails());
    dungeon.shoot(Direction.EAST, 1);
    dungeon.shoot(Direction.EAST, 1);
    dungeon.move(Direction.SOUTH);
    dungeon.move(Direction.SOUTH);
    dungeon.pick(false, true);
    dungeon.move(Direction.EAST);
    dungeon.move(Direction.WEST);
    dungeon.move(Direction.SOUTH);
    dungeon.move(Direction.EAST);
    dungeon.move(Direction.SOUTH);
    dungeon.pick(false, true);
    dungeon.move(Direction.EAST);
    System.out.println(dungeon.getGameDetails());
    dungeon.move(Direction.NORTH);
    dungeon.move(Direction.SOUTH);
    dungeon.move(Direction.WEST);
    System.out.println(dungeon.getGameDetails());
    dungeon.move(Direction.NORTH);
    dungeon.move(Direction.WEST);
    dungeon.move(Direction.SOUTH);
    System.out.println(dungeon.getGameDetails());
    dungeon.move(Direction.NORTH);
    dungeon.move(Direction.NORTH);
    dungeon.move(Direction.NORTH);
    System.out.println(dungeon.getGameDetails());
    dungeon.move(Direction.EAST);
    dungeon.move(Direction.EAST);
    dungeon.move(Direction.SOUTH);
    dungeon.move(Direction.NORTH);
    System.out.println(dungeon.getGameDetails());
    dungeon.move(Direction.WEST);
    dungeon.move(Direction.WEST);
    dungeon.move(Direction.WEST);
    dungeon.move(Direction.WEST);
    System.out.println(dungeon.getGameDetails());
    dungeon.shoot(Direction.SOUTH, 1);
    dungeon.shoot(Direction.SOUTH, 1);
    dungeon.move(Direction.SOUTH);
    dungeon.move(Direction.EAST);
    dungeon.move(Direction.WEST);
    System.out.println(dungeon.getGameDetails());
    dungeon.move(Direction.SOUTH);
    dungeon.move(Direction.SOUTH);
    dungeon.move(Direction.NORTH);
    dungeon.move(Direction.EAST);
    dungeon.shoot(Direction.SOUTH, 1);
    dungeon.shoot(Direction.SOUTH, 1);
    dungeon.move(Direction.SOUTH);
    System.out.println(dungeon.getGameDetails());
    assertTrue(dungeon.isGameOver());
  }

  /**
   * Test if the treasure is being stored in the way that is expected.
   */
  @Test // done
  public void testTreasurePercentage() {
    // wrapping
    dungeon = new AdvancedDungeon(7, 5, true, 8, 25,
            4);
    int[] temp = treasurePercentageHelper(dungeon.getDungeon());
    assertEquals(0.20, (float) temp[1] / temp[0], 0.2);
    assertNotEquals(0.19, (float) temp[1] / temp[0], 0.0);

    // non-wrapping
    dungeon = new AdvancedDungeon(6, 6, false, 4, 21,
            5);
    temp = treasurePercentageHelper(dungeon.getDungeon());
    System.out.println(Arrays.toString(temp));
    assertEquals(0.22, (float) temp[1] / temp[0], 0.01);
  }

  private int[] treasurePercentageHelper(Cells[][] dungeonCopy) {
    int caveCount = 0;
    int treasureCount = 0;
    for (Cells[] cellList: dungeonCopy) {
      for (Cells cell: cellList) {
        if (cell.getIsCave()) {
          caveCount += 1;
          if (cell.getTreasureList().size() > 0) {
            treasureCount += 1;
          }
        }
      }
    }
    return new int[]{caveCount, treasureCount};
  }

  /**
   * Test if the arrow is being stored in the way that is expected.
   */
  @Test // done
  public void testArrowPercentage() {
    // wrapping
    dungeon = new AdvancedDungeon(7, 5, true, 8, 25,
            4);
    int temp = arrowPercentageHelper(dungeon.getDungeon());
    assertEquals(0.20, (float) temp / 35, 0.2);
    assertNotEquals(0.19, (float) temp / 35, 0.0);

    // non-wrapping
    dungeon = new AdvancedDungeon(6, 6, false, 4, 21,
            5);
    temp = arrowPercentageHelper(dungeon.getDungeon());
    assertEquals(0.19, (float) temp / 36, 0.01);
  }

  private int arrowPercentageHelper(Cells[][] dungeon) {
    int arrowCount = 0;
    for (Cells[] cellList: dungeon) {
      for (Cells cell: cellList) {
        if (cell.getArrows() > 0) {
          arrowCount += 1;
        }
      }
    }
    System.out.println(arrowCount);
    return arrowCount;
  }

  /**
   * Test if the number of monsters in the dungeon are assigned as per the number provided by the
   * user.
   */
  @Test // done
  public void testOtyughNumbers() {
    // wrapping
    dungeon = new AdvancedDungeon(7, 5, true, 8, 25,
            4);
    assertEquals(4, otyughCounter(dungeon.getDungeon()));

    // non-wrapping
    dungeon = new AdvancedDungeon(6, 6, false, 4, 21,
            5);
    assertEquals(5, otyughCounter(dungeon.getDungeon()));
  }

  private int otyughCounter(Cells[][] dungeon) {
    int count = 0;
    for (Cells[] cellList: dungeon) {
      for (Cells cell: cellList) {
        if (cell.getOtyugh() != null) {
          count += 1;
        }
      }
    }
    return count;
  }


  /**
   * Test if the interconnectivity desired is achieved.
   */
  @Test // done
  public void testInterconnectivity() {
    // wrapping
    dungeon = new AdvancedDungeon(7, 5, true, 8, 25,
            4);
    assertEquals(42, interconnectivityHelper(dungeon.getDungeon()) / 2);

    // non-wrapping
    dungeon = new AdvancedDungeon(6, 6, false, 4, 21,
            5);
    assertEquals(39, interconnectivityHelper(dungeon.getDungeon()) / 2);
  }

  private int interconnectivityHelper(Cells[][] dungeonCopy) {
    int count = 0;
    for (Cells[] cellList: dungeonCopy) {
      for (Cells cell: cellList) {
        count += cell.getEntrances();
      }
    }
    return count;
  }

  /**
   * Test to check if caves and tunnels are assigned correctly depending on number of entrances.
   * AND
   * Test to check if treasure is only assigned to caves and not tunnels.
   */
  @Test // done
  public void testCavesTunnels() {
    // wrapping
    dungeon = new AdvancedDungeon(7, 5, true, 8, 25,
            4);
    Cells[][] dungeonCopy = dungeon.getDungeon();
    assertTrue(cavesTunnelsHelper(dungeonCopy));
    assertTrue(treasureOnlyInCaves(dungeonCopy));

    dungeon = new AdvancedDungeon(6, 6, false, 4, 21,
            5);
    dungeonCopy = dungeon.getDungeon();
    assertTrue(cavesTunnelsHelper(dungeonCopy));
    assertTrue(treasureOnlyInCaves(dungeonCopy));
  }

  private boolean cavesTunnelsHelper(Cells[][] dungeonCopy) {
    for (Cells[] cells : dungeonCopy) {
      for (int j = 0; j < dungeonCopy[0].length; j++) {
        if (cells[j].getEntrances() == 1 || cells[j].getEntrances() == 3
                || cells[j].getEntrances() == 4) {
          if (cells[j].getIsTunnel()) {
            return false;
          }
        } else if (cells[j].getEntrances() == 2) {
          if (cells[j].getIsCave()) {
            return false;
          }
        }
      }
    }
    return true;
  }

  private boolean treasureOnlyInCaves(Cells[][] dungeonCopy) {
    for (Cells[] cells : dungeonCopy) {
      for (Cells cell: cells) {
        if (cell.getIsTunnel()) {
          if (cell.getTreasureList().size() > 0) {
            return false;
          }
        }
      }
    }
    return true;
  }
  // end of class.
}
