import org.junit.Test;

import java.io.StringReader;

import controller.AdvDungeonConsoleController;
import controller.AdvDungeonConsoleControllerImpl;
import dungeon.AdvancedDungeon;
import dungeon.Dungeon;

import static org.junit.Assert.assertEquals;

/**
 * Testing the console controller using a Mock model.
 */
public class AdvDungeonConsoleControllerMockTest {

  private AdvDungeonConsoleController controller;

  /**
   * Test if the game is moving the player correctly as per the inputs given.
   */
  @Test
  public void testMove() {
    // wrapping dungeon
    StringReader input = new StringReader("m east m north m south m west");
    StringBuilder gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog,
            new LoggingDungeonModel(gameLog));
    controller.playGame();
    String expected = "is game over?\n\nReturning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? east\n"
            + "is game over?\n"
            + "Moving EAST\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? north\n"
            + "is game over?\n"
            + "Moving NORTH\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n"
            + "is game over?\n"
            + "Moving SOUTH\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n"
            + "is game over?\n"
            + "Moving WEST\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) "
            + "- is game over?\n\n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());

    // non-wrapping dungeon
    input = new StringReader("m west m south m north m east");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog,
            new LoggingDungeonModel(gameLog));
    controller.playGame();
    expected = "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n"
            + "is game over?\nMoving WEST\nis game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\nis game over?\nMoving SOUTH\nis game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? north\nis game over?\nMoving NORTH\nis game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? east\nis game over?\nMoving EAST\nis game over?\n\n"
            + "Returning game details.\n\nReturning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) "
            + "- is game over?\n\n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());
  }

  /**
   * Test if the game is shooting the monster correctly as per the inputs given by the user.
   */
  @Test
  public void testShoot() {
    // wrapping
    StringReader input = new StringReader("s north 2 s north 1 s north 1");
    StringBuilder gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog,
            new LoggingDungeonModel(gameLog));
    controller.playGame();
    String expected = "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? north\n"
            + "How far do you want the shoot the arrow? 2\n"
            + "Shooting NORTH by 2\nis game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? north\n"
            + "How far do you want the shoot the arrow? 1\n"
            + "Shooting NORTH by 1\nis game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? north\n"
            + "How far do you want the shoot the arrow? 1\n"
            + "Shooting NORTH by 1\nis game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P)"
            + " - is game over?\n\n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());

    // non-wrapping
    input = new StringReader("s south 1 s south 1");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog,
            new LoggingDungeonModel(gameLog));
    controller.playGame();
    expected = "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n"
            + "How far do you want the shoot the arrow? 1\n"
            + "Shooting SOUTH by 1\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n"
            + "How far do you want the shoot the arrow? 1\n"
            + "Shooting SOUTH by 1\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P)"
            + " - is game over?\n\n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());
  }

  /**
   * Test if the game is allowing the player to pickup treasure and arrows and is updating the
   * information as expected.
   */
  @Test
  public void testPick() {
    // wrapping
    StringReader input = new StringReader("p 0 1 m south m south p 1 1");
    StringBuilder gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog,
            new LoggingDungeonModel(gameLog));
    controller.playGame();
    String expected = "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - p\n"
            + "Do you want to pick up treasure?[1/0] false\n"
            + "Do you want to pick up arrows?[1/0] true\n"
            + "Picking Arrows.\n"
            + "is game over?\n\nReturning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n"
            + "is game over?\nMoving SOUTH\nis game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n"
            + "is game over?\nMoving SOUTH\nis game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - p\n"
            + "Do you want to pick up treasure?[1/0] true\n"
            + "Do you want to pick up arrows?[1/0] true\n"
            + "Picking Treasure.\nPicking Arrows.\nis game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P)"
            + " - is game over?\n\n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());

    // non-wrapping
    input = new StringReader("p 0 1 m west m west m west p 1 1");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog,
            new LoggingDungeonModel(gameLog));
    controller.playGame();
    expected = "is game over?\n\n"
            + "Returning game details.\n"
            + "\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - p\n"
            + "Do you want to pick up treasure?[1/0] false\n"
            + "Do you want to pick up arrows?[1/0] true\n"
            + "Picking Arrows.\n"
            + "is game over?\n"
            + "\n"
            + "Returning game details.\n"
            + "\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n"
            + "is game over?\n"
            + "Moving WEST\n"
            + "is game over?\n"
            + "\n"
            + "Returning game details.\n"
            + "\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n"
            + "is game over?\n"
            + "Moving WEST\n"
            + "is game over?\n"
            + "\n"
            + "Returning game details.\n"
            + "\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n"
            + "is game over?\n"
            + "Moving WEST\n"
            + "is game over?\n"
            + "\n"
            + "Returning game details.\n"
            + "\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - p\n"
            + "Do you want to pick up treasure?[1/0] true\n"
            + "Do you want to pick up arrows?[1/0] true\n"
            + "Picking Treasure.\n"
            + "Picking Arrows.\n"
            + "is game over?\n"
            + "\n"
            + "Returning game details.\n"
            + "\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) "
            + "- is game over?\n\n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());
  }

  /**
   * Test if player is getting correct hints in terms of smell when monsters are nearby.
   */
  @Test
  public void testSmell() {
    String expected = "";
    // wrapping
    //  More pungent smell because of one monster in neighboring cells.
    StringReader input = new StringReader("");
    StringBuilder gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog,
            new LoggingDungeonModel(gameLog));
    controller.playGame();
    expected = "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P)"
            + " - is game over?\n\n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());

    // Less pungent smell because of one monster at +2 distance from current cell.
    input = new StringReader("m south");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog,
            new LoggingDungeonModel(gameLog));
    controller.playGame();
    expected = "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\nis game over?\nMoving SOUTH\nis game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P)"
            + " - is game over?\n\n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());

    // More pungent smell because of multiple monsters at +2 distance from current cell.
    input = new StringReader("m south p 1 1 m south s south 1 s south 1 m south m east m east");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog,
            new LoggingDungeonModel(gameLog));
    controller.playGame();
    expected = "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n"
            + "is game over?\n"
            + "Moving SOUTH\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - p\n"
            + "Do you want to pick up treasure?[1/0] true\n"
            + "Do you want to pick up arrows?[1/0] true\n"
            + "Picking Treasure.\n"
            + "Picking Arrows.\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n"
            + "is game over?\n"
            + "Moving SOUTH\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n"
            + "How far do you want the shoot the arrow? 1\n"
            + "Shooting SOUTH by 1\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n"
            + "How far do you want the shoot the arrow? 1\n"
            + "Shooting SOUTH by 1\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n"
            + "is game over?\n"
            + "Moving SOUTH\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? east\n"
            + "is game over?\n"
            + "Moving EAST\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? east\n"
            + "is game over?\n"
            + "Moving EAST\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P)"
            + " - is game over?\n\n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());

    // non-wrapping
    //  More pungent smell because of one monster in neighboring cells.
    input = new StringReader("");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog,
            new LoggingDungeonModel(gameLog));
    controller.playGame();
    expected = "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) "
            + "- is game over?\n\n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());

    // Less pungent smell because of one monster at +2 distance from current cell.
    input = new StringReader("m west m west m west m west m south m south s south 1 s south 1");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog,
            new LoggingDungeonModel(gameLog));
    controller.playGame();
    expected = "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n"
            + "is game over?\n"
            + "Moving WEST\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n"
            + "is game over?\n"
            + "Moving WEST\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n"
            + "is game over?\n"
            + "Moving WEST\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n"
            + "is game over?\n"
            + "Moving WEST\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n"
            + "is game over?\n"
            + "Moving SOUTH\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n"
            + "is game over?\n"
            + "Moving SOUTH\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n"
            + "How far do you want the shoot the arrow? 1\n"
            + "Shooting SOUTH by 1\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n"
            + "How far do you want the shoot the arrow? 1\n"
            + "Shooting SOUTH by 1\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P)"
            + " - is game over?\n\n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());
  }

  /**
   * Test if the thief is stealing your bag when you encounter him.
   */
  @Test
  public void testThief() {
    String expected;
    // wrapping
    //  Encounters thief, bag gets stolen
    StringReader input = new StringReader("m south m south p 1 1 m east m south");
    StringBuilder gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog,
            new LoggingDungeonModel(gameLog));
    controller.playGame();
    expected = "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n"
            + "is game over?\n"
            + "Moving SOUTH\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n"
            + "is game over?\n"
            + "Moving SOUTH\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - p\n"
            + "Do you want to pick up treasure?[1/0] true\n"
            + "Do you want to pick up arrows?[1/0] true\n"
            + "Picking Treasure.\n"
            + "Picking Arrows.\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? east\n"
            + "is game over?\n"
            + "Moving EAST\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n"
            + "is game over?\n"
            + "Moving SOUTH\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P)"
            + " - is game over?\n\n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());
  }

  /**
   * Test how the controller reacts to invalid inputs for action(move, shoot, pickup),
   * direction(north, south, east, west), and distance(only > 0).
   */
  @Test
  public void testInvalidInputs() {
    String expected = "";

    // wrapping
    Dungeon wrap = new AdvancedDungeon(7, 5, true, 8,
            25, 4);
    //  Invalid action.
    StringReader input = new StringReader("n south [ 1 1 d south 1 m south p 1 1 s south 2");
    StringBuilder gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog,
            new LoggingDungeonModel(gameLog));
    controller.playGame();
    expected = "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - n\n"
            + "---Invalid choice of action - n. Choose Again.---is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - south\n"
            + "---Invalid choice of action - south. Choose Again.---is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - [\n"
            + "---Invalid choice of action - [. Choose Again.---is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - 1\n"
            + "---Invalid choice of action - 1. Choose Again.---is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - 1\n"
            + "---Invalid choice of action - 1. Choose Again.---is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - d\n"
            + "---Invalid choice of action - d. Choose Again.---is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - south\n"
            + "---Invalid choice of action - south. Choose Again.---is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - 1\n"
            + "---Invalid choice of action - 1. Choose Again.---is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n"
            + "is game over?\n"
            + "Moving SOUTH\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - p\n"
            + "Do you want to pick up treasure?[1/0] true\n"
            + "Do you want to pick up arrows?[1/0] true\n"
            + "Picking Treasure.\n"
            + "Picking Arrows.\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n"
            + "How far do you want the shoot the arrow? 2\n"
            + "Shooting SOUTH by 2\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) "
            + "- is game over?\n\n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());

    // invalid direction
    input = new StringReader("m pdp");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog,
            new LoggingDungeonModel(gameLog));
    controller.playGame();
    expected = "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? pdp\n"
            + "------Direction can only be North/South/East/West.------\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) "
            + "- is game over?\n\n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());

    // invalid distance.
    input = new StringReader("m south s south one s south -2");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog,
            new LoggingDungeonModel(gameLog));
    controller.playGame();
    expected = "is game over?\n\n"
            + "Returning game details.\n"
            + "\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n"
            + "is game over?\n"
            + "Moving SOUTH\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n"
            + "How far do you want the shoot the arrow? ---Give numerical digits---\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n"
            + "How far do you want the shoot the arrow? -2\n"
            + "Can't shoot. Distance too low.\n"
            + "Shooting SOUTH by -2\n"
            + "is game over?\n\n"
            + "Returning game details.\n\n"
            + "Returning player details.\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P)"
            + " - is game over?\n\n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());
  }
  // end of class
}
