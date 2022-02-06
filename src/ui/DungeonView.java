package ui;

import controller.AdvDungeonViewController;

/**
 * Interface for Dungeon View.
 */
public interface DungeonView {

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  void addClickListener(AdvDungeonViewController listener);

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible(boolean visible);

}
