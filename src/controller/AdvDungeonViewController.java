package controller;

import java.awt.event.ActionListener;

/**
 * Controls the User Interface for our dungeon game.
 */
public interface AdvDungeonViewController extends AdvDungeonConsoleController, ActionListener {

  /**
   * Handle all the mouse click events performed by the person playing the game.
   * @param action - action performed by the player.
   */
  void handleMouseInputs(String action);

  /**
   * Handle all the keyboard click events performed by the person playing the game.
   * @param action - action performed by the player.
   */
  void handleKeyboardInputs(String action);

}
