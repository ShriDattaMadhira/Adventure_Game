import org.junit.Before;
import org.junit.Test;

import controller.AdvDungeonViewController;
import controller.AdvDungeonViewControllerImpl;

import static org.junit.Assert.assertEquals;

/**
 * Testing the UI Controller using Mock View and mock model.
 */
public class AdvDugeonViewControllerMockTest {

  private AdvDungeonViewController controller;
  private StringBuilder log;

  /**
   * Setting up the testing environment.
   */
  @Before
  public void setUp() {
    log = new StringBuilder();
    LoggingDungeonView view = new LoggingDungeonView(log);
    LoggingDungeonModel model = new LoggingDungeonModel(log);

    controller = new AdvDungeonViewControllerImpl(model, view);
  }

  /**
   * Testing the move method.
   */
  @Test
  public void testMove() {
    controller.playGame();

    // mocking keyboard inputs.
    String expected = "Controller added.\n"
            + "User interacted with the view.\n"
            + "Making the frame visible.\n"
            + "is game over?\n"
            + "Moving SOUTH\n"
            + "Refreshing the view.\n"
            + "is game over?\n"
            + "Moving NORTH\n"
            + "Refreshing the view.\n";
    controller.handleKeyboardInputs("south");
    controller.handleKeyboardInputs("north");
    assertEquals(expected, log.toString());

    // mocking mouse inputs.
    expected = "Controller added.\n"
            + "User interacted with the view.\n"
            + "Making the frame visible.\n"
            + "is game over?\n"
            + "Moving SOUTH\n"
            + "Refreshing the view.\n"
            + "is game over?\n"
            + "Moving NORTH\n"
            + "Refreshing the view.\n"
            + "Returning current player coordinates.\n"
            + "Returning width of the dungeon.\n"
            + "Returning height of the dungeon\n"
            + "Returning height of the dungeon\n"
            + "Refreshing the view.\n";
    controller.handleMouseInputs("move 4 2");
    assertEquals(expected, log.toString());

  }

  /**
   * Testing the shoot method.
   */
  @Test
  public void testShoot() {
    controller.playGame();

    String expected = "Controller added.\n"
            + "User interacted with the view.\n"
            + "Making the frame visible.\n"
            + "Shooting NORTH by 1\n"
            + "Refreshing the view.\n"
            + "Shooting NORTH by 2\n"
            + "Refreshing the view.\n"
            + "Shooting NORTH by 1\n"
            + "Refreshing the view.\n"
            + "is game over?\n"
            + "Moving NORTH\n"
            + "Refreshing the view.\n";

    controller.handleKeyboardInputs("shoot north 1");
    controller.handleKeyboardInputs("shoot north 2");
    controller.handleKeyboardInputs("shoot north 1");
    controller.handleKeyboardInputs("north");

    assertEquals(expected, log.toString());
  }

  /**
   * Testing the pick method.
   */
  @Test
  public void testPick() {
    controller.playGame();

    String expected = "Controller added.\n"
            + "User interacted with the view.\n"
            + "Making the frame visible.\n"
            + "Refreshing the view.\n"
            + "is game over?\n"
            + "Moving SOUTH\n"
            + "Refreshing the view.\n"
            + "is game over?\n"
            + "Moving SOUTH\n"
            + "Refreshing the view.\n"
            + "Refreshing the view.\n";

    controller.handleKeyboardInputs("pick");
    controller.handleKeyboardInputs("south");
    controller.handleKeyboardInputs("south");
    controller.handleKeyboardInputs("pick");

    assertEquals(expected, log.toString());
  }

  /**
   * Testing how a new game is created.
   */
  @Test
  public void testNewGame() {
    controller.playGame();

    String expected = "Controller added.\n"
            + "User interacted with the view.\n"
            + "Making the frame visible.\n"
            + "Disposing the frame.\n";
    controller.handleMouseInputs("new 10 10 true 30 20 10");
    assertEquals(expected, log.toString());

  }

  /**
   * Testing how the reset game is working.
   */
  @Test
  public void testResetGame() {
    controller.playGame();

    String expected = "Controller added.\n"
            + "User interacted with the view.\n"
            + "Making the frame visible.\n"
            + "Resetting the dungeon.\n"
            + "Resetting player.\n"
            + "Resetting visitors.\n"
            + "Refreshing the view.\n"
            + "Refreshing the view.\n";
    controller.handleMouseInputs("Reset");
    assertEquals(expected, log.toString());
  }
  // end of class.
}
