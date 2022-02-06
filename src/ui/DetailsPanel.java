package ui;

import dungeon.ReadOnlyDungeonModel;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

class DetailsPanel extends JPanel {

  private static final int WIDTH = 450;
  private static final int HEIGHT = 150;

  DetailsPanel(ReadOnlyDungeonModel game) {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setVisible(true);
    setPreferredSize(new Dimension(WIDTH, HEIGHT));

    add(new GameDetailsPanel(game));
    add(new InstructionsPanel());
  }
  // end of class
}
