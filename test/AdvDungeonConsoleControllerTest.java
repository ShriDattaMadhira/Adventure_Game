import org.junit.Test;

import java.io.StringReader;

import controller.AdvDungeonConsoleController;
import controller.AdvDungeonConsoleControllerImpl;
import dungeon.AdvancedDungeon;
import dungeon.Dungeon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Here we conduct various tests on controller to see that the combination of controller and model
 * are working as expected for any given input.
 */
public class AdvDungeonConsoleControllerTest {
  private AdvDungeonConsoleController controller;

  /**
   * Test to check if the exception cases are handled properly.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArguments() {
    StringReader input = new StringReader("m south m south");
    StringBuilder gameLog = new StringBuilder();
    // width is invalid (less than or equal to zero).
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, new AdvancedDungeon(0,
            4, true, 3, 24, 4));

    // height is invalid (less than or equal to zero).
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, new AdvancedDungeon(7,
            0, true, 3, 24, 4));
    // width and height are too small.
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, new AdvancedDungeon(4,
            4, true, 3, 24, 4));
    // interconnectivity is invalid (less than zero).
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, new AdvancedDungeon(7,
            4, true, -1, 24, 4));
    // interconnectivity is greater than the number of edges that can be present.
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, new AdvancedDungeon(7,
            4, true, 26, 24, 4));
    // treasure percentage is invalid (less than zero).
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, new AdvancedDungeon(7,
            4, true, 3, -2, 4));
    // too less otyughs
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, new AdvancedDungeon(7,
            4, true, 3, 24, 0));
    // too many otyughs
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, new AdvancedDungeon(7,
            4, true, 3, 24, 100));
    // all good dungeon - wrapping.
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, new AdvancedDungeon(7,
            5, true, 8, 25, 4));
    // all good dungeon - non-wrapping.
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, new AdvancedDungeon(6,
            6, false, 4, 21, 5));
  }

  /**
   * Test if the game is moving the player correctly as per the inputs given.
   */
  @Test
  public void testMove() {
    // wrapping dungeon
    StringReader input = new StringReader("m east m north m south m west");
    StringBuilder gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, new AdvancedDungeon(7,
            5, true, 8, 25, 4));
    controller.playGame();
    String expected = "\nCurrent location: [0, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, NORTH, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? east\n\n"
            + "Current location: [0, 3]\n"
            + "This is a Cave.\n"
            + "There is treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, NORTH, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? north\n\n"
            + "Current location: [4, 3]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n\n"
            + "Current location: [0, 3]\n"
            + "This is a Cave.\n"
            + "There is treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, NORTH, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n\n"
            + "Current location: [0, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, NORTH, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - \n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    // This tests the following for a wrapping dungeon,
    //  1. Player is starting at the expected starting position - [0,2]
    //  2. Move East - [0,3]
    //  3. Move North - [4,3]
    //  4. Move South - [0,3]
    //  5. Move West - [0,2]
    assertEquals(expected, gameLog.toString());

    // non-wrapping dungeon
    input = new StringReader("m west m south m north m east");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, new AdvancedDungeon(6,
            6, false, 4, 21, 5));
    controller.playGame();
    expected = "\n"
            + "Current location: [0, 4]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n"
            + "\n"
            + "Current location: [0, 3]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n"
            + "\n"
            + "Current location: [1, 3]\n"
            + "This is a Tunnel.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to NORTH, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? north\n"
            + "\n"
            + "Current location: [0, 3]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? east\n"
            + "\n"
            + "Current location: [0, 4]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - \n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    // This tests the following for a non-wrapping dungeon,
    //  1. Player is starting at the expected starting position - [0,4]
    //  2. Move West - [0,3]
    //  3. Move South - [1,3]
    //  4. Move North - [0,3]
    //  5. Move East - [0,4]
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
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, new AdvancedDungeon(7,
            5, true, 8, 25, 4));
    controller.playGame();
    String expected = "\n"
            + "Current location: [0, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, NORTH, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? north\n"
            + "How far do you want the shoot the arrow? 2\n"
            + "\n"
            + "Current location: [0, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 2\n"
            + "Arrow Missed - Distance too far/close.\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, NORTH, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? north\n"
            + "How far do you want the shoot the arrow? 1\n"
            + "\n"
            + "Current location: [0, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 1\n"
            + "Nice Shot.\n"
            + "Nearby Otyugh Injured.\n"
            + "You can go to SOUTH, WEST, NORTH, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? north\n"
            + "How far do you want the shoot the arrow? 1\n"
            + "\n"
            + "Current location: [0, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 0\n"
            + "Nice Shot.\n"
            + "Nearby Otyugh Dead.\n"
            + "You can go to SOUTH, WEST, NORTH, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - \n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    // This test asserts that, for a wrapping dungeon,
    //  1. After shooting, player's arrow count decreases by 1.
    //  2. When shot with wrong distance but correct direction, nothing will happen.
    //  3. When shot in the correct direction and with correct distance.
    //        a. First shot, Otyugh gets injured.
    //        b. Second shot, Otyugh dies.
    assertEquals(expected, gameLog.toString());

    // non-wrapping
    input = new StringReader("s south 1 s south 1");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, new AdvancedDungeon(6,
            6, false, 4, 21, 5));
    controller.playGame();
    expected = "\n"
            + "Current location: [0, 4]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n"
            + "How far do you want the shoot the arrow? 1\n"
            + "\n"
            + "Current location: [0, 4]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 2\n"
            + "Nice Shot.\n"
            + "Nearby Otyugh Injured.\n"
            + "You can go to SOUTH, WEST, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n"
            + "How far do you want the shoot the arrow? 1\n"
            + "\n"
            + "Current location: [0, 4]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 1\n"
            + "Nice Shot.\n"
            + "Nearby Otyugh Dead.\n"
            + "You can go to SOUTH, WEST, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - \n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    // This test asserts that, for a non-wrapping dungeon,
    //  1. After shooting, player's arrow count decreases by 1.
    //  2. When shot with wrong distance but correct direction, nothing will happen.
    //  3. When shot in the correct direction and with correct distance,
    //        a. First shot, Otyugh gets injured.
    //        b. Second shot, Otyugh dies.
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
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, new AdvancedDungeon(7,
            5, true, 8, 25, 4));
    controller.playGame();
    String expected = "\n"
            + "Current location: [0, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, NORTH, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - p\n"
            + "Do you want to pick up treasure?[1/0] false\n"
            + "Do you want to pick up arrows?[1/0] true\n"
            + "\n"
            + "Current location: [0, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 8\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, NORTH, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n"
            + "\n"
            + "Current location: [1, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 8\n"
            + "Faint smell.\n"
            + "You can go to SOUTH, NORTH, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n"
            + "\n"
            + "Current location: [2, 2]\n"
            + "This is a Cave.\n"
            + "There is treasure here.\n"
            + "There are 5 arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 8\n"
            + "Faint smell.\n"
            + "You can go to SOUTH, WEST, NORTH, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - p\n"
            + "Do you want to pick up treasure?[1/0] true\n"
            + "Do you want to pick up arrows?[1/0] true\n"
            + "\n"
            + "Current location: [2, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {SAPPHIRE=1, DIAMONDS=2, RUBIES=3}\n"
            + "Current Arrow Count: 13\n"
            + "Faint smell.\n"
            + "You can go to SOUTH, WEST, NORTH, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - \n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());

    // non-wrapping
    input = new StringReader("p 0 1 m west m west m west p 1 1");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, new AdvancedDungeon(6,
            6, false, 4, 21, 5));
    controller.playGame();
    expected = "\n"
            + "Current location: [0, 4]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - p\n"
            + "Do you want to pick up treasure?[1/0] false\n"
            + "Do you want to pick up arrows?[1/0] true\n"
            + "\n"
            + "Current location: [0, 4]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 8\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n"
            + "\n"
            + "Current location: [0, 3]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 8\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n"
            + "\n"
            + "Current location: [0, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 8\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n"
            + "\n"
            + "Current location: [0, 1]\n"
            + "This is a Cave.\n"
            + "There is treasure here.\n"
            + "There are no arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 8\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - p\n"
            + "Do you want to pick up treasure?[1/0] true\n"
            + "Do you want to pick up arrows?[1/0] true\n"
            + "\n"
            + "Current location: [0, 1]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n"
            + "\n"
            + "\n"
            + "Current bag: {SAPPHIRE=1, DIAMONDS=2, RUBIES=3}\n"
            + "Current Arrow Count: 8\n"
            + "Pungent smell.\n"
            + "You can go to SOUTH, WEST, EAST\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - \n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());
  }

  /**
   * Test if the game is ending when the end/goal location is reached or when the player is eaten
   * by the monster(Otyugh).
   */
  @Test
  public void testGameEndScenarios() {
    //wrapping dungeon
    Dungeon wrap = new AdvancedDungeon(7, 5, true, 8,
            25, 4);
    // Player reaches the end location successfully without being killed
    StringReader input = new StringReader("p 0 1 m east m north m east s east 1 s east 1 "
            + "m east");
    StringBuilder gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, wrap);
    assertFalse(wrap.isGameOver());
    controller.playGame();
    assertTrue(wrap.isGameOver());

    // Player gets eaten by Otyugh at end location. Confirms presence of otyugh at end location.
    wrap = new AdvancedDungeon(7, 5, true, 8, 25,
            4);
    input = new StringReader("p 0 1 m east m north m east m east");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, wrap);
    System.out.println(wrap.isGameOver());
    assertFalse(wrap.isGameOver());
    assertFalse(wrap.getEaten());
    controller.playGame();
    assertTrue(wrap.getEaten());
    assertTrue(wrap.isGameOver());

    // Player gets eaten by Otyugh and game ends.
    wrap = new AdvancedDungeon(7, 5, true, 8,
            25, 4);
    input = new StringReader("m north");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, wrap);
    assertFalse(wrap.isGameOver());
    assertFalse(wrap.getEaten());
    controller.playGame();
    assertTrue(wrap.getEaten());
    assertTrue(wrap.isGameOver());

    //non-wrapping dungeon
    Dungeon nonWrap = new AdvancedDungeon(6, 6, false, 4,
            21, 5);
    // Player reaches the end location successfully without being killed
    input = new StringReader("p 0 1 m west m west m west m west m south m south s south 1"
            + "s south 1 m south m south m east s south 1 s south 1 m east");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, nonWrap);
    assertFalse(nonWrap.isGameOver());
    controller.playGame();
    assertTrue(nonWrap.isGameOver());

    // Player gets eaten by Otyugh and game ends.
    nonWrap = new AdvancedDungeon(6, 6, false, 4,
            21, 5);
    input = new StringReader("m south");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, nonWrap);
    assertFalse(nonWrap.isGameOver());
    assertFalse(nonWrap.getEaten());
    controller.playGame();
    assertTrue(nonWrap.getEaten());
    assertTrue(nonWrap.isGameOver());
  }

  /**
   * Test if player is getting correct hints in terms of smell when monsters are nearby.
   */
  @Test
  public void testSmell() {
    String expected = "";
    // wrapping
    Dungeon wrap = new AdvancedDungeon(7, 5, true, 8,
            25, 4);
    //  More pungent smell because of one monster in neighboring cells.
    StringReader input = new StringReader("");
    StringBuilder gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, wrap);
    controller.playGame();
    expected = "\nCurrent location: [0, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to WEST, EAST, NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - \n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());

    // Less pungent smell because of one monster at +2 distance from current cell.
    input = new StringReader("m south");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, wrap);
    controller.playGame();
    expected = "\nCurrent location: [0, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to WEST, EAST, NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n\n"
            + "Current location: [1, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Faint smell.\n"
            + "You can go to EAST, NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - \n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());

    // More pungent smell because of multiple monsters at +2 distance from current cell.
    input = new StringReader("m south p 1 1 m south s south 1 s south 1 m south m east m east");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, wrap);
    controller.playGame();
    expected = "\nCurrent location: [1, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Faint smell.\n"
            + "You can go to EAST, NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n\n"
            + "Current location: [2, 2]\n"
            + "This is a Cave.\n"
            + "There is treasure here.\n"
            + "There are 5 arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Faint smell.\n"
            + "You can go to WEST, EAST, NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - p\n"
            + "Do you want to pick up treasure?[1/0] true\n"
            + "Do you want to pick up arrows?[1/0] true\n\n"
            + "Current location: [2, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {SAPPHIRE=1, DIAMONDS=2, RUBIES=3}\n"
            + "Current Arrow Count: 8\n"
            + "Faint smell.\n"
            + "You can go to WEST, EAST, NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n\n"
            + "Current location: [3, 2]\n"
            + "This is a Tunnel.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n\n\n"
            + "Current bag: {SAPPHIRE=1, DIAMONDS=2, RUBIES=3}\n"
            + "Current Arrow Count: 8\n"
            + "Pungent smell.\n"
            + "You can go to NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n"
            + "How far do you want the shoot the arrow? 1\n\n"
            + "Current location: [3, 2]\n"
            + "This is a Tunnel.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n\n\n"
            + "Current bag: {SAPPHIRE=1, DIAMONDS=2, RUBIES=3}\n"
            + "Current Arrow Count: 7\n"
            + "Nice Shot.\n"
            + "Nearby Otyugh Injured.\n"
            + "You can go to NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n"
            + "How far do you want the shoot the arrow? 1\n\n"
            + "Current location: [3, 2]\n"
            + "This is a Tunnel.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n\n\n"
            + "Current bag: {SAPPHIRE=1, DIAMONDS=2, RUBIES=3}\n"
            + "Current Arrow Count: 6\n"
            + "Nice Shot.\n"
            + "Nearby Otyugh Dead.\n"
            + "You can go to NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n\n"
            + "Current location: [4, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 10 arrows here.\n\n\n"
            + "Current bag: {SAPPHIRE=1, DIAMONDS=2, RUBIES=3}\n"
            + "Current Arrow Count: 6\n"
            + "Nice Shot.\n"
            + "Otyugh in this cave is dead.\n"
            + "Pungent smell.\n"
            + "You can go to EAST, NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? east\n\n"
            + "Current location: [4, 3]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {SAPPHIRE=1, DIAMONDS=2, RUBIES=3}\n"
            + "Current Arrow Count: 6\n"
            + "Nice Shot.\n"
            + "Nearby Otyugh Dead.\n"
            + "You can go to WEST, EAST, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? east\n\n"
            + "Current location: [4, 4]\n"
            + "This is a Cave.\n"
            + "There is treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {SAPPHIRE=1, DIAMONDS=2, RUBIES=3}\n"
            + "Current Arrow Count: 6\n"
            + "Nice Shot.\n"
            + "Pungent smell.\n"
            + "You can go to WEST, EAST, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - \n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());

    // non-wrapping
    Dungeon nonWrap = new AdvancedDungeon(6, 6, false, 4,
            21, 5);
    //  More pungent smell because of one monster in neighboring cells.
    input = new StringReader("");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, nonWrap);
    controller.playGame();
    expected = "\nCurrent location: [0, 4]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to WEST, EAST, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - \n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());

    // Less pungent smell because of one monster at +2 distance from current cell.
    input = new StringReader("m west m west m west m west m south m south s south 1 s south 1");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, nonWrap);
    controller.playGame();
    expected = "\nCurrent location: [0, 4]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to WEST, EAST, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n\n"
            + "Current location: [0, 3]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to WEST, EAST, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n\n"
            + "Current location: [0, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to WEST, EAST, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n\n"
            + "Current location: [0, 1]\n"
            + "This is a Cave.\n"
            + "There is treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to WEST, EAST, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? west\n\n"
            + "Current location: [0, 0]\n"
            + "This is a Tunnel.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to EAST, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n\n"
            + "Current location: [1, 0]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n"
            + "Encountered a Thief. Your bag is stolen.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to EAST, NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n\n"
            + "Current location: [2, 0]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to EAST, NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n"
            + "How far do you want the shoot the arrow? 1\n\n"
            + "Current location: [2, 0]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 2\n"
            + "Nice Shot.\n"
            + "Nearby Otyugh Injured.\n"
            + "You can go to EAST, NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n"
            + "How far do you want the shoot the arrow? 1\n\n"
            + "Current location: [2, 0]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 1\n"
            + "Nice Shot.\n"
            + "Nearby Otyugh Dead.\n"
            + "You can go to EAST, NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - \n"
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
    Dungeon wrap = new AdvancedDungeon(7, 5, true, 8,
            25, 4);
    //  Encounters thief, bag gets stolen
    StringReader input = new StringReader("m south m south p 1 1 m east m south");
    StringBuilder gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, wrap);
    controller.playGame();
    expected = "\nCurrent location: [0, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are 5 arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Pungent smell.\n"
            + "You can go to WEST, EAST, NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n\n"
            + "Current location: [1, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Faint smell.\n"
            + "You can go to EAST, NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n\n"
            + "Current location: [2, 2]\n"
            + "This is a Cave.\n"
            + "There is treasure here.\n"
            + "There are 5 arrows here.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 3\n"
            + "Faint smell.\n"
            + "You can go to WEST, EAST, NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - p\n"
            + "Do you want to pick up treasure?[1/0] true\n"
            + "Do you want to pick up arrows?[1/0] true\n\n"
            + "Current location: [2, 2]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {SAPPHIRE=1, DIAMONDS=2, RUBIES=3}\n"
            + "Current Arrow Count: 8\n"
            + "Faint smell.\n"
            + "You can go to WEST, EAST, NORTH, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? east\n\n"
            + "Current location: [2, 3]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n\n\n"
            + "Current bag: {SAPPHIRE=1, DIAMONDS=2, RUBIES=3}\n"
            + "Current Arrow Count: 8\n"
            + "No Otyughs Nearby.\n"
            + "You can go to WEST, EAST, SOUTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n\n"
            + "Current location: [3, 3]\n"
            + "This is a Cave.\n"
            + "There is no treasure here.\n"
            + "There are no arrows here.\n"
            + "Encountered a Thief. Your bag is stolen.\n\n\n"
            + "Current bag: {}\n"
            + "Current Arrow Count: 8\n"
            + "No Otyughs Nearby.\n"
            + "You can go to NORTH\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - \n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());
  }

  /**
   * Test how the controller reacts to invalid inputs for action(move, shoot, pickup),
   * direction(north, south, east, west), and distance(only > 0).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInputs() {
    String expected = "";

    // passing a null for dungeon.
    StringReader input = new StringReader("n south [ 1 1 d south 1 m south p 1 1 s south 2");
    StringBuilder gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, null);
    controller.playGame();

    // wrapping
    Dungeon wrap = new AdvancedDungeon(7, 5, true, 8,
            25, 4);
    //  Invalid action.
    input = new StringReader("n south [ 1 1 d south 1 m south p 1 1 s south 2");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, wrap);
    controller.playGame();
    expected = "\n" + "Current location: [0, 2]. This is a Cave.\n"
            + "There is no treasure here. There are 5 arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 3\n" + "More pungent Smell. Otyugh in very close proximity.\n"
            + "You can go WEST, NORTH, SOUTH, EAST from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - n\n"
            + "---Invalid choice of action - n. Choose Again.---\n"
            + "Current location: [0, 2]. This is a Cave.\n"
            + "There is no treasure here. There are 5 arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 3\n" + "More pungent Smell. Otyugh in very close proximity.\n"
            + "You can go WEST, NORTH, SOUTH, EAST from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - south\n"
            + "---Invalid choice of action - south. Choose Again.---\n"
            + "Current location: [0, 2]. This is a Cave.\n"
            + "There is no treasure here. There are 5 arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 3\n" + "More pungent Smell. Otyugh in very close proximity.\n"
            + "You can go WEST, NORTH, SOUTH, EAST from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - [\n"
            + "---Invalid choice of action - [. Choose Again.---\n"
            + "Current location: [0, 2]. This is a Cave.\n"
            + "There is no treasure here. There are 5 arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 3\n" + "More pungent Smell. Otyugh in very close proximity.\n"
            + "You can go WEST, NORTH, SOUTH, EAST from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - 1\n"
            + "---Invalid choice of action - 1. Choose Again.---\n"
            + "Current location: [0, 2]. This is a Cave.\n"
            + "There is no treasure here. There are 5 arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 3\n" + "More pungent Smell. Otyugh in very close proximity.\n"
            + "You can go WEST, NORTH, SOUTH, EAST from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - 1\n"
            + "---Invalid choice of action - 1. Choose Again.---\n"
            + "Current location: [0, 2]. This is a Cave.\n"
            + "There is no treasure here. There are 5 arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 3\n" + "More pungent Smell. Otyugh in very close proximity.\n"
            + "You can go WEST, NORTH, SOUTH, EAST from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - d\n"
            + "---Invalid choice of action - d. Choose Again.---\n"
            + "Current location: [0, 2]. This is a Cave.\n"
            + "There is no treasure here. There are 5 arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 3\n" + "More pungent Smell. Otyugh in very close proximity.\n"
            + "You can go WEST, NORTH, SOUTH, EAST from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - south\n"
            + "---Invalid choice of action - south. Choose Again.---\n"
            + "Current location: [0, 2]. This is a Cave.\n"
            + "There is no treasure here. There are 5 arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 3\n" + "More pungent Smell. Otyugh in very close proximity.\n"
            + "You can go WEST, NORTH, SOUTH, EAST from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - 1\n"
            + "---Invalid choice of action - 1. Choose Again.---\n"
            + "Current location: [0, 2]. This is a Cave.\n"
            + "There is no treasure here. There are 5 arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 3\n" + "More pungent Smell. Otyugh in very close proximity.\n"
            + "You can go WEST, NORTH, SOUTH, EAST from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n" + "\n" + "Current location: [1, 2]. This is a Cave.\n"
            + "There is no treasure here. There are no arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 3\n" + "Less pungent smell. Otyugh in close proximity.\n"
            + "You can go SOUTH, EAST, NORTH from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - p\n"
            + "Do you want to pick up treasure?[1/0] true\n"
            + "Do you want to pick up arrows?[1/0] true\n" + "\n"
            + "Current location: [1, 2]. This is a Cave.\n"
            + "There is no treasure here. There are no arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 3\n" + "Less pungent smell. Otyugh in close proximity.\n"
            + "You can go SOUTH, EAST, NORTH from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n" + "How far do you want the shoot the arrow? 2\n" + "\n"
            + "Current location: [1, 2]. This is a Cave.\n"
            + "There is no treasure here. There are no arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 2\n" + "Less pungent smell. Otyugh in close proximity.\n"
            + "You can go SOUTH, EAST, NORTH from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - \n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());

    // invalid direction
    input = new StringReader("m pdp");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, wrap);
    controller.playGame();
    expected = "\nCurrent location: [1, 2]. This is a Cave.\n"
            + "There is no treasure here. There are no arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 2\n" + "Less pungent smell. Otyugh in close proximity.\n"
            + "You can go SOUTH, EAST, NORTH from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? pdp\n"
            + "------Direction can only be North/South/East/West.------\n" + "\n"
            + "Current location: [1, 2]. This is a Cave.\n"
            + "There is no treasure here. There are no arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 2\n" + "Less pungent smell. Otyugh in close proximity.\n"
            + "You can go SOUTH, EAST, NORTH from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - \n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());

    // invalid distance.
    input = new StringReader("m south s south one s south -2");
    gameLog = new StringBuilder();
    controller = new AdvDungeonConsoleControllerImpl(input, gameLog, wrap);
    controller.playGame();
    expected = "\n" + "Current location: [1, 2]. This is a Cave.\n"
            + "There is no treasure here. There are no arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 2\n" + "Less pungent smell. Otyugh in close proximity.\n"
            + "You can go SOUTH, EAST, NORTH from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - m\n"
            + "Which direction? south\n" + "\n" + "Current location: [2, 2]. This is a Cave.\n"
            + "There is treasure here. There are 5 arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 2\n" + "Less pungent smell. Otyugh in close proximity.\n"
            + "You can go SOUTH, WEST, EAST, NORTH from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n"
            + "How far do you want the shoot the arrow? ---Give numerical digits---\n" + "\n"
            + "Current location: [2, 2]. This is a Cave.\n"
            + "There is treasure here. There are 5 arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 2\n" + "Less pungent smell. Otyugh in close proximity.\n"
            + "You can go SOUTH, WEST, EAST, NORTH from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - s\n"
            + "Which direction? south\n" + "How far do you want the shoot the arrow? -2\n"
            + "---Can't shoot. Distance too low.---\n" + "\n"
            + "Current location: [2, 2]. This is a Cave.\n"
            + "There is treasure here. There are 5 arrows here.\n" + "Current bag: {}\n"
            + "Current Arrow Count: 2\n" + "Less pungent smell. Otyugh in close proximity.\n"
            + "You can go SOUTH, WEST, EAST, NORTH from here.\n" + "\n"
            + "What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - \n"
            + "Player is not eaten. Player did not reach end location. But input ended.\n";
    assertEquals(expected, gameLog.toString());
  }
  // end of class
}
